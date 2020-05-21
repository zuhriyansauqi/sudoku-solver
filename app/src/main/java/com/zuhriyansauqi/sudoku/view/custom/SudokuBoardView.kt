package com.zuhriyansauqi.sudoku.view.custom

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.zuhriyansauqi.sudoku.R
import com.zuhriyansauqi.sudoku.game.Cell

class SudokuBoardView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var sqrtSize = 3
    private var size = 9
    private var cellSizePixels = 0F

    private var selectedRow = 6
    private var selectedCol = 2

    private var listener: OnTouchListener? = null

    private var cells: List<Cell>? = null

    private val thickLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, R.color.dusty_orange)
        strokeWidth = 4F * Resources.getSystem().displayMetrics.density
    }

    private val thinLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, R.color.dusty_orange)
        strokeWidth = 2F * Resources.getSystem().displayMetrics.density
    }

    private val blueDarkPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = ContextCompat.getColor(context, R.color.dark_gray_blue)
    }

    private val blueLightPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = ContextCompat.getColor(context, R.color.deep_space_sparkle)
    }

    private val selectedCellPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, R.color.dusty_orange)
        strokeWidth = 2F * Resources.getSystem().displayMetrics.density
    }

    private val textPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.WHITE
        textSize = 24F * Resources.getSystem().displayMetrics.density
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = Math.min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(sizePixels, sizePixels)
    }

    override fun onDraw(canvas: Canvas) {
        cellSizePixels = (width / size).toFloat()
        fillCells(canvas)
        drawLines(canvas)
        drawText(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                handleTouchEvent(event.x, event.y)
                true
            }
            else -> false
        }
    }

    private fun fillCells(canvas: Canvas) {
        cells?.forEach {
            val r = it.row
            val c = it.col

            if (r % 2 == 0) {
                if (c % 2 == 0) {
                    fillCell(canvas, r, c, blueDarkPaint)
                } else {
                    fillCell(canvas, r, c, blueLightPaint)
                }

            } else {
                if (c % 2 == 0) {
                    fillCell(canvas, r, c, blueLightPaint)

                } else {
                    fillCell(canvas, r, c, blueDarkPaint)
                }
            }

            if (selectedRow == r && selectedCol == c) {
                fillCell(canvas, r, c, selectedCellPaint)
            }
        }
    }

    private fun fillCell(canvas: Canvas, r: Int, c: Int, paint: Paint) {
        canvas.drawRect(
            c * cellSizePixels,
            r * cellSizePixels,
            (c + 1) * cellSizePixels,
            (r + 1) * cellSizePixels,
            paint
        )
    }

    private fun drawLines(canvas: Canvas) {
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), thickLinePaint)

        for (i in 1 until size) {
            if (i % sqrtSize == 0) {
                canvas.drawLine(
                    i * cellSizePixels,
                    0F,
                    i * cellSizePixels,
                    height.toFloat(),
                    thinLinePaint
                )

                canvas.drawLine(
                    0F,
                    i * cellSizePixels,
                    width.toFloat(),
                    i * cellSizePixels,
                    thinLinePaint
                )
            }
        }
    }

    private fun drawText(canvas: Canvas) {
        cells?.forEach {
            if (it.value == 0) return@forEach

            val row = it.row
            val col = it.col
            val valueString = it.value.toString()

            val textBounds = Rect()
            textPaint.getTextBounds(valueString, 0, valueString.length, textBounds)
            val textWidth = textPaint.measureText(valueString)
            val textHeight = textBounds.height()

            canvas.drawText(
                valueString, (col * cellSizePixels) + cellSizePixels / 2 - textWidth / 2,
                (row * cellSizePixels) + cellSizePixels / 2 + textHeight / 2, textPaint
            )
        }
    }

    private fun handleTouchEvent(x: Float, y: Float) {
        val possibleSelectedRow = (y / cellSizePixels).toInt()
        val possibleSelectedCol = (x / cellSizePixels).toInt()
        listener?.onCellTouched(possibleSelectedRow, possibleSelectedCol)
    }

    fun updateSelectedCellUI(row: Int, col: Int) {
        selectedRow = row
        selectedCol = col
        invalidate()
    }

    fun updateCells(cells: List<Cell>) {
        this.cells = cells
        invalidate()
    }

    fun registerListener(listener: OnTouchListener) {
        this.listener = listener
    }

    interface OnTouchListener {
        fun onCellTouched(row: Int, col: Int)
    }
}