package part2actors

import akka.actor.ActorLogging
import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import akka.event.Logging

object ConfigDemo {
import MainActor._

  def main(args: Array[String]): Unit = {
    val actor  = ActorSystem(MainActor() , "MainActor")
    actor ! Start
  }


}




object MainActor  extends  {
 trait MessagesProtocol
  case object Start extends MessagesProtocol
  case object Middle extends MessagesProtocol
  case object End extends MessagesProtocol


  def apply(): Behavior[MessagesProtocol] = Behaviors.receive{
    (context , message) => message match {

      case Start =>println(s"Start : context =$context ")

        Behaviors.same
      case Middle =>println("Middle")
        Behaviors.same
      case End =>println("End")
        Behaviors.same
      Behaviors.same
    }

  }

}
