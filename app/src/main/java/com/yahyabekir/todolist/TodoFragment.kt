package com.yahyabekir.todolist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class TodoFragment(private val todoAdapter: TodoAdapter) : DialogFragment() {
    private lateinit var etTodoTitle: EditText
    private lateinit var btnAddTodo: Button
    private lateinit var dialog: AlertDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        // Get the layout inflater
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_todo, null, false)
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        view.apply {
            etTodoTitle = findViewById(R.id.etTodoTitle)
            btnAddTodo = findViewById(R.id.btnAddTodo)
            clipToOutline = true
        }
        btnAddTodo.setOnClickListener {
            val todo = Todo(etTodoTitle.text.toString())
            todoAdapter.addTodo(todo)
            etTodoTitle.text.clear()
        }
        builder.setView(view)
        dialog = builder.create()
        return dialog
    }

    fun changeBackground() {
        dialog.window?.setBackgroundDrawableResource(android.R.color.black)
    }
}