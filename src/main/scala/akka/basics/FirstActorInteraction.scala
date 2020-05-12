package akka.basics

import akka.actor.{Actor, ActorSystem, Props}

/**
 * It is important to note that actor communication is asynchronous in nature. They work on the message queue and can
 * work on 1 message at a time from their queue. Since it is asynchronous programming mode we do not guarantee the order
 * of execution.
 * Actor's, ActorSystem are uniquely identified in the JVM, for this they must have unique identifiers. No two actors,
 * actor systems can have the same name running on the same JVM. Also, you can only use AlphaNumeric characters when
 * naming them. No spaces are allowed. For example if an actor system name is "ActorSystem("myActor@System")", it will
 * throw: invalid ActorSystem name [myActor@System], must contain only word characters (i.e. [a-zA-Z0-9] plus
 * non-leading '-' or '_')
 */
object FirstActorInteraction extends App {
  // Step-1 generate an actor system, in general you should have only one actor system and all actors should be created from it.
  val myActorSys = ActorSystem("myActorSystem")

  // Step-2 create your own actor model(class) that extends Actor & overrides receive method of type [Any, Unit].
  class BasicActor extends Actor {
    def receive: PartialFunction[Any, Unit] = {
      case message: String => println(s"Hello to $message.")
      case _ => println("Nothing here.")
    }
  }

  // Step-3 obtain an ActorReference within the current actor system, using the actorOf() from actor system.
  val myActor = myActorSys.actorOf(Props[BasicActor], "BasicActorInstantiation")

  // Step-4 Pass messages to your actor `!` is the method define which is used by tell() internally, it returns Unit.
  myActor ! "Prashant"
  myActor ! 123

  // Step-5 Understand that you cannot create an actor using the new keyword directly, example new BasicActor(), is invalid.

  // Step-6 If the actor class had some parameters in it's constructor then how to create Actor instance? Using merely the new keyword won't work.

  class AnotherActor(name: String, age: Int) extends Actor {
    def receive: PartialFunction[Any, Unit] = {
      case msg: String => println(s"Hello $name your age is $age!")
      case _ => println("Nothing here!")
    }
  }

  val anotherActor = myActorSys.actorOf(Props(new AnotherActor("Prashant", 26)), "anotherActor")

  // communicate with the another actor that has parameters.

  anotherActor ! ("") (123)
}
