package com.hendisantika.comment.domain.msg

import com.fasterxml.jackson.annotation.JsonProperty
import com.twitter.finatra.request.{QueryParam, RouteParam}
import com.twitter.finatra.validation.Min

/**
  * Created by hendisantika on 10/28/16.
  */
case class PushNotifRequest(
  @RouteParam @Min(1) @JsonProperty("campaignId") cid: Long,
  @QueryParam @JsonProperty("nb") isNonBlocking: Boolean = true,
  @QueryParam @JsonProperty("dr") isDryRun: Boolean = false
)