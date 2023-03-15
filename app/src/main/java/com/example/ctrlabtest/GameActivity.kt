package com.example.ctrlabtest

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout

class GameActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var board: Array<IntArray>
    private var currentPlayer = 1
    private var isGameEnded = false
    private var gameResult = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        board = Array(3) { IntArray(3) }

        gridLayout = findViewById(R.id.grid_layout)

        for (row in 0..2) {
            for (col in 0..2) {
                val cell = gridLayout.getChildAt(row * 3 + col) as Button
                cell.setOnClickListener {
                    handleCellClick(row, col)
                }
            }
        }


    }

    //Update state of array after user taps on cell
    private fun handleCellClick(row: Int, col: Int) {
        if (board[row][col] == 0 && !isGameEnded) {
            if (currentPlayer == 1) {
                board[row][col] = 1
            } else {
                board[row][col] = 2
            }

            drawBoard()

            checkWinner()

            checkDraw()
            currentPlayer = 3 - currentPlayer
        }
    }

    //Check if draw combination
    private fun checkDraw() {
        var i = 0
        for (row in 0..2) {
            for (col in 0..2) {
                if (board[row][col] == 1 || board[row][col] == 2)
                    i++
            }
        }

        if (i == 9){
            isGameEnded = true
            gameResult = 1
            gameEnd()
        }

    }

    //Check if player 1 or 2 made win combination
    private fun checkWinner() {
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != 0 ||
                board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != 0){
                isGameEnded = true
                gameResult = 2
                gameEnd()
            }

        }

        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[1][1] != 0 ||
            board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[1][1] != 0){
            isGameEnded = true
            gameResult = 2
            gameEnd()
        }

    }

    //If game ended, show relevant notification about win/draw and Restart button
    private fun gameEnd() {

        val textResult : TextView = findViewById(R.id.textResult)
        if (gameResult == 2){
            textResult.text = "Player $currentPlayer won!"
        }
        else textResult.text = "Draw!"

        textResult.visibility = View.VISIBLE

        val buttonRestart: Button = findViewById(R.id.buttonRestart)
        buttonRestart.visibility = View.VISIBLE
        buttonRestart.setOnClickListener {
            buttonRestart.visibility = View.INVISIBLE

            textResult.visibility = View.INVISIBLE

            isGameEnded = false

            clearBoard()

            drawBoard()

        }

    }

    //Clear all data in array
    private fun clearBoard() {
        for (row in 0..2) {
            for (col in 0..2) {
                board[row][col] = 0
            }
        }
    }


    //Update every cell with data from array
    private fun drawBoard(){

        val cell00 = findViewById<Button>(R.id.cell_00)
        val cell01 = findViewById<Button>(R.id.cell_01)
        val cell02 = findViewById<Button>(R.id.cell_02)
        val cell10 = findViewById<Button>(R.id.cell_10)
        val cell11 = findViewById<Button>(R.id.cell_11)
        val cell12 = findViewById<Button>(R.id.cell_12)
        val cell20 = findViewById<Button>(R.id.cell_20)
        val cell21 = findViewById<Button>(R.id.cell_21)
        val cell22 = findViewById<Button>(R.id.cell_22)

        cell00.text = when (board[0][0]) {
            1 -> "X"
            2 -> "O"
            else -> ""
        }
        cell01.text = when (board[0][1]) {
            1 -> "X"
            2 -> "O"
            else -> ""
        }
        cell02.text = when (board[0][2]) {
            1 -> "X"
            2 -> "O"
            else -> ""
        }
        cell10.text = when (board[1][0]) {
            1 -> "X"
            2 -> "O"
            else -> ""
        }
        cell11.text = when (board[1][1]) {
            1 -> "X"
            2 -> "O"
            else -> ""
        }
        cell12.text = when (board[1][2]) {
            1 -> "X"
            2 -> "O"
            else -> ""
        }
        cell20.text = when (board[2][0]) {
            1 -> "X"
            2 -> "O"
            else -> ""
        }
        cell21.text = when (board[2][1]) {
            1 -> "X"
            2 -> "O"
            else -> ""
        }
        cell22.text = when (board[2][2]) {
            1 -> "X"
            2 -> "O"
            else -> ""
        }
    }
}