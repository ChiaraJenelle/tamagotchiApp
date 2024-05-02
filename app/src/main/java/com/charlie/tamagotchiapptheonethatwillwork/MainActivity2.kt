package com.charlie.tamagotchiapptheonethatwillwork

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.max
import kotlin.math.min

@Suppress("KotlinConstantConditions")
class MainActivity2 : AppCompatActivity() {

    private lateinit var petImageView: ImageView
    private lateinit var hungerText: TextView
    private lateinit var healthText: TextView
    private lateinit var cleanlinessText: TextView
    private lateinit var feedButton: Button
    private lateinit var cleanButton: Button
    private lateinit var playButton: Button

    private val initialHealth = 50
    private val initialHunger = 50
    private val initialCleanliness = 50
    private var health = initialHealth
    private var hunger = initialHunger
    private var cleanliness = initialCleanliness

    private val tag = "MainActivity2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        petImageView = findViewById(R.id.petImageView)
        healthText = findViewById(R.id.healthText)
        hungerText = findViewById(R.id.hungerText)
        cleanlinessText = findViewById(R.id.cleanlinessText)
        feedButton = findViewById(R.id.feedButton)
        cleanButton = findViewById(R.id.cleanButton)
        playButton = findViewById(R.id.playButton)

        updateStatus()

        feedButton.setOnClickListener {
            hunger = max(0, hunger - 10)
            updateStatus()
            Log.d(tag, "Fed the pet. Hunger: $hunger")
            Toast.makeText(this, "Fed the pet", LENGTH_SHORT).show()
        }

        cleanButton.setOnClickListener {
            cleanliness = min(100, cleanliness + 10)
            updateStatus()
            Log.d(tag, "Cleaned the pet. Cleanliness: $cleanliness")
            Toast.makeText(this, "Cleaned the pet", LENGTH_SHORT).show()
        }

        playButton.setOnClickListener {
            val healthChange = calculateHealthChange()

            health = if (hunger == 100 || cleanliness == 0) 0 else max(0, min(100, health + healthChange))

            hunger = min(100, hunger + 15)

            cleanliness = max(0, cleanliness - 5)

            updateStatus()
            Log.d(tag, "Played with the pet. Health change: $healthChange, New Health: $health")
            Toast.makeText(this, "Played with the pet", LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateStatus() {
        healthText.text = "Health: $health"
        hungerText.text = "Hunger: $hunger"
        cleanlinessText.text = "Cleanliness: $cleanliness"

        if (health <= 0) {
            petImageView.setImageResource(R.drawable.img_3)
        } else if (hunger == 100 || cleanliness == 0) {
            petImageView.setImageResource(R.drawable.img_2)
        } else {
            petImageView.setImageResource(R.drawable.img_1)
        }
    }

    private fun calculateHealthChange(): Int {
        val healthBonus = when {
            cleanliness >= 80 && hunger <= 50 -> 5
            cleanliness >= 50 && (hunger <= 70 || hunger >= 30) -> 3
            else -> 0
        }
        return healthBonus
    }
}
