package com.dicoding.calocare.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CalorieDiagramView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 20f
        color = Color.parseColor("#FF5722")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val rect = RectF(50f, 50f, width - 50f, height - 50f)

        // Draw the background arc
        paint.color = Color.LTGRAY
        canvas.drawArc(rect, 180f, 180f, false, paint)

        // Draw the progress arc
        val progressAngle = (1200f / 2000f) * 180f
        paint.color = Color.parseColor("#FF5722")
        canvas.drawArc(rect, 180f, progressAngle, false, paint)
    }
}