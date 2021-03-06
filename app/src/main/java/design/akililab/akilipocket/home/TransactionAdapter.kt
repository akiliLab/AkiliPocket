package design.akililab.akilipocket.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import design.akililab.akilipocket.database.Transaction
import design.akililab.akilipocket.databinding.ListItemTransactionBinding

class TransactionAdapter(private val clickListener: TransactionListener) : ListAdapter<Transaction, TransactionAdapter.ViewHolder>(TransactionDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(val binding: ListItemTransactionBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: TransactionListener, item: Transaction) {
            binding.transaction = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = ListItemTransactionBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

}


class TransactionDiffCallback :
    DiffUtil.ItemCallback<Transaction>() {
    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem == newItem
    }
}


class TransactionListener(val clickListener: (transactionId: String) -> Unit) {

    fun onClick(transaction: Transaction) = clickListener(transaction.id)
}