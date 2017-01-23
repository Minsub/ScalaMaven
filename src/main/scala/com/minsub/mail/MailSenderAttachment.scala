package com.minsub.mail

import java.net.URL

import org.apache.commons.mail._

object MailSenderAttachment extends App {
  // Create the attachment
  val attachment: EmailAttachment = new EmailAttachment
  attachment.setPath("C:/image.jpg")
  attachment.setDisposition(EmailAttachment.ATTACHMENT)
  attachment.setDescription("Picture of John")
  attachment.setName("flink.jpg")

  // Create the attachment with URL
  val attachment2: EmailAttachment = new EmailAttachment
  attachment2.setURL(new URL("http://www.apache.org/images/asf_logo_wide.gif"))
  attachment2.setDisposition(EmailAttachment.ATTACHMENT)
  attachment2.setDescription("Apache logo")
  attachment2.setName("Apache-logo.gif")

  // Create the email message
  val email: MultiPartEmail = new MultiPartEmail

  // by Google
  email.setHostName("smtp.googlemail.com")
  email.setSmtpPort(465);
  email.setAuthenticator(new DefaultAuthenticator(MailInfo.ID, MailInfo.PW));
  email.setSSLOnConnect(true);
  email.setFrom(MailInfo.ID)

  // by HMM Mail
//  email.setHostName("mailing.hmm21.com")
//  email.setFrom("test@hmm21.com", "test-mail-server")

  email.addTo("minsub.ji@hmm21.com", "minsub Ji")
  email.setSubject("test email with attachments")
  email.setMsg("Here is the picture you wanted")

  // add the attachment
  email.attach(attachment)
  email.attach(attachment2)

  // send the email
  email.send
  System.out.println("complete to send email")
}
