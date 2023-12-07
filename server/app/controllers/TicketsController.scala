package controllers

import javax.inject._
import play.api.mvc._
import models._
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._
import models.JsonFormats._
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

@Singleton
class TicketsController @Inject()(
    val controllerComponents: ControllerComponents,
    protected val dbConfigProvider: DatabaseConfigProvider,
    implicit val ec: ExecutionContext
) extends BaseController with HasDatabaseConfigProvider[slick.jdbc.JdbcProfile] {

  private val ticketModel = new TicketModel(dbConfigProvider.get[slick.jdbc.JdbcProfile].db)

  // Action to register a user for an event and create a ticket
  def registerForEvent(eventId: Int, userId: Int) = Action.async {
    ticketModel.registerForEvent(eventId, userId).map {
      case Some(ticket) => Ok(Json.toJson(ticket))
      case None => InternalServerError("Failed to register for event")
    }
  }

  // Action to retrieve a specific ticket
  def getTicket(ticketId: Int) = Action.async {
    ticketModel.getTicket(ticketId).map {
      case Some(ticket) => Ok(Json.toJson(ticket))
      case None => NotFound("Ticket not found")
    }
  }

  // Action to view all tickets for a specific user
  def getUserTickets(userId: Int) = Action.async {
    ticketModel.getUserTickets(userId).map { tickets =>
      Ok(Json.toJson(tickets))
    }
  }

  // Action to cancel a ticket
  def cancelTicket(ticketId: Int) = Action.async {
    ticketModel.cancelTicket(ticketId).map { result =>
      if (result) Ok("Ticket cancelled successfully")
      else BadRequest("Failed to cancel ticket")
    }
  }
}
