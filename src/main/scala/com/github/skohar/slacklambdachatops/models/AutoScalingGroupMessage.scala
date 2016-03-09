package com.github.skohar.slacklambdachatops.models

import play.api.libs.json.Json

case class AutoScalingGroupMessage(AccountId: String,
                                   ActivityId: String,
                                   AutoScalingGroupARN: String,
                                   AutoScalingGroupName: String,
                                   Cause: String,
                                   Description: String,
                                   Details: String,
                                   EC2InstanceId: String,
                                   EndTime: String,
                                   Event: String,
                                   Progress: Int,
                                   RequestId: String,
                                   Service: String,
                                   StartTime: String,
                                   StatusCode: String,
                                   StatusMessage: String,
                                   Time: String)

object AutoScalingGroupMessage {
  implicit val format = Json.format[AutoScalingGroupMessage]
}
