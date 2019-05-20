package design.akililab.akilipocket.qrcode.camera

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import design.akililab.akilipocket.R

/**
 * A view which renders a series of custom graphics to be overlaid on top of an associated preview
 * (i.e., the camera preview). The creator can add graphics objects, update the objects, and remove
 * them, triggering the appropriate drawing and invalidation within the view.
 *
 *
 * Supports scaling and mirroring of the graphics relative the camera's preview properties. The
 * idea is that detection items are expressed in terms of a preview size, but need to be scaled up
 * to the full view size, and also mirrored in the case of the front-facing camera.
 *
 *
 * Associated [Graphic] items should use [.translateX] and [ ][.translateY] to convert to view coordinate from the preview's coordinate.
 */


class CameraSourcePreview(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private var graphicOverlay: GraphicOverlay? = null


    override fun onFinishInflate() {
        super.onFinishInflate()
        graphicOverlay = findViewById(R.id.camera_preview_graphic_overlay)
    }


}