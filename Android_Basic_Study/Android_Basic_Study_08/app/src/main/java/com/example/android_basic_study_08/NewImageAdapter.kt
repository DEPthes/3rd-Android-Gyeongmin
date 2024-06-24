package com.example.android_basic_study_08

import android.util.Log
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.android_basic_study_08.databinding.ItemBookmarkBinding
import com.example.android_basic_study_08.databinding.ItemNewimageBinding
import com.example.android_basic_study_08.entity.NewImage
import kotlin.math.log

class NewImageAdapter () : RecyclerView.Adapter<NewImageAdapter.ViewHolder>() {
    val items = mutableListOf<NewImage>()
    inner class ViewHolder(private val binding: ItemNewimageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItems(items: NewImage) {
            binding.tvNewImage.text = items.description.toString()
            Log.d("TAG",items.description.toString())
            Glide.with(binding.ivNewImage)
                .load(items.urls)
                .transform(RoundedCorners(40))
                .into(binding.ivNewImage)
        }
    }
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var itemClickListener: onItemClickListener

    fun setItemClickListener(itemClickListener: onItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNewimageBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NewImageAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    fun addItem(newList: List<NewImage>) {
        items.addAll(newList)
        notifyItemRangeInserted(itemCount, newList.size)
    }
}