package design.akililab.akilipocket.home

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import design.akililab.akilipocket.R
import design.akililab.akilipocket.database.Transaction


const val EATING_OUT = "eating_out"
const val SHOPPING = "shopping"
const val EXPENSE = "expense"
const val GENERAL = "general"
const val TRANSPORT = "transport"
const val CASH = "cash"
const val BILLS = "bills"
const val HOLIDAY = "holidays"
const val GROCERIES = "groceries"


@BindingAdapter("transactionNameFormatted")
fun TextView.setTransactionName(item: Transaction) {
    item?.let {
        text = item.description.split("            ")[0]
    }
}

@BindingAdapter("transactionAmountString")
fun TextView.setTransactionAmount(item: Transaction) {
    item?.let {
        text = Math.abs(item.amount.div(100)).toString()
    }
}


@BindingAdapter("transactionImage")
fun ImageView.setTransactionIcons(item: Transaction) {
    item?.let {

        setImageResource(when (item.category) {

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
        })
    }

}