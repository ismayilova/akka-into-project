package part2actors
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import part2actors.ChangingActorBehavior.Mom.MomStart
object ChangingActorBehavior extends App{

 val system = ActorSystem("ChangingActorBeh")
  val mom = system.actorOf(Props[Mom],"mom")
  val kid  = system.actorOf(Props[FussyKid])
  val  statelessFussyKid = system.actorOf(Props[StatelessFussyKid])
  mom !MomStart(statelessFussyKid)




  //KID
  object  FussyKid {
    case object KidAccept
    case object KidReject
    val HAPPY = "happy"
    val SAD = "sad"
  }
  class FussyKid extends Actor{
    import FussyKid._
    import Mom._
    var state = HAPPY
    override def receive: Receive = {
      case Food(VEGETABLE) =>state = SAD
      case Food(CHOCOLATE) =>state = HAPPY
      case Ask(_) =>
         if (state ==HAPPY) sender() ! KidAccept
         else sender() ! KidReject
    }
  }
  class StatelessFussyKid extends Actor{
    import Mom._
    import FussyKid._
    override def receive: Receive = happyReceive

    def happyReceive:Receive = {
      case Food(VEGETABLE) => context.become(sadReceive)
      case Food(CHOCOLATE) =>
      case Ask(_) =>sender() ! KidAccept
    }
    def sadReceive:Receive = {
      case Food(VEGETABLE) => //stay sad
      case Food(CHOCOLATE) =>context.become(happyReceive) //change to happy
      case Ask(_) =>sender() ! KidReject
    }
  }

  //MOM
  object Mom {
    case class MomStart(kid:ActorRef)
    case class Food(food:String)
    case class Ask(message:String)
    val VEGETABLE = "veggies"
    val CHOCOLATE = "chocolate"
  }
  class  Mom extends Actor{
    import Mom._
    import FussyKid._
    override def receive: Receive = {
      case MomStart(kid) =>
          kid ! Food(VEGETABLE)
          kid ! Ask("Do you want to play?")
      case KidAccept =>println("Yye kid is happy")
      case KidReject =>println("My kid is sad but healthy")
    }
  }

  /*
  * 1. Counter Actor  -> with context become with no state
  * 2. - Simplified voting system
  *   Citizen  - Vote(cnadidate:String) ->then cictien state wiill be have voted
  *   VoteAggregator  - AggreagtesVotes(citizens: Set[ActorRef] )
  * case object VoteStatusRequest
  *

  * */

}
