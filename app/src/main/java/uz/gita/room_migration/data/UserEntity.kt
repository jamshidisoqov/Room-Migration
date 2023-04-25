package uz.gita.room_migration.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_list")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "full_name")
    val name: String,
    @ColumnInfo(defaultValue = "18")
    val age: Int
)
