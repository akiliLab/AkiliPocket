package design.akililab.akilipocket.home

import android.util.Log
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
const val CHARITY = "charity"


@BindingAdapter("transactionNameFormatted")
fun TextView.setTransactionName(item: Transaction) {
    Log.d("adapter", item.description)
    item.let {
        text = item.description.split("            ")[0]
    }
}

@BindingAdapter("transactionAmountString")
fun TextView.setTransactionAmount(item: Transaction) {
    item.let {
        text = Math.abs(item.amount.div(100)).toString()
    }
}


@BindingAdapter("transactionImage")
fun ImageView.setTransactionIcons(item: Transaction) {
    item.let {

        setImageResource(when (item.category) {

            EATING_OUT -> R.drawable.ic_cat_eating_out
            SHOPPING -> R.drawable.ic_cat_shopping
            EXPENSE -> R.drawable.ic_cat_expenses
            GENERAL -> R.drawable.ic_cat_general
            TRANSPORT -> R.drawable.ic_cat_transport
            CASH -> R.drawable.ic_cash_deposit
            BILLS -> R.drawable.ic_cat_bills
            HOLIDAY -> R.drawable.ic_cat_holidays
            GROCERIES -> R.drawable.ic_cat_groceries
            CHARITY -> R.drawable.ic_cat_charity

            else -> R.drawable.ic_cat_general
        })
    }

}