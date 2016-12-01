package com.hendisantika.comment.marshalling

import com.google.common.net.MediaType._
import com.trueaccord.scalapb.GeneratedMessage
import com.trueaccord.scalapb.json.JsonFormat
import com.twitter.finagle.http.{MediaType, Request}
import com.twitter.finatra.http.HttpHeaders
import com.twitter.finatra.http.exceptions.NotAcceptableException
import com.twitter.finatra.http.marshalling.{MessageBodyWriter, WriterResponse}
import com.twitter.finatra.http.request.MediaRange

/**
  * Created by hendisantika on 10/2/16.
  */
class SProtobufMessageBodyWriter extends MessageBodyWriter[GeneratedMessage]{
  override def write(request: Request, obj: GeneratedMessage): WriterResponse = {
    val acceptHeader = request.headerMap.getOrElse(HttpHeaders.Accept, "*/*")
    val mediaRanges = MediaRange.parseAndSort(acceptHeader)

    mediaRanges.collectFirst({
      case mr if mr.accepts(PROTOBUF.toString) => WriterResponse(PROTOBUF, obj.toByteArray)
      case mr if mr.accepts(MediaType.Json) => WriterResponse(JSON_UTF_8, JsonFormat.toJsonString(obj))
    }).getOrElse(
      throw new NotAcceptableException(PLAIN_TEXT_UTF_8, Seq("Not Acceptable Media Type"))
    )
  }

  override def write(obj: GeneratedMessage): WriterResponse =
    write(Request(), obj)
}
