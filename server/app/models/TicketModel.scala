package models

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{Future, ExecutionContext}
import models.Tables._
import com.google.zxing.{BarcodeFormat, EncodeHintType}
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import java.io.ByteArrayOutputStream
import java.util.Base64
import java.util.{EnumMap, Map}

class TicketModel(db: Database)(implicit ec: ExecutionContext) {

  // Generate a ticket for a user for an event
  def generateTicket(eventId: Int, userId: Int): Future[Option[TicketData]] = {
    try {
      val qrCodeData = s"EventID: $eventId, UserID: $userId"
      val hints = new EnumMap[EncodeHintType, Object](classOf[EncodeHintType])
      hints.put(EncodeHintType.CHARACTER_SET, "UTF-8")

      val qrCodeWriter = new QRCodeWriter()
      val bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 200, 200, hints)

      val outputStream = new ByteArrayOutputStream()
      MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream)
      val qrCodeBase64 = Base64.getEncoder.encodeToString(outputStream.toByteArray())

      val ticketRow = TicketsRow(-1, userId, eventId, qrCodeBase64)
      val insertQuery = (Tickets returning Tickets.map(_.ticketid)) += ticketRow

      db.run(insertQuery).map { ticketId =>
        Some(TicketData(Some(ticketId), userId, eventId, qrCodeBase64))
      }
    } catch {
      case ex: Exception =>
        println(s"Error generating ticket: ${ex.getMessage}")
        Future.successful(None)
    }
  }

  // Register a user for an event and create a ticket
  def registerForEvent(eventId: Int, userId: Int): Future[Option[TicketData]] = {
    generateTicket(eventId, userId)
  }

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
