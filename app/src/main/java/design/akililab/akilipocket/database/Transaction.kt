package design.akililab.akilipocket.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "transaction_table")

data class Transaction(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",

    @ColumnInfo(name = "account_id")
    var accountId: String,

    @ColumnInfo(name = "account_balance")
    var accountBalance: Float = 0F,

    @ColumnInfo(name = "amount")
    var amount: Float = 0F,


    @ColumnInfo(name = "created")
    var created: String,

    @ColumnInfo(name = "currency")
    var currency: String = "TZS",

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "merchant")
    var merchant: String = "",

    @ColumnInfo(name = "metadata")
    var metadata: String,


    @ColumnInfo(name = "decline_reason")
    var declineReason: String,

    @ColumnInfo(name = "notes")
    var notes: String,

    @ColumnInfo(name = "is_load")
    var isLoad: Boolean = false,

    @ColumnInfo(name = "settled")
    var settled: String,

    @ColumnInfo(name = "category")
    var category: String
)