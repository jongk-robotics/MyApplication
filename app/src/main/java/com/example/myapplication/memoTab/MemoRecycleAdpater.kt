package com.example.myapplication.memoTab

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.sqlite.Memo
import com.example.sqlite.SqliteHelper
import kotlinx.android.synthetic.main.memo_item.view.*
import kotlinx.android.synthetic.main.phone_recycler.view.*

/**
 * Created by ysh on 2018-04-12.
 */


class MemoRecycleAdapter(context: Context): RecyclerView.Adapter<MemoRecycleAdapter.MemoHolder>() {
    var listData = mutableListOf<Memo>()
    var helper = SqliteHelper(context, MemoConstant.MEMO_DB_NAME, MemoConstant.MEMO_DB_VERSION)
    inner class MemoHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvName: TextView

        init {
            tvName = itemView.findViewById(R.id.mContextTextView)
            itemView.setOnLongClickListener {

                val id = listData[adapterPosition].no
                val content = listData[adapterPosition].content
//


                val intent = Intent(itemView.context, MemoPopup::class.java)
                intent.putExtra("content", content)
                intent.putExtra("id", id)
                itemView.context.startActivity(intent)

                false
            }
        }
        fun setMemo(memo: Memo){
            itemView.mContextTextView.text = memo.content
            itemView.mContextTextViewTime.text = memo.datetime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.memo_item, parent, false)
        return MemoHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: MemoHolder, position: Int) {
        val memo = listData.get(position)
        holder.setMemo(memo)
    }

    fun addElment(memo: Memo){
        listData.add(memo)
    }

    private fun removeItemView(position: Int) {
        listData.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, listData.size)
    }

    // 데이터 삭제
    private fun removeMemo(memo: Memo) {
        helper.deleteMemo(memo)
    }
}


