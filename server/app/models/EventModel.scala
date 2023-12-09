package models

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import models.Tables._

class EventModel(db: Database)(implicit ec: ExecutionContext) {

  // List all events
  def getAllEvents(): Future[Seq[EventData]] = {
    db.run(Events.result).map(_.map(eventRowToData))
  }

  // Create a new event
  def createEvent(eventData: EventData): Future[Int] = {
    val newEventRow = EventsRow(0, eventData.orgId, eventData.name, eventData.date, eventData.location, eventData.description, eventData.image)
    val insertQuery = (Events returning Events.map(_.eventid)) += newEventRow
    db.run(insertQuery)
  }

  // Get event by ID
  def getEventById(eventId: Int): Future[Option[EventData]] = {
    val query = Events.filter(_.eventid === eventId).result.headOption
    db.run(query).map(_.map(eventRowToData))
  }

  // Update event
  def updateEvent(eventId: Int, eventData: EventData): Future[Boolean] = {
    val updateQuery = Events.filter(_.eventid === eventId)
                            .map(event => (event.name, event.date, event.location, event.description, event.image))
                            .update((eventData.name, eventData.date, eventData.location, eventData.description, eventData.image))
    db.run(updateQuery).map(_ == 1)
  }

  // Delete event
  def deleteEvent(eventId: Int): Future[Boolean] = {
    val query = Events.filter(_.eventid === eventId).delete
    db.run(query).map(_ > 0)
  }

  // Helper method to convert EventsRow to EventData
  private def eventRowToData(row: EventsRow): EventData = {
    EventData(Some(row.eventid), row.orgid, row.name, row.date, row.location, row.description, row.image)
  }

  // Additional methods...
}
