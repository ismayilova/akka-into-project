package part2actors
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
object VotingExercise  extends App {


 object Citizen {
   case class Vote(candidate:String)
   case object VoteStatusRequest
   case class VoteStatusReply(candidate:Option[String])
 }
  class Citizen extends Actor{
    override def receive: Receive = ???
  }

case class AggregatesVotes(citizens:Set[ActorRef])
  class VotingAggregator extends Actor{
    override def receive: Receive = ???
  }

}
