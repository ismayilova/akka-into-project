package part2actors

import akka.actor.ActorSystem
import akka.actor.{Actor, ActorRef, Props}


object ChildActors extends App{
 //Actors can create other actors


  object Parent {
    case class CreateChild(name:String)
    case class  TellChild(message:String)
    var child:ActorRef = null;  //mutable
  }
  class Parent extends Actor{
    import Parent._
    override def receive: Receive = {
      case CreateChild(name) => println(s"${self.path}  Creating child $name")
        val childRef = context.actorOf(Props[Child] , name )
        context.become(withChild(childRef))

    }

    def withChild(ref: ActorRef) :Receive = {
      case TellChild(message) =>if(ref !=null) ref forward(message)
    }

  }

  class Child extends Actor{
    override def receive: Receive = {
      case messae =>println(s"${self.path} I got message : $messae")
    }
  }



  val parent = ActorSystem("System").actorOf(Props[Parent],"Parent")
  import Parent._
  parent ! CreateChild("Kamila")
  parent ! TellChild("Hey baby ")
}
