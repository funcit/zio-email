package zio.mail.client

import java.nio.charset.Charset

sealed trait Content

final case class Text(
  body: String,
  charset: Charset = Charset.defaultCharset
) extends Content
