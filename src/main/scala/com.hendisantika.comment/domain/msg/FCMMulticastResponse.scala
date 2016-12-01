package com.hendisantika.comment.domain.msg

/**
  * Created by hendisantika on 10/27/16.
  */
case class FCMMulticastResponse(
  multicast_id: Long,
  success: Int,
  failure: Int,
  canonical_ids: Int,
  results: List[FCMResultItem]
)

case class FCMResultItem(
  message_id: Option[String],
  registration_id: Option[String],
  error: Option[String]
)

