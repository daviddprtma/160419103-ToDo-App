package id.davidpratama.todoapp.view

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import id.davidpratama.todoapp.R
import id.davidpratama.todoapp.viewmodel.ListToDoViewModel
import kotlinx.android.synthetic.main.fragment_todo_list.*

class TodoListFragment : Fragment() {
    private lateinit var viewModel: ListToDoViewModel
    private var toDoListAdapter : ToDoListAdapter = ToDoListAdapter(arrayListOf(),
        {item -> viewModel.clearTask(item)})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListToDoViewModel::class.java)
        viewModel.refresh()

        recViewToDo.layoutManager = LinearLayoutManager(context)
        recViewToDo.adapter = toDoListAdapter

        fabAddToDo.setOnClickListener {
            val action = TodoListFragmentDirections.actionCreateToDo()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            toDoListAdapter.updateTodList(it)

            if (it.isEmpty()){
                txtEmpty.visibility = View.VISIBLE
            }
            else{
                txtEmpty.visibility = View.GONE
            }
        })
    }
}