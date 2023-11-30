package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._
import play.api.libs.json._
import play.api.i18n._
import models._

@Singleton
class Application @Inject() (cc: ControllerComponents)
    extends AbstractController(cc) {

  implicit val userDataReads = Json.reads[LoginData]

  def withJsonBody[A](f: A => Result)(implicit request: Request[AnyContent], reads: Reads[A]) = {
    request.body.asJson.map { body =>
      Json.fromJson[A](body) match {
        case JsSuccess(a, path) => f(a)
        case e @ JsError(_) => Redirect(routes.Application.load)
      }
    }.getOrElse(Redirect(routes.Application.load))
  }

  def withSessionUsername(f: String => Result)(implicit request: Request[AnyContent]) = {
    request.session.get("email").map(f).getOrElse(Ok(Json.toJson(Seq.empty[String])))
  }

  def index = Action {
    Ok(views.html.index(SharedMessages.itWorks))
  }

  def login = Action {
    Redirect(routes.AuthenticationController.loginPage)
  }

  def load = Action { implicit request =>
    // Logic for the 'load' action
    // This could be a simple redirect, or you might want to load some initial data for your application.
    // For example, you might want to check if a user is logged in and redirect accordingly.
    request.session.get("email") match {
      case Some(email) => 
        // If the user is logged in, redirect to a dashboard or home page
        // Redirect(routes.HomeController.dashboard)
        Ok(views.html.index(SharedMessages.itWorks))
      case None => 
        // If the user is not logged in, redirect to the login page or a public home page
        Redirect(routes.AuthenticationController.loginPage)
    }
  }

}
