trait CensorFromFile {
  var censorMap = Map[String, String]()
  scala.io.Source.fromResource("censorWords.txt").getLines().foreach(line => censorMap += (line.split(",")(0) -> line.split(",")(1)))

  def replaceWords(input: String): String = {
    (input /: censorMap) ((string, curseWord) => string.replaceAll(curseWord._1, curseWord._2))
  }
}

object CensorImp2 extends CensorFromFile {
  def main(args: Array[String]): Unit = {
    println(replaceWords("Shooty Darned Shooter Shoots"))
  }

}
