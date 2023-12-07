package controllers

import javax.inject._
import play.api.mvc._
import models._
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._
import models.JsonFormats._
import models.Tables._
import shared.SharedMessages

@Singleton
class EventsController @Inject()(
    val controllerComponents: ControllerComponents,
    val eventModel: EventModel
)(implicit ec: ExecutionContext) extends BaseController {

  // List all events
  def listAllEvents = Action.async { implicit request =>
    eventModel.getAllEvents().map { events =>
      Ok(Json.toJson(events))
    }
  }

  // Create a new event
  def createNewEvent = Action.async(parse.json) { implicit request =>
    handleJsonValidation(request.body.validate[EventData]) { eventData =>
      val newEvent = EventsRow(
        eventid = 0, // 0 for auto-increment
        orgid = eventData.orgId,
        name = eventData.name,
        date = eventData.date,
        location = eventData.location,
        description = eventData.description,
        image = eventData.image
      )
      eventModel.createEvent(newEvent).map { eventId =>
        if(eventId > 0) Ok(s"Event created successfully with ID $eventId")
        else BadRequest("Failed to create event")
      }
    }
  }

  // View details of a specific event
  def viewEvent(eventId: Int) = Action.async { implicit request =>
    eventModel.getEventById(eventId).map {
      case Some(event) => Ok(Json.toJson(event))
      case None => NotFound("Event not found")
    }
  }

  // List attendees for a specific event
  def listAttendees(eventId: Int) = Action.async { implicit request =>
    eventModel.getEventAttendees(eventId).map { attendees =>
      Ok(Json.toJson(attendees))
    }
  }

  // Update event details
  def updateEventDetails(eventId: Int) = Action.async(parse.json) { implicit request =>
    handleJsonValidation(request.body.validate[EventData]) { eventData =>
      val updatedEvent = EventsRow(
        eventid = eventId,
        orgid = eventData.orgId,
        name = eventData.name,
        date = eventData.date,
        location = eventData.location,
        description = eventData.description,
        image = eventData.image
      )
      eventModel.updateEvent(eventId, updatedEvent).map { success =>
        if(success) Ok(s"Event with ID $eventId updated successfully")
        else BadRequest("Failed to update event")
      }
    }
  }

  // Delete an event
  def deleteEvent(eventId: Int) = Action.async { implicit request =>
    eventModel.deleteEvent(eventId).map { success =>
      if(success) Ok(s"Event with ID $eventId deleted successfully")
      else BadRequest("Failed to delete event")
    }
  }

  // Helper method for handling JSON validation
  private def handleJsonValidation[A](result: JsResult[A])(success: A => Future[Result]): Future[Result] = {
    result.fold(
      errors => Future.successful(BadRequest("Invalid JSON data")),
      success
    )
  }

  // Additional methods as needed...
}
