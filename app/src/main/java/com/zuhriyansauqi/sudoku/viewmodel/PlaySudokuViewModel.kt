package com.zuhriyansauqi.sudoku.viewmodel

import androidx.lifecycle.ViewModel
import com.zuhriyansauqi.sudoku.game.SudokuGame

class PlaySudokuViewModel : ViewModel() {

    val sudokuGame = SudokuGame()
}