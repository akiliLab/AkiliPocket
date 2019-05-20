package design.akililab.akilipocket.qrcode.camera


import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import design.akililab.akilipocket.R
import design.akililab.akilipocket.databinding.FragmentCameraBinding
import kotlinx.android.synthetic.main.camera_preview_overlay.*


class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding


    private val mutableGraphicOverlay = MutableLiveData<GraphicOverlay>()


    val graphicOverlay: LiveData<GraphicOverlay> = mutableGraphicOverlay

    var cameraRunning = false
        private set

    var imageAnalyzer: ThreadedImageAnalyzer? = null
        set(value) {
            field = value
            if (cameraRunning) {
                startCamera()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_camera, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mutableGraphicOverlay.postValue(camera_preview_graphic_overlay)

        CameraPermissionHelper().requestCameraPermission(childFragmentManager) {
            if (it) {
                startCamera()
            } else {
                activity?.finish()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

        if (cameraRunning) {
            CameraX.unbindAll()
            cameraRunning = false
            Log.i("CameraFragment","Stopping camera")
        }
    }


    private fun startCamera() {
       binding.cameraPreview.post {
            val usesCases = mutableListOf<UseCase>()

            // Make sure that there are no other use cases bound to CameraX
            CameraX.unbindAll()

            // Create configuration object for the viewfinder use case
            val previewConfig = PreviewConfig.Builder().apply {
                setTargetAspectRatio(Rational(16, 9))
                setTargetResolution(Size(binding.cameraPreview.width, binding.cameraPreview.height))
            }.build()

            // Build the viewfinder use case
            usesCases.add(AutoFitPreviewBuilder.build(previewConfig, binding.cameraPreview))

            // Setup image analysis pipeline that computes average pixel luminance in real time
            if (imageAnalyzer != null) {
                val analyzerConfig = ImageAnalysisConfig.Builder().apply {
                    // Use a worker thread for image analysis to prevent preview glitches
                    setCallbackHandler(imageAnalyzer!!.getHandler())
                    // In our analysis, we care more about the latest image than analyzing *every* image
                    setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
                    setTargetResolution(Size(binding.cameraPreview.width / 2, binding.cameraPreview.height / 2))
                }.build()

                usesCases.add(ImageAnalysis(analyzerConfig).apply {
                    analyzer = imageAnalyzer
                })
            }

            // Bind use cases to lifecycle
            CameraX.bindToLifecycle(this, *usesCases.toTypedArray())
            cameraRunning = true
            Log.i("CameraFragment","Started camera with useCases=$usesCases")
        }
    }


}
