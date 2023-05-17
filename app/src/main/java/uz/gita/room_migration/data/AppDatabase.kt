package uz.gita.room_migration.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [UserEntity::class,Gender::class,Names::class],
    version = 8,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = AppDatabase.AutoMigration1To2::class),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5, spec = AppDatabase.AutoMigration4To5::class),
        AutoMigration(from = 5, to = 6, spec = AppDatabase.AutoMigration5To6::class),
        //Auto migration create table
        AutoMigration(from = 6, to = 7),
        //Auto migration create table
        AutoMigration(from = 7, to = 8),

    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UsersDao

    abstract fun getGenderDao():GenderDao

    @RenameColumn(tableName = "users_list", fromColumnName = "name", toColumnName = "full_name")
    class AutoMigration1To2 : AutoMigrationSpec

    @DeleteColumn(tableName = "users_list", columnName = "age")
    class AutoMigration4To5 : AutoMigrationSpec

    @RenameColumn(tableName = "users_list", fromColumnName = "gender", toColumnName = "gender_is")
    @RenameTable(fromTableName = "users_list", toTableName = "users")
    class AutoMigration5To6 : AutoMigrationSpec

}
