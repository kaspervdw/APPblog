import sys.process._

val repo = getRepo
val latestHash = retrieveCommitHash
val currentHash = scala.io.Source.fromFile(".git/refs/heads/master").getLines.next

if (!currentHash.equals(latestHash)) {
  println("It seems the remote repository is ahead of the local repository. Pulling to retrieve the changes.\n----------")
  if ("git pull".! != 0) {
    println("----------\nThere was a problem while updating the repository.")
  }
}

def getRepo(): String = {
  scala.io.Source.fromFile(".git/config").getLines.filter(line => line.matches("^\turl =.*")).mkString.stripPrefix("\turl = git@github.com:")
}

def retrieveCommitHash(): String = {
  val doc = scala.io.Source.fromURL("https://github.com/" + repo + "/commits/master", "UTF-8")
  val returnVal = doc.getLines.filter(line => line.matches(".*class=\"message\".*")).next.replaceFirst("", "").replaceFirst("^.*href=\".*/", "").replaceFirst("\".*$", "")
  doc.close
  returnVal
}