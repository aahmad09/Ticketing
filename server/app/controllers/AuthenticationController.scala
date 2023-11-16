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

class AuthenticationController @Inject() (
    val controllerComponents: ControllerComponents,
    protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends BaseController with I18nSupport with HasDatabaseConfigProvider[JdbcProfile] {

  private val userModel = new UserModel(db)

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
    loginForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(BadRequest(views.html.login_page(errorForm)))
      },
      data => {
        userModel.validateUser(data.email, data.password).flatMap {
          case Some(userId) =>
            // Successful login logic here, possibly updating session
            Future.successful(Ok(views.html.index(SharedMessages.itWorks)))
          case None =>
            // Failed login logic
            Future.successful(BadRequest(views.html.login_page(loginForm.withGlobalError("Invalid email or password"))))
        }
      }
    )
  }

  // Additional actions for logout, registration, etc. can be added here
}
