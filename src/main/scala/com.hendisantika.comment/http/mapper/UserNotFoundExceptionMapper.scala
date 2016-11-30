package com.hendisantika.comment.http.mapper

import com.google.inject.{Inject, Singleton}
import com.hendisantika.comment.http.UserNotFoundException
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.exceptions.ExceptionMapper
import com.twitter.finatra.http.response.ResponseBuilder

/**
  * Created by hendisantika on 10/9/16.
  */
@Singleton
class UserNotFoundExceptionMapper @Inject()(response: ResponseBuilder)
  extends ExceptionMapper[UserNotFoundException] {

  override def toResponse(request: Request, throwable: UserNotFoundException): Response = {
    response.unauthorized(s"User not found - ${throwable.getMessage}")
  }
}