package controllers

import javax.inject._
import play.api.i18n._
import play.api.mvc._
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.ExecutionContext
import models._
import shared.SharedMessages
import play.api.data._
import play.api.data.Forms._
import scala.concurrent.Future
import views.html.defaultpages.error
import play.api.libs.json._
import models._


class AuthenticationController @Inject() (
    val controllerComponents: ControllerComponents,
    protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends BaseController with I18nSupport with HasDatabaseConfigProvider[JdbcProfile] {

  private val userModel = new UserModel(db)

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

  def loginPage = Action { implicit request =>
    Ok(views.html.loginPage())
  }

  def handleLogin = Action.async { implicit request =>
    Future.successful(
      withJsonBody[LoginData] { args =>
        userModel.validateUser(data.email, data.password).flatMap {
          case Some(userId) =>
            // Successful login logic here, possibly updating session
            Ok(views.html.index(SharedMessages.itWorks)).withSession("email" -> args.email, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
          case None =>
            // Failed login logic
            BadRequest(views.html.errorPage())
        }
      }
    )
  }

  // Additional actions for logout, registration, etc. can be added here
}
