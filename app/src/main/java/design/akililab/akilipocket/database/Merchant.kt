package design.akililab.akilipocket.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URL
import java.sql.Timestamp
import java.time.OffsetDateTime
import java.util.*

@Entity(tableName = "merchant_table")

data class Merchant(
    @PrimaryKey(autoGenerate = false)
    var id: String,

    @ColumnInfo(name = "address")
    var address: String,

    @ColumnInfo(name = "created")
    var created: Date,

    @ColumnInfo(name = "logo")
    var logo: String,

    @ColumnInfo(name = "emoji")
    var emoji: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "category")
    var category: String,


    @ColumnInfo(name = "group_id")
    var groupId: String


)