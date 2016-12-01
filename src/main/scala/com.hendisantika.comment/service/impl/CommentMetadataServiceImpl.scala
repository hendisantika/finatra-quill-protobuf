package com.hendisantika.comment.service.impl

import java.text.SimpleDateFormat
import java.util.Date

import com.google.inject.Inject
import com.google.protobuf.timestamp.Timestamp
import com.hendisantika.comment.domain.dao.{CommentConfig, CommentInsert, CommentObject, CommentRequest}
import com.hendisantika.comment.domain.msg.CommentInsertion._
import com.hendisantika.comment.repository.db.CommentRepository
import com.hendisantika.comment.service.CommentMetadataService
import com.twitter.inject.Logging
import com.twitter.util.Future
import com.typesafe.config.Config

import scala.util.matching.Regex

/**
  * Created by hendisantika on 11/11/16.
  */
class CommentMetadataServiceImpl @Inject()(dbRepository: CommentRepository, filterComment: CommentConfig, config: Config) extends CommentMetadataService with Logging {

  //  val sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
  val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  var submittedDateConvert = new Date()
  var tgl = sdf.format(submittedDateConvert)
  val date = new java.util.Date()
  val timestamp = new java.sql.Timestamp(date.getTime())

  val ttl = "redis.ttl"
  val score = "redis.score"

  def decodeFromBase64(words: String): String = {
    new String(new sun.misc.BASE64Decoder().decodeBuffer(words))
  }

  def encodeToBase64(words: String): String = {
    new String(new sun.misc.BASE64Encoder().encodeBuffer(words.getBytes()))
  }

  def getCommentInsert(insertComment: CommentInsertion, isActivated: Option[Long] = None): CommentInsert = {
    new CommentInsert(
      commentId = None,
      articleId = insertComment.articleId,
      replyTo = insertComment.replyTo,
      userId = insertComment.userId,
      username = Option(insertComment.username),
      email = Option(insertComment.email),
      content = encodeToBase64(insertComment.content),
      //      created = Option(submittedDateConvert),
      created = new Date(insertComment.created * 1000l),
      numReport = insertComment.report,
      isDeleted = insertComment.isDeleted,
      isActivated = isActivated.getOrElse(1l)
    )
  }

  /* get comment detail, validate by content comment*/
  def getComment(insertComment: CommentInsertion) = {

    // get comment metadata
    if (isSuspectContent(insertComment.content)) {
      // TODO finishing regex , bayesian ???
      debug(s"found suspect Comment ${insertComment.content}")
      (getCommentInsert(insertComment, Option(0l)), true) // set inactive
    } else {
      debug(s"not found suspect Comment ${insertComment.content}")
      (getCommentInsert(insertComment, Option(1l)), false) // set active
    }

  }


  def insert(insertComment: CommentInsertion) = {

    val (comment, status) = getComment(insertComment)

    debug(s"comment ${comment.created} - ${insertComment.created}" )
    for {
      commentId <- dbRepository.insertComment(comment) // put to db - done
    } yield {
      CommentInsertResponse(commentId.getOrElse(0l))
    }

  }

  def isSuspectContent(content: String): Boolean = {

    // TODO Comment By Bayesian , threshold dependency mas adi
    val bayesDetail = filterComment.bayes.classifyDetailed(content.split(" "))
    debug(s" bayesDetail -> ${bayesDetail}")
    val rs1 = false

    // TODO regex
    val regexes = filterComment.negatifRegex
    val rs2 = (regexKomen(regexes, content))

    rs2
  }


  def selectComments(req: CommentRequest): Future[CommentListResponse] = {
    for {
      comments <- dbRepository.selectComment(req)
    } yield {

      debug(s"Result Request -> ${req}")
      debug(s"Result comments -> ${comments}")

      val ret: Seq[CommentInsertion] = comments.map { comment =>
        CommentInsertion(
          commentId = comment.commentId.getOrElse(0l),
          articleId = comment.articleId,
          replyTo = comment.replyTo,
          username = comment.username.getOrElse(""),
          email = comment.email.getOrElse(""),
          content = decodeFromBase64(comment.content),
          //          created = DateToTimestamp(comment.created.get),
          created = comment.created.getTime,
          report = comment.numReport,
          isDeleted = comment.isDeleted,
          isActivated = comment.isActivated
        )
      }

      debug(s"Result retrieve -> ${ret}")
      CommentListResponse(ret)
    }
  }

  def getCommentObj(obj: CommentInsert, commentId: Long): CommentObject = {
    CommentObject(
      commentId = commentId,
      articleId = obj.articleId,
      replyToId = obj.replyTo.toInt,
      userId = obj.userId,
      username = obj.username.get,
      email = obj.email.get,
      content = decodeFromBase64(obj.content),
      //      createdTs = obj.created.get.getTime / 1000l,
      createdTs = obj.created.getTime / 1000l,
      report = 0,
      isDeleted = 0,
      replyCount = 0,
      likeCount = 0,
      dislikeCount = 0,
      isFiltered = 0
    )
  }


  override def isSuspectContent(req: CommentFilter): Future[CommentFilterResponse] = Future {

    debug(filterByRegEx(req.content))
    CommentFilterResponse(req.content, 1)
  }


  //  def filterByRegEx(content: String): Boolean = {
  def filterByRegEx(content: String) = {

    //load the configuration file from the classpath
    val regexs = filterComment.negatifRegex
    val rs1 = (regexKomen(regexs.toList, content))
    debug(rs1)

  }

  def regexKomen(regexes: Seq[Regex], comment: String) =
    regexes.iterator
      .map(_.findFirstIn(comment))
      .collectFirst {
        case Some(str) => str
      }.isDefined


  def DateToTimestamp(tgl: Date): Option[Timestamp] = {
    val tgl = new java.util.Date()
    val timestamp = new Timestamp(tgl.getTime())
    return Option(timestamp)
  }

  def LongToDateTime(tgl: Long): String = {
    val tgl1: Long = tgl
    var date2 = new Date(tgl1)
    var df2: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val dateText: String = df2.format(date2)

    return dateText
  }


}
