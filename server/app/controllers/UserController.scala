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

  def withSessionUserId(f: Int => Future[Result])(implicit request: Request[AnyContent]): Future[Result] = {
    request.session.get("userId").map(_.toInt).map(f).getOrElse(Future.successful(Ok(Json.toJson(Seq.empty[String]))))
  }

  def withSessionRole(f: String => Future[Result])(implicit request: Request[AnyContent]): Future[Result] = {
    request.session.get("role").map(f).getOrElse(Future.successful(Ok(Json.toJson(Seq.empty[String]))))
  }

  // Dashboard
  def dashboard = Action {implicit request =>
    Ok(views.html.dashboard())
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
  def viewTickets = Action.async { implicit request =>
    withSessionUserId { userId =>
      userModel.getUserTickets(userId).map { tickets =>
        Ok(Json.toJson(tickets))
      }
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
