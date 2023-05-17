package uz.gita.room_migration.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface GenderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGender(gender: Gender)
}