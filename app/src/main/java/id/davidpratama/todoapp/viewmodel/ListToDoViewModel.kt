package id.davidpratama.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import id.davidpratama.todoapp.model.ToDo
import id.davidpratama.todoapp.model.ToDoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListToDoViewModel(application: Application):AndroidViewModel(application),CoroutineScope {
    val todoLD = MutableLiveData<List<ToDo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
            val db = Room.databaseBuilder(
                getApplication(),
                ToDoDatabase::class.java, "newtododb").build()

            todoLD.value = db.todoDao().selectAllTodo()
        }
    }

    fun clearTask(todo: ToDo) {
        launch {
            val db = Room.databaseBuilder(
                getApplication(),
                ToDoDatabase::class.java, "newtododb").build()
            db.todoDao().deleteTodo(todo)

            todoLD.value = db.todoDao().selectAllTodo()
        }
    }


}