import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.io.StdIn

/**
  * Created by avovsya on 14/09/2016.
  */
object Server extends App {

  import akka.http.scaladsl.server.Directives._

  implicit val actorSystem = ActorSystem("akka-system")
  implicit val flowMaterializer = ActorMaterializer()
  implicit val executionContext = actorSystem.dispatcher

  val interface = "localhost"
  val port = 8000

  val route = get {
    pathEndOrSingleSlash {
      complete("Welcome to Tic-Tac-Toe")
    }
  }

  val binding = Http().bindAndHandle(route, interface, port)
  println(s"Server is listening on http://$interface:$port\nPress RETURN to stop...")

  StdIn.readLine()

  binding.flatMap(_.unbind()).onComplete(_ => actorSystem.terminate())
  println("Server is down...")
}