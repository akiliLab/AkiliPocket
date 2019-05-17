package design.akililab.akilipocket


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import design.akililab.akilipocket.databinding.FragmentPaymentBinding


class PaymentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = DataBindingUtil.inflate<FragmentPaymentBinding>(inflater, R.layout.fragment_payment, container, false)

//        val homeActivity: AppCompatActivity = (activity as AppCompatActivity)
//
//        homeActivity.setSupportActionBar(binding.payment)
//
//        homeActivity.supportActionBar!!.setDisplayShowTitleEnabled(false)

        return binding.root
    }


}
