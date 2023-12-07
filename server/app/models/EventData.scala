package models

// Define a case class for event data
case class EventData(
  eventId: Option[Int], // Optional because it's not present when creating a new event
  orgId: Int,
  name: String,
  date: java.sql.Timestamp,
  location: String,
  price: BigDecimal,
  description: Option[String],
  image: Option[String]
)

// Define a case class for event registration data
case class EventRegistrationData(
  userId: Int,
  eventId: Int
)

