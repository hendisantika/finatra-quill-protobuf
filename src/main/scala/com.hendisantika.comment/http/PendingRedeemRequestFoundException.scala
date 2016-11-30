package com.hendisantika.comment.http

/**
  * Created by hendisantika on 10/17/16.
  */
class PendingRedeemRequestFoundException(message: String, cause: Throwable) extends Exception (message, cause) {
  def this(message: String) = this(message, null)
}