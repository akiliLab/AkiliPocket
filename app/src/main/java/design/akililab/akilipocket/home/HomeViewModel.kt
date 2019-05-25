package design.akililab.akilipocket.home

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