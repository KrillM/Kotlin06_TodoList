package com.example.kotlin06_todolist

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin06_todolist.databinding.ActivityMainBinding
import com.example.kotlin06_todolist.db.AppDB
import com.example.kotlin06_todolist.db.TodoDao
import com.example.kotlin06_todolist.db.TodoEntity
import java.util.ArrayList

class MainActivity : AppCompatActivity(), OnItemLongClickListener {

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
            adapter = TodoRecyclerViewAdapter(todoList, this)
            binding.recyclerview.adapter = adapter
            binding.recyclerview.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllTodoList()
    }

    override fun onLongClick(position: Int) {
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.alert_message))
        builder.setNegativeButton(getString(R.string.alert_no), null)
        builder.setPositiveButton(getString(R.string.alert_yes),
            object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    deleteTodo(position)
                }
            })
        builder.show()
    }

    private fun deleteTodo(position: Int){
        Thread{
            todoDao.deleteTodo(todoList[position])
            todoList.removeAt(position)
            runOnUiThread {
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
}