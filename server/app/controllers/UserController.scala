package controllers

import javax.inject._
import play.api.mvc._
import models._
import models.JsonFormats._
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import shared.SharedMessages

@Singleton
class UserController @Inject() (
    val controllerComponents: ControllerComponents,
    protected val dbConfigProvider: DatabaseConfigProvider,
    implicit val ec: ExecutionContext
) extends BaseController with HasDatabaseConfigProvider[slick.jdbc.JdbcProfile] {

  private val userModel = new UserModel(dbConfigProvider.get[slick.jdbc.JdbcProfile].db)
  private val eventModel = new EventModel(dbConfigProvider.get[slick.jdbc.JdbcProfile].db)

  // Endpoint to view all listed events
  def listEvents = Action.async { implicit request =>
    eventModel.getAllEvents().map { events =>
      Ok(Json.toJson(events))
    }
  }

  // Dashboard
  def dashboard = Action {
    Ok(views.html.index(SharedMessages.itWorks))
  }

  // Endpoint to register for an event
  def registerForEvent = Action.async(parse.json) { implicit request =>
    handleJsonValidation(request.body.validate[EventRegistrationData]) {
      registration =>
        userModel.registerForEvent(registration.userId, registration.eventId).map { registered =>
            if (registered) Ok("Registration successful")
            else BadRequest("Registration failed")
        }
    }
  }

  // Endpoint to view user's tickets
  def viewTickets(userId: Int) = Action.async { implicit request =>
    userModel.getUserTickets(userId).map { tickets =>
      Ok(Json.toJson(tickets))
    }
  }

  // Helper method for handling JSON validation
  private def handleJsonValidation[A](result: JsResult[A])(success: A => Future[Result]): Future[Result] = {
    result.fold(
      errors => Future.successful(BadRequest("Invalid JSON data")),
      success
    )
  }
}
