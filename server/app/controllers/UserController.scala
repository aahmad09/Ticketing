package controllers
import shared.SharedMessages

import javax.inject._
import play.api.mvc._
import models._
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import java.sql.Timestamp
import models.Tables._

@Singleton
class UserController @Inject()(
    val controllerComponents: ControllerComponents,
    protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext) extends BaseController with HasDatabaseConfigProvider[JdbcProfile] {
    
    private val userModel = new UserModel(db)
    private val eventModel = new EventModel(db)

    // implicit val timestampFormat: Format[Timestamp] = new Format[Timestamp] {
    //     def writes(ts: Timestamp): JsValue = JsString(ts.toString)
    //     def reads(json: JsValue): JsResult[Timestamp] = json match {
    //         case JsString(s) => JsSuccess(Timestamp.valueOf(s))
    //         case _ => JsError(Seq(JsPath() -> Seq(JsonValidationError("error.expected.jsstring"))))
    //     }
    // }

    // // Custom Writes for EventsRow
    // implicit val eventsRowWrites: Writes[EventsRow] = new Writes[EventsRow] {
    //     def writes(event: EventsRow) = Json.obj(
    //         "eventId" -> event.eventid,
    //         "orgId" -> event.orgid,
    //         "name" -> event.name,
    //         "date" -> event.date.toString,
    //         "location" -> event.location,
    //         "price" -> event.price,
    //         "description" -> event.description,
    //         "image" -> event.image
    //     )
    // }

    // // Implicit Reads for JSON conversions
    // implicit val eventReads: Reads[EventData] = Json.reads[EventData]
    // implicit val registrationReads: Reads[EventRegistrationData] = Json.reads[EventRegistrationData]
    
    // // Endpoint to view all listed events
    // def listEvents = Action.async { implicit request =>
    //     eventModel.getAllEvents().map { events =>
    //         Ok(Json.toJson(events))  // This will now work with the custom Writes
    //     }
    // }

    def dashboard = Action { implicit request =>
        Ok(views.html.dashboard())
    }
    //             userModel.registerForEvent(registration.userId, registration.eventId).map { registered =>
    //                 if(registered) Ok("Registration successful")
    //                 else BadRequest("Registration failed")
    //             }
    //         }
    //     )
    // }

    // // Endpoint to view user's tickets
    // def viewTickets(userId: Long) = Action.async { implicit request =>
    //     userModel.getUserTickets(userId).map { tickets =>
    //         Ok(Json.toJson(tickets))
    //     }
    // }

    // // For Organizers
    // // Endpoint to create an event
    // def createEvent = Action.async(parse.json) { implicit request =>
    //     request.body.validate[Event].fold(
    //         errors => Future.successful(BadRequest("Invalid event data")),
    //         event => {
    //             eventModel.createEvent(event).map { created =>
    //                 if(created) Ok("Event created successfully")
    //                 else BadRequest("Event creation failed")
    //             }
    //         }
    //     )
    // }

    // // Endpoint to view the list of attendees for an event
    // def viewAttendees(eventId: Long) = Action.async { implicit request =>
    //     eventModel.getEventAttendees(eventId).map { attendees =>
    //         Ok(Json.toJson(attendees))
    //     }
    // }

    // Additional endpoints as needed...
}