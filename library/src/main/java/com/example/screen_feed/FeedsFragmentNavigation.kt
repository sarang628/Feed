package com.example.screen_feed

import android.content.Context
import android.widget.Toast
import com.sryang.torang_core.navigation.LoginNavigation
import javax.inject.Inject

class FeedsFragmentNavigation @Inject constructor(
    val loginNavigation: LoginNavigation
) : FeedRvAdtNavigation {
    override fun goProfile(context: Context) {
        toast(context, "goProfile")
    }

    override fun showMenu(context: Context) {
        toast(context, "showMenu")
    }

    override fun showProfile(context: Context) {
        toast(context, "showProfile")
    }

    override fun moveRestaurant(context: Context) {
        toast(context, "moveRestaurant")
    }

    override fun moveComment(context: Context) {
        toast(context, "moveComment")
    }

    override fun showShare(context: Context) {
        toast(context, "showShare")
    }

    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun goWriteReview(context : Context){
        Toast.makeText(context, "준비중..", Toast.LENGTH_SHORT).show()
    }

    fun goLogin(context: Context){
        loginNavigation.goLogin(context)
    }
}

interface FeedRvAdtNavigation {
    fun goProfile(context: Context)
    fun showMenu(context: Context)
    fun showProfile(requireContext: Context)
    fun moveRestaurant(requireContext: Context)
    fun moveComment(requireContext: Context)
    fun showShare(requireContext: Context)
}