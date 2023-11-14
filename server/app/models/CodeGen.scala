package models

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile", 
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost/aahmad?user=aahmad&password=0854523",
    "/users/aahmad/FL23/Ticketing/server/app",
	"models", None, None, true, false
  )
}
