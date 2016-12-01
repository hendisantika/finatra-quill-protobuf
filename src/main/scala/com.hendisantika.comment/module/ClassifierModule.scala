package com.hendisantika.comment.module

import com.google.inject.{Provides, Singleton}
import com.hendisantika.comment.domain.dao.CommentConfig
import com.tsukaby.bayes.classifier.BayesClassifier
import com.twitter.inject.TwitterModule
import com.typesafe.config.Config

import scala.collection.JavaConversions._

/**
  * Created by hendisantika on 11/20/2016.
  */
object ClassifierModule extends TwitterModule{

  @Provides
  @Singleton
  def providesBayesClassifier(config: Config) = {

    // Create instance
    val bayes = new BayesClassifier[String, String]()

    val bayesList = config.getStringList("classifier.negatif.tokens")
    bayes.learn("negatif", bayesList)
    val negatifList = config.getStringList("classifier.negatif.regex").toList
    val regexList = negatifList.map(_.r)

    CommentConfig(bayes,regexList)
  }

}
