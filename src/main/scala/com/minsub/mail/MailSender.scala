package com.minsub.mail

import org.apache.commons.mail._

object MailSender extends App {
  val email: Email = new SimpleEmail

  // by google
  email.setHostName("smtp.googlemail.com")
  email.setSmtpPort(465);
  email.setAuthenticator(new DefaultAuthenticator(MailInfo.ID, MailInfo.PW));
  email.setSSLOnConnect(true);
  email.setFrom(MailInfo.ID)

  // by HMM mail
//  email.setHostName("mailing.hmm21.com")
//  email.setFrom("test@hmm21.com")

  email.setSubject("TestMail")
  email.setMsg("This is a test mail ... :-)")
  email.addTo("minsub.ji@hmm21.com")
  email.send
  println("complete to send email")
}
