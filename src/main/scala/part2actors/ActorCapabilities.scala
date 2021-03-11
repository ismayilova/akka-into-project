package part2actors

import akka.actor.{Actor, ActorSystem, Props}

object ActorCapabilities extends App {
  val system = ActorSystem("actorCapabilitiesDemo")

  val simpleActor = system.actorOf(Props[SimpleActor] , "simpleActor")

  simpleActor ! "Hello ,Actjjjjor"
  simpleActor ! SpecialMessage("Really special !")
}


class SimpleActor extends  Actor {
  override def receive: Receive = {
    case message:String =>println(s"[${context.self}] i have received $message")
    case  n:Int =>println("I ahev a integer " + n)
    case  SpecialMessage(message:String) =>println(s"A very special $message")
  }
}


case class SpecialMessage(content:String)

/*   1 Counter actor
*   -Increment message
*   -Decrement message
*
*
*   2 Bank account as an account
*    --Deposit an amount
*    - Withdraw an amount
*  - Statement
*   Replies with
*  --Reply with Success/Failure
* */
