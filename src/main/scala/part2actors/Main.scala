package part2actors

import akka.actor.{Actor, ActorSystem, Props}
import part2actors.Person.LiveTheLife

object Main extends App {
  import Counter._
  val system  = ActorSystem("ExerciseActor")

  val counter1 = system.actorOf(Counter.props , "FirstCounter")

  counter1 ! Decrement(75) // 0-75 -> -75
  counter1 ! Increment(5)  //-75+5 = -70
  counter1  ! Increment  // -69
  counter1 ! Print  //69



//  import BankAccount._
//  val system = ActorSystem("BankAccountSystem")
//  val bankAccount =system.actorOf(BankAccount.props , "bank-account")
//  val person  = system.actorOf(Props[Person],"Kamilai")
//
//  person ! LiveTheLife(bankAccount)

}
