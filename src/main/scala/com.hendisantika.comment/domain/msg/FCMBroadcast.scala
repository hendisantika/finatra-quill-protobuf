package com.hendisantika.comment.domain.msg

/**
  * Created by hendisantika on 10/27/16.
  */
case class FCMBroadcast(
  registration_ids: List[String],
  priority: String = "high",
  time_to_live: Int = 24 * 60 * 60, // in seconds
  dry_run: Boolean = false,
  notification: FCMNotification,
  data: Option[String] = None
)

case class FCMNotification(
  title: String,
  body: String
)
