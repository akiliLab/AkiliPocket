package design.akililab.akilipocket.qrcode
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import design.akililab.akilipocket.BuildConfig
import design.akililab.akilipocket.R
import design.akililab.akilipocket.databinding.ActivityQrcodeBinding
import design.akililab.akilipocket.qrcode.camera.BoundingBoxQRCodeBasic
import design.akililab.akilipocket.qrcode.camera.CameraFragment


class QRCodeActivity : AppCompatActivity() {


    private lateinit var binding: ActivityQrcodeBinding

    private lateinit var imageAnalyzer: QRCodeViewModel

    private val camera
        get() = supportFragmentManager.findFragmentById(R.id.cameraFragment) as CameraFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_qrcode)


        val boundingBoxArOverlay = BoundingBoxQRCodeBasic(this, BuildConfig.DEBUG)

        imageAnalyzer = ViewModelProviders.of(this).get(QRCodeViewModel::class.java)

        camera.imageAnalyzer = imageAnalyzer

        camera.graphicOverlay.observe(camera, Observer {

//            it.doOnLayout { view ->
//                imageAnalyzer.arObjectTracker
//                    .pipe(PositionTranslator(view.width, view.height))
//                    .pipe(PathInterpolator())
//                    .addTrackingListener(boundingBoxArOverlay)
//            }


            it.add(boundingBoxArOverlay)
        })


    }



    companion object {
        private const val TAG = "LiveBarcodeActivity"
    }


}
