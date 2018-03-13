package com.spotify.data.example

import com.spotify.scio._

object BrownieRecsJob {

  def main(cmdlineArgs: Array[String]): Unit = {
    val (sc, args) = ContextAndArgs(cmdlineArgs)

    sc.parallelize(1 to 10)
      .saveAsTextFile("s3a://catalog-upload-test/ravtest/")

    sc.close().waitUntilDone()
  }

}
