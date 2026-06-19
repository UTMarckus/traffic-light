package com.utmarckus.trafficlight

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var blockRed: LinearLayout
    lateinit var blockYellow: LinearLayout
    lateinit var blockGreen: LinearLayout
    lateinit var button: Button

    var isRun = false
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        button = findViewById(R.id.button)
        blockRed = findViewById(R.id.block_red)
        blockYellow = findViewById(R.id.block_yellow)
        blockGreen = findViewById(R.id.block_green)

        button.setOnClickListener { onClickStart() }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRun = false
    }

    fun onClickStart() {
        if (!isRun) {
            isRun = true
            button.text = "Stop"

            Thread {
                run() {
                    while (isRun) {
                        resetBlocksColor()

                        when (position) {
                            0 -> blockRed.setBackgroundColor(getColor(R.color.red))
                            1 -> blockYellow.setBackgroundColor(getColor(R.color.yellow))
                            2 -> blockGreen.setBackgroundColor(getColor(R.color.green))
                        }
                        position = (position + 1) % 3
                        Thread.sleep(1500)
                    }
                }
            }.start()
        } else {
            isRun = false
            button.text = "Start"
        }
    }

    @SuppressLint("ResourceAsColor")
    fun resetBlocksColor() {
        blockRed.setBackgroundColor(resources.getColor(R.color.gray))
        blockYellow.setBackgroundColor(getColor(R.color.gray))
        blockGreen.setBackgroundColor(getColor(R.color.gray))
    }
}