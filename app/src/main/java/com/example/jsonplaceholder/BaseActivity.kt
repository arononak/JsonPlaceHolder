package com.example.jsonplaceholder

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_base_template.*


open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_template)
        showProgress(true)
    }

    fun showProgress(isShow: Boolean) {
        progressBar.visibility = if (!isShow) View.GONE else View.VISIBLE
    }
}
