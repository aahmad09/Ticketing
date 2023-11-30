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

  def handleLogin = Action.async { implicit request =>
    request.body.asJson
      .map { json =>
        json
          .validate[LoginData]
          .fold(
            invalid => {
              Future.successful(BadRequest("Invalid JSON")) // Handle bad JSON
            },
            args => {
              userModel.validateUser(args.email, args.password).flatMap {
                case Some(userId) =>
                  Future.successful(
                    Ok(views.html.index(SharedMessages.itWorks))
                      .withSession("email" -> args.email)
                  )
                case None =>
                  Future.successful(BadRequest("Invalid credentials"))
              }
            }
          )
      }
      .getOrElse(
        Future.successful(Redirect(routes.Application.load))
      ) // Handle missing JSON body
  }

  def handleRegistration = Action.async(parse.json) { implicit request =>
    request.body
      .validate[RegistrationData]
      .fold(
        errors => Future.successful(BadRequest("Invalid data")),
        data => {
          userModel
            .createUser(
              data.name,
              data.email,
              data.password,
              Some(data.role),
              None
            )
            .map {
              case Some(userId) =>
                Ok(
                  Json.obj(
                    "status" -> "success",
                    "message" -> "User registered successfully."
                  )
                )
              case None =>
                BadRequest(
                  Json.obj(
                    "status" -> "error",
                    "message" -> "User already exists."
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
