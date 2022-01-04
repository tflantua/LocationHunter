package com.bijgepast.locationhunter.utils

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.bijgepast.locationhunter.R
import com.bijgepast.locationhunter.adapters.SearchAdapter
import com.bijgepast.locationhunter.databinding.PopupSearchBinding
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.search.SearchException
import com.tomtom.online.sdk.search.fuzzy.FuzzyOutcome
import com.tomtom.online.sdk.search.fuzzy.FuzzyOutcomeCallback
import timber.log.Timber

class PopUpClass(inflater: LayoutInflater, private val tomtomManager: TomTomManager) :
    FuzzyOutcomeCallback, SearchAdapter.OnItemClick {
    private var popupWindow: PopupWindow?
    private val binding: PopupSearchBinding
    private val context: Context

    fun dismiss() {
        println("am i here?")
        if (popupWindow != null) {
            popupWindow!!.dismiss()
            popupWindow = null
        }
    }

    fun search() {
        val searchText = binding.searchField.text.toString()

        tomtomManager.search(searchText, this)

        val inputManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(popupWindow?.contentView?.windowToken, 0)
    }

    fun dummy() {

    }

    init {
// inflate the layout of the popup window
        val popupView: View = inflater.inflate(R.layout.popup_search, null)
        binding = PopupSearchBinding.bind(popupView)
        binding.tomtom = tomtomManager
        binding.popup = this
        context = inflater.context

        binding.searchRecyclerView.adapter = SearchAdapter(this)
        // create the popup window
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT
        val focusable = true // lets taps outside the popup also dismiss it
        popupWindow = PopupWindow(popupView, width, height, focusable)
        // show the popup window
// which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow!!.showAtLocation(View(inflater.context), Gravity.CENTER, 0, 0)
        // dismiss the popup window when touched
        popupView.setOnTouchListener { v: View, event: MotionEvent? ->
            v.performClick()
            true
        }
    }

    override fun onError(error: SearchException) {
        Timber.e(error.message.toString())
    }

    override fun onSuccess(fuzzyOutcome: FuzzyOutcome) {
        (binding.searchRecyclerView.adapter as SearchAdapter).setAddresses(fuzzyOutcome.fuzzyDetailsList)
    }

    override fun onClick(location: LatLng) {
        tomtomManager.planRoute(location)
        dismiss()
    }
}