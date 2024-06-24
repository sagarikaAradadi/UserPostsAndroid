package com.samplerecyclerview.ui.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samplerecyclerview.databinding.ItemUsersBinding
import com.samplerecyclerview.ui.ui.model.UsersModel

class UsersAdapter(
    private val context: Context?,
    var postList: List<UsersModel>?,
) : RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersAdapter.MyViewHolder, position: Int) {
        holder.binding?.postModel= postList?.get(position)
    }

    override fun getItemCount(): Int = postList?.size?:0

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemUsersBinding? = null

        constructor(view: ItemUsersBinding) : this(view.root) {
            binding = view
        }
    }

}