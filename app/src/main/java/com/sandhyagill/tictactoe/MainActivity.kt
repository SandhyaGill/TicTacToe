package com.sandhyagill.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.sandhyagill.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    enum class turn{
        NOUGHT,
        CROSS
    }
    private var firstTurn = turn.CROSS
    private var currenturn = turn.CROSS

    private var noughtScore = 0
    private var crossesScore = 0

    private var boardList = mutableListOf<Button>()

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
    }

    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTapped(view: View) {
        if (view !is Button)
            return
        addToBoard(view)

        if (chechForVictory(NOUGHT)){
            noughtScore++
            result("Nought Win!")
        }
        if (chechForVictory(CROSS)){
            crossesScore++
            result("Crosses Win!")
        }
        if (fullBoard()){
            result("Draw")
        }
    }

    private fun chechForVictory(s: String): Boolean {
        //for Horizontal
         if (match(binding.a1,s) && match(binding.a2,s) && match(binding.a3,s))
             return true
         if (match(binding.b1,s) && match(binding.b2,s) && match(binding.b3,s))
             return true
         if (match(binding.c1,s) && match(binding.c2,s) && match(binding.c3,s))
             return true
        //for Vertical
         if (match(binding.a1,s) && match(binding.b1,s) && match(binding.c1,s))
             return true
         if (match(binding.a2,s) && match(binding.b2,s) && match(binding.c2,s))
             return true
         if (match(binding.a3,s) && match(binding.b3,s) && match(binding.c3,s))
             return true
        //for Diagonal
        if (match(binding.a1,s) && match(binding.b2,s) && match(binding.c3,s))
            return true
        if (match(binding.a3,s) && match(binding.b2,s) && match(binding.c1,s))
            return true

        return false
    }

    private fun match(button: Button, symbol: String): Boolean = button.text == symbol

    private fun result(title : String){
        var message = "\n Nought $noughtScore\n\n Crosses $crossesScore"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Reset"){_,_->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard() {
         for (button in boardList){
             button.text = ""
         }
        if (firstTurn == turn.NOUGHT){
            firstTurn = turn.CROSS
        }else if (firstTurn == turn.CROSS){
            firstTurn = turn.NOUGHT
        }
        currenturn = firstTurn
            setTurnLabel()
    }

    private fun fullBoard() : Boolean {
         for (button in boardList)
             if (button.text == "") {
                 return false
             }
             return true
    }

    private fun addToBoard(button: Button) {
       if (button.text != "")
           return
        if (currenturn == turn.NOUGHT){
            button.text = NOUGHT
            currenturn = turn.CROSS
        }else if (currenturn == turn.CROSS){
            button.text = CROSS
            currenturn = turn.NOUGHT
        }
        setTurnLabel()
    }

    private fun setTurnLabel() {
        var turnText = ""
        if (currenturn == turn.NOUGHT)
            turnText = "Turn $NOUGHT"
        else if (currenturn == turn.CROSS)
            turnText = "Turn $CROSS"
        binding.tvTurn.text = turnText
    }

    companion object{
        const val NOUGHT = "O"
        const val CROSS = "X"
    }
}