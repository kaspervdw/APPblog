import scala.collection.mutable.ListBuffer
import sys.process._

//val repo = getRepo
//val latestHash = retrieveCommitHash
//val currentHash = scala.io.Source.fromFile(".git/refs/heads/master").getLines.next
//
//if (!currentHash.equals(latestHash)) {
//  println("It seems the remote repository is ahead of the local repository. Pulling to retrieve the changes.\n----------")
//  if ("git pull".! != 0) {
//    println("----------\nThere was a problem while updating the repository.")
//  }
//}
//
//def getRepo(): String = {
//  scala.io.Source.fromFile(".git/config").getLines.filter(line => line.matches("^\turl =.*")).mkString.stripPrefix("\turl = git@github.com:")
//}
//
//def retrieveCommitHash(): String = {
//  val doc = scala.io.Source.fromURL("https://github.com/" + repo + "/commits/master", "UTF-8")
//  val returnVal = doc.getLines.filter(line => line.matches(".*class=\"message\".*")).next.replaceFirst("", "").replaceFirst("^.*href=\".*/", "").replaceFirst("\".*$", "")
//  doc.close
//  returnVal
//}
//

def getRepoLink(): String = {
  scala.io.Source.fromFile(".git/config").getLines().filter(line => line.matches("^\turl =.*")).mkString.stripPrefix("\turl = git@github.com:").stripSuffix(".git")
}

def getLocalCommitHash(): String = {
  scala.io.Source.fromFile(".git/refs/heads/master").getLines().next()
}

def getHashesFromRepo(): List[String] = {
  val page = scala.io.Source.fromURL("https://github.com/" + getRepoLink() + "/commits/master", "UTF-8").getLines().filter(line => line.matches(".*data-url=\"/" + getRepoLink() + "/commit.*"))
  val listOfHashes = ListBuffer[String]()

  while (page.hasNext) {
    listOfHashes.addOne(page.next().replaceFirst(".*commit/", "").replaceFirst("/_render.*", ""))
  }

  listOfHashes.toList
}

println("Checking if your commit is already on the remote...")
if (getHashesFromRepo().contains(getLocalCommitHash())) {
  println("Your commit has already been pushed!")
  println("Checking if you're up to date with the remote...")
  if (!getHashesFromRepo().head.equals(getLocalCommitHash())) {
    println("The remote repository is ahead of your local repository. Pulling updates...")
    if ("git pull".! != 0) {
      println("There was a problem while pulling the changes...")
    } else {
      "Pulled remote changes successfully..."
    }
  }
  println("Seems like you're fully up to date with the remote repository!")
} else {
  println("Your local commit hasn't been pushed to the remote repository yet! Please do so or discard your local changes before executing this program again...")
}



