package models

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import models.Tables._

class WaiverModel(db: Database)(implicit ec: ExecutionContext) {

  // Create a new waiver
  def createWaiver(waiverData: WaiverData): Future[Int] = {
    val waiverRow = WaiversRow(-1, waiverData.userId, waiverData.eventId, waiverData.signStatus)
    val insertQuery = (Waivers returning Waivers.map(_.waiverid)) += waiverRow
    db.run(insertQuery)
  }

  // Get a specific waiver by ID
  def getWaiverById(waiverId: Int): Future[Option[WaiverData]] = {
    val query = Waivers.filter(_.waiverid === waiverId).result.headOption
    db.run(query).map(_.map(waiverRow =>
      WaiverData(Some(waiverRow.waiverid), waiverRow.userid, waiverRow.eventid, waiverRow.signstatus)))
  }

  // Get waivers for a specific event
  def getEventWaivers(eventId: Int): Future[Seq[WaiverData]] = {
    val query = Waivers.filter(_.eventid === eventId).result
    db.run(query).map(_.map(waiverRow =>
      WaiverData(Some(waiverRow.waiverid), waiverRow.userid, waiverRow.eventid, waiverRow.signstatus)))
  }

  // Update waiver sign status
  def updateWaiverSignStatus(waiverId: Int, signStatus: Boolean): Future[Boolean] = {
    val updateQuery = Waivers.filter(_.waiverid === waiverId)
      .map(waiver => waiver.signstatus)
      .update(signStatus)
    db.run(updateQuery).map(_ == 1)
  }

  // Delete a waiver
  def deleteWaiver(waiverId: Int): Future[Boolean] = {
    val query = Waivers.filter(_.waiverid === waiverId).delete
    db.run(query).map(_ > 0)
  }
}