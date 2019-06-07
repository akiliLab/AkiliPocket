package design.akililab.akilipocket.transactiondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import design.akililab.akilipocket.database.TransactionDao


class TransactionDetailViewModelFactory (
    private val transactionId: String,
    private val dataSource: TransactionDao
): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionDetailViewModel::class.java)) {
            return TransactionDetailViewModel(transactionId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}