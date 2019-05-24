package design.akililab.akilipocket.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "balance_table")
data class Balance(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "account_id")
    var accountId: String,

    @ColumnInfo(name = "balance")
    var balance: Float = 0F,

    @ColumnInfo(name = "total_balance")
    var totalBalance: Float = 0F,

    @ColumnInfo(name = "currency")
    var currency: String = "TZS",

    @ColumnInfo(name = "spent_today")
    var spentToday: Float = 0F

)