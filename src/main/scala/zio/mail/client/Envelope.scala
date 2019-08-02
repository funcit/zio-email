package zio.mail.client

import java.nio.charset.Charset

final case class Envelope(
  from: String,
  subject: Option[(String, Option[Charset])] = None,
  to: List[String] = List.empty[String],
  cc: List[String] = List.empty[String],
  bcc: List[String] = List.empty[String],
  replyTo: Option[String] = None,
  replyToAll: Option[Boolean] = None,
  headers: List[(String, String)] = List.empty[(String, String)],
  content: Content = Text("")
)
