package com.github.skohar.slacklambdachatops.models

sealed trait ScoptConfig

case class AutoScalingDeleteConfig(loadBalancerName: String = "", leaveCount: Int = 0, dryRun: Boolean = false) extends ScoptConfig

case class AutoScalingCleanConfig(deleteCount: Int = 0, dryRun: Boolean = false) extends ScoptConfig