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
        val amount = Math.abs(item.amount.div(100))
        holder.transactionAmount.text = amount.toString()
        holder.transactionNote.text = item.notes
        holder.transactionName.text = item.description.split("            ")[0]
        // This should implement the required category type
        holder.transactionIcon.setImageResource(R.drawable.googleg_standard_color_18)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val view = layoutInflater.inflate(R.layout.list_item_transaction, parent, false)

        return ViewHolder(view)
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val transactionName: TextView = itemView.findViewById(R.id.transaction_name)
        val transactionAmount: TextView = itemView.findViewById(R.id.transaction_amount)
        val transactionNote: TextView = itemView.findViewById(R.id.transaction_note)
        val transactionIcon: ImageView = itemView.findViewById(R.id.transaction_icon)
    }
}