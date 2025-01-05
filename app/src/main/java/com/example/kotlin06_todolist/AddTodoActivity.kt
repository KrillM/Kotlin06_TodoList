package com.example.kotlin06_todolist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin06_todolist.databinding.ActivityAddTodoBinding
import com.example.kotlin06_todolist.db.AppDB
import com.example.kotlin06_todolist.db.TodoDao
import com.example.kotlin06_todolist.db.TodoEntity
import kotlin.concurrent.thread

class AddTodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTodoBinding
    lateinit var db : AppDB
    lateinit var todoDao: TodoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDB.getInstance(this)!!
        todoDao = db.getTodoDao()

        binding.btnComplete.setOnClickListener {
            insertTodo()
        }
    }

    private fun insertTodo() {
        val todoTitle = binding.edtTitle.text.toString()
        var todoImportance = binding.radioGroup.checkedRadioButtonId
        var impData = 0

        when(todoImportance){
            R.id.btn_high -> {
                impData = 1
            }
            R.id.btn_middle -> {
                impData = 2
            }
            R.id.btn_low -> {
                impData = 3
            }
        }

        if(impData==0 || todoTitle.isBlank()){
            Toast.makeText(this, "모든 항목을 채워주세요.", Toast.LENGTH_SHORT).show()
        }
        else{
            thread {
                todoDao.insertTodo(TodoEntity(null, todoTitle, impData))
                runOnUiThread {
                    Toast.makeText(this, "할 일이 추가되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.start()
        }
    }
}