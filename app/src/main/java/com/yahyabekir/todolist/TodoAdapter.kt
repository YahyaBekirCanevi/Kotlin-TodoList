package com.yahyabekir.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yahyabekir.todolist.databinding.ItemTodoBinding

class TodoAdapter(
    private val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current: Todo = todos[position]
        holder.bind(current)
    }

    override fun getItemCount(): Int {
        return todos.size
    }


    fun addTodo(todo: Todo, position: Int? = null) {
        if(todo.title.isNotEmpty()) {
            todos.add(todo)
            notifyItemInserted(position ?: (todos.size - 1))
        }
    }

    fun deleteTodo(position: Int): Todo {
        val deleted = todos[position]
        todos.removeAt(position)
        notifyItemRemoved(position)
        return deleted
    }

    class TodoViewHolder(
        private val itemBinding: ItemTodoBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
            tvTodoTitle.paintFlags = if (isChecked)
                tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
            else tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }

        fun bind(todo: Todo) {
            itemBinding.apply {
                tvTodoTitle.text = todo.title
                cbDone.isChecked = todo.isChecked
                toggleStrikeThrough(tvTodoTitle, todo.isChecked)
                cbDone.setOnCheckedChangeListener { _, isChecked ->
                    toggleStrikeThrough(tvTodoTitle, isChecked)
                    todo.isChecked = !todo.isChecked
                }
            }
        }
    }
}