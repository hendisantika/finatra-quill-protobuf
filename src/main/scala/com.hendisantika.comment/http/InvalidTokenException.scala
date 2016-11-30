package com.hendisantika.comment.http

/**
  * Created by hendisantika on 9/7/16.
  */
class InvalidTokenException(message: String, cause: Throwable) extends Exception (message, cause) {
  def this(message: String) = this(message, null)
}
