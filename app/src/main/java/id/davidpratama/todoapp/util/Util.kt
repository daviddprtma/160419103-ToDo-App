package id.davidpratama.todoapp.util

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import id.davidpratama.todoapp.model.ToDoDatabase

val DB_NAME = "newtododb"

fun buildDB(context:Context):ToDoDatabase{
    val db = Room.databaseBuilder(context, ToDoDatabase::class.java, DB_NAME)
        .addMigrations(MIGRATION_1_2).build()
    return db
}

val MIGRATION_1_2 = object : Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 3 not null")
    }
}