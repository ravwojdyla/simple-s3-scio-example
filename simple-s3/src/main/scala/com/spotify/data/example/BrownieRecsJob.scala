package com.spotify.data.example

import com.spotify.scio._

object BrownieRecsJob {

  def main(cmdlineArgs: Array[String]): Unit = {
    val (sc, args) = ContextAndArgs(cmdlineArgs)

    // afair the / at the end of save path is significant
    sc.parallelize(1 to 10)
      .saveAsTextFile("s3a://BUCKET/ravtest/")

    sc.close().waitUntilDone()
  }

}
