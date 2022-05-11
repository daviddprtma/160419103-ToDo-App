package id.davidpratama.todoapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import id.davidpratama.todoapp.R
import id.davidpratama.todoapp.databinding.FragmentEditTodoBinding
import id.davidpratama.todoapp.model.ToDo
import id.davidpratama.todoapp.viewmodel.DetailToDoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*

class EditToDoFragment : Fragment(),RadioClickListener,ToDoSaveChangeListener {
    private  lateinit var viewModel:DetailToDoViewModel
    private lateinit var dataBinding: FragmentEditTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate<FragmentEditTodoBinding>(inflater,R.layout.fragment_edit_todo,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailToDoViewModel::class.java)

        val uuid = EditToDoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)
        observeViewModel()

        dataBinding.radioListener = this
        dataBinding.listener = this

//        btnAdd.setOnClickListener {
//            val radio = view.findViewById<RadioButton>(radiogroupPriority.checkedRadioButtonId)
//            viewModel.update(txtTitle.text.toString(), txtNotes.text.toString(),
//                radio.tag.toString().toInt(), uuid)
//            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
//            Navigation.findNavController(it).popBackStack()
//        }
    }

    private fun observeViewModel() {
        viewModel.toDoLD.observe(viewLifecycleOwner, Observer {
            dataBinding.todo = it
        })
    }

    override fun onRadioClick(v: View, obj: ToDo) {
        obj.priority = v.tag.toString().toInt()
    }

    override fun onToDoSaveChange(v: View, obj: ToDo) {
        Log.d("Coba todo", obj.toString())
        viewModel.update(obj.title,obj.notes,obj.priority,obj.uuid)
        Toast.makeText(v.context, "Todo updated", Toast.LENGTH_SHORT).show()
    }
}