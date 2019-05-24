package design.akililab.akilipocket.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


/**
 * Defines methods for using the Balance class with Room.
 */

@Dao
interface BalanceDao {

    @Insert
    fun insert(balance: Balance)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param balance new value to write
     */

    @Update
    fun update(balance: Balance)


    /**
     * Selects and returns the row that matches the supplied accountId, which is our key.
     *
     * @param key accountId to match
     */
    @Query("SELECT * from balance_table WHERE account_id = :key")
    fun get(key: String): Balance?
}