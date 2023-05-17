package uz.gita.room_migration.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "names")
data class Names(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val fullName:String
)
