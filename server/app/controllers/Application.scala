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

  def withJsonBody[A](
      f: A => Result
  )(implicit request: Request[AnyContent], reads: Reads[A]): Option[Result] = {
    request.body.asJson.flatMap { body =>
      Json.fromJson[A](body).asOpt.map(f)
    }
  }

  // TODO: remove
  def index = Action {
    Ok(views.html.index(SharedMessages.itWorks))
  }

  def login = Action {
    Redirect(routes.AuthenticationController.loginPage)
  }

<<<<<<< HEAD

=======
  def home = Action { implicit request =>
    Ok(views.html.attendeeHome())
  }
>>>>>>> 297346b1738d12948f1be4aac3010c2888e78369

}
