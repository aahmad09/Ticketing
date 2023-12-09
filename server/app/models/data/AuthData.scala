package models

final case class LoginData(email: String, password: String)
final case class RegistrationData(name: String, email: String, password: String, role: String)
