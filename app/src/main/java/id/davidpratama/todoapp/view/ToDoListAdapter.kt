package id.davidpratama.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import id.davidpratama.todoapp.R
import id.davidpratama.todoapp.databinding.TodoItemLayoutBinding
import id.davidpratama.todoapp.model.ToDo
import kotlinx.android.synthetic.main.todo_item_layout.view.*

class ToDoListAdapter(val todoList:ArrayList<ToDo>,
                      val adapterOnClick: (ToDo)-> Unit):RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder>(),
                      ToDoCheckedChangeListener,ToDoEditClickListener {
    class ToDoListViewHolder(var view:TodoItemLayoutBinding) :RecyclerView.ViewHolder(view.root)

    fun updateTodList(newToDoList:List<ToDo>){
        todoList.clear()
        todoList.addAll(newToDoList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.todo_item_layout,parent,false)
        val view = DataBindingUtil.inflate<TodoItemLayoutBinding>(inflater,R.layout.todo_item_layout,parent,false)
        return ToDoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        holder.view.todo = todoList[position]
        holder.view.listener = this
        holder.view.editListener = this
//       holder.view.checkTask.text = todoList[position].title
//
//        holder.view.checkTask.setOnCheckedChangeListener { compoundButton, isChecked ->
//            if(isChecked) {
//                adapterOnClick(todoList[position])
//            }
//        }
//
//        holder.view.imgEdit.setOnClickListener {
//            val action = TodoListFragmentDirections.actionEditToDoFragment(todoList[position].uuid)
//
//            Navigation.findNavController(it).navigate(action)
//        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onToDoCheckedChange(cb: CompoundButton, isChecked: Boolean, obj: ToDo) {
        if(isChecked) {
             adapterOnClick(obj)
        }
    }

    override fun onToDoEditClick(v: View) {
        val action = TodoListFragmentDirections.actionEditToDoFragment(v.tag.toString().toInt())
        Navigation.findNavController(v).navigate(action)
    }
}