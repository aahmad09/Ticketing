package edu.trinity.videoquizreact

import shared.SharedMessages
import org.scalajs.dom

import slinky.core._
import slinky.web.ReactDOM
import slinky.web.html._

object ScalaJSExample {

  def main(args: Array[String]): Unit = {
    // dom.document.getElementById("scalajsShoutOut").textContent = SharedMessages.itWorks
    ReactDOM.render(
      h1("Hello, world!"),
      dom.document.getElementById("root")
    )
  }
}