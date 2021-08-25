package com.udacity.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ListItemAsteroidBinding

class AsteroidAdapter(val clickListener: AsteroidListener, val iconDiscriminator: HazardousIconDiscriminator): ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(AsteroidDiffCallback()) { // ListAdapter is used to check if any item in the data that's changed (advanced performance optimization)
    // For creating new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    // Tell recyclerview how to actually draw an item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!, clickListener, iconDiscriminator)
    }


    class ViewHolder private constructor(val binding: ListItemAsteroidBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Asteroid, clickListener: AsteroidListener, iconDiscriminator: HazardousIconDiscriminator) {
            binding.asteroid = item
            binding.clickListener = clickListener
            binding.iconDiscriminator = iconDiscriminator
            binding.executePendingBindings() // Makes it faster to size the views (advanced perf optimization)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflator = LayoutInflater.from(parent.context)
                val binding = ListItemAsteroidBinding.inflate(layoutInflator, parent, false)

                return ViewHolder(binding)
            }
        }
    }


}


class AsteroidDiffCallback: DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }

}


class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}


class HazardousIconDiscriminator(val iconDiscriminator: (asteroid: Asteroid) -> String) {
    fun getIconDescription(asteroid: Asteroid) = iconDiscriminator(asteroid)
}