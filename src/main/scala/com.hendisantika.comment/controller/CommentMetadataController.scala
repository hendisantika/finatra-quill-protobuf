package com.hendisantika.comment.controller

import com.google.inject.Inject
import com.hendisantika.comment.domain.dao.CommentRequest
import com.hendisantika.comment.domain.msg.CommentInsertion.{CommentFilter, CommentInsertion}
import com.hendisantika.comment.service.CommentMetadataService
import com.wix.accord.dsl._
import com.wix.accord.{Failure, Success, _}

/**
  * Created by hendisantika on 10/11/16.
  */

case class CommentMetadataController @Inject()(service: CommentMetadataService) extends BaseController {

  case class Exception(errCode: Int, errorMsg: String)


  put("/v1/comments/") { req: CommentInsertion =>

    validate(req) match {
      case Success => service.insert(req)
      case Failure(violations) => handleValidationError(violations)
    }
  }


  get("/v1/comments") { req: CommentRequest =>
    service.selectComments(req)

  }


  post("/v1/comments/classifier") { req: CommentFilter =>

    validate(req) match {
      case Success => service.isSuspectContent(req)
      case Failure(violations) => handleValidationError(violations)
    }

  }


  implicit val insertValidator = validator[CommentInsertion] { r =>
    r.email is notEmpty
  }

  implicit val commentValidator = validator[CommentFilter] { r =>
    r.content is notEmpty
  }
}
