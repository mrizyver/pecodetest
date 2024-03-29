package com.izyver.pecodetest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.izyver.pecodetest.di.main.RequireNotificationCreator
import com.izyver.pecodetest.di.main.RequireScreenController
import com.izyver.pecodetest.notifications.NotificationCreator
import kotlinx.android.synthetic.main.fragmetn_notification.*

class NotificationFragment() : Fragment(),
    RequireScreenController, RequireNotificationCreator {

    constructor(number: Int) : this() {
        val args = Bundle()
        args.putInt(ARG_NUMBER, number)
        args.putString(ARG_FRAGMENT_NAME, makeFragmentName(number))
        arguments = args
    }

    private var mController: ScreenController? = null
    private var mNotificationCreator: NotificationCreator? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmetn_notification, container, false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser){
            updateMinusVisibility(getNumber())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val number = getNumber()
        initButtons(number)
        counter.text = number.toString()
        createNotification.setOnClickListener { mNotificationCreator?.create(number) }
    }

    override fun setScreenController(screenController: ScreenController) {
        mController = screenController
    }

    override fun setNotificationCreator(creator: NotificationCreator) {
        mNotificationCreator = creator
    }

    private fun getNumber() = arguments?.getInt(ARG_NUMBER) ?: 1

    private fun initButtons(number: Int) {
        updateMinusVisibility(number)
        minusImage?.setOnClickListener {
            mController?.remove()
            updateMinusVisibility(number)
        }

        plusImage?.setOnClickListener { mController?.add() }
    }

    private fun updateMinusVisibility(number: Int) {
        if (number > 1) return
        val count = mController?.count ?: 1
        val isMinusVisible = count > 1
        val visibility = if (isMinusVisible) VISIBLE else GONE
        if (visibility == minusImage?.visibility) return
        minusImage?.visibility = visibility
    }

    companion object Constants {
        private const val ARG_NUMBER = "number"
        private const val TAG = "NotificationFragment"
        const val ARG_FRAGMENT_NAME = "notification_fragment_name"
        fun makeFragmentName(number: Int) = "$TAG:$number"
    }
}