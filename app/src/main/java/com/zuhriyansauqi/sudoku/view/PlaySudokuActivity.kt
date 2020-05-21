package com.zuhriyansauqi.sudoku.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zuhriyansauqi.sudoku.R
import com.zuhriyansauqi.sudoku.game.Cell
import com.zuhriyansauqi.sudoku.view.custom.SudokuBoardView
import com.zuhriyansauqi.sudoku.viewmodel.PlaySudokuViewModel
import kotlinx.android.synthetic.main.main_activity.*

class PlaySudokuActivity : AppCompatActivity(), SudokuBoardView.OnTouchListener {

    private lateinit var viewModel: PlaySudokuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        sudokuBoardView.registerListener(this)

        viewModel = ViewModelProviders.of(this).get(PlaySudokuViewModel::class.java)
        viewModel.sudokuGame.selectedCellLiveData.observe(
            this,
            Observer { updateSelectedCellUI(it) })
        viewModel.sudokuGame.cellsLiveData.observe(this, Observer { updateCells(it) })

        val inputs =
            listOf(tvOne, tvTwo, tvThree, tvFour, tvFive, tvSix, tvSeven, tvEight, tvNine)

        inputs.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                viewModel.sudokuGame.handleInput(index + 1)
            }
        }

        btnSolveMe.setOnClickListener {
            viewModel.sudokuGame.solve()
        }

        btnClear.setOnClickListener {
            viewModel.sudokuGame.clear()
        }

        btnClearAll.setOnClickListener {
            viewModel.sudokuGame.newGame()
        }
    }

    private fun updateSelectedCellUI(cell: Pair<Int, Int>?) = cell?.let {
        sudokuBoardView.updateSelectedCellUI(cell.first, cell.second)
    }

    private fun updateCells(cells: List<Cell>?) = cells?.let {
        sudokuBoardView.updateCells(cells)
    }

    override fun onCellTouched(row: Int, col: Int) {
        viewModel.sudokuGame.updateSelectedCell(row, col)
    }
}