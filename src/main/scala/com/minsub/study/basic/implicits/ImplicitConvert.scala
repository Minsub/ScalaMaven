package com.minsub.study.basic.implicits

/**
  * Created by gray.ji on 2018. 1. 9..
  */
object ImplicitConvert extends App {
  
  class Button {
    var clickListener: ActionListener = null
    def addClickListener(listener: ActionListener) = {
      clickListener = listener
    }
    def click() = clickListener.action(new ActionEvent {})
  }

  trait ActionListener {
    def action(event: ActionEvent): Unit
  }

  trait ActionEvent {
  }

  val button = new Button
  button.addClickListener(new ActionListener {
    override def action(event: ActionEvent): Unit = println("Twit")
  })

  button.click()

  // implicit 적용 후 간단해진 add ClickListener
  
  implicit def functionToAction(f: ActionEvent => Unit) = {
    new ActionListener {
      override def action(event: ActionEvent): Unit = f(event)
    }
  }
  
  val button2 = new Button
  button2.addClickListener((_: ActionEvent) => println("implicit Twit"))

  button2.click()
  
}
