package com.hendisantika.comment.controller

import com.google.inject.Inject
import com.hendisantika.comment.domain.msg.CommentsAlteration.CommentRemoval
import com.hendisantika.comment.service.CommentAlterationService
import com.twitter.finagle.http.Request
import com.wix.accord._
import com.wix.accord.dsl._
/**
  * Created by hendisantika on 10/11/2016.
  */
class CommentAlterationController @Inject()(service : CommentAlterationService) extends BaseController{

  delete("/v1/comments/:id"){ req: Request =>

    val id = req.getLongParam("id")
    service.remove(id)
    response.noContent.toFuture
  }

  delete("/v1/comments"){ req: CommentRemoval =>
    validate(req) match {
      case Success => service.removeBatch(req.ids)
      case Failure(violations) => handleValidationError(violations)
    }
  }

  post("/v1/activate/:id"){ req: Request =>
    val id = req.getLongParam("id")
    service.activate(id, 1)
    response.noContent.toFuture
  }

  post("/v1/activate"){ req: CommentRemoval =>
    validate(req) match {
      case Success => service.activateBatch(req.ids, 1)
      case Failure(violations) => handleValidationError(violations)
    }
  }

  post("/v1/deactivate/:id"){ req: Request =>
    val id = req.getLongParam("id")
    service.activate(id, 0)
    response.noContent.toFuture
  }

  post("/v1/deactivate"){ req: CommentRemoval =>
    validate(req) match {
      case Success => service.activateBatch(req.ids, 0)
      case Failure(violations) => handleValidationError(violations)
    }
  }

  implicit val redeemRequestValidator = validator[CommentRemoval]{ r =>
    r.ids is notEmpty
  }

}
