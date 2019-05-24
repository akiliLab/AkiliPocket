package design.akililab.akilipocket.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "account_table")

data class Account(
    @PrimaryKey(autoGenerate = false)
    var id: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "created")
    var created: Date
)