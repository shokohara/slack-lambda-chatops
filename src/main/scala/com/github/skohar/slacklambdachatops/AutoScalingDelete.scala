package com.github.skohar.slacklambdachatops

import java.io.ByteArrayOutputStream

import cats.std.all._
import cats.syntax.all._
import com.amazonaws.services.autoscaling.AmazonAutoScalingClient
import com.amazonaws.services.autoscaling.model.{DetachLoadBalancersRequest, UpdateAutoScalingGroupRequest}
import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancingClient
import com.amazonaws.services.elasticloadbalancing.model.LoadBalancerDescription
import com.github.skohar.slacklambdachatops.models.AutoScalingDeleteConfig
import scopt.OptionParser

import scala.collection.JavaConversions._
import scalaz.\/

object AutoScalingDelete extends Program[AutoScalingDeleteConfig] {

  val parser = new OptionParser[AutoScalingDeleteConfig]("autoscalingdelete") {
    opt[String]("load-balancer-name") required() action { (x, c) =>
      c.copy(loadBalancerName = x)
    }
    opt[Int]("leave-count") required() action { (x, c) =>
      c.copy(leaveCount = x)
    }
    opt[Boolean]("dry-run") optional() action { (x, c) =>
      c.copy(dryRun = x)
    }
  }

  def parse(args: Array[String]): \/[String, AutoScalingDeleteConfig] = {
    val err = Util.using(new ByteArrayOutputStream()) { errStream =>
      Console.withErr(errStream) {
        parser.parse(args, AutoScalingDeleteConfig())
      }
      errStream.toString("UTF-8")
    }
    val out = Util.using(new ByteArrayOutputStream()) { outStream =>
      Console.withOut(outStream) {
        parser.parse(args, AutoScalingDeleteConfig())
      }
      outStream.toString("UTF-8")
    }
    \/.fromEither {
      parser.parse(args, AutoScalingDeleteConfig()) match {
        case Some(config) => Right(config)
        case None => Left(err + out)
      }
    }
  }

  val InService = "InService"
  val autoScalingClient = new AmazonAutoScalingClient()
  val elasticLoadBalancingClient = new AmazonElasticLoadBalancingClient()

  def detachAutoScalingGroup(config: AutoScalingDeleteConfig): String = {
    var result = ""
    val loadBalancer = findLoadBalancer(config.loadBalancerName).get
    if (autoScalingClient.describeAutoScalingGroups.getAutoScalingGroups.filter(_.getLoadBalancerNames.toList === loadBalancer.getLoadBalancerName :: Nil).sortBy(_.getCreatedTime).takeRight(config.leaveCount).flatMap(_.getInstances).forall(_.getLifecycleState === InService)) {
      autoScalingClient.describeAutoScalingGroups.getAutoScalingGroups.filter(_.getLoadBalancerNames.toList === loadBalancer.getLoadBalancerName :: Nil).sortBy(_.getCreatedTime).dropRight(config.leaveCount).foreach { asg =>
        if (!config.dryRun) {
          autoScalingClient.detachLoadBalancers(new DetachLoadBalancersRequest().withAutoScalingGroupName(asg.getAutoScalingGroupName).withLoadBalancerNames(loadBalancer.getLoadBalancerName))
        }
        result = result + s"Detached LoadBalancer(${loadBalancer.getLoadBalancerName}) from AutoScalingGroup(${asg.getAutoScalingGroupName})"
        if (!config.dryRun) {
          autoScalingClient.updateAutoScalingGroup(new UpdateAutoScalingGroupRequest().withAutoScalingGroupName(asg.getAutoScalingGroupName).withMaxSize(0).withMinSize(0).withDesiredCapacity(0))
        }
        result = result + s"Updated AutoScalingGroup(${asg.getAutoScalingGroupName}) min=0, desired=0, max=0"
      }
    }
    result
  }

  def findLoadBalancer(loadBalancerName: String): Option[LoadBalancerDescription] = {
    elasticLoadBalancingClient.describeLoadBalancers().getLoadBalancerDescriptions.find(_.getLoadBalancerName === loadBalancerName)
  }
}
