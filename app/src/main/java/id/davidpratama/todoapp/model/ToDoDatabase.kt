package id.davidpratama.todoapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ToDo::class), version = 1)

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
            ).build()

        operator fun invoke(context:Context) = instance?: synchronized(LOCK) {
            instance?:buildDatabase(context).also {
                instance = it
            }
        }

    }
}