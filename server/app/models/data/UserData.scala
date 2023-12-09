package models

final case class UserData(
    userId: Option[Int],
    name: String,
    email: String,
    password: String,
    role: Option[String]
)


final case class EventAttendeeData(
    eventId: Int,
    userId: Int
)
