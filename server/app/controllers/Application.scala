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

  // TODO: remove
  def index = Action {
    Ok(views.html.index(SharedMessages.itWorks))
  }

  def login = Action {
    Redirect(routes.AuthenticationController.loginPage)
  }

  def dashboard = Action { implicit request =>
    Ok(views.html.dashboard())
  }
}
