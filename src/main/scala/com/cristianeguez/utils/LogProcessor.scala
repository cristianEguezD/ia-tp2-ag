package com.cristianeguez.utils

import java.io.File

object LogProcessor extends App with FileUtils {

  val regex = "(.+)aptitud: \\[(.+)\\](.+)".r

  def procesarLog(name: String): Unit = {

    var i = -1

    Writer.printToFile(new File(s"$name.csv")) { p =>
      p.println("Iteraciones,Aptitud")

      getResourceFileAsString(s"/$name.log")
        .split("\n")
        .filter(_.contains("EL MEJOR DE LA ITERACION"))
        .foreach { linea =>
          i = i + 1
          p.println(s"$i,${regex.findFirstMatchIn(linea).map(_.group(2)).head.toDouble}")
        }
    }

  }

  def procesarLog(name: String, other: String): Unit = {

    var i = -1

    Writer.printToFile(new File(s"$other.csv")) { p =>
      p.println("index,aptitud")

      getResourceFileAsString(s"/$name.log")
        .split("\n")
        //.filter(_.contains("EL MEJOR DE LA ITERACION"))
        .foreach { linea =>
          i = i + 1
          //p.println(s"$i,${String.format(Locale.FRANCE,"%,.2f", regex.findFirstMatchIn(linea).map(_.group(2)).head.toDouble)}")
          p.println(s"$i,${regex.findFirstMatchIn(linea).map(_.group(2)).head.toDouble}")
        }

      getResourceFileAsString(s"/$other.log")
        .split("\n")
        //.filter(_.contains("EL MEJOR DE LA ITERACION"))
        .foreach { linea =>
          i = i + 1
          //p.println(s"$i,${String.format(Locale.FRANCE,"%,.2f", regex.findFirstMatchIn(linea).map(_.group(2)).head.toDouble)}")
          p.println(s"$i,${regex.findFirstMatchIn(linea).map(_.group(2)).head.toDouble}")
        }

    }

  }

  procesarLog("2022-11-06 07:13")
  procesarLog("2022-11-06 07:16")
  procesarLog("2022-11-06 07:19")
  procesarLog("2022-11-06 07:21")
  procesarLog("2022-11-06 07:26-solo_mejores_iteraciones", "2022-11-06 07:27-solo_mejores_iteraciones")

}

object Writer {

  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try {
      op(p)
    } finally {
      p.close()
    }
  }

}
