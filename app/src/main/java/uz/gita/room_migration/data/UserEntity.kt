package uz.gita.room_migration.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "full_name")
    val name: String,
    @ColumnInfo(name = "gender_is", defaultValue = "1")
    val gender:Int,
    @ColumnInfo(name = "work", defaultValue = "Uz kassa")
    val work:String = "Uz kassa"
)
