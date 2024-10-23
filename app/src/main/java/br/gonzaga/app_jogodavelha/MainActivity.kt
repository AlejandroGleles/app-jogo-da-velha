package br.gonzaga.app_jogodavelha

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var currentPlayer = "X"
    private var board = Array(3) { arrayOfNulls<String>(3) }
    private var gameActive = true

    private lateinit var statusTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.tvStatus)

        setupBoard()

        val btnReiniciarJogo = findViewById<Button>(R.id.btnReiniciarJogo)
        btnReiniciarJogo.setOnClickListener {
            resetGame()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                val buttonId = resources.getIdentifier("button_$i$j", "id", packageName)
                val button = findViewById<Button>(buttonId)

                button.setOnClickListener {
                    if (gameActive && button.text.isEmpty()) {
                        button.text = currentPlayer // Exibe "X" ou "O" no botão
                        board[i][j] = currentPlayer

                        if (checkWin()) {
                            statusTextView.text = "Jogador $currentPlayer venceu!"
                            gameActive = false
                        } else if (board.flatten().none { it == null }) {
                            statusTextView.text = "Empate!"
                            gameActive = false
                        } else {
                            currentPlayer = if (currentPlayer == "X") "O" else "X"
                            statusTextView.text = "Jogador $currentPlayer: Sua vez!"
                        }
                    }
                }
            }
        }
    }

    private fun checkWin(): Boolean {
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true

        return false
    }

    private fun resetGame() {
        currentPlayer = "X"
        board = Array(3) { arrayOfNulls<String>(3) }
        statusTextView.text = "Jogador 1: Sua vez!"
        gameActive = true

        for (i in 0..2) {
            for (j in 0..2) {
                val buttonId = resources.getIdentifier("button_$i$j", "id", packageName)
                val button = findViewById<Button>(buttonId)
                button.text = ""
            }
        }
    }
}
