package controllers

import javax.inject._
import play.api.i18n._
import play.api.mvc._
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.ExecutionContext
import models.Tables._
import models.LoginData
import shared.SharedMessages
import play.api.data.Forms._
import play.api.data._
import scala.concurrent.Future

class AuthenticationController @Inject() (
    val controllerComponents: ControllerComponents,
    protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends BaseController with I18nSupport with HasDatabaseConfigProvider[JdbcProfile] {

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
        val resultingUsers = db.run(Users.result)
        resultingUsers.map(users => Ok(views.html.index(users)))
        // val userQuery = Users.filter(_.email == data.email).result.headOption
        // db.run(userQuery).flatMap {
        //   case Some(user) if user.password == data.password =>
        //     Future.successful(Ok(views.html.index(SharedMessages.itWorks)))
        //   case _ =>
        //     Future.successful(BadRequest(views.html.login_page(loginForm.withGlobalError("Invalid email or password"))))
        // }
      }
    )
  }
}
