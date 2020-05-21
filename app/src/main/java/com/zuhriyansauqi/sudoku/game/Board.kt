package com.zuhriyansauqi.sudoku.game

class Board(val size: Int, var cells: List<Cell>) {
    fun getCell(row: Int, col: Int) = cells[row * size + col]

    fun resetCells() {
        val cells = List(size * size) { i -> Cell(i / size, i % size, 0) }
        this.cells = cells
    }

    fun solve(): Boolean {
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (getCell(i, j).value == 0) {
                    for (k in 1..size) {
                        getCell(i, j).value = k
                        if (isValid(i, j) && solve()) {
                            return true
                        }
                        getCell(i, j).value = 0
                    }
                    return false
                }
            }
        }
        return true
    }

    private fun isValid(row: Int, col: Int): Boolean {
        return rowConstraint(row) && columnConstraint(col) && subSectionConstraint(row, col)
    }

    private fun rowConstraint(row: Int): Boolean {
        val constraint = BooleanArray(size)
        return (0 until size).all { col -> checkConstraint(row, constraint, col) }
    }

    private fun columnConstraint(col: Int): Boolean {
        val constraint = BooleanArray(size)
        return (0 until size).all { row -> checkConstraint(row, constraint, col) }
    }

    private fun subSectionConstraint(row: Int, col: Int): Boolean {
        val constraint = BooleanArray(size)
        val subSectionSize = Math.sqrt(size.toDouble()).toInt()

        val subSectionRowStart = row / subSectionSize * subSectionSize
        val subSectionRowEnd = subSectionRowStart + subSectionSize

        val subSectionColStart = col / subSectionSize * subSectionSize
        val subSectionColEnd = subSectionColStart + subSectionSize

        for (r in subSectionRowStart until subSectionRowEnd) {
            for (c in subSectionColStart until subSectionColEnd) {
                if (!checkConstraint(r,constraint,c)) return false
            }
        }
        return true
    }

    private fun checkConstraint(row: Int, constraint: BooleanArray, col: Int): Boolean {
        if (getCell(row, col).value != 0) {
            if (!constraint[getCell(row, col).value - 1]) {
                constraint[getCell(row, col).value - 1] = true;
            } else {
                return false
            }
        }
        return true
    }
}