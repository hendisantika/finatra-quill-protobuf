package com.hendisantika.comment.domain.dao

import java.util.Date
import javax.inject.Inject

import com.fasterxml.jackson.annotation.JsonProperty
import com.twitter.finagle.http.Request
import com.twitter.finatra.request.QueryParam


/* Comment */
case class CommentInsert(
  commentId: Option[Long],
  articleId: Long,
  replyTo: Long,
  userId: Long,
  username: Option[String],
  email: Option[String],
  content: String,
  created: Date,
  numReport: Long,
  isDeleted: Long,
  isActivated: Long
)


case class CommentInsert1(
  commentId: Option[Long],
  articleId: Long,
  replyTo: Long,
  userId: Long,
  username: Option[String],
  email: Option[String],
  content: String,
  created: Date,
  numReport: Long,
  isDeleted: Boolean,
  isActivated: Boolean
)

case class CommentRequest(
  @QueryParam @JsonProperty("off") off: Long = 0,
  @QueryParam @JsonProperty("lim") lim: Long = 10,
  @QueryParam @JsonProperty("src") src: Option[String] = None,
  @QueryParam @JsonProperty("art") art: Option[Long] = None,
  @QueryParam @JsonProperty("stsDel") stsDel: Option[Long] = None,
  @QueryParam @JsonProperty("stsAct") stsAct: Option[Long] = None,
  @Inject request: Request
)
