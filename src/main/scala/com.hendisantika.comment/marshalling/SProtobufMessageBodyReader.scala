package com.hendisantika.comment.marshalling

import java.time.format.DateTimeParseException

import com.google.common.net.MediaType._
import com.trueaccord.scalapb.json.{JsonFormat, JsonFormatException}
import com.trueaccord.scalapb.{GeneratedMessage, GeneratedMessageCompanion, Message}
import com.twitter.finagle.http.{MediaType, Request}
import com.twitter.finatra.http.exceptions.BadRequestException
import com.twitter.finatra.http.marshalling.MessageBodyReader
import com.twitter.finatra.http.request.MediaRange
import com.hendisantika.comment.util.ReflectionSugars._

import scala.reflect._

/**
  * Created by hendisantika on 10/2/16.
  */
class SProtobufMessageBodyReader[PM <: GeneratedMessage with Message[PM] with RequestAware] extends MessageBodyReader[PM] {

  override def parse[M <: PM : Manifest](request: Request): PM = {
    val contentType = request.contentType.getOrElse(PROTOBUF.toString)
    val mediaRanges = MediaRange.parseAndSort(contentType)

    val companion = companionOf[M, GeneratedMessageCompanion[PM]]

    mediaRanges.collectFirst({
      case mr if mr.accepts(PROTOBUF.toString) =>
        val proto = companion.parseFrom(request.getInputStream())
        proto.request = request
        proto
      case mr if mr.accepts(MediaType.Json) =>
        try {
          val proto = JsonFormat.fromJsonString[PM](request.getContentString())(companion)
          proto.request = request
          proto
        }catch{
          case _:JsonFormatException =>
            throw new BadRequestException("Bad JSON format !")
          case _:DateTimeParseException =>
            throw new BadRequestException("Bad JSON format, timestamp should be ISO-8601 format!")
        }
    }).getOrElse(
      throw new BadRequestException("Invalid Content-Type")
    )
  }
}