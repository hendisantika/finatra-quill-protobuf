package com.hendisantika.comment.marshalling

import java.io.InputStream

import com.google.common.net.MediaType._
import com.google.protobuf.Message
import com.google.protobuf.Message.Builder
import com.google.protobuf.util.JsonFormat
import com.hendisantika.comment.domain.http.JProtoRequestWrapper
import com.twitter.finagle.http.{MediaType, Request}
import com.twitter.finatra.http.exceptions.BadRequestException
import com.twitter.finatra.http.marshalling.MessageBodyReader
import com.twitter.finatra.http.request.MediaRange

import scala.reflect._
import scala.reflect.runtime.universe._

/**
  * Created by hendisantika on 9/9/16.
  */
class JProtobufMessageBodyReader extends MessageBodyReader[JProtoRequestWrapper[Message]] {
  val mParseFrom = "parseFrom"
  val mNewBuilder = "newBuilder"

  def parse[M <: JProtoRequestWrapper[Message] : Manifest](request: Request): JProtoRequestWrapper[Message] = {
    val contentType = request.contentType.getOrElse(PROTOBUF.toString)
    val mediaRanges = MediaRange.parseAndSort(contentType)

    val t = typeTag[M]
    val cls = t.mirror.runtimeClass(t.tpe.typeArgs.head)

    mediaRanges.collectFirst({
      case mr if mr.accepts(PROTOBUF.toString) =>
        val msg = cls.getDeclaredMethod(mParseFrom, classOf[InputStream])
          .invoke(null, request.getInputStream())
          .asInstanceOf[Message]

        JProtoRequestWrapper[Message](msg, request)
      case mr if mr.accepts(MediaType.Json) =>
        val builder = cls.getDeclaredMethod(mNewBuilder).invoke(null).asInstanceOf[Builder]
        JsonFormat.parser().merge(request.getContentString(), builder)

        JProtoRequestWrapper[Message](builder.build(), request)
    }).getOrElse(
      throw new BadRequestException("Invalid Content-Type")
    )
  }
}
