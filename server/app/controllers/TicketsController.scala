package controllers

import javax.inject._
import play.api.mvc._
import models._
import models.JsonFormats._
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

@Singleton
class TicketsController @Inject()(
    val controllerComponents: ControllerComponents,
    protected val dbConfigProvider: DatabaseConfigProvider,
    implicit val ec: ExecutionContext
) extends BaseController with HasDatabaseConfigProvider[slick.jdbc.JdbcProfile] {

  private val ticketModel = new TicketModel(dbConfigProvider.get[slick.jdbc.JdbcProfile].db)

  // Helper method to create a JSON response
  private def createJsonResponse[A](future: Future[Option[A]])(implicit writes: Writes[A]): Future[Result] = {
    future.map {
      case Some(value) => Ok(Json.toJson(value))
      case None => NotFound(Json.obj("message" -> "Not found"))
    }.recover {
      case ex: Throwable => InternalServerError(Json.obj("error" -> ex.getMessage))
    }
  }

  def withSessionUserId(f: Int => Future[Result])(implicit request: Request[AnyContent]): Future[Result] = {
    request.session.get("userId").map(_.toInt).map(f).getOrElse(Future.successful(Ok(Json.toJson(Seq.empty[String]))))
  }

  // Action to register a user for an event and create a ticket
  def registerForEventTwo(eventId: Int): Action[AnyContent] = Action.async { implicit request =>
    withSessionUserId { userId =>
      createJsonResponse(ticketModel.registerForEvent(eventId, userId))
    }
  }

  // Action to retrieve a specific ticket
  def getTicket(ticketId: Int): Action[AnyContent] = Action.async {
    createJsonResponse(ticketModel.getTicket(ticketId))
  }

  // Action to view all tickets for a specific user
  def getUserTickets: Action[AnyContent] = Action.async { implicit request =>
    withSessionUserId { userId =>
      ticketModel.getUserTickets(userId).map { tickets =>
        Ok(Json.toJson(tickets))
      }
    }
  }

  // Action to cancel a ticket
  def cancelTicket(ticketId: Int): Action[AnyContent] = Action.async {
    ticketModel.cancelTicket(ticketId).map { result =>
      if (result) Ok(Json.obj("message" -> "Ticket cancelled successfully"))
      else BadRequest(Json.obj("message" -> "Failed to cancel ticket"))
    }.recover {
      case ex: Throwable => InternalServerError(Json.obj("error" -> ex.getMessage))
    }
  }

  // Additional actions for waivers, event details, etc. can be added here
}
