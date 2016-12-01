package com.hendisantika.comment.domain.msg

import javax.inject.Inject

import com.fasterxml.jackson.annotation.JsonProperty
import com.twitter.finagle.http.Request
import com.twitter.finatra.request.{Header, QueryParam, RouteParam}
import com.twitter.finatra.validation.{Min, NotEmpty}


/**
  * Created by hendisantika on 10/23/16.
  */
case class CampaignClickEvent(
  @Header `user-agent`: String,
  @RouteParam cid: Long,
  @QueryParam @Min(1) @JsonProperty("ref") uid: Long = 0,
  @QueryParam @NotEmpty @JsonProperty("st") appSignature: String = "",
  @QueryParam @NotEmpty @JsonProperty("lp") landingPage: String = "",
  @Inject request: Request
){
  def getRemoteAddress() = {
    request.xForwardedFor.getOrElse(request.remoteHost)
  }
}
