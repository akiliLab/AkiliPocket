package design.akililab.akilipocket.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


/**
 * Defines methods for using the Merchant class with Room.
 */

@Dao
interface MerchantDao {

    @Insert
    fun insert(merchant: Merchant)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param merchant new value to write
     */

    @Update
    fun update(merchant: Merchant)


    /**
     * Selects and returns the row that matches the supplied merchant id, which is our key.
     *
     * @param key id to match
     */
    @Query("SELECT * from merchant_table WHERE id = :key")
    fun get(key: String): Merchant?
}