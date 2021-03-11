package akkaTyped

import akka.actor.typed.ActorSystem


object Main {

import Counter._
  def main(args: Array[String]): Unit = {
    println("Start actor")

    val actor1 = ActorSystem( Counter() , "FirstActor");

    (1 to 10).foreach(_=> actor1 ! Increment)
    actor1 ! Print
    actor1 ! Decrement
    actor1 ! Print
  }

}
