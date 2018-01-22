package com.minsub.akka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.event.Logging

class Hello(val hello: String) extends Actor {
  val log = Logging(context.system, this)
  override def receive: Receive = {
    case "hello" => log.info(s"Received a${hello}")
    case msg => {
      log.info(s"Unexpected message ${msg}")
      context.stop(self)
    }
  }
}

object HelloAkka extends App {
  val mySystem = ActorSystem("mySystem")

  val hiActor: ActorRef = mySystem.actorOf(Props(new Hello("Hi")), name="greeter")
  hiActor ! "hi"
  Thread.sleep(1000)

  hiActor ! 3
  Thread.sleep(1000)

  mySystem.terminate()
}


