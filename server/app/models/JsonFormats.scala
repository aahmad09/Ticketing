package models

import play.api.libs.json.{Json, Writes}
import models.Tables._

object JsonFormats {
  implicit val eventsRowWrites: Writes[EventsRow] = new Writes[EventsRow] {
    def writes(event: EventsRow) = Json.obj(
      "eventId" -> event.eventid,
      "orgId" -> event.orgid,
      "name" -> event.name,
      // Assuming you have a method to properly format the timestamp to a string
      "date" -> event.date.toString,
      "location" -> event.location,
      "price" -> event.price,
      "description" -> event.description,
      "image" -> event.image
    )
  }
}

