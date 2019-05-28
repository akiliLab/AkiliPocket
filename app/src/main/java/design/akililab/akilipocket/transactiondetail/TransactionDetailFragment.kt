package design.akililab.akilipocket.transactiondetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import design.akililab.akilipocket.R
import design.akililab.akilipocket.databinding.FragmentTransactionDetailBinding

/**
 * A simple [TransactionDetailFragment] subclass.
 */
class TransactionDetailFragment : Fragment() {

    lateinit var binding: FragmentTransactionDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_transaction_detail,container, false)

        val homeActivity: AppCompatActivity = (activity as AppCompatActivity)

        homeActivity.setSupportActionBar(binding.transactionDetailToolbar)

        homeActivity.supportActionBar!!.setDisplayShowTitleEnabled(false)

        return binding.root
    }


}
