package design.akililab.akilipocket.qrcode.camera


import android.graphics.Canvas

/**
 * Base class for a custom graphics object to be rendered within the graphic overlay. Subclass
 * this and implement the [Graphic.draw] method to define the graphics element. Add
 * instances to the overlay using [GraphicOverlay.add].
 */


abstract class Graphic () {

    protected var host: GraphicOverlay? = null

    /** Draws the graphic on the supplied canvas.  */
    abstract fun onDraw(canvas: Canvas)

    open fun onAttached(view: GraphicOverlay) {
        this.host = view
    }
}