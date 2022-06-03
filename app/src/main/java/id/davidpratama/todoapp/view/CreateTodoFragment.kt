package id.davidpratama.todoapp.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.TimePicker
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
import kotlinx.android.synthetic.main.fragment_create_todo.view.*
import java.util.*
import java.util.concurrent.TimeUnit

class CreateTodoFragment : Fragment(),ButtonAddToDoClickListener,RadioClickListener
,DateClickListener,TimeClickListener,DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private lateinit var viewModel: DetailToDoViewModel
    private lateinit var dataBinding: FragmentCreateTodoBinding
    var year = 0
    var month = 0
    var day = 0
    var hour = 0
    var minute = 0

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
        dataBinding.listenerDate = this
        dataBinding.listenerTime = this
    }

    override fun onButtonAddtoDo(v: View) {
        viewModel.addTodo(dataBinding.todo!!)

        val c = Calendar.getInstance()
        c.set(year,month,day,hour,minute,0)
        val today = Calendar.getInstance()
        val diff = (c.timeInMillis /1000L) - (today.timeInMillis/1000L)
        dataBinding.todo!!.todo_date = (c.timeInMillis/1000L).toInt()

        Toast.makeText(v.context,"Todo Created",Toast.LENGTH_SHORT).show()
        val myWorkReq = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(diff,TimeUnit.SECONDS)
                .setInputData(workDataOf("title" to "Todo Created", "message" to "A New todo has been created. Congrats!! :D"))
                .build()
        WorkManager.getInstance(requireContext()).enqueue(myWorkReq)
        Navigation.findNavController(v).popBackStack()
    }

    override fun onRadioClick(v: View, obj: ToDo) {
       obj.priority = v.tag.toString().toInt()
    }

    override fun onDateClick(v: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        activity?.let {
                it1 ->
            DatePickerDialog(it1,this,year,month,day).show()
        }
    }

    override fun onTimeClick(v: View) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)
        TimePickerDialog(activity,this,hour,minute , DateFormat.is24HourFormat(activity)).show()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        Calendar.getInstance().let {
            it.set(year,month,day)
            dataBinding.root.txtDate.setText(day.toString().padStart(2,'0')
                    + "- " + month.toString().padStart(2,'0') + " -" + year)
            this.year = year
            this.month = month
            this.day = day
        }
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        dataBinding.root.txtTime.setText(hourOfDay.toString().padStart(2,'0')
                + " -" + minute.toString().padStart(2,'0'))
        hour =  hourOfDay
        this.minute = minute
    }
}