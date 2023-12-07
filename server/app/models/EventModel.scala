package models

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import models.Tables._

class EventModel(db: Database)(implicit ec: ExecutionContext) {

  // Get all events
  def getAllEvents(): Future[Seq[EventsRow]] = {
    db.run(Events.result)
  }

  // Create a new event
  def createEvent(event: EventsRow): Future[Int] = {
    val insertQuery = (Events returning Events.map(_.eventid)) += event
    db.run(insertQuery)
  }

  // Get a specific event by ID
  def getEventById(eventId: Int): Future[Option[EventsRow]] = {
    db.run(Events.filter(_.eventid === eventId).result.headOption)
  }

  // Get attendees for a specific event
  def getEventAttendees(eventId: Int): Future[Seq[UsersRow]] = {
    val query = for {
      ea <- Eventattendees if ea.eventid === eventId
      u <- Users if u.userid === ea.userid
    } yield u
    db.run(query.result)
  }

  // Register a user for an event
  def registerUserForEvent(userId: Int, eventId: Int): Future[Boolean] = {
    val newAttendee = EventattendeesRow(eventId, userId)
    db.run(Eventattendees += newAttendee).map(_ > 0)
  }

  // Unregister a user from an event
  def unregisterUserFromEvent(userId: Int, eventId: Int): Future[Boolean] = {
    val query = Eventattendees.filter(ea => ea.userid === userId && ea.eventid === eventId).delete
    db.run(query).map(_ > 0)
  }

  // Update event details
  def updateEvent(eventId: Int, updatedEvent: EventsRow): Future[Boolean] = {
    val updateQuery = Events.filter(_.eventid === eventId)
      .map(event => (event.name, event.date, event.location, event.description, event.image))
      .update((updatedEvent.name, updatedEvent.date, updatedEvent.location, updatedEvent.description, updatedEvent.image))
    db.run(updateQuery).map(_ == 1)
  }

  // Delete an event
  def deleteEvent(eventId: Int): Future[Boolean] = {
    val query = Events.filter(_.eventid === eventId).delete
    db.run(query).map(_ > 0)
  }

}
