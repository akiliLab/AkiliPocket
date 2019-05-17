package design.akililab.akilipocket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import design.akililab.akilipocket.databinding.ActivityQrcodeBinding


class QRCodeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNUSED_VARIABLE")

        val binding = DataBindingUtil.setContentView<ActivityQrcodeBinding>(this, R.layout.activity_qrcode)

    }

}
