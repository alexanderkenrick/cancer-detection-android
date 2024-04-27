package com.dicoding.asclepius.view

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.database.History
import com.dicoding.asclepius.databinding.ItemRowHistoryBinding
import java.text.SimpleDateFormat
import java.util.Date

class HistoryAdapter : ListAdapter<History, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, holder)
    }

    class MyViewHolder(private val binding: ItemRowHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(history: History, holder: MyViewHolder) {
            val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
            val date = history.date?.let { Date(it) }?.let { formatter.format(it) }
            with(binding) {
                imgUri.setImageURI(Uri.parse(history.imageUri))
                dateText.text = date.toString()
                predictionText.text = "${history.prediction} ${history.score}"
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<History>() {
            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }
        }
    }
}