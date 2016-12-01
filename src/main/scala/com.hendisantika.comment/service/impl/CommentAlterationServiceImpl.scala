package com.hendisantika.comment.service.impl

import com.google.inject.Inject
import com.hendisantika.comment.constant.Constants
import com.hendisantika.comment.domain.dao.{CommentDelete, CommentObject}
import com.hendisantika.comment.domain.msg.CommentsAlteration.CommentRemoval
import com.hendisantika.comment.repository.db.CommentRepository
import com.hendisantika.comment.service.CommentAlterationService
import com.twitter.inject.Logging
import com.twitter.io.Buf
import com.twitter.util.Future
import com.typesafe.config.Config

import scala.collection.JavaConversions
import scala.collection.JavaConversions._

/**
  * Created by hendisantika on 10/11/2016.
  */
class CommentAlterationServiceImpl @Inject() (dbRepository : CommentRepository, config: Config) extends CommentAlterationService with Logging{

  override def remove (id : Long) ={

    for {
      dbDeleted     <- dbRepository.updateCommentDeletedByIds(List(id), 1)
    } yield {}

  }

  override def removeBatch (ids : Seq[Long]) /*: Future[Unit] */= {

    val dbProcess    = dbRepository.updateCommentDeletedByIds(ids.toList, 1)

    for {
      res     <- Future.collect(Seq(dbProcess))
    } yield {CommentRemoval(res.flatten.distinct)}
  }

  /*    is_activated = 1 -> active
   *    is_activated = 0 -> inactive
   *    is_activated = 2 -> suspected */
  override def activate (id : Long, status : Int) : Future[Unit] = {
    for {
      dbDeleted     <- dbRepository.updateCommentActivatedByIds(List(id), status) // update isActivated = status
    } yield {}

  }

  override def activateBatch (ids : Seq[Long], status: Int) : Future[CommentRemoval] = {
    val dbProcess    = dbRepository.updateCommentActivatedByIds(ids.toList, status)

    for {
      res     <- Future.collect(Seq(dbProcess))
    } yield {CommentRemoval(res.flatten.distinct)}
  }

  def activateCommentByIds (ids : Seq[Long], status : Int) = {

    debug(s"ids -> ${ids}")

    for {
      lCommentDelete  <- dbRepository.selectCommentByIds (ids.toList, status)
    }yield {JavaConversions.asScalaBuffer(lCommentDelete.map(_.id)).toList}

  }

  def updateCommentByIds(commentIds : Seq[Long]) = {

    for{
      lCommentDelete  <- dbRepository.selectCommentByIds(commentIds.toList,1)
    }yield{JavaConversions.asScalaBuffer(lCommentDelete.map(_.id)).toList}
  }



  def getListKeysByIds (ids : List[CommentDelete]) : Seq[Buf] = {
    ids.map{ id =>
      val key = Constants.X_COMMENT.format(id)
      Buf.Utf8(key)
    }
  }

  def getCommentObj (comment : CommentDelete) : Future[CommentObject] = Future{
    CommentObject(
      commentId = comment.id,
      articleId = comment.articleId,
      replyToId = comment.replyTo,
      userId = comment.userId,
      username = comment.username,
      email = comment.email,
      content = comment.content,
      createdTs = comment.created.getTime/1000l,
      report = 0,
      isDeleted = 0,
      replyCount = 0,
      likeCount = 0,
      dislikeCount = 0,
      isFiltered = 0
    )
  }

}
