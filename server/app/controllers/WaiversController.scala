package controllers

import javax.inject._
import play.api.mvc._
import models._
import models.JsonFormats._
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

@Singleton
class WaiversController @Inject() (
    val controllerComponents: ControllerComponents,
    protected val dbConfigProvider: DatabaseConfigProvider,
    implicit val ec: ExecutionContext
) extends BaseController
    with HasDatabaseConfigProvider[slick.jdbc.JdbcProfile] {

  private val waiverModel = new WaiverModel(db)

  // Endpoint to create a new waiver
  def createWaiver = Action.async(parse.json) { implicit request =>
    handleJsonValidation(request.body.validate[WaiverData]) { waiverData =>
      waiverModel.createWaiver(waiverData).map { waiverId =>
        Ok(Json.obj("waiverId" -> waiverId))
      }
    }
  }

  // Endpoint to get a specific waiver by ID
  def getWaiverById(waiverId: Int) = Action.async { implicit request =>
    waiverModel.getWaiverById(waiverId).map {
      case Some(waiver) => Ok(Json.toJson(waiver))
      case None         => NotFound("Waiver not found")
    }
  }

  // Endpoint to get all waivers for a specific event
  def getEventWaivers(eventId: Int) = Action.async { implicit request =>
    waiverModel.getEventWaivers(eventId).map { waivers =>
      Ok(Json.toJson(waivers))
    }
  }

  // Endpoint to update waiver sign status
  def updateWaiverSignStatus(waiverId: Int) = Action.async(parse.json) { implicit request =>
    handleJsonValidation(request.body.validate[Boolean]) { signStatus =>
      waiverModel.updateWaiverSignStatus(waiverId, signStatus).map {
        updated =>
          if (updated) Ok("Waiver sign status updated")
          else BadRequest("Failed to update waiver sign status")
      }
    }
  }

  // Endpoint to delete a waiver by ID
  def deleteWaiver(waiverId: Int) = Action.async { implicit request =>
    waiverModel.deleteWaiver(waiverId).map { deleted =>
      if (deleted) Ok("Waiver deleted")
      else NotFound("Waiver not found")
    }
  }

  // Helper method for handling JSON validation
  private def handleJsonValidation[A](result: JsResult[A])(success: A => Future[Result]): Future[Result] = {
    result.fold(
      errors => Future.successful(BadRequest("Invalid JSON data")),
      success
    )
  }
}
