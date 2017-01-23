package com.minsub.mail

import java.net.URL

import org.apache.commons.mail._

object MailSenderHtml extends App {
  // Create the email message
  val email: HtmlEmail = new HtmlEmail

  // by google
  email.setHostName("smtp.googlemail.com")
  email.setSmtpPort(465);
  email.setAuthenticator(new DefaultAuthenticator(MailInfo.ID, MailInfo.PW));
  email.setSSLOnConnect(true);
  email.setFrom(MailInfo.ID)

  // by HMM Mail
//  email.setHostName("mailing.hmm21.com")
//  email.setFrom("test@hmm21.com", "mail-server")

  email.addTo("minsub.ji@hmm21.com", "MS Ji")
  email.setSubject("Test email with inline image")

  // embed the image and get the content id
  val url: URL = new URL("http://www.apache.org/images/asf_logo_wide.gif")
  val cid: String = email.embed(url, "Apache logo")

  // set the html message
  email.setHtmlMsg("<html>The apache logo - <img src=\"cid:" + cid + "\"></html>")

  // set the alternative message
  email.setTextMsg("Your email client does not support HTML messages")

  // send the email
  email.send
  System.out.println("complete to send email")
}
