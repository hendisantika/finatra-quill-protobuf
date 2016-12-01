package com.hendisantika.comment.util

import java.time.Duration
import java.util.Date

import com.google.protobuf.duration.{Duration => ProtoDuration}
import com.google.protobuf.timestamp.{Timestamp => ProtoTimestamp}
import com.twitter.{util => twitter}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.{Failure, Success, Try}

/**
  * Created by hendisantika on 10/9/16.
  */
object Implicits {
  implicit def dateToTimestamp(d: Date): ProtoTimestamp = ProtoTimestamp(d.getTime/1000, 0)

  implicit def timestampToDate(t: ProtoTimestamp): Date = new Date(t.seconds * 1000)

  implicit def protoDurationToDuration(d: ProtoDuration): Duration = Duration.ofSeconds(d.seconds, d.nanos)

  implicit def durationToProtoDuration(d: Duration): ProtoDuration = ProtoDuration(seconds = d.getSeconds, nanos = d.getNano)

  implicit class ScalaToTwitterFuture[T](f: Future[T]) {
    def toTwitterFuture: twitter.Future[T] = scalaToTwitterFuture(f)
  }

  implicit class TwitterToScalaFuture[T](f: twitter.Future[T]) {
    def toScalaFuture: Future[T] = twitterToScalaFuture(f)
  }

  implicit def scalaToTwitterFuture[T](f: Future[T])(implicit ec: ExecutionContext): twitter.Future[T] = {
    val promise = twitter.Promise[T]()
    f.onComplete(promise update _)
    promise
  }

  implicit def twitterToScalaFuture[T](f: twitter.Future[T]): Future[T] = {
    val promise = Promise[T]()
    f.respond(promise complete _)
    promise.future
  }

  implicit def scalaToTwitterTry[T](t: Try[T]): twitter.Try[T] = t match {
    case Success(r) => twitter.Return(r)
    case Failure(ex) => twitter.Throw(ex)
  }

  implicit def twitterToScalaTry[T](t: twitter.Try[T]): Try[T] = t match {
    case twitter.Return(r) => Success(r)
    case twitter.Throw(ex) => Failure(ex)
  }
}
