package design.akililab.akilipocket.transactiondetail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import design.akililab.akilipocket.database.Transaction
import design.akililab.akilipocket.database.TransactionDao
import kotlinx.coroutines.Job


class TransactionDetailViewModel (private val transactionId: String, dataSource: TransactionDao): ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its SleepDatabaseDao.
     */
    val database = dataSource

    /** Coroutine setup variables */

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()


    private val transaction = MediatorLiveData<Transaction>()

    fun getTransaction() = transaction

    init {
        transaction.addSource(database.get(transactionId), transaction::setValue)
    }



    /**
    * Cancels all coroutines when the ViewModel is cleared, to cleanup any pending work.
    *
    * onCleared() gets called when the ViewModel is destroyed.
    */

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}