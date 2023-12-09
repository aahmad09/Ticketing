package controllers

import javax.inject._
import play.api.mvc._
import models._
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._
import models.JsonFormats._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

class AuthenticationController @Inject() (
    val controllerComponents: ControllerComponents,
    protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends BaseController
    with HasDatabaseConfigProvider[JdbcProfile] {

  private val userModel = new UserModel(db)

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
        loginData =>
          userModel.validateUser(loginData.email, loginData.password).map {
            case Some(userId) =>
              Ok(
                Json.obj("status" -> "success", "message" -> "Login successful")
              ).withSession("email" -> loginData.email, "userId" -> userId.toString)
            case None =>
              BadRequest(
                Json
                  .obj("status" -> "error", "message" -> "Invalid credentials")
              )
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
          val userData = UserData(
            None,
            registrationData.name,
            registrationData.email,
            registrationData.password,
            Some(registrationData.role)
          )
          userModel.createUser(userData).map {
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
                Json
                  .obj("status" -> "error", "message" -> "User already exists")
              )
          }
        }
      )
  }

  def logout = Action { implicit request =>
    Redirect(routes.AuthenticationController.loginPage).withNewSession
      .flashing("success" -> "You've been logged out")
  }
}
