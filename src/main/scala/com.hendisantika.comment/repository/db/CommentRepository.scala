package com.hendisantika.comment.repository.db

import com.google.inject.{Inject, Singleton}
import com.hendisantika.comment.domain.dao.{CommentDelete, CommentInsert, CommentRequest}
import com.twitter.util.Future
import io.getquill._

import scala.collection.mutable.ListBuffer

/**
  * Created by hendisantika on 10/5/2016.
  */
@Singleton
class CommentRepository @Inject()(ctx: FinagleMysqlContext[SnakeCase]) {

  import ctx._

  def encodeToBase64(words: String): String = {
    new String(new sun.misc.BASE64Encoder().encodeBuffer(words.getBytes()))
  }

  private val COMMENT = quote {
    query[CommentDelete].schema(
      _.entity("comment").columns(
        _.id -> "id",
        _.articleId -> "article_id",
        _.isDeleted -> "is_deleted",
        _.isActivated -> "is_activated",
        _.replyTo -> "reply_to",
        _.content -> "content"
      )
    )
  }

  private val COMMENT_INSERT = quote {
    query[CommentInsert].schema(
      _.entity("comment").columns(
        _.commentId -> "id",
        _.articleId -> "article_id",
        _.replyTo -> "reply_to",
        _.userId -> "user_id",
        _.username -> "username",
        _.email -> "email",
        _.content -> "content",
        _.created -> "created",
        _.numReport -> "report",
        _.isDeleted -> "is_deleted",
        _.isActivated -> "is_activated"
      )
    )
  }

  private def qInsertCommentDetail(comment: CommentInsert) = quote {
    COMMENT_INSERT.insert(
      _.articleId -> lift(comment.articleId),
      _.replyTo -> lift(comment.replyTo),
      _.userId -> lift(comment.userId),
      _.username -> lift(comment.username),
      _.email -> lift(comment.email),
      _.content -> lift(comment.content),
      _.created -> lift(comment.created),
      _.numReport -> lift(comment.numReport),
      _.isDeleted -> lift(comment.isDeleted),
      _.isActivated -> lift(comment.isActivated)
    ).returning(_.commentId)
  }

  private def qUpdateCommentDeleted(ids: List[Long], status: Int) = quote {
    liftQuery(ids).foreach { id =>
      COMMENT.filter(_.id == id).update(_.isDeleted -> lift(status))
    }
  }

  private def qSelectCommentById(ids: List[Long], status: Int) = quote {
    COMMENT.filter(r => liftQuery(ids).contains(r.id))
  }

  private def qUpdateCommentActivated(ids: List[Long], status: Int) = quote {
    liftQuery(ids).foreach { id =>
      COMMENT.filter(_.id == id).update(_.isActivated -> lift(status))
    }
  }


  private def selective(q: Quoted[Query[CommentInsert]], ps: CommentRequest): Quoted[Query[CommentInsert]] = {
    val list = ListBuffer(q)

    if (ps.art.isDefined) {
      val lastQuery = list.head
      quote {
        lastQuery filter (_.articleId == lift(ps.art.get))
      } +=: list
    }

    if (ps.src.isDefined) {
      val lastQuery = list.head
      val keyword = encodeToBase64(ps.src.getOrElse(""))
//      println(s"keyword -> ${keyword}")
       quote {
//          lastQuery filter (_.content like lift(s"%${encodeToBase64(keyword)}%"))
         lastQuery filter (p => p.content like lift(s"%${keyword}%"))
      } +=: list
    }

    if (ps.stsDel.isDefined) {
      val lastQuery = list.head
      quote {
        lastQuery.filter(_.isDeleted == lift(ps.stsDel.get))
      } +=: list
    }

    if (ps.stsAct.isDefined) {
      val lastQuery = list.head
      quote {
        lastQuery.filter(_.isActivated == lift(ps.stsAct.get))
      } +=: list
    }

    val lastQuery = list.head
    quote {
      lastQuery.drop(lift(ps.off.toInt)).take(lift(ps.lim.toInt))
    } +=: list

//    println(s"Comment Repo --> artId : ${ps.art} | src : ${ps.src}| isDeleted : ${ps.stsDel}| isActivated : ${ps.stsDel}| limit : ${ps.lim}| offset : ${ps.off}")
    list.head
  }

  private def selective2(q: Quoted[Query[CommentInsert]], ps: CommentRequest): Quoted[Query[CommentInsert]] = {
    val a = ListBuffer(q)
    val b = quote {
      for {
        art <- query[CommentInsert].filter(_.articleId == lift(ps.art.get)) if ps.art.isDefined
        act <- query[CommentInsert].filter(_.isActivated == lift(ps.stsAct.get)) if ps.stsAct.isDefined
        del <- query[CommentInsert].filter(_.isDeleted == lift(ps.stsDel.get)) if ps.stsDel.isDefined
        lim <- query[CommentInsert].drop(lift(ps.off.toInt)).take(lift(ps.lim.toInt))
      } yield (art, act, del, lim)
    }
    println("Selective 2 : " + a)

    val list = ListBuffer(q)

    if (ps.art.isDefined) {
      val lastQuery = list.head
      quote {
        lastQuery.filter(_.articleId == lift(ps.art.get))
      } +=: list
    }

    list.head
  }

  private val q = quote {
    query[CommentInsert]
  }

  def updateCommentDeletedByIds(ids: List[Long], status: Int): Future[List[Long]] = {
    run(qUpdateCommentDeleted(ids, status)).map(_.toList)
  }

  def updateCommentActivatedByIds(ids: List[Long], status: Int): Future[List[Long]] = {
    run(qUpdateCommentActivated(ids, status)).map(_.toList)
  }

  def selectCommentByIds(ids: List[Long], status: Int): Future[List[CommentDelete]] = {
    run(qSelectCommentById(ids, status)).map(_.toList)
  }

  def insertComment(comment: CommentInsert) = {
    ctx.run(qInsertCommentDetail(comment))
  }

  def selectComment(req: CommentRequest) = {
     run(selective(COMMENT_INSERT, req)).map(_.toList)
//    run(selective2(COMMENT_INSERT, req)).map(_.toList)
  }

}
