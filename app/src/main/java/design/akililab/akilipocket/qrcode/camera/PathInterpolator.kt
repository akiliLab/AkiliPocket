package design.akililab.akilipocket.qrcode.camera

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.RectF
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.LinearInterpolator


/**
 * This class can be used to interpolate the path of an ArObject. We are always chasing the AR object, thus the result might
 * be slightly delayed but missing intermediary positions will be added
 */
class PathInterpolator(private val lostObjectCacheDurationMs: Long = 200, private val animationDurationMs: Long = 100) :
    QRCodeTracker() {

    private val handler = Handler(Looper.getMainLooper())
    private var lastArObject: QRCodeObject? = null
    private var currentBoundingBox: RectF? = null
    private var currentAnimator: Animator? = null

    override fun processObject(qrCodeObject: QRCodeObject?) {
        // We lost track of the object. We schedule to remove it after the cache duration. If the Firebase
        // finds the object again (most likely with a new tracking id) we can animate the bounding box to the new
        // (most very similar) position
        if (qrCodeObject == null) {
            if (currentBoundingBox != null) {
                handler.postDelayed(this::clearTracking, lostObjectCacheDurationMs)
            }
        }

        // We did track an object and got a new one
        // We will animate the current bounding box to the new bounding box
        // This will make the transition very smooth
        else {
            // If we got a non-null object, cancel the clear request (if any)
            handler.removeCallbacks(this::clearTracking)

            val start = currentBoundingBox?.toEdges() ?: qrCodeObject.boundingBox.toEdges()
            val end = qrCodeObject.boundingBox.toEdges()
            Log.i("PathInterpolator","Animating from ${currentBoundingBox?.toEdges()?.toList()} to ${end.toList()}")

            lastArObject = qrCodeObject
            currentAnimator?.cancel()
            currentAnimator = ObjectAnimator.ofMultiFloat(this, "boundingBox", arrayOf(start, end)).apply {
                duration = animationDurationMs
                interpolator = LinearInterpolator()
                addUpdateListener {
                    // The automated call to setBoundingBox does not work...Kotlin?
                    setBoundingBox(it.animatedValue as FloatArray)
                }
                start()
            }
        }
    }

    private fun clearTracking() {
        Log.i("PathInterpolator","Clearing bounding box")
        lastArObject = null
        setBoundingBox(null)
    }

    private fun setBoundingBox(edges: FloatArray?) {
        Log.i("PathInterpolator","Set bounding box ${edges?.toList()}")
        currentBoundingBox = edges?.toRectF()

        publish(
            if (currentBoundingBox != null) {
                lastArObject?.copy(boundingBox = currentBoundingBox!!.copy())
            } else {
                null
            }
        )
    }

    private fun RectF.copy() = RectF(left, top, right, bottom)

    private fun RectF.toEdges() = arrayOf(left, top, right, bottom).toFloatArray()

    private fun FloatArray.toRectF() = RectF().also {
        it.left = this[0]
        it.top = this[1]
        it.right = this[2]
        it.bottom = this[3]
    }
}