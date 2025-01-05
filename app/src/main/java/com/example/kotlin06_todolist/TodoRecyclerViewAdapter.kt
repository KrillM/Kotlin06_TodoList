package com.example.kotlin06_todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin06_todolist.databinding.ItemTodoBinding
import com.example.kotlin06_todolist.db.TodoEntity
import java.util.ArrayList

class TodoRecyclerViewAdapter(private val todoList : ArrayList<TodoEntity>, private val listener: OnItemLongClickListener) : RecyclerView.Adapter<TodoRecyclerViewAdapter.MyViewHolder>(){

    inner class MyViewHolder(binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root){
        val tv_importance = binding.tvImportance
        val tv_title = binding.tvTitle
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding : ItemTodoBinding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todoData = todoList[position]

        when(todoData.importance){
            1->{
                holder.tv_importance.setBackgroundColor(
                    ContextCompat.getColor(holder.tv_importance.context, R.color.red)
                )
            }
            2->{
                holder.tv_importance.setBackgroundColor(
                    ContextCompat.getColor(holder.tv_importance.context, R.color.yellow)
                )
            }
            3->{
                holder.tv_importance.setBackgroundColor(
                    ContextCompat.getColor(holder.tv_importance.context, R.color.green)
                )
            }
        }
        holder.tv_importance.text = todoData.importance.toString()
        holder.tv_title.text = todoData.title

        holder.root.setOnLongClickListener {
            listener.onLongClick(position)
            false
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

}