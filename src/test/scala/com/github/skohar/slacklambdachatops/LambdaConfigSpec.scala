package com.github.skohar.slacklambdachatops

import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.Json

class LambdaConfigSpec extends FlatSpec with Matchers {

  behavior of "LambdaConfig's JSON parser"

  it should "returns no exception" in {
    noException should be thrownBy Json.parse(config).as[LambdaConfig]
  }

  val config = """{"slack_web_hook_url":"https://hooks.slack.com/services/T01234567/B01234567/APBqfQQeDwNVMrOtpuMIQ0H0"}"""
}
