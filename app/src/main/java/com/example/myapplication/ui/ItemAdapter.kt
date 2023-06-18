package com.example.myapplication.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemScanBinding
import com.example.myapplication.model.ScanModel

class ItemAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mList: ArrayList<ScanModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemScanBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolderItem(binding)
    }

    fun setData(list: List<ScanModel>) {
        mList.clear()
        mList.addAll(list)

        //notifyItemInserted(list.size)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderItem).bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolderItem(private val itemBinding: ItemScanBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(scan: ScanModel) {
            itemBinding.tvText.text = scan.value
        }
    }
}