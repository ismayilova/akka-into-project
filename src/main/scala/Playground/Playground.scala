package Playground

import akka.actor.{Actor, Props,ActorSystem}

object Playground extends App{
 val actorSystem = ActorSystem("firstActorSystem")
  println(actorSystem.name)

  //instantiate our actor
  val wordCountActor = actorSystem.actorOf(Props[WordCountActor],"wordCounter")
  val anotherWordCounter = actorSystem.actorOf(Props[WordCountActor], "anotherWordCounter")
  //communicate
  wordCountActor ! "Hello World"
  wordCountActor ! "I am learning Akka."
  anotherWordCounter ! "I am another"
  anotherWordCounter ! "dd dd d d d d d"
}


object MySimpleMessageActor  {
def props:Props =Props(new MySimpleMessageActor)

}

class MySimpleMessageActor extends Actor {
  override def receive:Receive = {
    case _ =>println("Message")}
}








class WordCountActor extends Actor{
  //Internal data
  var totalWord = 0

  //behavior
  override def receive: Receive = {
    case message:String =>
      totalWord +=message.split(" ").length
      println(s"I have receiced : $message")
      println(s"${sender()} send total amount of   = $totalWord   words")
    case _=> println(s"[WordCounter] I dont understand that .... ")
  }
}