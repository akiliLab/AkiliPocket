package design.akililab.akilipocket.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TransactionDao {




    @Insert
    fun insert(transaction: Transaction)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param transaction new value to write
     */

    @Update
    fun update(transaction: Transaction)


    /**
     * Selects and returns the row that matches the supplied merchant id, which is our key.
     *
     * @param key id to match
     */
    @Query("SELECT * from transaction_table WHERE id = :key")
    fun get(key: String): Transaction?



    /**
     * Selects and returns all rows in the table,
     *
     * sorted by created in descending order.
     */
    @Query("SELECT * FROM transaction_table WHERE account_id = :key ORDER BY created DESC")
    fun getTransactionsByAccountId(key: String): LiveData<List<Transaction>>
}