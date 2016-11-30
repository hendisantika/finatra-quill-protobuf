package com.hendisantika.comment.domain.dao

import java.util.Date

import com.tsukaby.bayes.classifier.BayesClassifier

import scala.util.matching.Regex


/* Comment */
case class CommentDelete(id:Long, articleId:Long, isDeleted:Int, isActivated:Int, replyTo:Int, content:String, userId:Long,
  username: String,
  email:String,
  created:Date)

case class CommentObject(
  commentId:Long,
  articleId:Long,
  replyToId:Int,
  userId:Long,
  username:String,
  email:String,
  content:String,
  createdTs:Long,
  report:Int,
  isDeleted:Int,
  replyCount:Int,
  likeCount:Int,
  dislikeCount:Int,
  isFiltered:Int
  )

case class CommentConfig(bayes : BayesClassifier[String, String], negatifRegex : List[Regex])

