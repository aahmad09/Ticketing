package models

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{Future, ExecutionContext}
import models.Tables._

class TicketModel(db: Database)(implicit ec: ExecutionContext) {

  // Generate a ticket for a user for an event
  def generateTicket(eventId: Int, userId: Int): Future[Option[TicketData]] = {
    // Generate a QR code (placeholder logic)
    val qrCode = "Generated-QR-Code-Here"

    val ticketRow = TicketsRow(-1, userId, eventId, qrCode)
    val insertQuery = (Tickets returning Tickets.map(_.ticketid)) += ticketRow

    db.run(insertQuery).map { ticketId =>
      Some(TicketData(Some(ticketId), userId, eventId, qrCode))
    }.recover {
      case ex: Exception =>
        println(s"Error generating ticket: ${ex.getMessage}")
        None
    }
  }

  // Register a user for an event and create a ticket
  def registerForEvent(eventId: Int, userId: Int): Future[Option[TicketData]] =
    generateTicket(eventId, userId)

  // Retrieve a specific ticket
  def getTicket(ticketId: Int): Future[Option[TicketData]] = {
    val query = Tickets.filter(_.ticketid === ticketId).result.headOption

    db.run(query).map(_.map { row =>
      TicketData(Some(row.ticketid), row.userid, row.eventid, row.qrcode)
    })
  }

  // View all tickets for a specific user
  def getUserTickets(userId: Int): Future[Seq[TicketData]] = {
    val query = Tickets.filter(_.userid === userId).result

    db.run(query).map(_.map { row =>
      TicketData(Some(row.ticketid), row.userid, row.eventid, row.qrcode)
    })
  }

  // Cancel or invalidate a ticket
  def cancelTicket(ticketId: Int): Future[Boolean] = {
    val updateQuery = Tickets.filter(_.ticketid === ticketId)
                             .map(_.qrcode)
                             .update("Cancelled-QR-Code")
    db.run(updateQuery).map(_ > 0)
  }

  // Additional methods as needed for waivers, event details, etc.
}
