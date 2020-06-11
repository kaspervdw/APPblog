import scala.io._
import scala.actors._
import Actor._

object PageLoader {
  def getPageSize(url: String) = Source.fromURL(url).mkString.length

  def getLinkCount(url: String) = Source.fromURL(url).getLines.count(line => line.matches(".*http.*"))
}

val urls = List("http://www.amazon.com/",
  "http://www.twitter.com/",
  "http://www.google.com/",
  "http://www.cnn.com/")

def timeMethod(method: () => Unit) = {
  val start = System.nanoTime
  method()
  val end = System.nanoTime
  println("Method took " + (end - start) / 1000000000.0 + " seconds.")
}

def getPageSizeSequentially() = {
  for (url <- urls) {
    println("Size for " + url + ": " + PageLoader.getPageSize(url))
  }
}

def getPageSizeConcurrently() = {
  val caller = self

  for (url <- urls) {
    actor {
      caller ! (url, PageLoader.getPageSize(url), "size")
      caller ! (url, PageLoader.getLinkCount(url), "link count")
    }
  }

  for (i <- 1 to urls.size * 2) {
    receive {
      case (url, size, "size") =>
        println("Size for " + url + ": " + size)
      case (url, linkCount, "link count") =>
        println("Link count on " + url + ": " + linkCount)
    }
  }
}

def main(args: Array[String]): Unit = {
  println("Sequential run:")
  timeMethod {
    getPageSizeSequentially
  }

  println("Concurrent run")
  timeMethod {
    getPageSizeConcurrently
  }
}
