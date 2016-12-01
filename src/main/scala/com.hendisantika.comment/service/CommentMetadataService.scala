package com.hendisantika.comment.service

import com.google.inject.ImplementedBy
import com.hendisantika.comment.domain.dao.CommentRequest
import com.hendisantika.comment.domain.msg.CommentInsertion._
import com.hendisantika.comment.service.impl.CommentMetadataServiceImpl
import com.twitter.util.Future

/**
  * Created by hendisantika on 29/11/16.
  */
@ImplementedBy(classOf[CommentMetadataServiceImpl])
trait CommentMetadataService {
  def selectComments(req: CommentRequest): Future[CommentListResponse]

  def insert(commentInsert: CommentInsertion):  Future[CommentInsertResponse]

  def isSuspectContent (req : CommentFilter) : Future[CommentFilterResponse]

}
