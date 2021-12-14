package com.bijgepast.locationhunter.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bijgepast.locationhunter.BR
import com.bijgepast.locationhunter.R
import com.bijgepast.locationhunter.models.RiddleModel

class RiddleAdapter(
    private val listener: OnItemClick
) : RecyclerView.Adapter<RiddleAdapter.RiddleViewHolder>() {
    companion object {
        private val LOGTAG: String = RiddleAdapter::class.java.name
    }

    interface OnItemClick {
        fun onClick(riddle: RiddleModel)
    }

    private var riddles: List<RiddleModel>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setRouteModels(routeModels: List<RiddleModel>) {
        riddles = routeModels
        notifyDataSetChanged()
    }

    class RiddleViewHolder(
        private val binding: ViewDataBinding,
        private val listener: OnItemClick,
        private var riddle: RiddleModel
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            this.itemView.setOnClickListener(this)
        }

        fun bind(riddle: RiddleModel) {
            this.riddle = riddle
            this.binding.setVariable(BR.riddleModel, this.riddle)
        }


        override fun onClick(v: View?) {
            this.listener.onClick(this.riddle)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiddleViewHolder {
        Log.d(LOGTAG, "onCreateViewHolder() called")
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            R.layout.riddle_list_item,
            parent,
            false
        )
        return RiddleViewHolder(binding, listener, this.riddles!![viewType])
    }

    override fun onBindViewHolder(holder: RiddleViewHolder, position: Int) {
        Log.d(LOGTAG, "Binding view")

        holder.bind(this.riddles!![position])
    }

    override fun getItemCount(): Int {
        if (this.riddles == null)
            return 0
        return this.riddles!!.size
    }

}