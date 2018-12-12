package com.kadmiv.game.fragments;

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kadmiv.game.R
import kotlinx.android.synthetic.main.fragment_web.*
import android.view.KeyEvent.KEYCODE_BACK


class WebFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    override fun onStart() {
        super.onStart()
        web_view.settings.javaScriptEnabled = true
        web_view.loadUrl(getString(R.string.html5test))
    }

    private fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && web_view.canGoBack()) {
            web_view.goBack()
            return true
        }

        return false
    }

}