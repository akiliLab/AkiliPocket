package design.akililab.akilipocket.transactiondetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import design.akililab.akilipocket.R
import design.akililab.akilipocket.database.AppDatabase
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

        val application = requireNotNull(this.activity).application

        // Get arguments values
        val arguments =  TransactionDetailFragmentArgs.fromBundle(arguments!!)

        // Create an instance of the ViewModel Factory.
        val dataSource = AppDatabase.getInstance(application).transactionDao
        val viewModelFactory = TransactionDetailViewModelFactory(arguments.transactionId, dataSource)


        // Get a reference to the ViewModel associated with this fragment.
        val transactionDetailViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(TransactionDetailViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.

        binding.transactionDetailViewModel = transactionDetailViewModel

        binding.lifecycleOwner = this


        homeActivity.setSupportActionBar(binding.transactionDetailToolbar)

        homeActivity.supportActionBar!!.setDisplayShowTitleEnabled(false)

        return binding.root
    }


}
