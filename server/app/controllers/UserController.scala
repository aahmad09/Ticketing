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

  def withSessionUserIdSync(f: Int => Result)(implicit request: Request[AnyContent]): Result = {
    request.session.get("userId").map(_.toInt).map(f).getOrElse(Ok(Json.toJson(Seq.empty[String])))
  }

  def withSessionRole(f: String => Future[Result])(implicit request: Request[AnyContent]): Future[Result] = {
    request.session.get("role").map(f).getOrElse(Future.successful(Ok(Json.toJson(Seq.empty[String]))))
  }

  def withSessionRoleSync(f: String => Result)(implicit request: Request[AnyContent]): Result = {
    request.session.get("role").map(f).getOrElse(Ok(Json.toJson(Seq.empty[String])))
  }

  def getProfileInfo = Action.async {implicit request =>
    withSessionUserId { userId =>
      userModel.getUserById(userId).map {
        case Some(a) => Ok(Json.obj(
          "role" -> a.role,
          "name" -> a.name
        ))
        case None => Ok(Json.obj(
          "role" -> "attendee",
          "name" -> "NOT FOUND"
        ))
      }
    }
  }

  def getUserId = Action { implicit request =>
    withSessionUserIdSync { userId => 
      Ok(Json.obj("userId" -> userId))
    }
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

  // Endpoint to update user preferences
  def updateUserPreferences = Action.async(parse.json) { implicit request =>
    request.body.validate[UserData].fold(
      errors => Future.successful(BadRequest(Json.obj("status" -> "error", "message" -> "Invalid JSON data"))),
      userData => {
        userModel.updateUserPreferences(userData.userId.getOrElse(-1), userData).map {
          case true => Ok(Json.obj("status" -> "success", "message" -> "Preferences updated successfully"))
          case false => BadRequest(Json.obj("status" -> "error", "message" -> "Failed to update preferences"))
        }
      }
    )
  }

  // Helper method for handling JSON validation
  private def handleJsonValidation[A](result: JsResult[A])(success: A => Future[Result]): Future[Result] = {
    result.fold(
      errors => Future.successful(BadRequest("Invalid JSON data")),
      success
    )
  }
}
