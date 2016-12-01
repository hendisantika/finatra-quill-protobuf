package com.hendisantika.comment.module

import com.google.inject.{Provides, Singleton}
import com.twitter.inject.TwitterModule
import com.typesafe.config.Config
import io.getquill.{FinagleMysqlContext, SnakeCase}

/**
  * Created by hendisantika on 9/11/16.
  */
object QuillDatabaseModule extends TwitterModule{

  private val prefix = "quill.db"

  @Singleton
  @Provides
  def providesDatabaseSource(config: Config) = {
    val c = new FinagleMysqlContext[SnakeCase](config.getConfig(prefix))
    c
  }
}
