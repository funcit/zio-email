package zio.mail.client

import java.util.Properties

import javax.mail.Message.RecipientType
import javax.mail.internet.{InternetAddress, MimeMessage}
import javax.mail.{Authenticator, PasswordAuthentication, Session, Transport}
import zio.{Task, ZIO}

object MailSession {

  implicit class String2InternetAddress(address: String) {
    def toInternetAddress: InternetAddress = new InternetAddress(address)
  }

  private def auth(mailer: MailerSettings): Option[Authenticator] =
    mailer.creds.map { c =>
      new Authenticator {
        override def getPasswordAuthentication: PasswordAuthentication =
          new PasswordAuthentication(c.username, c.password)
      }
    }

  private def getSession(mailer: MailerSettings): ZIO[Any, Throwable, Session] =
    for {
      props <- ZIO.effect {
        val properties = new Properties(System.getProperties)
        mailer.host.foreach(properties.put("mail.smtp.host", _))
        mailer.port.foreach(p => properties.put("mail.smtp.port", p.toString))
        mailer.auth.foreach(a => properties.put("mail.smtp.auth", a.toString))
        mailer.startTls.foreach(s => properties.put("mail.smtp.starttls.enable", s.toString))
        mailer.ssl.foreach(s => properties.put("mail.smtp.ssl.enable", s.toString))
        mailer.trustAll.collect { case true => properties.put("mail.smtp.ssl.trust", "*") }
        mailer.socketFactory.foreach(s => properties.put("mail.smtp.socketFactory.class", s))
        mailer.socketFactoryPort.foreach(s => properties.put("mail.smtp.socketFactory.port", s))
        properties
      }
      auth    <- ZIO.effect(auth(mailer))
      session <- ZIO.effect(Session.getInstance(props, auth.orNull))
    } yield session

  private def processEnvelope(e: Envelope, message: MimeMessage): ZIO[Any, Throwable, MimeMessage] = {
    for {
      _ <- ZIO.effect(message.addFrom(Array(e.from.toInternetAddress)))
      _ <- ZIO.effect {
        e.subject match {
          case Some((subject, _)) =>
            message.setSubject(subject)
          case Some((_, _)) =>
            message.setSubject("")
          case None =>
            message.setSubject("")
        }
      }
      _ <- ZIO.effect(e.to.foreach(x => message.addRecipient(RecipientType.TO, x.toInternetAddress)))
      _ <- ZIO.effect(e.cc.foreach(x => message.addRecipient(RecipientType.CC, x.toInternetAddress)))
      _ <- ZIO.effect(e.bcc.foreach(x => message.addRecipient(RecipientType.BCC, x.toInternetAddress)))
      _ <- ZIO.effect(e.replyTo.foreach(x => message.setReplyTo(Array(x.toInternetAddress))))
      _ <- ZIO.effect(e.headers.foreach(x => message.addHeader(x._1, x._2)))
      _ <- ZIO.effect {
        e.content match {
          case Text(t, c) => message.setText(t, c.displayName)
        }
      }
    } yield message
  }

  def sendMail(mail: Envelope, settings: MailerSettings): Task[Unit] = {
    for {
      session <- getSession(settings)
      message <- processEnvelope(mail, new MimeMessage(session))
      _       <- ZIO.effect(Transport.send(message))
    } yield ()
  }
}
