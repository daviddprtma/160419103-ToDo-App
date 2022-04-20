package id.davidpratama.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import id.davidpratama.todoapp.model.ToDo
import id.davidpratama.todoapp.model.ToDoDatabase
import id.davidpratama.todoapp.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailToDoViewModel(application: Application):AndroidViewModel(application),CoroutineScope {
    val toDoLD = MutableLiveData<ToDo>()
    private val job = Job()
    override val coroutineContext: CoroutineContext
    get() = job + Dispatchers.Main

    fun addTodo(toDo: ToDo) {
        launch {
            val db = buildDB(getApplication())
            db.todoDao().insertAll(toDo)
        }
    }

    fun fetch(uuid:Int){
        launch {
            val db = buildDB(getApplication())
            toDoLD.value = db.todoDao().selectTodo(uuid)
        }
    }

    fun update(title:String, notes:String, priority:Int,uuid:Int){
        launch {
            val db = buildDB(getApplication())
            db.todoDao().update(title,notes,priority,uuid)
        }
    }

}