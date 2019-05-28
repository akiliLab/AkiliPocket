package design.akililab.akilipocket.transactiondetail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import design.akililab.akilipocket.R
import design.akililab.akilipocket.databinding.FragmentTransactionDetailBinding

/**
 * A simple [Fragment] subclass.
 */
class TransactionDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =  DataBindingUtil.inflate<FragmentTransactionDetailBinding>(inflater,
            R.layout.fragment_transaction_detail,container, false)

        val homeActivity: AppCompatActivity = (activity as AppCompatActivity)

        homeActivity.setSupportActionBar(binding.transactionDetailToolbar)

        homeActivity.supportActionBar!!.setDisplayShowTitleEnabled(false)

        return binding.root
    }


}
