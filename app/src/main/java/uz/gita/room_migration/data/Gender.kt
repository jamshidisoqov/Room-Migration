package uz.gita.room_migration.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gender")
data class Gender(
    @PrimaryKey(autoGenerate = true) val id: Int, val gender: String
)
