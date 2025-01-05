package com.example.kotlin06_todolist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin06_todolist.databinding.ActivityMainBinding
import com.example.kotlin06_todolist.db.AppDB
import com.example.kotlin06_todolist.db.TodoDao
import com.example.kotlin06_todolist.db.TodoEntity
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var db : AppDB
    private lateinit var todoDao: TodoDao
    private lateinit var todoList : ArrayList<TodoEntity>
    private lateinit var adapter: TodoRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDB.getInstance(this)!!
        todoDao = db.getTodoDao()

        getAllTodoList()

        binding.btnAddTodo.setOnClickListener{
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getAllTodoList() {
        Thread{
            todoList = ArrayList(todoDao.getAllTodo())
            setRecycleView()
        }.start()
    }

    private fun setRecycleView() {
        runOnUiThread {
            adapter = TodoRecyclerViewAdapter(todoList)
            binding.recyclerview.adapter = adapter
            binding.recyclerview.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllTodoList()
    }
}