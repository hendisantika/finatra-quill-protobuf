package com.hendisantika.comment.http

/**
  * Created by hendisantika on 10/9/16.
  */
class UserNotFoundException(message: String, cause: Throwable) extends Exception (message, cause) {
  def this(message: String) = this(message, null)
}

