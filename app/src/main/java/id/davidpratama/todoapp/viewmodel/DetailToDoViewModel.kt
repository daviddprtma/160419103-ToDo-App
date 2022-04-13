package id.davidpratama.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import id.davidpratama.todoapp.model.ToDo
import id.davidpratama.todoapp.model.ToDoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailToDoViewModel(application: Application):AndroidViewModel(application),CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
    get() = job + Dispatchers.Main

    fun addTodo(toDo: ToDo) {
        launch {
            val db = Room.databaseBuilder(
                getApplication(), ToDoDatabase::class.java,
                "newtododb").build()
            db.todoDao().insertAll(toDo)
        }
    }

}