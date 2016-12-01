package com.hendisantika.comment.server

import com.google.inject.Module
import com.hendisantika.comment.controller.{CommentAlterationController, CommentMetadataController}
import com.hendisantika.comment.domain.msg.CommentInsertion._
import com.hendisantika.comment.domain.msg.CommentsAlteration.CommentRemoval
import com.hendisantika.comment.http.mapper.{InsufficientBalanceExceptionMapper, InvalidTokenExceptionMapper, UserNotFoundExceptionMapper}
import com.hendisantika.comment.marshalling.{SProtobufMessageBodyReader, SProtobufMessageBodyWriter}
import com.hendisantika.comment.module._
import com.hendisantika.model.proto.Exception.MSException
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, ExceptionMappingFilter, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.inject.Logging

import scala.language.postfixOps

/**
  * Created by hendisantika on 8/25/16.
  */
object APIServerMain extends APIServer

class APIServer extends HttpServer with Logging {

  private val mainThread = Thread.currentThread()

  protected override def defaultFinatraHttpPort: String = ":9000"

  override def defaultHttpPort: Int = 6665

  protected override def failfastOnFlagsNotParsed: Boolean = true

  override protected def modules: Seq[Module] = Seq(
    TypesafeConfigModule,
    QuillDatabaseModule,
    ClassifierModule
  )

  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      // All Exchange Message should be register here to take advantage of auto response handler of ProtobufMessageBodyWriter
      .register[SProtobufMessageBodyWriter, MSException]
      .register[SProtobufMessageBodyWriter, CommentRemoval]
      .register[SProtobufMessageBodyWriter, CommentInsertResponse]
      .register[SProtobufMessageBodyWriter, CommentListResponse]
      .register[SProtobufMessageBodyWriter, CommentFilterResponse]

      // All Exchange Message should be register here to take advantage of auto request builder of ProtobufMessageBodyReader
      .register[SProtobufMessageBodyReader[CommentRemoval]]
      .register[SProtobufMessageBodyReader[CommentInsertion]]
      .register[SProtobufMessageBodyReader[CommentFilter]]

      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .filter[ExceptionMappingFilter[Request]]

      .add[CommentAlterationController]
      .add[CommentMetadataController]

      .exceptionMapper[InvalidTokenExceptionMapper]
      .exceptionMapper[UserNotFoundExceptionMapper]
      .exceptionMapper[InsufficientBalanceExceptionMapper]
  }


}