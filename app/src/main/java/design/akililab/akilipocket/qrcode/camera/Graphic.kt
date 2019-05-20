package design.akililab.akilipocket.qrcode.camera

import android.content.Context
import android.graphics.Canvas

/**
 * Base class for a custom graphics object to be rendered within the graphic overlay. Subclass
 * this and implement the [Graphic.draw] method to define the graphics element. Add
 * instances to the overlay using [GraphicOverlay.add].
 */


abstract class Graphic protected constructor(protected val overlay: GraphicOverlay) {
    protected val context: Context = overlay.context

    /** Draws the graphic on the supplied canvas.  */
    abstract fun draw(canvas: Canvas)
}