package com.hendisantika.comment.constant

/**
  * Created by hendisantika on 10/6/2016.
  */
object Constants {

  val X_COMMENT = "x-comment:%d"

  // list comment article x-art-comment:{articleId}, score = createdTs.getTime/1000l
  val X_ART_COMMENT = "x-art-comment:%d"
  // list reply comment x-art-comment-r:{articleId}, score = replyCount
  val X_ART_COMMENT_R = "x-art-comment-r:%d"
  // list reply comment x-comment-reply:{replyToId}, score = createdTs.getTime/1000l
  val X_COMMENT_REPLY = "x-comment-reply:%d"
  // list reply comment x-comment-hist:{userId}, score = createdTs.getTime/1000l
  val X_COMMENT_HISTORY = "x-comment-hist:%d"
  // list reply comment x-art-comment-m:{articleId}, score = createdTs.getTime/1000l
  val X_ART_COMMENT_M = "x-art-comment-m:%d"



  val X_ART_META = "x-art-meta:{%s}"
  val X_ART_CATS = "x-art-cats:{%s}"
  val X_ART_ENTITIES = "x-art-entities:{%s}"


  // Redis Content Related
  // x-art:{artId}
  val REDIS_ARTICLE_KEY      			= 	"x-art:%d"
  // x-list:{catName} ( member list = article id )
  val REDIS_ARTICLE_LIST_KEY      	= 	"x-list:%s"
  // x-art-likes:{artId}
  val REDIS_ARTICLE_LIKES_KEY     	= 	"x-art-likes:%d"
  // x-art-dislikes:{artId}
  val REDIS_ARTICLE_DISLIKES_KEY      = 	"x-art-dislikes:%d"
  // x-pub-rel-art:{relatedArtId}
  val REDIS_ARTICLE_PUB_REL_KEY      	= 	"x-pub-rel-art:%d"
  // x-list-popular:{categoryId}
  val REDIS_ARTICLE_POPULAR_KEY      	= 	"x-list-popular:%d"

  // Redis Comment Related
  // x-comment:{commentId}
  val REDIS_COMMENT_KEY      			= 	"x-comment:%d"
  // x-art-comment:{artId}
  val REDIS_COMMENT_LIST_KEY      	= 	"x-art-comment:%d"
  // x-art-comment-r:{artId}
  val REDIS_COMMENT_LIST_R_KEY      	= 	"x-art-comment-r:%d"
  // x-art-comment-m:{artId}
  val REDIS_COMMENT_LIST_M_KEY      	= 	"x-art-comment-m:%d"

}
