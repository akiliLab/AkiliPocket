package design.akililab.akilipocket.qrcode

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import design.akililab.akilipocket.qrcode.camera.QRCodeTracker
import design.akililab.akilipocket.qrcode.camera.ThreadedImageAnalyzer
import java.util.concurrent.atomic.AtomicBoolean

class QRCodeViewModel : ViewModel(), ThreadedImageAnalyzer {


    val qrCodeTracker = QRCodeTracker()
    private val isBusy = AtomicBoolean(false)
    private val handlerThread = HandlerThread("ClassifySneakerImageAnalyzer").apply { start() }

    private val options = FirebaseVisionBarcodeDetectorOptions.Builder()
        .setBarcodeFormats(
            FirebaseVisionBarcode.FORMAT_QR_CODE
        )
        .build()

    private val objectDetector = FirebaseVision.getInstance()
        .getVisionBarcodeDetector(options)

    override fun getHandler() = Handler(handlerThread.looper)


    override fun analyze(image: ImageProxy, rotationDegrees: Int) {
        if (image.image != null && isBusy.compareAndSet(false, true)) {

            // Create an FirebaseVisionImage. The image's bytes are in YUV_420_888 format (camera2 API)
            // If you use camera (deprecated, pre L) instead of camera2, use:
            // FirebaseVisionImage.fromByteArray(data, FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
            // and pass the bytes from the Camera.PreviewCallback. Pay attention to rotation of the image in this case!

            val rotation = rotationDegreesToFirebaseRotation(rotationDegrees)

            val visionImage = FirebaseVisionImage.fromMediaImage(image.image!!, rotation)


            objectDetector.detectInImage(visionImage)
                .addOnSuccessListener { _ ->
                    isBusy.set(false)
                    Log.i("Bar Code detected", "barcode")
                    // Task completed successfully
                    // ...
                }
                .addOnFailureListener {
                    isBusy.set(false)
                    Log.i("Bar Code failed", "barcode")
                    // Task failed with an exception
                    // ...
                }

        }
    }



    private fun rotationDegreesToFirebaseRotation(rotationDegrees: Int) = when (rotationDegrees) {

        0 -> FirebaseVisionImageMetadata.ROTATION_0
        90 -> FirebaseVisionImageMetadata.ROTATION_90
        180 -> FirebaseVisionImageMetadata.ROTATION_180
        270 -> FirebaseVisionImageMetadata.ROTATION_270
        else -> throw IllegalArgumentException("Rotation $rotationDegrees not supported")
    }


}