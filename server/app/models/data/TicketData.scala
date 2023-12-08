package models

final case class TicketData(
  ticketId: Option[Int],
  userId: Int,
  eventId: Int,
  qrCode: String,
)
