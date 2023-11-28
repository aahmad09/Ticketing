package models

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile", 
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost/ticketing?user=ticketing&password=will8you!attend",
    "/users/aahmad/FL23/Ticketing/server/app",
	"models", None, None, true, false
  )
}
