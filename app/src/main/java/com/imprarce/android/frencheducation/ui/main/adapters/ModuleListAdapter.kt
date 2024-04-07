package com.imprarce.android.frencheducation.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.data.db.module.ModuleListItem
import dagger.Module

class ModuleListAdapter(private val onItemClick: (Module) -> Unit) :
    RecyclerView.Adapter<ModuleListAdapter.ModuleViewHolder>() {

    private var moduleListItems = listOf<ModuleListItem>()

    fun setModuleListItems(items: List<ModuleListItem>) {
        moduleListItems = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_home_recycler_view_main, parent, false)
        return ModuleViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        val currentModule = moduleListItems[position]
        holder.titleModule.text = currentModule.module.moduleName
        holder.levelModule.text = currentModule.module.moduleLevel.toString()
        holder.progressModule.progress = currentModule.progress

        if (currentModule.module.imageUrl != null) {
            holder.imageModule.setImageResource(R.drawable.image_plug)
        } else {
            holder.imageModule.setImageResource(R.drawable.image_plug)
        }
    }

    override fun getItemCount(): Int = moduleListItems.size


    inner class ModuleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageModule: ImageView = itemView.findViewById(R.id.module_image)
        val titleModule: TextView = itemView.findViewById(R.id.module_title)
        val levelModule: TextView = itemView.findViewById(R.id.module_level)
        val progressModule: ProgressBar = itemView.findViewById(R.id.module_progressBar)
    }
}
