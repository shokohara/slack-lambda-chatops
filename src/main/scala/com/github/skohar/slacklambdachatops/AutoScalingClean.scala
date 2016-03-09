package com.github.skohar.slacklambdachatops

import java.io.ByteArrayOutputStream

import com.amazonaws.services.autoscaling.AmazonAutoScalingClient
import com.amazonaws.services.autoscaling.model.DeleteAutoScalingGroupRequest
import com.github.skohar.slacklambdachatops.models.AutoScalingCleanConfig
import scopt.OptionParser

import scala.collection.JavaConversions._
import scalaz.\/

object AutoScalingClean extends Program[AutoScalingCleanConfig] {

  val parser = new OptionParser[AutoScalingCleanConfig]("autoscalingclean") {
    opt[Int]("delete-count") required() action { (x, c) =>
      c.copy(deleteCount = x)
    }
    opt[Boolean]("dry-run") optional() action { (x, c) =>
      c.copy(dryRun = x)
    }
  }

  def parse(args: Array[String]): \/[String, AutoScalingCleanConfig] = {
    val err = Util.using(new ByteArrayOutputStream()) { stream =>
      Console.withErr(stream) {
        parser.parse(args, AutoScalingCleanConfig())
      }
      stream.toString("UTF-8")
    }
    val out = Util.using(new ByteArrayOutputStream()) { stream =>
      Console.withOut(stream) {
        parser.parse(args, AutoScalingCleanConfig())
      }
      stream.toString("UTF-8")
    }
    \/.fromEither {
      parser.parse(args, AutoScalingCleanConfig()) match {
        case Some(config) => Right(config)
        case None => Left(err + out)
      }
    }
  }

  def cleanAutoScalingGroup(config:AutoScalingCleanConfig): String = {
    var result = ""
    val autoScalingClient = new AmazonAutoScalingClient()
    autoScalingClient.describeAutoScalingGroups.getAutoScalingGroups.filter(_.getLoadBalancerNames.isEmpty).filter(_.getInstances.isEmpty).sortBy(_.getCreatedTime).take(config.deleteCount).foreach { asg =>
      if (!config.dryRun) {
        autoScalingClient.deleteAutoScalingGroup(new DeleteAutoScalingGroupRequest().withAutoScalingGroupName(asg.getAutoScalingGroupName))
      }
      result = result + s"Delete AutoScalingGroup(${asg.getAutoScalingGroupName})"
    }
    result
  }
}
