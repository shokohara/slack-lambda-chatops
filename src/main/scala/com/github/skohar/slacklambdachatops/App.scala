package com.github.skohar.slacklambdachatops

import java.io.{InputStream, OutputStream}

import cats.std.all._
import cats.syntax.all._
import com.amazonaws.services.lambda.AWSLambdaClient
import com.amazonaws.services.lambda.model.GetFunctionRequest
import com.amazonaws.services.lambda.runtime.Context
import com.github.skohar.slacklambdachatops.models.{AutoScalingCleanConfig, AutoScalingDeleteConfig, Slack}
import net.gpedro.integrations.slack.{SlackApi, SlackMessage}
import org.apache.commons.lang3.exception.ExceptionUtils
import play.api.libs.json.Json

import scala.io.Source
import scala.util.control.Exception._
import scalaz._

class App {

  def handler(req: InputStream, res: OutputStream, context: Context) {
    for {
      config <- description2config(context)
    } yield {
      try {
        val request = Util.using(req)(Source.fromInputStream).mkString
        new SlackApi(config.slackWebHookUrl).call(new SlackMessage("lambda:req", request))
        context.getLogger.log(request)
        val slack = Json.parse(request).as[Slack]
        val response: String = App.run(slack.message.text.split(' ').drop(2))
        new SlackApi(config.slackWebHookUrl).call(new SlackMessage("lambda:res", response))
        context.getLogger.log(response)
        Util.using(res)(_.write(response.getBytes))
      } catch {
        case t: Throwable =>
          val stackTraceString = ExceptionUtils.getStackTrace(t)
          context.getLogger.log(stackTraceString)
          new SlackApi(config.slackWebHookUrl).call(new SlackMessage("lambda:catch", stackTraceString))
          Util.using(res)(_.write(stackTraceString.getBytes))
      }
    }
  }

  def logToSlack(config: LambdaConfig, text: String) =
    new SlackApi(config.slackWebHookUrl).call(new SlackMessage("lambda:debug", text))

  def description2config(context: Context) = \/.fromEither(allCatch either {
    val client = new AWSLambdaClient()
    val function = client.getFunction(new GetFunctionRequest().withFunctionName(context.getFunctionName))
    val description = function.getConfiguration.getDescription
    Json.parse(description).as[LambdaConfig]
  })
}

object App {

  def run(args: Array[String]) = {
    AutoScalingClean :: AutoScalingDelete :: Nil find (_.parser.programName === args.head) map (_.parse(args.tail)) match {
      case Some(x) => x match {
        case \/-(c) => c match {
          case x: AutoScalingCleanConfig => AutoScalingClean.cleanAutoScalingGroup(x)
          case x: AutoScalingDeleteConfig => AutoScalingDelete.detachAutoScalingGroup(x)
        }
        case -\/(s) => s
      }
      case None => "Found no such command"
    }
  }
}
