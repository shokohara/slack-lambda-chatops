package com.github.skohar.slacklambdachatops.models

import play.api.libs.json.Json

case class ElbMessage(AlarmName: String,
                      AlarmDescription: String,
                      AWSAccountId: String,
                      NewStateValue: String,
                      NewStateReason: String,
                      StateChangeTime: String,
                      Region: String,
                      OldStateValue: String,
                      Trigger: String)
object ElbMessage {
  implicit val format = Json.format[ElbMessage]
}
