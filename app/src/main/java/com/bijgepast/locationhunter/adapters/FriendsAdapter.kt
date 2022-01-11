package com.bijgepast.locationhunter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BaseObservable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bijgepast.locationhunter.BR
import com.bijgepast.locationhunter.adapters.FriendsAdapter.FriendsViewHolder
import com.bijgepast.locationhunter.models.FriendModel

class FriendsAdapter(private val layoutId: Int) :
    RecyclerView.Adapter<FriendsViewHolder>() {

    private var friends: MutableList<FriendModel> = ArrayList<FriendModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setFriendModels(friends: List<FriendModel>?) {
        if (friends != null) {
            this.friends.clear()
            this.friends.addAll(friends)
            notifyDataSetChanged()
        }
    }

    class FriendsViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(friend: FriendModel) {
            this.binding.setVariable(BR.friend, friend)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            layoutId,
            parent,
            false
        )
        return FriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        holder.bind(this.friends[position])
    }

    override fun getItemCount(): Int {
        return this.friends.size
    }
}