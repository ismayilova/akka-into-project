package akkaTyped

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object Counter {

trait CounterMessages
  case object Increment extends CounterMessages
  case object Decrement extends CounterMessages
  case object Print extends CounterMessages


  def apply() = receive(0)


  def receive(n:Int):Behavior[CounterMessages] = Behaviors.receiveMessage[CounterMessages]{
    case Increment =>receive(n+1)
    case Decrement =>receive(n-1)
    case Print => println(s"The value is $n ")
    Behaviors.same

  }

}
