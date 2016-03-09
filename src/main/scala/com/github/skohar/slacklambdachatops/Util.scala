package com.github.skohar.slacklambdachatops

object Util {

  def using[A <: {def close() : Unit}, B](s: A)(f: A => B): B = try f(s) finally s.close

}
