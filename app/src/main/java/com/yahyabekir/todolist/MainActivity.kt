package com.yahyabekir.todolist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.yahyabekir.todolist.databinding.ActivityMainBinding

/// TODO: Deleted Todo bug
/// TODO: Hold Press to change position
/// TODO: Tap to see details
/// TODO: Detail from Bottom
/// TODO: Add button will be replaced
/// TODO: Design change
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        todoAdapter = TodoAdapter(mutableListOf())

        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        binding.rvTodoItems.adapter = todoAdapter
        binding.rvTodoItems.layoutManager = LinearLayoutManager(this)

        swipeTodo()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home_actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.addTodo -> {
            try {
                // User chose the "Add" item, show the app settings UI...
                val todoFragment = TodoFragment(todoAdapter)
                todoFragment.show(supportFragmentManager, "Todo")
                todoFragment.changeBackground()
                true
            } catch (error: java.lang.Exception) {
                print(error.message)
                false
            }
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun swipeTodo() {
        val direction = ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            direction, direction
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called when the item is moved.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // below line is to get the position of the item at that position.
                val position = viewHolder.adapterPosition
                val deleted = todoAdapter.deleteTodo(position)

                // below line is to display our snack-bar with action.
                Snackbar
                    .make(binding.rvTodoItems, "Deleted ${deleted.title}", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        // adding on click listener to our action of snack bar.
                        // below line is to add our item to array list with a position.
                        todoAdapter.addTodo(deleted, position)
                    }
                    .show()
            }
            // at last we are adding this to our recycler view.
        }).attachToRecyclerView(binding.rvTodoItems)
    }
}