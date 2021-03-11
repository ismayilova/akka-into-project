package part2actors

import akka.actor.{Actor, ActorSystem, Props}

object Counter {
  def props:Props = Props(new Counter)
  case class Increment(number:Int)
  case class Decrement(number:Int)
  case object Increment
  case object Decrement
  case object Print



}

class Counter  extends Actor {
   var num:Int  = 0
  import  Counter._
  override def receive: Receive = countReceive(0)

  def countReceive(n:Int) :Receive = {
    case Increment =>
      println(s"$self is incrementing $n to ${n+1}" )
      context.become(countReceive(n+1))
    case Decrement =>
      println(s"$self is decrementing $n to ${n-1}" )
      context.become(countReceive(n-1))

    case Increment(i) =>
      println(s"$self is incrementing $n to ${n+i}" )
      context.become(countReceive(n+i))
    case Decrement(i) =>
      println(s"$self is decrementing $n to ${n-i}" )
      context.become(countReceive(n-i))
    case Print =>println(s"The res is $n")
  }
}




