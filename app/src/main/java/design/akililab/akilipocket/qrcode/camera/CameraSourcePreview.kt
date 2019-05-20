package design.akililab.akilipocket.qrcode.camera

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.Rational
import android.util.Size
import android.widget.FrameLayout
import androidx.camera.core.*
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
    private var cameraRunning = false


    var imageAnalyzer: ThreadedImageAnalyzer? = null
        set(value) {
            field = value
            if (cameraRunning) {
                createCamera()
            }
        }


    override fun onFinishInflate() {
        super.onFinishInflate()
        graphicOverlay = findViewById(R.id.camera_preview_graphic_overlay)
    }







    private fun createCamera() {

        preview.post {
            val usesCases = mutableListOf<UseCase>()

            // Make sure that there are no other use cases bound to CameraX
            CameraX.unbindAll()

            // Create configuration object for the viewfinder use case
            val previewConfig = PreviewConfig.Builder().apply {
                setTargetAspectRatio(Rational(16, 9))
                setTargetResolution(Size(preview.width, preview.height))
            }.build()

            // Build the viewfinder use case
            usesCases.add(AutoFitPreviewBuilder.build(previewConfig, preview))

            // Setup image analysis pipeline that computes average pixel luminance in real time
            if (imageAnalyzer != null) {
                val analyzerConfig = ImageAnalysisConfig.Builder().apply {
                    // Use a worker thread for image analysis to prevent preview glitches
                    setCallbackHandler(imageAnalyzer!!.getHandler())
                    // In our analysis, we care more about the latest image than analyzing *every* image
                    setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
                    setTargetResolution(Size(preview.width / 2, preview.height / 2))
                }.build()

                usesCases.add(ImageAnalysis(analyzerConfig).apply {
                    analyzer = imageAnalyzer
                })
            }

            // Bind use cases to lifecycle
            CameraX.bindToLifecycle(this, *usesCases.toTypedArray())
            cameraRunning = true
            Log.i("CameraPreviewClass","Started camera with useCases=$usesCases")
        }

    }


}