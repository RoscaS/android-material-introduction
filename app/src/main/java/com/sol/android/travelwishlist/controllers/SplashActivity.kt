package com.sol.android.travelwishlist.controllers

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sol.android.travelwishlist.R
import kotlin.concurrent.thread


class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    thread {

      Thread.sleep((3 * 1000).toLong())
      startMainActivity()


    }.priority = Thread.NORM_PRIORITY
  }

  private fun startMainActivity() {
    val intent = Intent(applicationContext, MainActivity::class.java)
    startActivity(intent)
    finish()
  }
}