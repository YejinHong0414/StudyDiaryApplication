package com.guru2_6.studydiaryapplication

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guru2_6.studydiaryapplication.databinding.ActivityTodoListBinding


// 할 일 리스트 의 걍우 Fragment가 오류는 없으나 작동을 하지않아 Activity로 따로 제출합니다
class TodoListActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    private lateinit var binding: ActivityTodoListBinding

    private val data = arrayListOf<TodoListFragment.Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        recyclerView = findViewById(R.id.recycler_view);

        data.add(TodoListFragment.Todo("과제 끝내기"))
        data.add(TodoListFragment.Todo("방 정리하기", true))

        fun toggleTodo(todo: TodoListFragment.Todo) {
            todo.isDone = !todo.isDone
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }

        fun addTodo() {
            val todo = TodoListFragment.Todo(binding.editTextTextPersonName.text.toString())
            data.add(todo)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }

        fun deleteTodo(todo: TodoListFragment.Todo) {
            data.remove(todo)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }


        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@TodoListActivity)
            adapter = TodoListFragment.TodoAdapter(data,
                onClickDeleteIcon = {
                    deleteTodo(it)
                },
                onClickItem = {
                    toggleTodo(it)
                }
            )
        }

        binding.addButton.setOnClickListener {
            addTodo()
        }

    }


}
