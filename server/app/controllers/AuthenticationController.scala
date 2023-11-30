package controllers

import javax.inject._
import play.api.i18n._
import play.api._
import play.api.mvc._
import play.api.mvc.Results._

import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.ExecutionContext
import models._
import shared.SharedMessages
import play.api.libs.json._
import scala.concurrent.Future

class AuthenticationController @Inject() (
    val controllerComponents: ControllerComponents,
    protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends BaseController
    with I18nSupport
    with HasDatabaseConfigProvider[JdbcProfile] {

  private val userModel = new UserModel(db)

  implicit val userDataReads: Reads[LoginData] = Json.reads[LoginData]
  implicit val registrationDataReads: Reads[RegistrationData] =
    Json.reads[RegistrationData]

  def withJsonBody[A](
      f: A => Result
  )(implicit request: Request[AnyContent], reads: Reads[A]): Option[Result] = {
    request.body.asJson.flatMap { body =>
      Json.fromJson[A](body).asOpt.map(f)
    }
  }

  def loginPage = Action { implicit request =>
    Ok(views.html.loginPage())
  }

  def registerPage = Action { implicit request =>
    Ok(views.html.registerPage())
  }

  def handleLogin = Action.async(parse.json) { implicit request =>
    request.body
      .validate[LoginData]
      .fold(
        invalid =>
          Future.successful(
            BadRequest(
              Json.obj("status" -> "error", "message" -> "Invalid JSON format")
            )
          ),
        loginData => {
          userModel.validateUser(loginData.email, loginData.password).map {
            case Some(userId) =>
              Ok(
                Json.obj("status" -> "success", "message" -> "Login successful")
              )
                .withSession("email" -> loginData.email) // Set session
            case None =>
              BadRequest(
                Json
                  .obj("status" -> "error", "message" -> "Invalid credentials")
              )
          }
        }
      )
  }

  def handleRegistration = Action.async(parse.json) { implicit request =>
    request.body
      .validate[RegistrationData]
      .fold(
        errors =>
          Future.successful(
            BadRequest(
              Json.obj("status" -> "error", "message" -> "Invalid JSON format")
            )
          ),
        registrationData => {
          userModel
            .createUser(
              registrationData.name,
              registrationData.email,
              registrationData.password,
              Some(registrationData.role),
              None
            )
            .map {
              case Some(userId) =>
                Ok(
                  Json.obj(
                    "status" -> "success",
                    "message" -> "User registered successfully",
                    "userId" -> userId
                  )
                )
              case None =>
                BadRequest(
                  Json.obj(
                    "status" -> "error",
                    "message" -> "User already exists"
                  )
                )
            }
        }
      )
  }
  def logout = Action { implicit request =>
    Redirect(routes.AuthenticationController.loginPage).withNewSession.flashing(
      "success" -> "You've been logged out"
    )
  }

}
