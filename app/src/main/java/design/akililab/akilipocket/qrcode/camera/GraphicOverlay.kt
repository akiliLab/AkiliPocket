package design.akililab.akilipocket.qrcode.camera

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.annotation.GuardedBy

class GraphicOverlay(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val graphics = ArrayList<Graphic>()

    init {

        setWillNotDraw(false)
    }


    @GuardedBy("objectLock")
    fun remove(graphic: Graphic) {

        graphics.remove(graphic)

    }

    @GuardedBy("objectLock")
    fun add(graphic: Graphic) {
        remove(graphic)
        graphics.add(graphic)
        invalidate()
    }

    @GuardedBy("objectLock")
    fun clear() {
        graphics.clear()
        invalidate()
    }

    @GuardedBy("objectLock")

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        graphics.forEach { it.draw(canvas) }
    }

}