package com.example.ridecellexample.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ridecellexample.data.model.CharacterData
import com.example.ridecellexample.databinding.LayoutCharacterItemBinding

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutCharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            itemClickListener,
            ::getItem
        )
    }

    private var characterList: List<CharacterData> = listOf()
    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(view: View, characterData: CharacterData)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun submitList(list: List<CharacterData>) {
        val diffUtil =
            DiffUtilCallBack(characterList, list)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        characterList = list
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun getItem(position: Int): CharacterData {
        return characterList[position]
    }

    class MyViewHolder(
        private val binding: LayoutCharacterItemBinding,
        itemClickListener: OnItemClickListener,
        getItem: (Int) -> CharacterData
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.root.setOnClickListener {
                itemClickListener.onItemClick(it, getItem(adapterPosition))
            }
        }

        fun bind(characterData: CharacterData) {


            binding.characterName.text = characterData.name
            binding.characterSpecies.text = characterData.species

            binding.characterImage.load(characterData.image)
        }
    }

    class DiffUtilCallBack(
        private val oldList: List<CharacterData>,
        private val newList: List<CharacterData>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
                    && oldList[oldItemPosition].name == newList[newItemPosition].name
                    && oldList[oldItemPosition].status == newList[newItemPosition].status
        }

    }
}
