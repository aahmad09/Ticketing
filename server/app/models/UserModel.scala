package models

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import models.Tables._

class UserModel(db: Database)(implicit ec: ExecutionContext) {

  // Validate user credentials
  def validateUser(email: String, password: String): Future[Option[Int]] = {
    val query = Users
      .filter(user => user.email === email && user.password === password)
      .result
      .headOption
    db.run(query).map(_.map(_.userid))
  }

  // Create a new user
  def createUser(userData: UserData): Future[Option[Int]] = {
    val newUserRow = UsersRow(-1, userData.name, userData.email, userData.role, userData.password)
    val insertQuery = (Users returning Users.map(_.userid)) += newUserRow
    val existingUserQuery = Users.filter(_.email === userData.email).exists.result

    db.run(existingUserQuery).flatMap {
      case true => Future.successful(None) // User already exists
      case false => db.run(insertQuery).map(Some(_)) // Wrap the returned ID in an Option
    }
  }

  // Get user by ID
  def getUserById(userId: Int): Future[Option[UserData]] = {
    val query = Users.filter(_.userid === userId).result.headOption
    db.run(query).map(_.map(userRow => 
      UserData(Some(userRow.userid), userRow.name, userRow.email, userRow.password, userRow.role)))
  }

  // Update user details
  def updateUser(userId: Int, userData: UserData): Future[Boolean] = {
    val updateQuery = Users
      .filter(_.userid === userId)
      .map(user => (user.name, user.email, user.password, user.role))
      .update((userData.name, userData.email, userData.password, userData.role))

    db.run(updateQuery).map(_ > 0)
  }

  // Delete a user
  def deleteUser(userId: Int): Future[Boolean] = {
    db.run(Users.filter(_.userid === userId).delete).map(_ > 0)
  }

  // List all users
  def getAllUsers(): Future[Seq[UserData]] = {
    db.run(Users.result).map(_.map(userRow => 
      UserData(Some(userRow.userid), userRow.name, userRow.email, userRow.password, userRow.role)))
  }

  // Register user for an event
  def registerForEvent(userId: Int, eventId: Int): Future[Boolean] = {
    val newAttendee = EventattendeesRow(eventId, userId)
    db.run(Eventattendees += newAttendee).map(_ > 0)
  }

  // Get user tickets
  def getUserTickets(userId: Int): Future[Seq[TicketData]] = {
    val query = Tickets.filter(_.userid === userId).result
    db.run(query).map(_.map(ticketRow => 
      TicketData(Some(ticketRow.ticketid), ticketRow.userid, ticketRow.eventid, ticketRow.qrcode)))
  }

    // Update user details (serving as preferences)
  def updateUserPreferences(userId: Int, updatedUserData: UserData): Future[Boolean] = {
    val updateQuery = Users
      .filter(_.userid === userId)
      .map(user => (user.name, user.email, user.role, user.password))
      .update((updatedUserData.name, updatedUserData.email, updatedUserData.role, updatedUserData.password))

    db.run(updateQuery).map(_ > 0)
  }
  
}