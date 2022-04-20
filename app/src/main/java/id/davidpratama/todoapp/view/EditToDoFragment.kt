package id.davidpratama.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import id.davidpratama.todoapp.R
import id.davidpratama.todoapp.viewmodel.DetailToDoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*

class EditToDoFragment : Fragment() {
    private  lateinit var viewModel:DetailToDoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailToDoViewModel::class.java)

        val uuid = EditToDoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)
        observeViewModel()

        txtJudulToDo.text = "Edit ToDo"
        btnAdd.text = "Save Changes"

        btnAdd.setOnClickListener {
            val radio = view.findViewById<RadioButton>(radiogroupPriority.checkedRadioButtonId)
            viewModel.update(txtTitle.text.toString(), txtNotes.text.toString(),
                radio.tag.toString().toInt(), uuid)
            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }
    }

    private fun observeViewModel() {
        viewModel.toDoLD.observe(viewLifecycleOwner, Observer {
            txtTitle.setText(it.title)
            txtNotes.setText(it.notes)

            when(it.priority){
                1 -> radioLow.isChecked = true
                2 -> radioMedium.isChecked = true
                else -> radioHigh.isChecked = true
            }
        })
    }
}