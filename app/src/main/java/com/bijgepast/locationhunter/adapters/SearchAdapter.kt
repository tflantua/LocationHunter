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
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchDetails

class SearchAdapter(
    private val listener: OnItemClick
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    companion object {
        private val LOGTAG: String = SearchAdapter::class.java.name
    }

    interface OnItemClick {
        fun onClick(location: LatLng)
    }

    private var addresses: List<FuzzySearchDetails>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setAddresses(addresses: List<FuzzySearchDetails>) {
        this.addresses = addresses
        notifyDataSetChanged()
    }

    class SearchViewHolder(
        private val binding: ViewDataBinding,
        private val listener: OnItemClick,
        private var address: FuzzySearchDetails
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            this.itemView.setOnClickListener(this)
        }

        fun bind(address: FuzzySearchDetails) {
            this.address = address
            this.binding.setVariable(BR.address, this.address)
        }

        override fun onClick(v: View?) {
            this.listener.onClick(address.position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        Log.d(LOGTAG, "onCreateViewHolder() called")
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            R.layout.search_list_item,
            parent,
            false
        )
        return SearchViewHolder(binding, listener, this.addresses!![viewType])
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        Log.d(LOGTAG, "Binding view")

        holder.bind(this.addresses!![position])
    }

    override fun getItemCount(): Int {
        if (this.addresses == null)
            return 0
        return this.addresses!!.size
    }
}