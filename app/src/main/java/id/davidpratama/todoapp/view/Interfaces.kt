package id.davidpratama.todoapp.view

import android.view.View
import android.widget.CompoundButton
import id.davidpratama.todoapp.model.ToDo

interface ToDoCheckedChangeListener{
    fun onToDoCheckedChange(cb:CompoundButton, isChecked:Boolean, obj:ToDo)
}

interface ToDoEditClickListener{
    fun onToDoEditClick(v:View)
}

interface RadioClickListener{
    fun onRadioClick(v:View,obj:ToDo)
}

interface ToDoSaveChangeListener{
    fun onToDoSaveChange(v:View,obj:ToDo)
}

interface ButtonAddToDoClickListener{
    fun onButtonAddtoDo(v:View)
}

interface DateClickListener{
    fun onDateClick(v:View)
}

interface TimeClickListener{
    fun onTimeClick(v:View)
}