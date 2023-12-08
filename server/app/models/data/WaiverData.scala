package models

case class WaiverData(
  waiverId: Option[Int],
  userId: Int,
  eventId: Int,
  signStatus: Boolean
)

