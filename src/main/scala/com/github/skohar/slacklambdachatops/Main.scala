package com.github.skohar.slacklambdachatops

import cats.std.all._
import cats.syntax.all._
import com.github.skohar.slacklambdachatops.models.{AutoScalingCleanConfig, AutoScalingDeleteConfig}

import scalaz.{-\/, \/-}

object Main {

  def main(args: Array[String]) = {
    val result = AutoScalingClean :: AutoScalingDelete :: Nil find (_.parser.programName === args.head) map (_.parse(args.tail)) match {
      case Some(x) => x match {
        case \/-(c) => c match {
          case x: AutoScalingCleanConfig => AutoScalingClean.cleanAutoScalingGroup(x)
          case x: AutoScalingDeleteConfig => AutoScalingDelete.detachAutoScalingGroup(x)
        }
        case -\/(s) => s
      }
      case None => "Found no such command"
    }
    Console.out.println(result)
  }

}
