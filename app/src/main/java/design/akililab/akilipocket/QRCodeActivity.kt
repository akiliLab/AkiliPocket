package design.akililab.akilipocket

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.hardware.Camera
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import android.view.View.OnClickListener
import com.google.android.material.chip.Chip
import design.akililab.akilipocket.camera.CameraSource
import design.akililab.akilipocket.camera.GraphicOverlay
import design.akililab.akilipocket.databinding.ActivityQrcodeBinding


class QRCodeActivity : AppCompatActivity(), OnClickListener {


    private var graphicOverlay: GraphicOverlay? = null
    private var cameraSource: CameraSource? = null
    private var promptChipAnimator: AnimatorSet? = null
    private var promptChip: Chip? = null
    private var settingsButton: View? = null
    private var flashButton: View? = null

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




}
