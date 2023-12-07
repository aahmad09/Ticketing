package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends Tables {
  val profile = slick.jdbc.PostgresProfile
}

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Eventattendees.schema ++ Events.schema ++ Tickets.schema ++ Users.schema ++ Waivers.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Eventattendees
   *  @param eventid Database column eventid SqlType(int4)
   *  @param userid Database column userid SqlType(int4) */
  case class EventattendeesRow(eventid: Int, userid: Int)
  /** GetResult implicit for fetching EventattendeesRow objects using plain SQL queries */
  implicit def GetResultEventattendeesRow(implicit e0: GR[Int]): GR[EventattendeesRow] = GR{
    prs => import prs._
    EventattendeesRow.tupled((<<[Int], <<[Int]))
  }
  /** Table description of table eventattendees. Objects of this class serve as prototypes for rows in queries. */
  class Eventattendees(_tableTag: Tag) extends profile.api.Table[EventattendeesRow](_tableTag, "eventattendees") {
    def * = (eventid, userid).<>(EventattendeesRow.tupled, EventattendeesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(eventid), Rep.Some(userid))).shaped.<>({r=>import r._; _1.map(_=> EventattendeesRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column eventid SqlType(int4) */
    val eventid: Rep[Int] = column[Int]("eventid")
    /** Database column userid SqlType(int4) */
    val userid: Rep[Int] = column[Int]("userid")

    /** Primary key of Eventattendees (database name eventattendees_pkey) */
    val pk = primaryKey("eventattendees_pkey", (eventid, userid))

    /** Foreign key referencing Events (database name eventattendees_eventid_fkey) */
    lazy val eventsFk = foreignKey("eventattendees_eventid_fkey", eventid, Events)(r => r.eventid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Users (database name eventattendees_userid_fkey) */
    lazy val usersFk = foreignKey("eventattendees_userid_fkey", userid, Users)(r => r.userid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Eventattendees */
  lazy val Eventattendees = new TableQuery(tag => new Eventattendees(tag))

  /** Entity class storing rows of table Events
   *  @param eventid Database column eventid SqlType(serial), AutoInc, PrimaryKey
   *  @param orgid Database column orgid SqlType(int4)
   *  @param name Database column name SqlType(varchar), Length(255,true)
   *  @param date Database column date SqlType(timestamp)
   *  @param location Database column location SqlType(varchar), Length(255,true)
   *  @param description Database column description SqlType(text), Default(None)
   *  @param image Database column image SqlType(text), Default(None) */
  case class EventsRow(eventid: Int, orgid: Int, name: String, date: java.sql.Timestamp, location: String, description: Option[String] = None, image: Option[String] = None)
  /** GetResult implicit for fetching EventsRow objects using plain SQL queries */
  implicit def GetResultEventsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[String]]): GR[EventsRow] = GR{
    prs => import prs._
    EventsRow.tupled((<<[Int], <<[Int], <<[String], <<[java.sql.Timestamp], <<[String], <<?[String], <<?[String]))
  }
  /** Table description of table events. Objects of this class serve as prototypes for rows in queries. */
  class Events(_tableTag: Tag) extends profile.api.Table[EventsRow](_tableTag, "events") {
    def * = (eventid, orgid, name, date, location, description, image).<>(EventsRow.tupled, EventsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(eventid), Rep.Some(orgid), Rep.Some(name), Rep.Some(date), Rep.Some(location), description, image)).shaped.<>({r=>import r._; _1.map(_=> EventsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column eventid SqlType(serial), AutoInc, PrimaryKey */
    val eventid: Rep[Int] = column[Int]("eventid", O.AutoInc, O.PrimaryKey)
    /** Database column orgid SqlType(int4) */
    val orgid: Rep[Int] = column[Int]("orgid")
    /** Database column name SqlType(varchar), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column date SqlType(timestamp) */
    val date: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("date")
    /** Database column location SqlType(varchar), Length(255,true) */
    val location: Rep[String] = column[String]("location", O.Length(255,varying=true))
    /** Database column description SqlType(text), Default(None) */
    val description: Rep[Option[String]] = column[Option[String]]("description", O.Default(None))
    /** Database column image SqlType(text), Default(None) */
    val image: Rep[Option[String]] = column[Option[String]]("image", O.Default(None))

    /** Foreign key referencing Users (database name events_orgid_fkey) */
    lazy val usersFk = foreignKey("events_orgid_fkey", orgid, Users)(r => r.userid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Events */
  lazy val Events = new TableQuery(tag => new Events(tag))

  /** Entity class storing rows of table Tickets
   *  @param ticketid Database column ticketid SqlType(serial), AutoInc, PrimaryKey
   *  @param userid Database column userid SqlType(int4)
   *  @param eventid Database column eventid SqlType(int4)
   *  @param qrcode Database column qrcode SqlType(text) */
  case class TicketsRow(ticketid: Int, userid: Int, eventid: Int, qrcode: String)
  /** GetResult implicit for fetching TicketsRow objects using plain SQL queries */
  implicit def GetResultTicketsRow(implicit e0: GR[Int], e1: GR[String]): GR[TicketsRow] = GR{
    prs => import prs._
    TicketsRow.tupled((<<[Int], <<[Int], <<[Int], <<[String]))
  }
  /** Table description of table tickets. Objects of this class serve as prototypes for rows in queries. */
  class Tickets(_tableTag: Tag) extends profile.api.Table[TicketsRow](_tableTag, "tickets") {
    def * = (ticketid, userid, eventid, qrcode).<>(TicketsRow.tupled, TicketsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(ticketid), Rep.Some(userid), Rep.Some(eventid), Rep.Some(qrcode))).shaped.<>({r=>import r._; _1.map(_=> TicketsRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ticketid SqlType(serial), AutoInc, PrimaryKey */
    val ticketid: Rep[Int] = column[Int]("ticketid", O.AutoInc, O.PrimaryKey)
    /** Database column userid SqlType(int4) */
    val userid: Rep[Int] = column[Int]("userid")
    /** Database column eventid SqlType(int4) */
    val eventid: Rep[Int] = column[Int]("eventid")
    /** Database column qrcode SqlType(text) */
    val qrcode: Rep[String] = column[String]("qrcode")

    /** Foreign key referencing Events (database name tickets_eventid_fkey) */
    lazy val eventsFk = foreignKey("tickets_eventid_fkey", eventid, Events)(r => r.eventid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Users (database name tickets_userid_fkey) */
    lazy val usersFk = foreignKey("tickets_userid_fkey", userid, Users)(r => r.userid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Tickets */
  lazy val Tickets = new TableQuery(tag => new Tickets(tag))

  /** Entity class storing rows of table Users
   *  @param userid Database column userid SqlType(serial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(varchar), Length(255,true)
   *  @param email Database column email SqlType(varchar), Length(255,true)
   *  @param role Database column role SqlType(varchar), Length(50,true), Default(None)
   *  @param password Database column password SqlType(varchar), Length(255,true) */
  case class UsersRow(userid: Int, name: String, email: String, role: Option[String] = None, password: String)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Int], <<[String], <<[String], <<?[String], <<[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = (userid, name, email, role, password).<>(UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(userid), Rep.Some(name), Rep.Some(email), role, Rep.Some(password))).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get, _4, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column userid SqlType(serial), AutoInc, PrimaryKey */
    val userid: Rep[Int] = column[Int]("userid", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(varchar), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column email SqlType(varchar), Length(255,true) */
    val email: Rep[String] = column[String]("email", O.Length(255,varying=true))
    /** Database column role SqlType(varchar), Length(50,true), Default(None) */
    val role: Rep[Option[String]] = column[Option[String]]("role", O.Length(50,varying=true), O.Default(None))
    /** Database column password SqlType(varchar), Length(255,true) */
    val password: Rep[String] = column[String]("password", O.Length(255,varying=true))

    /** Uniqueness Index over (email) (database name users_email_key) */
    val index1 = index("users_email_key", email, unique=true)
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))

  /** Entity class storing rows of table Waivers
   *  @param waiverid Database column waiverid SqlType(serial), AutoInc, PrimaryKey
   *  @param userid Database column userid SqlType(int4)
   *  @param eventid Database column eventid SqlType(int4)
   *  @param signstatus Database column signstatus SqlType(bool) */
  case class WaiversRow(waiverid: Int, userid: Int, eventid: Int, signstatus: Boolean)
  /** GetResult implicit for fetching WaiversRow objects using plain SQL queries */
  implicit def GetResultWaiversRow(implicit e0: GR[Int], e1: GR[Boolean]): GR[WaiversRow] = GR{
    prs => import prs._
    WaiversRow.tupled((<<[Int], <<[Int], <<[Int], <<[Boolean]))
  }
  /** Table description of table waivers. Objects of this class serve as prototypes for rows in queries. */
  class Waivers(_tableTag: Tag) extends profile.api.Table[WaiversRow](_tableTag, "waivers") {
    def * = (waiverid, userid, eventid, signstatus).<>(WaiversRow.tupled, WaiversRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(waiverid), Rep.Some(userid), Rep.Some(eventid), Rep.Some(signstatus))).shaped.<>({r=>import r._; _1.map(_=> WaiversRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column waiverid SqlType(serial), AutoInc, PrimaryKey */
    val waiverid: Rep[Int] = column[Int]("waiverid", O.AutoInc, O.PrimaryKey)
    /** Database column userid SqlType(int4) */
    val userid: Rep[Int] = column[Int]("userid")
    /** Database column eventid SqlType(int4) */
    val eventid: Rep[Int] = column[Int]("eventid")
    /** Database column signstatus SqlType(bool) */
    val signstatus: Rep[Boolean] = column[Boolean]("signstatus")

    /** Foreign key referencing Events (database name waivers_eventid_fkey) */
    lazy val eventsFk = foreignKey("waivers_eventid_fkey", eventid, Events)(r => r.eventid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Users (database name waivers_userid_fkey) */
    lazy val usersFk = foreignKey("waivers_userid_fkey", userid, Users)(r => r.userid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Waivers */
  lazy val Waivers = new TableQuery(tag => new Waivers(tag))
}
