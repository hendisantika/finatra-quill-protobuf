package com.hendisantika.comment.marshalling

import com.twitter.finagle.http.Request

/**
  * Created by hendisantika on 10/26/16.
  */
trait RequestAware {
  var request: Request = _
}
