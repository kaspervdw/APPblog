trait Censor {
  val censorMap = Map("Shoot" -> "Pucky", "Darn" -> "Beans")
  censorMap

  def replaceWords(input: String): String = {
    (input /: censorMap) ((string, curseWord) => string.replaceAll(curseWord._1, curseWord._2))
  }
}

object CensorImp extends Censor {
  def main(args: Array[String]): Unit = {
    println(replaceWords("Shooty Darned Shooter Shoots"))
  }
}
