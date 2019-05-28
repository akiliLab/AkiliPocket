package design.akililab.akilipocket.home


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import design.akililab.akilipocket.R
import design.akililab.akilipocket.database.AppDatabase
import design.akililab.akilipocket.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
           R.layout.fragment_home, container, false)


        val homeActivity: AppCompatActivity = (activity as AppCompatActivity)

        homeActivity.setSupportActionBar(binding.homeToolBar)

        homeActivity.supportActionBar!!.setDisplayShowTitleEnabled(false)

        val accountId = "acc_00009237aqC8c5umZmrRdh"

        val application = requireNotNull(this.activity).application

        val dataSource = AppDatabase.getInstance(application).transactionDao

        val viewModelFactory = HomeViewModelFactory(dataSource, accountId)

        val homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)

        binding.homeViewModel = homeViewModel

        val adapter = TransactionAdapter(TransactionListener { transactionId ->
            homeViewModel.onTransactionItemClicked(transactionId)
        })

        binding.transactionList.adapter = adapter

        homeViewModel.transactions.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        homeViewModel.navigateToTransactionDetail.observe(this, Observer { transactionId ->

            transactionId?.let {

                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTransactionDetailFragment(transactionId))

                homeViewModel.onTransactionDetailNavigated()
            }

        })

        setHasOptionsMenu(true)
        
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_app_bar_menu, menu)
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return NavigationUI.onNavDestinationSelected(item, view!!.findNavController())|| super.onOptionsItemSelected(item)
    }
}
 