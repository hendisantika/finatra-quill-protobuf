package com.hendisantika.comment.http.mapper

import com.google.inject.{Inject, Singleton}
import com.hendisantika.comment.http.InvalidTokenException
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.exceptions.ExceptionMapper
import com.twitter.finatra.http.response.ResponseBuilder

/**
  * Created by hendisantika on 9/7/16.
  */
@Singleton
class InvalidTokenExceptionMapper @Inject()(response: ResponseBuilder)
  extends ExceptionMapper[InvalidTokenException] {

  override def toResponse(request: Request, throwable: InvalidTokenException): Response = {
    response.unauthorized(s"Invalid Token - ${throwable.getMessage}")
  }
}
