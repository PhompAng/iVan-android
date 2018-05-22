package com.firebaseapp.ivan.ivan.ui

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.jetbrains.anko.startActivity

/**
 * @author phompang on 15/2/2018 AD.
 */
class SplashScreenActivity : LocalizationActivity() {

	private lateinit var handler: Handler
	private lateinit var runnable: Runnable
	private var delayTime = 0L
	private var time = 2000L

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash_screen)
		animateSplash()

		handler = Handler()
		runnable = Runnable {
			startActivity<LoginActivity>()
			finish()
		}
	}

	private fun animateSplash() {
		splashImageView.alpha = 0F
		titleTextView.alpha = 0F
		subTitleTextView.alpha = 0F
		splashImageView.animate().setStartDelay(200).alpha(1F).setDuration(500).start()
		titleTextView.animate().setStartDelay(200).alpha(1F).setDuration(500).start()
		subTitleTextView.animate().setStartDelay(200).alpha(1F).setDuration(500).start()
	}

	override fun onResume() {
		super.onResume()
		delayTime = time
		handler.postDelayed(runnable, delayTime)
		time = System.currentTimeMillis()
	}

	override fun onPause() {
		super.onPause()
		handler.removeCallbacks(runnable)
		time = delayTime - (System.currentTimeMillis() - time)
	}
}