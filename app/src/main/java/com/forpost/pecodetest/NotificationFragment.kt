package com.forpost.pecodetest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.forpost.pecodetest.di.RequireScreenController
import com.forpost.pecodetest.di.ScreenController
import kotlinx.android.synthetic.main.fragmetn_notification.*

class NotificationFragment() : Fragment(), RequireScreenController {

    constructor(number: Int) : this() {
        arguments = Bundle().also { it.putInt(ARG_NUMBER, number) }
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
        plusImage.setOnClickListener { controller?.add() }
        minusImage.setOnClickListener { controller?.remove() }
        createNotification.setOnClickListener { }
    }

    companion object Constants {
        private const val ARG_NUMBER = "number"
    }
}