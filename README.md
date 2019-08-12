# zio-email

[![Build Status](https://travis-ci.org/funcit/zio-email.svg?branch=master)](https://travis-ci.org/funcit/zio-email) 
[![Download](https://api.bintray.com/packages/funcit/zio/zio-email/images/download.svg?version=0.1.0) ](https://bintray.com/funcit/zio/zio-email/0.1.0/link)

Email using [ZIO](https://zio.dev/)

## Getting started

Add zio-email dependency to your `build.sbt`:

`libraryDependencies += "com.funcit" %% "zio-email" % "0.1.0"`

## Client code

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
