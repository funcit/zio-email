# zio-email

Email using [ZIO](https://zio.dev/)

### Client code

```
    val settings = MailerSettings(
      host = "smtp.gmail.com".some,
      port = 465.some,
      creds = Credentials("username@gmail.com", "password").some,
      ssl = true.some,
      auth = true.some
    )

    val e = Envelope(
      from = "username@gmail.com",
      subject = ("Some subject", Charset.defaultCharset.some).some,
      to = List("recipient@gmail.com"),
      content = Text("Nonsense")
    )

    unsafeRun(
      for{
        _ <- sendMail(e, settings)
      } yield ()
    )
```
