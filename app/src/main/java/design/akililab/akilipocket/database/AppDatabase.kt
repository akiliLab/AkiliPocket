package design.akililab.akilipocket.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * A database that stores App information.
 * And a global method to get access to the database.
 *
 */


@Database(entities = [Account::class, Balance::class, Transaction::class, Merchant::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)

abstract class AppDatabase: RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract val accountDao: AccountDao

    abstract val balanceDao: BalanceDao

    abstract val merchantDao: MerchantDao

    abstract val transactionDao: TransactionDao


    /**
     * Define a companion object, this allows us to add functions on the SleepDatabase class.
     *
     * For example, clients can call `SleepDatabase.getInstance(context)` to instantiate
     * a new SleepDatabase.
     */
    companion object {
        /**
         * INSTANCE will keep a reference to any database returned via getInstance.
         *
         * This will help us avoid repeatedly initializing the database, which is expensive.
         *
         *  The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. It means that changes made by one
         *  thread to shared data are visible to other threads.
         */
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Helper function to get the database.
         *
         * If a database has already been retrieved, the previous database will be returned.
         * Otherwise, create a new database.
         *
         * This function is threadsafe, and callers should cache the result for multiple database
         * calls to avoid overhead.
         *
         * This is an example of a simple Singleton pattern that takes another Singleton as an
         * argument in Kotlin.
         *
         * To learn more about Singleton read the wikipedia article:
         * https://en.wikipedia.org/wiki/Singleton_pattern
         *
         * @param context The application context Singleton, used to get access to the filesystem.
         */
        fun getInstance(context: Context): AppDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {
                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE
                // If instance is `null` make a new database instance.
                if (instance == null) {

                    instance = buildDatabase(context)
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }
                // Return instance; smart cast to be non-null.
                return instance
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "akililab_akilipocket"
            ).addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // insert the data on the IO Thread
                    ioThread {
                        getInstance(context).transactionDao.insertMultiple(PREPOPULATE_DATA)
                    }
                }
            })
            // Wipes and rebuilds instead of migrating if no Migration object.
            // Migration is not part of this lesson. You can learn more about
            // migration with Room in this blog post:
            // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
            //
            .fallbackToDestructiveMigration()
            .build()


        val PREPOPULATE_DATA = listOf(
            Transaction(
                "tx_00008zIcpb1TB4yeIFXMzx",
                "acc_00009237aqC8c5umZmrRdh",
                13013f,
                -510f,
                "2015-08-22T12:20:18Z",
                "GBP",
                "THE DE BEAUVOIR DELI C LONDON GBR",
                "merch_00008zIcpbAKe8shBxXUtl",
                "",
                "null",
                "Salmon sandwich 🍞",
                false,
                "2015-08-23T12:20:18Z",
                "eating_out"
            ),
            Transaction(
                "tx_06008zL2INM3xZ41THuRF3",
                "acc_00009237aqC8c5umZmrRdh",
                12334f,
                -679f,
                "2015-08-23T16:15:03Z",
                "GBP",
                "VUE BSL LTD ISLINGTON GBR",
                "merch_00008z6uFVhVBcaZzSQwCX",
                "",
                "null",
                "Gastric Juice",
                false,
                "2015-08-23T12:20:18Z",
                "eating_out"
            ),
            Transaction(
                "tx_00708zL2INM3xZ41THuRF3",
                "acc_00009237aqC8c5umZmrRdh",
                13013f,
                -510f,
                "2015-08-22T12:20:18Z",
                "GBP",
                "THE DE BEAUVOIR DELI C LONDON GBR",
                "merch_00008zIcpbAKe8shBxXUtl",
                "",
                "null",
                "Salmon sandwich 🍞",
                false,
                "2015-08-22T12:20:18Z",
                "eating_out"
            ),
            Transaction(
                "tx_00088zL2INM3xZ41THuRF3",
                "acc_00009237aqC8c5umZmrRdh",
                12334f,
                -679f,
                "2015-08-22T12:20:18Z",
                "GBP",
                "VUE BSL LTD ISLINGTON GBR",
                "merch_00008z6uFVhVBcaZzSQwCX",
                "",
                "null",
                "Gastric Juice",
                false,
                "2015-08-22T12:20:18Z",
                "eating_out"
            ),
            Transaction(
                "tx_00009fRczGMWXXifXfqLq5",
                "acc_00009237aqC8c5umZmrRdh",
                12334f,
                -20239f,
                "2019-02-03T03:02:42.925Z",
                "GBP",
                "RwandAir Kigali RWA",
                "merch_00009ctN0K0eAV7pr00Sf3",
                "",
                "null",
                "Gastric Juice",
                false,
                "2019-02-04T05:15:56.799Z",
                "expenses"
            ),
            Transaction(
                "tx_00009f5HoGe8lqWjjDQ61B",
                "acc_00009237aqC8c5umZmrRdh",
                12334f,
                -679f,
                "2019-01-23T08:21:46.362Z",
                "GBP",
                "Spotify UK London        GBR",
                "merch_00009bk8PYjxF8iGn4ZSdN",
                "",
                "null",
                "Gastric Juice",
                false,
                "2019-01-24T16:00:04.432Z",
                "entertainment"
            ),
            Transaction(
                "tx_00009fCAFghMZxefoYl8sb",
                "acc_00009237aqC8c5umZmrRdh",
                0f,
                -640f,
                "2019-01-26T16:00:04.432Z",
                "GBP",
                "GAME DISCOUNT WORLD T  DAR ES SAL    TZA",
                "merch_00009RD3XHWlkWM4pZ72UT",
                "",
                "null",
                "Gastric Juice",
                false,
                "2019-01-26T16:00:04.432Z",
                "eating_out"
            ),
            Transaction(
                "tx_1009fCAFghMZxefoYl8sb",
                "acc_00009237aqC8c5umZmrRdh",
                0f,
                -640f,
                "2015-08-22T12:20:18Z",
                "GBP",
                "GAME DISCOUNT WORLD T  DAR ES SAL    TZA",
                "merch_00009RD3XHWlkWM4pZ72UT",
                "",
                "null",
                "Gastric Juice",
                false,
                "2015-08-22T12:20:18Z",
                "shopping"
            )
            ,
            Transaction(
                "tx_00009fE3w1om8Whk7COA2z",
                "acc_00009237aqC8c5umZmrRdh",
                0f,
                -640f,
                "2019-01-27T13:58:42.436Z",
                "GBP",
                "CENTURY CINEMAX-MLIMAN DAR ES SALAAM TZA",
                "merch_00009fE3w2STkuSo6Kxrvt",
                "",
                "null",
                "Gastric Juice",
                false,
                "2019-01-30T16:00:04.432Z",
                "entertainment"
            ),
            Transaction(
                "tx_07809fCAFghMZxefoYl8sb",
                "acc_00009237aqC8c5umZmrRdh",
                0f,
                -640f,
                "2019-04-23T03:14:11.84Z",
                "GBP",
                "NETFLIX.COM   866-716-0414  NLD",
                "merch_000094OsgoFbcexvVB3AUT",
                "",
                "null",
                "Gastric Juice",
                false,
                "2019-04-23T03:14:12.086Z",
                "entertainment"
            )
        )
    }

}