package com.github.skohar.slacklambdachatops.models

import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.Json

class SlackSpec extends FlatSpec with Matchers {

  behavior of "Slack"

  it should "returns no exception" in {
    noException should be thrownBy Json.parse(json).as[Slack]
  }

  val json =
    """{
      |  "room":"sandbox-shok4a",
      |  "user":{
      |    "id":"U01234567",
      |    "name":"shok4a",
      |    "real_name":"Sho Kohara",
      |    "email_address":"skohar@users.noreply.github.com",
      |    "slack":{
      |      "id":"U01234567",
      |      "team_id":"T01234567",
      |      "name":"shok4a",
      |      "deleted":false,
      |      "status":null,
      |      "color":"e23f99",
      |      "real_name":"Sho Kohara",
      |      "tz":"Asia/Tokyo",
      |      "tz_label":"Japan Standard Time",
      |      "tz_offset":32400,
      |      "profile":{
      |        "first_name":"Sho",
      |        "last_name":"Kohara",
      |        "image_24":"https://avatars.slack-edge.com/2015-06-11/6254214034_387ba92a9066e381c66f_24.jpg",
      |        "image_32":"https://avatars.slack-edge.com/2015-06-11/6254214034_387ba92a9066e381c66f_32.jpg",
      |        "image_48":"https://avatars.slack-edge.com/2015-06-11/6254214034_387ba92a9066e381c66f_48.jpg",
      |        "image_72":"https://avatars.slack-edge.com/2015-06-11/6254214034_387ba92a9066e381c66f_72.jpg",
      |        "image_192":"https://avatars.slack-edge.com/2015-06-11/6254214034_387ba92a9066e381c66f_192.jpg",
      |        "image_original":"https://avatars.slack-edge.com/2015-06-11/6254214034_387ba92a9066e381c66f_original.jpg",
      |        "phone":"08012345678",
      |        "real_name":"Sho Kohara",
      |        "real_name_normalized":"Sho Kohara",
      |        "email":"skohar@users.noreply.github.com",
      |        "fields":null
      |      },
      |      "is_admin":false,
      |      "is_owner":false,
      |      "is_primary_owner":false,
      |      "is_restricted":false,
      |      "is_ultra_restricted":false,
      |      "is_bot":false,
      |      "presence":"active"
      |    },
      |    "room":"sandbox-shok4a"
      |  },
      |  "message":{
      |    "user":{
      |      "id":"U01234567",
      |      "name":"skohar",
      |      "real_name":"Sho Kohara",
      |      "email_address":"skohar@users.noreply.github.com",
      |      "slack":{
      |        "id":"U01234567",
      |        "team_id":"T01234567",
      |        "name":"skohar",
      |        "deleted":false,
      |        "status":null,
      |        "color":"e23f99",
      |        "real_name":"Sho Kohara",
      |        "tz":"Asia/Tokyo",
      |        "tz_label":"Japan Standard Time",
      |        "tz_offset":32400,
      |        "profile":{
      |          "first_name":"Sho",
      |          "last_name":"Kohara",
      |          "image_24":"https://avatars.slack-edge.com/2015-06-11/6254214034_387ba92a9066e381c66f_24.jpg",
      |          "image_32":"https://avatars.slack-edge.com/2015-06-11/6254214034_387ba92a9066e381c66f_32.jpg",
      |          "image_48":"https://avatars.slack-edge.com/2015-06-11/6254214034_387ba92a9066e381c66f_48.jpg",
      |          "image_72":"https://avatars.slack-edge.com/2015-06-11/6254214034_387ba92a9066e381c66f_72.jpg",
      |          "image_192":"https://avatars.slack-edge.com/2015-06-11/6254214034_387ba92a9066e381c66f_192.jpg",
      |          "image_original":"https://avatars.slack-edge.com/2015-06-11/6254214034_387ba92a9066e381c66f_original.jpg",
      |          "phone":"08012345678",
      |          "real_name":"Sho Kohara",
      |          "real_name_normalized":"Sho Kohara",
      |          "email":"skohar@users.noreply.github.com",
      |          "fields":null
      |        },
      |        "is_admin":false,
      |        "is_owner":false,
      |        "is_primary_owner":false,
      |        "is_restricted":false,
      |        "is_ultra_restricted":false,
      |        "is_bot":false,
      |        "presence":"active"
      |      },
      |      "room":"sandbox-shok4a"
      |    },
      |    "text":"cody infrastructure test",
      |    "rawText":"cody infrastructure test",
      |    "rawMessage":{
      |      "id":1,
      |      "type":"message",
      |      "channel":"C01234567",
      |      "text":"cody infrastructure test"
      |    },
      |    "id":"0123456789.012345",
      |    "done":false,
      |    "room":"sandbox-shok4a"
      |  }
      |}""".stripMargin
}
