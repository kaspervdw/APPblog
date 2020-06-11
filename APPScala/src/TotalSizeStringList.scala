// Using files in scala: https://alvinalexander.com/scala/how-to-open-read-text-files-in-scala-cookbook-examples/
// The difference between a code block and a closure: https://stackoverflow.com/questions/1812401/exactly-what-is-the-difference-between-a-closure-and-a-block#:~:text=The%20main%20difference%20is%20that,it%2C%20and%20mainly%20call%20it!

object TotalSizeStringList extends App {
  val strings = List("Test", "Foo", "Bar")
  println(strings.foldLeft(0)((sum, string) => sum + string.length))
}
