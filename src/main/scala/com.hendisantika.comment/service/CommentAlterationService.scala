package com.hendisantika.comment.service

import com.google.inject.ImplementedBy
import com.hendisantika.comment.domain.msg.CommentsAlteration.CommentRemoval
import com.hendisantika.comment.service.impl.CommentAlterationServiceImpl
import com.twitter.util.Future

/**
  * Created by hendisantika on 10/11/2016.
  */
@ImplementedBy(classOf[CommentAlterationServiceImpl])
trait CommentAlterationService {


  def remove (commentId : Long) : Future[Unit]
  def removeBatch (ids : Seq[Long]) : Future[CommentRemoval]

  def activate (id : Long, status : Int) : Future[Unit]
  def activateBatch (ids : Seq[Long],status : Int) : Future[CommentRemoval]

}
