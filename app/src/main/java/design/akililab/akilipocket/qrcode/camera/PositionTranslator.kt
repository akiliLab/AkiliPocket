package design.akililab.akilipocket.qrcode.camera

import android.graphics.RectF
import android.util.Log
import android.util.Size

/**
 * Allows the tracked ArObject to be mapped to a other coordinate system, e.g. a view.
 *
 * We use this to map the coordinate system of the preview image we received from the camera API to the ArOverlayView's
 * coordinate system which will have an other size.
 */
class PositionTranslator(private val targetWidth: Int, private val targetHeight: Int) : QRCodeTracker() {

    override fun processObject(qrCodeObject: QRCodeObject?) {
        if (qrCodeObject != null) {
            Log.i("PositionTranslator","boundingBoxStart = ${qrCodeObject.boundingBox}")

            // Rotate Size
            val rotatedSize = when(qrCodeObject.sourceRotationDegrees) {
                90, 270 -> Size(qrCodeObject.sourceSize.height, qrCodeObject.sourceSize.width)
                0, 180 -> qrCodeObject.sourceSize
                else -> throw IllegalArgumentException("Unsupported rotation. Must be 0, 90, 180 or 270")
            }
            Log.i("PositionTranslator","Mapping from source ${rotatedSize.width}x${rotatedSize.height} to ${targetWidth}x$targetHeight")


            // Calculate scale
            val scaleX = targetWidth / rotatedSize.width.toDouble()
            val scaleY = targetHeight / rotatedSize.height.toDouble()
            val scale = Math.max(scaleX, scaleY)
            val scaleF = scale.toFloat()
            val scaledSize = Size(Math.ceil(rotatedSize.width * scale).toInt(), Math.ceil(rotatedSize.height * scale).toInt())
            Log.i("PositionTranslator","Use scale=$scale, scaledSize: ${scaledSize.width}x${scaledSize.height}")


            // Calculate offset (we need to center the overlay on the target)
            val offsetX = (targetWidth - scaledSize.width) / 2
            val offsetY = (targetHeight - scaledSize.height) / 2
            Log.i("PositionTranslator","Use offsetX=$offsetX, offsetY=$offsetY")


            // Map bounding box
            val mappedBoundingBox = RectF().apply {
                left = qrCodeObject.boundingBox.left * scaleF + offsetX
                top = qrCodeObject.boundingBox.top * scaleF + offsetY
                right = qrCodeObject.boundingBox.right * scaleF + offsetX
                bottom = qrCodeObject.boundingBox.bottom * scaleF + offsetY
            }
            Log.i("PositionTranslator","Mapped bounding box=$mappedBoundingBox")


            super.processObject(
                qrCodeObject.copy(
                    boundingBox = mappedBoundingBox,
                    sourceSize = Size(targetWidth, targetHeight)
                )
            )
        } else {
            super.processObject(null)
        }
    }

    private fun RectF.toSize() = Size(width().toInt(), height().toInt())
}