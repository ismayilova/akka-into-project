package part2actors
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import part2actors.Person.LiveTheLife
object BankAccount {
   def props:Props = Props(new BankAccount)
  case class Deposit(amount:Double)
  case class Withdraw(amount:Double)
  case object Statement
}


class BankAccount extends Actor{
  import  BankAccount._
  var balance: Double =  0
  override def receive: Receive = {
    case Deposit(num) => balance +=num
      println(s" [$self] Deposit $num . Account balance  = $balance ")
    case Withdraw(num) =>balance -=num
      println(s" [$self] Withdraw $num . Account balance  = $balance ")
    case Statement => sender() ! s"Your balance is $balance " //println(s" [$self] account balance  = $balance ")

  }
}
object Person{
  case class LiveTheLife(account: ActorRef)

}

class Person extends Actor {
  import BankAccount._
  override def receive: Receive = {
    case LiveTheLife(account) =>account ! Deposit(1000)
                                account ! Withdraw(700)
                                account ! Deposit(200)
                                account ! Withdraw(500)
                                account ! Statement
    case message =>println(message.toString)
  }

}







