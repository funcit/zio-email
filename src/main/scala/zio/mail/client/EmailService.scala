package zio.mail.client

import zio.Task

trait EmailService {
  def emailService: EmailService.Service
}

object EmailService {
  trait Service {
    def sendMail(envelope: Envelope, settings: MailerSettings): Task[Unit]
  }

  trait Live extends EmailService{
    override def emailService: EmailService.Service = {
      new EmailService.Service {
        override def sendMail(envelope: Envelope, settings: MailerSettings): Task[Unit] =
          for {
           _ <- MailSession.sendMail(envelope, settings)
          }yield()
      }
    }
  }
}



