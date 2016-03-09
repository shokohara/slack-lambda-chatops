package com.github.skohar.slacklambdachatops.models

import com.github.tototoshi.play.json.JsonNaming
import play.api.libs.json.Json


case class Profile(email: String,
                   fields: Option[String],
                   firstName: String,
                   image24: String,
                   image32: String,
                   image48: String,
                   image72: String,
                   image192: String,
                   imageOriginal: String,
                   lastName: String,
                   phone: String,
                   realName: String,
                   realNameNormalized: String)

object Profile {
  implicit val format = JsonNaming.snakecase(Json.format[Profile])
}

case class Slack2(color: String,
                  deleted: Boolean,
                  id: String,
                  isAdmin: Boolean,
                  isBot: Boolean,
                  isOwner: Boolean,
                  isPrimaryOwner: Boolean,
                  isRestricted: Boolean,
                  isUltraRestricted: Boolean,
                  name: String,
                  presence: String,
                  profile: Profile,
                  realName: String,
                  status: Option[String],
                  teamId: String,
                  tz: String,
                  tzLabel: String,
                  tzOffset: Int
                 )

object Slack2 {
  implicit val format = JsonNaming.snakecase(Json.format[Slack2])
}

case class User(
                 emailAddress: String,
                 id: String,
                 name: String,
                 realName: String,
                 room: String,
                 slack: Slack2)

object User {
  implicit val format = JsonNaming.snakecase(Json.format[User])
}

case class RawMessage(channel: String, id: Int, text: String, `type`: String)

object RawMessage {
  implicit val format = JsonNaming.snakecase(Json.format[RawMessage])
}

case class Message(done: Boolean,
                   id: String,
                   rawMessage: RawMessage,
                   rawText: String,
                   room: String,
                   text: String,
                   user: User)

object Message {
  implicit val format = JsonNaming.snakecase(Json.format[Message])
}


case class Slack(room: String, user: User, message: Message)

object Slack {
  implicit val format = JsonNaming.snakecase(Json.format[Slack])
}
