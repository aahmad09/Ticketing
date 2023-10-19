package controllers

import javax.inject._
import play.api.i18n._

import shared.SharedMessages
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import models.LoginData
import scala.concurrent.Future
import views.html.defaultpages.error

class AuthenticationController @Inject() (
    val controllerComponents: ControllerComponents
) extends BaseController
    with I18nSupport {

  val loginForm: Form[LoginData] = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText
    )(LoginData.apply)(LoginData.unapply)
  )

  def loginPage = Action { implicit request =>
    Ok(views.html.login_page(loginForm))
  }

  def handleLogin = Action.async { implicit request =>
    loginForm
      .bindFromRequest()
      .fold(
        errorForm => {
          // Handle form errors
          Future.successful(BadRequest(views.html.login_page(errorForm)))
        },
        data => {
          // check the submitted email and password against your database, set up a session if the credentials are valid, or render an error message if they're not.
          Future.successful(Ok(views.html.index(SharedMessages.itWorks)))
        }
      )
  }
}
