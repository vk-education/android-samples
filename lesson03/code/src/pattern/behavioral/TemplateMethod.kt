package pattern.behavioral

fun main() {
    val gameCode = GameCode.CHESS

    val game = when (gameCode) {
        GameCode.CHESS -> Chess()
        GameCode.MONOPOLY -> Monopoly()
    }
    game.playOneGame(2)
}

enum class GameCode {
    CHESS,
    MONOPOLY
}

abstract class Game {
    private var playersAmount = 0

    protected abstract fun initializeGame()

    protected abstract fun playGame()

    protected abstract fun endGame()

    protected abstract fun printWinner()

    fun playOneGame(playersAmount: Int) {
        setPlayersAmount(playersAmount)

        initializeGame()
        playGame()
        endGame()

        printWinner()
    }

    private fun setPlayersAmount(playersAmount: Int) {
        this.playersAmount = playersAmount
    }
}


/*	Игра "Шахматы". Специфически только для шахмат реализует методы класса Game.
 *
 *      Файл Chess.java
 * */
class Chess : Game() {
    override fun initializeGame() {
        // chess specific initialization actions
    }

    override fun playGame() {
        // chess specific play actions
    }

    override fun endGame() {
        // chess specific actions to end a game
    }

    override fun printWinner() {
        // chess specific actions to print winner
    }
}


/*	Игра "Монополия". Специфически только для монополии реализует методы класса Game.
 *
 *      Файл Monopoly.java
 * */
class Monopoly : Game() {
    override fun initializeGame() {
        // monopoly specific initialization actions
    }

    override fun playGame() {
        // monopoly specific play actions
    }

    override fun endGame() {
        // monopoly specific actions to end a game
    }

    override fun printWinner() {
        // monopoly specific actions to print winner
    }
}

