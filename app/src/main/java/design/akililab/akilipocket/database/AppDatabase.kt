package design.akililab.akilipocket.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * A database that stores App information.
 * And a global method to get access to the database.
 *
 */


@Database(entities = [Account::class, Balance::class, Transaction::class, Merchant::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)

abstract class AppDatabase: RoomDatabase() {



}