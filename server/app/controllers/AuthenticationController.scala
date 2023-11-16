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
import play.api.libs.json._

class AuthenticationController @Inject() (
    val controllerComponents: ControllerComponents
) extends BaseController
    with I18nSupport {

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
          Future.successful(Ok(views.html.index(SharedMessages.itWorks)).withSession("email" -> data.email, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse("")))
        }
      )
  }
}
