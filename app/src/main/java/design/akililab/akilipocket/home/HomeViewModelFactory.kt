package design.akililab.akilipocket.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import design.akililab.akilipocket.database.TransactionDao


class HomeViewModelFactory(
    private val dataSource: TransactionDao,
    private val accountId: String
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dataSource, accountId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}