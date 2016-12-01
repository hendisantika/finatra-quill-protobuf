package com.hendisantika.comment.module

import com.github.racc.tscg.{TypesafeConfigModule => TCModule}
import com.google.inject.{Provides, Singleton}
import com.twitter.inject.{Logging, TwitterModule}
import com.typesafe.config.ConfigFactory

/**
  * Created by hendisantika on 9/11/16.
  */
object TypesafeConfigModule extends TwitterModule with Logging{
  val mode = flag("mode", "dev", "application run mode [dev:default, alpha, sandbox, beta, real]")

  val configPath = "conf/"

  @Provides
  @Singleton
  def provideConfig = config

  protected override def configure(): Unit = {
    install(TCModule.fromConfigWithPackage(config, "com.hendisantika.comment"))
  }

  private lazy val config = {
    info(s"LOADING CONFIG FROM: ${mode()}")

    ConfigFactory load (configPath + mode())
  }
}
