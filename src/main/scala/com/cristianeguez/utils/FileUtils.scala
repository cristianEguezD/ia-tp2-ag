package com.cristianeguez.utils

import scala.io.BufferedSource
import scala.io.Source._

trait FileUtils {

  def getBufferAsString(buffer: BufferedSource): String = try buffer.mkString finally buffer.close

  def getResourceFileAsString(filename: String) = getBufferAsString(fromInputStream(getClass.getResourceAsStream(filename)))

}
