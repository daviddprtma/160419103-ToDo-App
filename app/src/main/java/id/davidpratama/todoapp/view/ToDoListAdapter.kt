package id.davidpratama.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import id.davidpratama.todoapp.R
import id.davidpratama.todoapp.model.ToDo
import kotlinx.android.synthetic.main.todo_item_layout.view.*

class ToDoListAdapter(val todoList:ArrayList<ToDo>, val adapterOnClick: (ToDo)-> Unit):RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder>() {
    class ToDoListViewHolder(var view:View) :RecyclerView.ViewHolder(view)

    fun updateTodList(newToDoList:List<ToDo>){
        todoList.clear()
        todoList.addAll(newToDoList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todo_item_layout,parent,false)
        return ToDoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
       holder.view.checkTask.text = todoList[position].title

        holder.view.checkTask.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked) {
                adapterOnClick(todoList[position])
            }
        }

        holder.view.imgEdit.setOnClickListener {
            val action = TodoListFragmentDirections.actionEditToDoFragment(todoList[position].uuid)

            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
}