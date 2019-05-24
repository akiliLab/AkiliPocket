package design.akililab.akilipocket.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Defines methods for using the Account class with Room.
 */

@Dao
interface AccountDao {

    @Insert
    fun insert(account: Account)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param account new value to write
     */

    @Update
    fun update(account: Account)


    /**
     * Selects and returns the row that matches the supplied accountId, which is our key.
     *
     * @param key accountId to match
     */
    @Query("SELECT * from account_table WHERE id = :key")
    fun get(key: String): Account?



    /**
     * Selects and returns all rows in the table,
     *
     * sorted by created in descending order.
     */
    @Query("SELECT * FROM account_table ORDER BY created DESC")
    fun getAllAccounts(): LiveData<List<Account>>

}