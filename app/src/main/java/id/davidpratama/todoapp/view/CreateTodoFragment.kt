package id.davidpratama.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import id.davidpratama.todoapp.R
import id.davidpratama.todoapp.databinding.FragmentCreateTodoBinding
import id.davidpratama.todoapp.model.ToDo
import id.davidpratama.todoapp.util.NotificationHelper
import id.davidpratama.todoapp.util.TodoWorker
import id.davidpratama.todoapp.viewmodel.DetailToDoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*
import java.util.concurrent.TimeUnit

class CreateTodoFragment : Fragment(),ButtonAddToDoClickListener,RadioClickListener {
    private lateinit var viewModel: DetailToDoViewModel
    private lateinit var dataBinding: FragmentCreateTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

//        return inflater.inflate(R.layout.fragment_create_todo, container, false)
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_create_todo,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.todo = ToDo("","",3,0,0)
        viewModel = ViewModelProvider(this).get(DetailToDoViewModel::class.java)
        dataBinding.listener = this
        dataBinding.radioListener = this

//        btnAdd.setOnClickListener {
//            NotificationHelper(view.context).createNotification("Todo Created","A New todo has been created. Congrats!! :D ")
//            val myWorkReq = OneTimeWorkRequestBuilder<TodoWorker>()
//                .setInitialDelay(30,TimeUnit.SECONDS)
//                .setInputData(workDataOf("title" to "Todo Created", "message" to "A New todo has been created. Congrats!! :D"))
//                .build()
//            WorkManager.getInstance(requireContext()).enqueue(myWorkReq)
//
//            var radio = view.findViewById<RadioButton>(radiogroupPriority.checkedRadioButtonId)
//            var todo = ToDo(txtTitle.text.toString(), txtNotes.text.toString(),radio.tag.toString().toInt(),0)
//            viewModel.addTodo(todo)
//            Toast.makeText(it.context,"Todo Created",Toast.LENGTH_SHORT).show()
//            Navigation.findNavController(it).popBackStack()
//        }
    }

    override fun onButtonAddtoDo(v: View) {
        viewModel.addTodo(dataBinding.todo!!)
        Toast.makeText(v.context,"Todo Created",Toast.LENGTH_SHORT).show()
        val myWorkReq = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(30,TimeUnit.SECONDS)
                .setInputData(workDataOf("title" to "Todo Created", "message" to "A New todo has been created. Congrats!! :D"))
                .build()
        WorkManager.getInstance(requireContext()).enqueue(myWorkReq)
        Navigation.findNavController(v).popBackStack()
    }

    override fun onRadioClick(v: View, obj: ToDo) {
       obj.priority = v.tag.toString().toInt()
    }
}