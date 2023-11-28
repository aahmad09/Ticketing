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
      
  def load = Action { implicit request =>
    Ok(views.html.testing_page())
  }

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

  def testing = Action { implicit request =>
    Ok(views.html.testing_page())
  }

}
