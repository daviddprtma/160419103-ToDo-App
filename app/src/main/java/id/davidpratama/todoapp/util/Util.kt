package id.davidpratama.todoapp.util

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import id.davidpratama.todoapp.model.ToDoDatabase

val DB_NAME = "newtododb"

fun buildDB(context:Context):ToDoDatabase{
    val db = Room.databaseBuilder(context, ToDoDatabase::class.java, DB_NAME)
        .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4).build()
    return db
}

val MIGRATION_1_2 = object : Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 3 not null")
    }
}

//mengapa menggunakan integer dan bukan menggunakan boolean?
//Karena kalau menggunakan integer tidak perlu mengconvert data menjadi toInteger (apabila menggunakan boolean)
//dan itu akan mempercepat proses drpd menggunakan boolean
val MIGRATION_2_3 = object : Migration(2,3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN is_done INTEGER DEFAULT 0 not null ")
    }
}

val MIGRATION_3_4 = object : Migration(3,4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN todo_date INTEGER DEFAULT 0 not null ")
    }
}