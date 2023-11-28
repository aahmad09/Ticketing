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
  def createUser(
      name: String,
      email: String,
      password: String,
      role: Option[String],
      profilePic: Option[String]
  ): Future[Option[Int]] = {
    val newUser = UsersRow(-1, name, email, role, password, profilePic)
    val insertQuery = (Users returning Users.map(_.userid)) += newUser
    val existingUserQuery = Users.filter(_.email === email).exists.result

    db.run(existingUserQuery).flatMap {
      case true => Future.successful(None) // User already exists
      case false =>
        db.run(insertQuery).map(Some(_)) // Wraps the returned ID in an Option
    }
  }

  // Get user by ID
  def getUserById(userId: Int): Future[Option[UsersRow]] = {
    db.run(Users.filter(_.userid === userId).result.headOption)
  }

  // Update user details
  def updateUser(
      userId: Int,
      name: String,
      email: String,
      password: String,
      role: Option[String],
      profilePic: Option[String]
  ): Future[Boolean] = {
    val updateQuery = Users
      .filter(_.userid === userId)
      .map(user =>
        (user.name, user.email, user.password, user.role, user.profilepic)
      )
      .update((name, email, password, role, profilePic))

    db.run(updateQuery).map(_ > 0)
  }

  // Delete a user
  def deleteUser(userId: Int): Future[Boolean] = {
    db.run(Users.filter(_.userid === userId).delete).map(_ > 0)
  }

  def getAllUsers(): Future[Seq[UsersRow]] = {
    db.run(Users.result)
  }

  // Additional methods as needed

}
