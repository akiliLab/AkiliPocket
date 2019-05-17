package design.akililab.akilipocket

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import android.view.View.OnClickListener
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import com.google.android.gms.common.internal.Objects
import com.google.android.material.chip.Chip
import design.akililab.akilipocket.camera.CameraSource
import design.akililab.akilipocket.camera.CameraSourcePreview
import design.akililab.akilipocket.camera.GraphicOverlay
import design.akililab.akilipocket.camera.WorkflowModel
import design.akililab.akilipocket.camera.WorkflowModel.WorkflowState
import design.akililab.akilipocket.databinding.ActivityQrcodeBinding
import java.io.IOException


class QRCodeActivity : AppCompatActivity(), OnClickListener {


    private var graphicOverlay: GraphicOverlay? = null
    private var cameraSource: CameraSource? = null
    private var preview: CameraSourcePreview? = null
    private var promptChipAnimator: AnimatorSet? = null
    private var promptChip: Chip? = null
    private var settingsButton: View? = null
    private var flashButton: View? = null
    private var workflowModel: WorkflowModel? = null
    private var currentWorkflowState: WorkflowState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNUSED_VARIABLE")

        val binding = DataBindingUtil.setContentView<ActivityQrcodeBinding>(this, R.layout.activity_qrcode)

        graphicOverlay = findViewById<GraphicOverlay>(R.id.camera_preview_graphic_overlay).apply {
            setOnClickListener(this@QRCodeActivity)
            cameraSource = CameraSource(this)
        }

        promptChip = findViewById(R.id.bottom_prompt_chip)
        promptChipAnimator = (AnimatorInflater.loadAnimator(this, R.animator.bottom_prompt_chip_enter) as AnimatorSet).apply {
            setTarget(promptChip)
        }

        findViewById<View>(R.id.close_button).setOnClickListener(this)
        flashButton = findViewById<View>(R.id.flash_button).apply {
            setOnClickListener(this@QRCodeActivity)
        }

        settingsButton = findViewById<View>(R.id.settings_button).apply {
            setOnClickListener(this@QRCodeActivity)
        }

        setUpWorkflowModel()


    }

    override fun onResume() {
        super.onResume()

        workflowModel?.markCameraFrozen()
        settingsButton?.isEnabled = true
        currentWorkflowState = WorkflowState.NOT_STARTED

    }



    override fun onClick(view: View) {
        when (view.id) {
            R.id.close_button -> onBackPressed()
            R.id.flash_button -> {
                flashButton?.let {
                    if (it.isSelected) {
                        it.isSelected = false
                        cameraSource?.updateFlashMode(Camera.Parameters.FLASH_MODE_OFF)
                    } else {
                        it.isSelected = true
                        cameraSource!!.updateFlashMode(Camera.Parameters.FLASH_MODE_TORCH)
                    }

                }
            }

            R.id.settings_button -> {
                settingsButton?.isEnabled = false
            }
        }

    }

    private fun setUpWorkflowModel() {

        workflowModel = ViewModelProviders.of(this).get(WorkflowModel::class.java)


        // Observes the workflow state changes, if happens, update the overlay view indicators and
        // camera preview state.
        workflowModel!!.workflowState.observe(this, Observer { workflowState ->

            if (workflowState == null || Objects.equal(currentWorkflowState, workflowState)) {
                return@Observer
            }

            currentWorkflowState = workflowState

            Log.d(TAG, "Current workflow state: " + currentWorkflowState!!.name)

            val wasPromptChipGone = promptChip?.visibility == View.GONE


            when (workflowState) {
                WorkflowState.DETECTING -> {
                    promptChip?.visibility = View.VISIBLE
                    promptChip?.setText(R.string.prompt_point_at_a_barcode)
                    startCameraPreview()
                }
                WorkflowState.CONFIRMING -> {
                    promptChip?.visibility = View.VISIBLE
                    promptChip?.setText(R.string.prompt_move_camera_closer)
                    startCameraPreview()
                }
                WorkflowState.SEARCHING -> {
                    promptChip?.visibility = View.VISIBLE
                    promptChip?.setText(R.string.prompt_searching)
                    stopCameraPreview()
                }
                WorkflowState.DETECTED, WorkflowState.SEARCHED -> {
                    promptChip?.visibility = View.GONE
                    stopCameraPreview()
                }
                else -> promptChip?.visibility = View.GONE
            }


            val shouldPlayPromptChipEnteringAnimation = wasPromptChipGone && promptChip?.visibility == View.VISIBLE

            promptChipAnimator?.let {
                if (shouldPlayPromptChipEnteringAnimation && !it.isRunning) it.start()
            }

        })

        workflowModel?.detectedBarcode?.observe(this, Observer { barcode ->
            if (barcode != null) {

//                TODO: Add   QR detection

//                val barcodeFieldList = ArrayList<BarcodeField>()
//                barcodeFieldList.add(BarcodeField("Raw Value", barcode.rawValue ?: ""))
//                BarcodeResultFragment.show(supportFragmentManager, barcodeFieldList)
            }
        })

    }


    private fun startCameraPreview() {

        val workflowModel = this.workflowModel ?: return
        val cameraSource = this.cameraSource ?: return
        if (!workflowModel.isCameraLive) {
            try {
                workflowModel.markCameraLive()
                preview?.start(cameraSource)
            } catch (e: IOException) {
                Log.e(TAG, "Failed to start camera preview!", e)
                cameraSource.release()
                this.cameraSource = null
            }
        }

    }

    private fun stopCameraPreview() {
        val workflowModel = this.workflowModel ?: return
        if (workflowModel.isCameraLive) {
            workflowModel.markCameraFrozen()
            flashButton?.isSelected = false
            preview?.stop()
        }
    }


    companion object {
        private const val TAG = "LiveBarcodeActivity"
    }


}
