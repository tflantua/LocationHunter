package com.bijgepast.locationhunter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

import com.bijgepast.locationhunter.BR
import com.bijgepast.locationhunter.R
import com.bijgepast.locationhunter.models.HintModel

class HintsAdapter(private val hints: List<HintModel>) :
    RecyclerView.Adapter<HintsAdapter.HintsViewHolder>() {

    class HintsViewHolder(
        private val binding: ViewDataBinding,
        private var hint: HintModel
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            this.itemView.setOnClickListener(this)
        }

        fun bind(hint: HintModel) {
            this.hint = hint
            this.binding.setVariable(BR.hintModel, this.hint)
        }

        override fun onClick(v: View?) {
            this.hint.setShow(!this.hint.getShow())
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HintsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            R.layout.hint_list_item,
            parent,
            false
        )
        return HintsViewHolder(binding, this.hints[viewType])
    }

    override fun onBindViewHolder(holder: HintsViewHolder, position: Int) {
        holder.bind(this.hints[position])
    }

    override fun getItemCount(): Int {
        return this.hints.size
    }

}