package models

final case class EventData(
  eventId: Option[Int],
  orgId: Int,
  name: String,
  date: java.sql.Timestamp,
  location: String,
  description: Option[String],
  image: Option[String]
)

final case class EventRegistrationData(
  userId: Int,
  eventId: Int
)

