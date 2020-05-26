package com.zuhriyansauqi.sudoku

import com.zuhriyansauqi.sudoku.game.Board
import com.zuhriyansauqi.sudoku.game.Cell
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class BoardTest {

    @Test
    fun getCell_givenRowAndColumnIndex() {
        val boardSize = 9
        val expectedValue = 0

        val board = Board(boardSize, List(boardSize * boardSize) { i ->
            Cell(i / boardSize, i % boardSize, 0)
        })

        val actualValue = board.getCell(0,0).value
        Assertions.assertEquals(expectedValue, actualValue)
    }
}