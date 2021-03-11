package akkaTyped

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object TypedStatelessActor {

  trait SimpleThing
  case object EatChocolate extends SimpleThing
  case object  WashDishes extends SimpleThing
  case object  LearnAkka extends SimpleThing
  case object  Res extends SimpleThing


  val emotionalMutableActor:Behavior[SimpleThing]  = Behaviors.setup{context =>
     //spin up the actor state
    var happiness = 0
    Behaviors.receiveMessage[SimpleThing]{
      case EatChocolate =>println(s"$happiness Eating chocolate")
        happiness+=1
        Behaviors.same
      case WashDishes =>println(s"$happiness Wahsing dishes")
        happiness-=1
        Behaviors.same
      case LearnAkka =>println(s"$happiness Learning Akka")
        happiness+=100
        Behaviors.same
      case _ =>println(s"$happiness Common")
         Behaviors.same
    }
  }

  def emotionalFunctionalActor(happiness:Int = 0):Behavior[SimpleThing] = Behaviors.receive{
    (context , message) => message match {
      case EatChocolate =>println(s"$happiness Eating chocolate")
        emotionalFunctionalActor(happiness+1)  //new Behavior!!!
      case WashDishes =>println(s"$happiness Wahsing dishes")
        emotionalFunctionalActor(happiness-2)
      case LearnAkka =>println(s"$happiness Learning Akka")
        emotionalFunctionalActor(happiness+100)
      case _ =>println(s"$happiness Common")
        Behaviors.same
    }
  }


  def main(args: Array[String]): Unit = {
  //Define Actor system
    val emotionalActorSystem = ActorSystem(emotionalMutableActor , "EmotionalActor")
    emotionalActorSystem ! EatChocolate
    emotionalActorSystem ! EatChocolate
    emotionalActorSystem !WashDishes
    emotionalActorSystem ! LearnAkka
    emotionalActorSystem ! Res


     Thread.sleep(1000)
     emotionalActorSystem.terminate()


    println("NEw Actor")
    val statelessActor = ActorSystem(emotionalFunctionalActor() , "StateLess")
    statelessActor !EatChocolate
    statelessActor  ! EatChocolate
    statelessActor ! WashDishes
    statelessActor ! LearnAkka

    statelessActor ! Res

    Thread.sleep(1000)
    statelessActor.terminate()



  }

}






