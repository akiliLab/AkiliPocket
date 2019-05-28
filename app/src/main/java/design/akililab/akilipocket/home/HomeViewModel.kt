package design.akililab.akilipocket.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import design.akililab.akilipocket.database.TransactionDao
import kotlinx.coroutines.Job

class HomeViewModel(val database: TransactionDao, accountId: String) : ViewModel() {


    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */

    private val viewModelJob =  Job()


    val transactions = database.getTransactionsByAccountId(accountId)

    /**
     * Variable that tells the Fragment to navigate to a specific [TransactionDetailFragment]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */

    private val _navigateToTransactionDetail = MutableLiveData<String>()


    val navigateToTransactionDetail
        get() = _navigateToTransactionDetail



    fun onTransactionItemClicked(id: String) {
        _navigateToTransactionDetail.value = id
    }

    fun onTransactionDetailNavigated() {
        _navigateToTransactionDetail.value = null
    }


    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all coroutines;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     */

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}