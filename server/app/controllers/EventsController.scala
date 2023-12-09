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
class EventsController @Inject() (
    val controllerComponents: ControllerComponents,
    protected val dbConfigProvider: DatabaseConfigProvider,
    implicit val ec: ExecutionContext
) extends BaseController
    with HasDatabaseConfigProvider[slick.jdbc.JdbcProfile] {

  private val eventModel = new EventModel(db)

  def withSessionUserId(f: Int => Future[Result])(implicit request: Request[AnyContent]): Future[Result] = {
    request.session.get("userId").map(_.toInt).map(f).getOrElse(Future.successful(Ok(Json.toJson(Seq.empty[String]))))
  }

  // List all events
  def listAllEvents = Action.async { implicit request =>
    eventModel.getAllEvents().map { events =>
      Ok(Json.toJson(events))
    }
  }

  // Create a new event
  def createNewEvent = Action.async(parse.json) { implicit request =>
    handleJsonValidation(request.body.validate[EventData]) { eventData =>
      eventModel.createEvent(eventData).map { eventId =>
        if (eventId > 0) Ok(s"Event created successfully with ID $eventId")
        else BadRequest("Failed to create event")
      }
    }
  }

  // View details of a specific event
  def viewEvent(eventId: Int) = Action.async { implicit request =>
    eventModel.getEventById(eventId).map {
      case Some(event) => Ok(Json.toJson(event))
      case None        => NotFound("Event not found")
    }
  }

  // Update event details
  def updateEventDetails(eventId: Int) = Action.async(parse.json) { implicit request =>
    handleJsonValidation(request.body.validate[EventData]) { eventData =>
      eventModel.updateEvent(eventId, eventData).map { success =>
        if (success) Ok(s"Event with ID $eventId updated successfully")
        else BadRequest("Failed to update event")
      }
    }
  }

  // Delete an event
  def deleteEvent(eventId: Int) = Action.async { implicit request =>
    eventModel.deleteEvent(eventId).map { success =>
      if (success) Ok(s"Event with ID $eventId deleted successfully")
      else BadRequest("Failed to delete event")
    }
  }

  // Helper method for handling JSON validation
  private def handleJsonValidation[A](
      result: JsResult[A]
  )(success: A => Future[Result]): Future[Result] = {
    result.fold(
      errors => Future.successful(BadRequest("Invalid JSON data")),
      success
    )
  }

  // Additional methods as needed...
}
