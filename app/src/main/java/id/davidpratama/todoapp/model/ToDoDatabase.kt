package id.davidpratama.todoapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.davidpratama.todoapp.util.MIGRATION_1_2

@Database(entities = arrayOf(ToDo::class), version = 2)

abstract class ToDoDatabase:RoomDatabase() {
    abstract fun todoDao():ToDoDao

    companion object{
        @Volatile private var instance: ToDoDatabase ?= null
        private val LOCK = Any()

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ToDoDatabase::class.java,
                "newtododb"
            ).addMigrations(MIGRATION_1_2).build()

        operator fun invoke(context:Context) = instance?: synchronized(LOCK) {
            instance?:buildDatabase(context).also {
                instance = it
            }
        }

    }
}