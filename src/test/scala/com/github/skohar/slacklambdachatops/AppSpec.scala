package com.github.skohar.slacklambdachatops

import org.scalatest.{FlatSpec, Matchers}

class AppSpec extends FlatSpec with Matchers {

  behavior of "App"

  it should "returns error" in {
    App.run(Array("autoscalingdelete")).lines.toList(0) shouldEqual "Error: Missing option --load-balancer-name"
    App.run(Array("autoscalingclean")).lines.toList(0) shouldEqual "Error: Missing option --delete-count"
    App.run(Array("test")).lines.toList(0) shouldEqual "Found no such command"
  }
}
