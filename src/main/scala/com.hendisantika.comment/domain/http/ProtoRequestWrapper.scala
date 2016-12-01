package com.hendisantika.comment.domain.http

import com.google.protobuf.Message
import com.twitter.finagle.http.Request

/**
  * Created by hendisantika on 9/15/16.
  */
case class JProtoRequestWrapper[T <: Message](wrapper: T, request: Request)
