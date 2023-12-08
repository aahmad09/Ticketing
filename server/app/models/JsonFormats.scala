package models

import play.api.libs.json._
import java.sql.Timestamp
import models.Tables._

object JsonFormats {

  //  Writes

  implicit val timestampFormat: Format[Timestamp] = new Format[Timestamp] {
    def writes(ts: Timestamp): JsValue = JsString(ts.toString)
    def reads(json: JsValue): JsResult[Timestamp] = json match {
      case JsString(s) => JsSuccess(Timestamp.valueOf(s))
      case _ => JsError("error.expected.jsstring")
    }
  }

  implicit val eventDataWrites: Writes[EventData] = new Writes[EventData] {
    def writes(event: EventData): JsValue = Json.obj(
      "eventId" -> event.eventId,
      "orgId" -> event.orgId,
      "name" -> event.name,
      "date" -> event.date,
      "location" -> event.location,
      "description" -> event.description,
      "image" -> event.image
    )
  }

  implicit val userDataWrites: Writes[UserData] = new Writes[UserData] {
    def writes(user: UserData): JsValue = Json.obj(
      "userid" -> user.userId,
      "name" -> user.name,
      "email" -> user.email,
    )
  }

  implicit val ticketDataWrites: Writes[TicketData] = new Writes[TicketData] {
    def writes(ticket: TicketData): JsValue = Json.obj(
      "ticketId" -> ticket.ticketId,
      "userId" -> ticket.userId,
      "eventId" -> ticket.eventId,
      "qrCode" -> ticket.qrCode
    )
  }

  implicit val waiverDataWrites: Writes[WaiverData] = new Writes[WaiverData] {
    def writes(waiver: WaiverData): JsValue = Json.obj(
      "waiverId" -> waiver.waiverId,
      "userId" -> waiver.userId,
      "eventId" -> waiver.eventId,
      "signStatus" -> waiver.signStatus
    )
  }
  
  // Reads
  implicit val eventRegistrationDataReads: Reads[EventRegistrationData] = Json.reads[EventRegistrationData]
  implicit val eventDataReads: Reads[EventData] = Json.reads[EventData]
  implicit val ticketsDataReads: Reads[TicketData] = Json.reads[TicketData]
  implicit val waiversDataReads: Reads[WaiverData] = Json.reads[WaiverData]
  implicit val userDataReads: Reads[LoginData] = Json.reads[LoginData]
  implicit val registrationDataReads: Reads[RegistrationData] =
    Json.reads[RegistrationData]


}
