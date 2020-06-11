/* X | X | O
   X | O | O
   O | O | X
 */

object TicTacToe {
  def main(args: Array[String]): Unit = {
    val args = Array("X", "X", "O", "X", "O", "O", "O", "O", "X")

    if (args.length != 9) throw new Exception("Please input a board of 9 characters.")

    val board = new Board(args)
    val winner = board.returnWinner

    if (winner == "X") print("Player X wins!")
    else if (winner == "O") print("Player O wins!")
    else print("Nobody won this game..")
  }
}

class Board(args: Array[String]) {
  val winningLines = List(
    // Rows
    new Line(args(0), args(1), args(2)),
    new Line(args(3), args(4), args(5)),
    new Line(args(6), args(7), args(8)),
    // Columns
    new Line(args(0), args(3), args(6)),
    new Line(args(1), args(4), args(7)),
    new Line(args(2), args(5), args(8)),
    // Diagonals
    new Line(args(0), args(4), args(8)),
    new Line(args(2), args(4), args(6)))

  def returnWinner: String = {
    if (winningLines.exists(line => line.isWinningLine("X"))) return "X"
    else if (winningLines.exists(line => line.isWinningLine("O"))) return "O"
    else return "None"
  }

}

class Line(input: (String, String, String)) {
  val cells = List(new Cell(input._1), new Cell(input._2), new Cell(input._3))

  def isWinningLine(symbol: String): Boolean = {
    !cells.exists(c => c.value != symbol)
  }
}

class Cell(input: String) {
  val value: String = input
}

