package design.akililab.akilipocket.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import design.akililab.akilipocket.R
import design.akililab.akilipocket.database.Transaction

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var data = listOf<Transaction>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount(): Int  = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val transactionName: TextView = itemView.findViewById(R.id.transaction_name)
        private val transactionAmount: TextView = itemView.findViewById(R.id.transaction_amount)
        private val transactionNote: TextView = itemView.findViewById(R.id.transaction_note)
        private val transactionIcon: ImageView = itemView.findViewById(R.id.transaction_icon)


        fun bind(
            item: Transaction
        ) {
            val amount = Math.abs(item.amount.div(100))
            transactionAmount.text = amount.toString()
            transactionNote.text = item.notes
            transactionName.text = item.description.split("            ")[0]
            // This should implement the required category type
            transactionIcon.setImageResource(
                when (item.category) {

                    EATING_OUT -> R.drawable.googleg_standard_color_18
                    SHOPPING -> R.drawable.googleg_standard_color_18
                    EXPENSE -> R.drawable.googleg_standard_color_18
                    GENERAL -> R.drawable.googleg_standard_color_18
                    TRANSPORT -> R.drawable.googleg_standard_color_18
                    CASH -> R.drawable.googleg_standard_color_18
                    BILLS -> R.drawable.googleg_standard_color_18
                    HOLIDAY -> R.drawable.googleg_standard_color_18
                    GROCERIES -> R.drawable.googleg_standard_color_18

                    else -> R.drawable.googleg_standard_color_18
                }
            )
    }
        companion object {
            const val EATING_OUT = "eating_out"
            const val SHOPPING = "shopping"
            const val EXPENSE = "expense"
            const val GENERAL = "general"
            const val TRANSPORT = "transport"
            const val CASH = "cash"
            const val BILLS = "bills"
            const val HOLIDAY = "holidays"
            const val GROCERIES = "groceries"

            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val view = layoutInflater.inflate(R.layout.list_item_transaction, parent, false)

                return ViewHolder(view)
            }
        }
    }

}