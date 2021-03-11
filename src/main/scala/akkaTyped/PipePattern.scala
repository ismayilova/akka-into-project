package akkaTyped

import scala.concurrent.{ExecutionContext, Future}
import java.util.concurrent.Executors

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

import scala.util.{Failure, Success}

object PipePattern {

  //Encapsulation
  object Infrastructure {
    private implicit val ec:ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(8))

     private val db:Map[String , Int] = Map(
       "D"->123 ,
       "K" ->543 ,
       "B" ->999
     )

    def asyncRetrievePhoneNumber(name:String):Future[Int] =
      Future(db(name))

  }


  trait PhoneCallProtocol
  case class  FindAndCallPhoneNumer(name:String) extends PhoneCallProtocol
  case  class InitiatePhoneCall(number:Int)  extends PhoneCallProtocol
  case class LogPhoneCallFailure(reason:Throwable) extends PhoneCallProtocol
  //Actor
  val phoneCallInitiatorV1:Behavior[PhoneCallProtocol] = Behaviors.setup{
    context =>
      var nPhoneCalls = 0
      var nFailures = 0
      implicit val ec:ExecutionContext = context.system.executionContext //???
     Behaviors.receiveMessage{
       case FindAndCallPhoneNumer(name) =>
         val futureNumber:Future[Int] = Infrastructure.asyncRetrievePhoneNumber(name)
         futureNumber.onComplete{ //Happens on another thread
           case Success(number) =>println(s"Initiating phone call for $number")
           nPhoneCalls +=1
           case Failure(exception)  => println(s"Exception for  $name $exception")
             nFailures+=1
         }
         Behaviors.same
     }
  }
//


  //Pipe Pattern = forward the result of a future back to me as a message

  val phoneCallInitiatorV2:Behavior[PhoneCallProtocol] = Behaviors.setup{
    context =>
      var nPhoneCalls = 0
      var nFailures = 0
      implicit val ec:ExecutionContext = context.system.executionContext //???
      Behaviors.receiveMessage{
        case FindAndCallPhoneNumer(name) =>
          val futureNumber:Future[Int] = Infrastructure.asyncRetrievePhoneNumber(name)
          context.pipeToSelf(futureNumber){
            case Success(number) => InitiatePhoneCall(number)
            case Failure(ex) =>LogPhoneCallFailure(ex)
          }

          Behaviors.same

        case InitiatePhoneCall(number)  =>println(s"Initiating phone call for $number")
          nPhoneCalls +=1
          Behaviors.same
        case LogPhoneCallFailure(reason) =>   println(s"Exception for   $reason")
          nFailures+=1
          Behaviors.same
      }
  }


  def phoneCallInitiatorV3(nPhoneCalls:Int = 0 , nFailures:Int = 0):Behavior[PhoneCallProtocol]= Behaviors.setup{
    context =>
      Behaviors.receiveMessage{
        case FindAndCallPhoneNumer(name) =>
           val futureNumber = Infrastructure.asyncRetrievePhoneNumber(name)
          context.pipeToSelf(futureNumber){
            case Success(name) =>InitiatePhoneCall(name)
            case Failure(exception) =>LogPhoneCallFailure(exception)
          }

          Behaviors.same
        case InitiatePhoneCall(number) =>
          println(s"Initiating phone call for $number")
        phoneCallInitiatorV3(nPhoneCalls+1, nFailures)
        Behaviors.same

        case LogPhoneCallFailure(ex)=>println(s"Exception for   $ex")
        phoneCallInitiatorV3(nPhoneCalls , nFailures+1)
        Behaviors.same
      }
  }




  def main(args: Array[String]): Unit = {
   val rooActor = ActorSystem(phoneCallInitiatorV3(), "PhoneCaller")
    rooActor ! FindAndCallPhoneNumer("K")
    rooActor ! FindAndCallPhoneNumer("Mom")

    Thread.sleep(1000)
    rooActor.terminate()
  }

}
