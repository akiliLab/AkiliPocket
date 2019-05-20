package design.akililab.akilipocket.payment


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import design.akililab.akilipocket.qrcode.QRCodeActivity
import design.akililab.akilipocket.R
import design.akililab.akilipocket.databinding.FragmentPaymentBinding


class PaymentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = DataBindingUtil.inflate<FragmentPaymentBinding>(inflater,
            R.layout.fragment_payment, container, false)

        val homeActivity: AppCompatActivity = (activity as AppCompatActivity)

        homeActivity.setSupportActionBar(binding.paymentToolBar)

        homeActivity.supportActionBar!!.setDisplayShowTitleEnabled(false)

        setHasOptionsMenu(true)

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.payment_app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

       when(item.itemId){

           R.id.qRCodeActivity -> startActivity(Intent(this.context, QRCodeActivity::class.java))
       }

        return super.onOptionsItemSelected(item)
    }
}
