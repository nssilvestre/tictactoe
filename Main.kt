package tictactoe

fun main() {
    val newGame = TicTacToe()
    newGame.play()
}

class TicTacToe {
    private val cells = mutableMapOf<Pair<Int, Int>, Char>()

    private fun showGrid() {
        println("---------")
        println("| ${cells[Pair(1,1)]} ${cells[Pair(1,2)]} ${cells[Pair(1,3)]} |")
        println("| ${cells[Pair(2,1)]} ${cells[Pair(2,2)]} ${cells[Pair(2,3)]} |")
        println("| ${cells[Pair(3,1)]} ${cells[Pair(3,2)]} ${cells[Pair(3,3)]} |")
        println("---------")
    }

    private fun getMoves() {
        do {
            val coordinates: Pair<Int, Int>
            var input: List<String>
            do {
                print("Enter the coordinates: ")
                input = readLine()!!.split(" ")
            } while (!checkCoordinates(input.first(), input.last()))

            coordinates = Pair(input.first().toInt(), input.last().toInt())

            val player = if (cells.count { it.value == 'X' } <= cells.count { it.value == 'O' }) 'X' else 'O'
            cells.put(Pair(coordinates.first, coordinates.second), player)

            showGrid()
        } while (!checkForWinnerOrEnd())
    }

    private fun checkForWinnerOrEnd(): Boolean {
        val winCombinations = mutableSetOf<List<Pair<Int, Int>>>()

        winCombinations.add(listOf(Pair(1, 1), Pair(1, 2), Pair(1, 3)))
        winCombinations.add(listOf(Pair(2, 1), Pair(2, 2), Pair(2, 3)))
        winCombinations.add(listOf(Pair(3, 1), Pair(3, 2), Pair(3, 3)))
        winCombinations.add(listOf(Pair(1, 1), Pair(2, 1), Pair(3, 1)))
        winCombinations.add(listOf(Pair(1, 2), Pair(2, 2), Pair(3, 2)))
        winCombinations.add(listOf(Pair(1, 3), Pair(2, 3), Pair(3, 3)))
        winCombinations.add(listOf(Pair(1, 1), Pair(2, 2), Pair(3, 3)))
        winCombinations.add(listOf(Pair(1, 3), Pair(2, 2), Pair(3, 1)))

        for (c in winCombinations) {
            if (cells[c[0]] == 'X' || cells[c[0]] == 'O') {
                if (cells[c[0]] == cells[c[1]] && cells[c[1]] == cells[c[2]] ) {
                    println("${cells[c[0]]} wins")
                    return true
                }
            }
        }

        if (cells.count { it.value == 'X' || it.value == 'O' } == 9) {
            println("Draw")
            return true
        }

        return false
    }

    private fun checkCoordinates(row: String, col: String) : Boolean {
        val coordinates = Pair(row.toIntOrNull(), col.toIntOrNull())

        return if (coordinates.first == null || coordinates.second == null) {
            println("You should enter numbers!")
            false
        } else if (coordinates.first !in 1..3 || coordinates.second !in 1..3) {
            println("Coordinates should be from 1 to 3!")
            false
        } else if (cells[coordinates] == 'X' || cells[coordinates] == 'O') {
            println("This cell is occupied! Choose another one!")
            false
        } else true
    }

    private fun newGrid(char: Char = ' ') {
        for (r in 1..3) {
            for (c in 1..3) {
                cells[Pair(r, c)] = char
            }
        }
    }

    fun play() {
        newGrid()
        showGrid()
        getMoves()
    }
}