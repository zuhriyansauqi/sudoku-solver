package com.zuhriyansauqi.sudoku.game

import androidx.lifecycle.MutableLiveData

class SudokuGame {

    var selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()
    var cellsLiveData = MutableLiveData<List<Cell>>()

    private var selectedRow = -1
    private var selectedCol = -1

    private val boardSize = 9

    private val board: Board

    init {
        val cells = List(boardSize * boardSize) { i ->
            Cell(i / boardSize, i % boardSize, 0)
        }
        board = Board(boardSize, cells)

        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
        cellsLiveData.postValue(board.cells)
    }

    fun newGame() {
        board.resetCells()

        selectedRow = -1
        selectedCol = -1

        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
        cellsLiveData.postValue(board.cells)
    }

    fun solve() {
        board.solve()

        selectedRow = -1
        selectedCol = -1

        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
        cellsLiveData.postValue(board.cells)
    }

    fun clear() {
        if (selectedRow == -1 || selectedCol == -1) return

        board.getCell(selectedRow, selectedCol).value = 0
        selectedRow = -1
        selectedCol = -1

        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
        cellsLiveData.postValue(board.cells)
    }

    fun handleInput(number: Int) {
        if (selectedRow == -1 || selectedCol == -1) return
        if (board.getCell(selectedRow, selectedCol).value == number) return

        board.getCell(selectedRow, selectedCol).value = number

        cellsLiveData.postValue(board.cells)
    }

    fun updateSelectedCell(row: Int, col: Int) {
        selectedRow = row
        selectedCol = col
        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
    }
}