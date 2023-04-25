package uz.gita.room_migration.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.DeleteTable
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [UserEntity::class],
    version = 7,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4, spec = AppDatabase.AutoMigration3To4::class),
        AutoMigration(from = 4, to = 5, spec = AppDatabase.AutoMigration4To5::class),
        AutoMigration(from = 5, to = 6, spec = AppDatabase.AutoMigration5To6::class),
        AutoMigration(from = 6, to = 7, spec = AppDatabase.AutoMigration6To7::class)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UsersDao

    @DeleteColumn(tableName = "users", columnName = "gender")
    class AutoMigration3To4 : AutoMigrationSpec

    @RenameColumn(tableName = "users", fromColumnName = "name", toColumnName = "full_name")
    class AutoMigration4To5 : AutoMigrationSpec

    @RenameTable(fromTableName = "users", toTableName = "users_list")
    class AutoMigration5To6 : AutoMigrationSpec

    @DeleteTable(tableName = "orders")
    class AutoMigration6To7 : AutoMigrationSpec

}