package com.app.reddittask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.reddittask.data.model.Character
import com.app.reddittask.databinding.CharacterItemLayoutBinding
import javax.inject.Inject

class CharactersAdapter @Inject constructor() :
    RecyclerView.Adapter<CharactersAdapter.RatesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        val binding = CharacterItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size


    inner class RatesViewHolder(private val itemBinding: CharacterItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: Character) {
            itemBinding.apply {
                data = model
            }
        }
    }

    private val differCallBack  = object : DiffUtil.ItemCallback<Character, >() {

        override fun areItemsTheSame(oldItem: Character, newItem: Character, ): Boolean {
            return  oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Character, newItem: Character, ): Boolean {
            return  oldItem==newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)
}
