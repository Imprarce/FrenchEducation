package com.imprarce.android.feature_home.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imprarce.android.feature_home.R
import com.imprarce.android.feature_home.ui.detailhome.interfaces.OnModuleClickListener
import com.imprarce.android.local.module.ModuleListItem

class ModuleListAdapter(private val moduleList: List<ModuleListItem>, private val listener: OnModuleClickListener) : RecyclerView.Adapter<ModuleListAdapter.ModuleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_home_recycler_view_main, parent, false)
        return ModuleViewHolder(inflater)
    }


    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        val currentItem = moduleList[position]
        holder.bind(currentItem, listener)
    }

    override fun getItemCount(): Int = moduleList.size

    @SuppressLint("SetTextI18n")
    inner class ModuleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val imageModule: ImageView = itemView.findViewById(R.id.module_image)
        private val titleModule: TextView = itemView.findViewById(R.id.module_title)
        private val levelModule: TextView = itemView.findViewById(R.id.module_level)
        private val progressModule: TextView = itemView.findViewById(R.id.module_progress_text)

        fun bind(moduleListItem: ModuleListItem, listener: OnModuleClickListener) {
            titleModule.text = moduleListItem.module.moduleName
            levelModule.text = "A" + moduleListItem.module.moduleLevel.toString()
            progressModule.text = moduleListItem.progress.toString() + "%"

            if (moduleListItem.module.imageUrl != null) {
                imageModule.setImageResource(R.drawable.image_plug)
            } else {
                imageModule.setImageResource(R.drawable.image_plug)
            }

            itemView.setOnClickListener {
                listener.onModuleClick(moduleListItem)
            }
        }
    }

}
