package com.hendisantika.comment.domain.request

import com.google.inject.Inject
import com.hendisantika.comment.domain.msg.FCMBroadcast
import com.twitter.finagle.http.Method._
import com.twitter.finagle.http.{Fields, _}
import com.typesafe.config.Config
import io.circe.generic.auto._
import io.circe.syntax._

/**
  * Created by hendisantika on 10/27/16.
  */
object FCMBroadcastRequest{
  @Inject
  val allConfig: Config = null
  lazy val endpointPath: String = allConfig.getString("fcm.notif.endpoint")
  lazy val apiKey: String = allConfig.getString("fcm.notif.apiKey")
}

case class FCMBroadcastRequest(notif: FCMBroadcast) extends RequestProxy {
  import FCMBroadcastRequest._

  override lazy val request: Request = {
    val req = Request(Post, endpointPath)
    req.contentString = notif.asJson.toString()
    req.headerMap += Fields.Authorization -> s"key=$apiKey"
    req.headerMap += Fields.ContentType -> MediaType.Json
    req
  }
}

