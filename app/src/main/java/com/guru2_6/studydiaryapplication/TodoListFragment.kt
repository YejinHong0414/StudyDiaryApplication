package com.guru2_6.studydiaryapplication

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.guru2_6.studydiaryapplication.databinding.ItemTodoBinding


class TodoListFragment : Fragment() {


    override fun onCreateView( // 추가
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("life_cycle","F onCreateView")
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.activity_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("life cycle", "F onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        // 기능!!!!

        /* lateinit var binding: ActivityTodoListBinding     얘네는 TodoListActivity로 옮겨감

        val data = arrayListOf<Todo>()

        fun toggleTodo(todo: Todo) {
            todo.isDone = !todo.isDone
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }

        fun addTodo() {
            val todo = Todo(binding.editTextTextPersonName.text.toString())
            data.add(todo)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }

        fun deleteTodo(todo: Todo) {
            data.remove(todo)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }*/
    }


    data class Todo(
        val text: String,
        var isDone: Boolean = false
    )

    class TodoAdapter(
        private val myDataset: List<Todo>,
        val onClickDeleteIcon: (todo: Todo) -> Unit,
        val onClickItem: (todo: Todo) -> Unit
    ) :
        RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

        class TodoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): TodoAdapter.TodoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_todo, parent, false)
            return TodoViewHolder(ItemTodoBinding.bind(view))
        }

        override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
            val todo = myDataset[position]
            holder.binding.todoText.text = todo.text
            //holder.binding.todoText.paintFlags = holder.binding.todoText.paintFlags or Paint.UNDERLINE_TEXT_FLAG

            if (todo.isDone) {
                holder.binding.todoText.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    setTypeface(null, Typeface.ITALIC)
                }
            } else {
                holder.binding.todoText.apply {
                    paintFlags = 0
                    setTypeface (null, Typeface.NORMAL)
                }
            }

            holder.binding.deleteImageView.setOnClickListener {
                onClickDeleteIcon.invoke(todo)
            }

            holder.binding.root.setOnClickListener {
                onClickItem.invoke(todo)
            }
        }

        override fun getItemCount() = myDataset.size



    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d("life_cycle","F onActivityCreated")

        val data = arguments?.getString("hello")
        //Log.d("data", data)

        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.d("life_cycle", "F onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("life_cycle", "F onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("life_cycle", "F onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("life_cycle", "F onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("life_cycle", "F onDestroyView")
        super.onDestroyView()
    }

    override fun onDetach() {
        Log.d("life_cycle", "F onDetach")
        super.onDetach()

    }


}