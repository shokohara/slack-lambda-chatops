package com.github.skohar.slacklambdachatops

import com.github.skohar.slacklambdachatops.models.ScoptConfig
import scopt.OptionParser

import scalaz.\/

trait Program[T <: ScoptConfig] {
  val parser: OptionParser[T]

  def parse(args: Array[String]): \/[String, T]
}
