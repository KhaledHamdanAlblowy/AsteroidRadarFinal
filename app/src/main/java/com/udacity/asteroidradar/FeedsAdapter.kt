package com.udacity.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.AsterHolderLayoutBinding
import com.udacity.asteroidradar.main.MainFragmentDirections
import kotlinx.android.synthetic.main.aster_holder_layout.view.*

class FeedsAdapter(
	private val data: List<Asteroid>
) : RecyclerView.Adapter<FeedsAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflator = LayoutInflater.from(parent.context)
		val listItemBinding = AsterHolderLayoutBinding.inflate(inflator, parent, false)
		return ViewHolder(listItemBinding)
		
	}
	
	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(data[position])
		holder.itemView.root_layout.setOnClickListener {
			it.findNavController().navigate(
				MainFragmentDirections.actionShowDetail(data[position])
			)
		}
	}
	
	override fun getItemCount(): Int {
		return data.size
	}
	
	inner class ViewHolder(val binding: AsterHolderLayoutBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(item: Asteroid) {
			binding.asteriod = item
		}
		
	}
	
}