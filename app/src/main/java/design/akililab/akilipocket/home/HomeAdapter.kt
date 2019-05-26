package design.akililab.akilipocket.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import design.akililab.akilipocket.R
import design.akililab.akilipocket.TextItemViewHolder
import design.akililab.akilipocket.database.Transaction

class HomeAdapter : RecyclerView.Adapter<TextItemViewHolder>() {

    var data = listOf<Transaction>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount(): Int  = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.description.capitalize()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView

        return TextItemViewHolder(view)
    }
}