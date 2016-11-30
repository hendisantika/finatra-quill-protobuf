package com.hendisantika.comment.http.mapper

import com.google.inject.{Inject, Singleton}
import com.hendisantika.comment.http.InsufficientBalanceException
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.exceptions.ExceptionMapper
import com.twitter.finatra.http.response.ResponseBuilder

/**
  * Created by hendisantika on 10/17/16.
  */
@Singleton
class InsufficientBalanceExceptionMapper @Inject()(response: ResponseBuilder)
  extends ExceptionMapper[InsufficientBalanceException] {

  override def toResponse(request: Request, throwable: InsufficientBalanceException): Response = {
    response.badRequest(s"Insufficient balance - ${throwable.getMessage}")
  }
}
