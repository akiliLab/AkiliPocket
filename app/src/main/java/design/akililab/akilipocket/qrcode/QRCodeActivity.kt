package design.akililab.akilipocket.qrcode
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import design.akililab.akilipocket.R
import design.akililab.akilipocket.databinding.ActivityQrcodeBinding



class QRCodeActivity : AppCompatActivity() {


    private lateinit var binding: ActivityQrcodeBinding





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_qrcode)


    }



    companion object {
        private const val TAG = "LiveBarcodeActivity"
    }


}
