package com.forpost.pecodetest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.forpost.pecodetest.di.RequireScreenController
import com.forpost.pecodetest.di.ScreenController
import kotlinx.android.synthetic.main.fragmetn_notification.*

class NotificationFragment() : Fragment(), RequireScreenController {

    constructor(number: Int) : this() {
        val args = Bundle()
        args.putInt(ARG_NUMBER, number)
        args.putString(ARG_FRAGMENT_NAME, makeFragmentName(number))
        arguments = args
    }

    var controller: ScreenController? = null

    override fun setScreenController(screenController: ScreenController) {
        controller = screenController
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmetn_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val number = arguments?.getInt(ARG_NUMBER) ?: 1
        initButtons(number)
        counter.text = number.toString()
        createNotification.setOnClickListener { }
    }

    private fun initButtons(number: Int) {
        if (number > 1) minusImage.setOnClickListener { controller?.remove() }
        else minusImage.visibility = GONE

        plusImage.setOnClickListener { controller?.add() }
    }

    companion object Constants {
        private const val ARG_NUMBER = "number"
        private const val TAG = "NotificationFragment"
        const val ARG_FRAGMENT_NAME = "notification_fragment_name"
        fun makeFragmentName(number: Int) = "$TAG:$number"
    }
}