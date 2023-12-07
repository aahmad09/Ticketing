package models

import play.api.libs.json._
import java.sql.Timestamp
import models.Tables._

object JsonFormats {

  // Format for java.sql.Timestamp
  implicit val timestampFormat: Format[Timestamp] = new Format[Timestamp] {
    def writes(ts: Timestamp): JsValue = JsString(ts.toString)
    def reads(json: JsValue): JsResult[Timestamp] = json match {
      case JsString(s) => JsSuccess(Timestamp.valueOf(s))
      case _ => JsError("error.expected.jsstring")
    }
  }

  // Writes for EventsRow
  implicit val eventsRowWrites: Writes[EventsRow] = new Writes[EventsRow] {
    def writes(event: EventsRow): JsValue = Json.obj(
      "eventId" -> event.eventid,
      "orgId" -> event.orgid,
      "name" -> event.name,
      "date" -> event.date,
      "location" -> event.location,
      "description" -> event.description,
      "image" -> event.image
    )
  }

  // Writes for UsersRow
  implicit val usersRowWrites: Writes[UsersRow] = new Writes[UsersRow] {
    def writes(user: UsersRow): JsValue = Json.obj(
      "userid" -> user.userid,
      "name" -> user.name,
      "email" -> user.email,
      // Add other fields if necessary
    )
  }

  // Implicit Json Writes for TicketsRow
  implicit val ticketsRowWrites: Writes[TicketsRow] = new Writes[TicketsRow] {
    def writes(ticket: TicketsRow): JsValue = Json.obj(
      "ticketId" -> ticket.ticketid,
      "userId" -> ticket.userid,
      "eventId" -> ticket.eventid,
      "qrCode" -> ticket.qrcode
    )
  }

  // Reads
  implicit val eventRegistrationDataReads: Reads[EventRegistrationData] = Json.reads[EventRegistrationData]
  implicit val eventDataReads: Reads[EventData] = Json.reads[EventData]

}
