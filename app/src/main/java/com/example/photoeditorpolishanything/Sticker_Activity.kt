package com.example.photoeditorpolishanything

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.emoji2.bundled.BundledEmojiCompatConfig
import androidx.emoji2.text.EmojiCompat
import com.bumptech.glide.Glide
import com.example.photoeditorpolishanything.Adapter.StickerAdapter
import com.example.photoeditorpolishanything.databinding.ActivityStickerBinding


class Sticker_Activity : AppCompatActivity() {

    lateinit var binding: ActivityStickerBinding
//    private lateinit var viewModel: StickerViewModel
    private lateinit var adapter: StickerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.black)

        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)


        initializeEmojiCompat()
        initView()
//        loadData()
    }

    private fun initializeEmojiCompat() {
        val config = BundledEmojiCompatConfig(this)
        EmojiCompat.init(config)
    }

    private fun initView()
    {
//        binding.rcvStiker.layoutManager = LinearLayoutManager(this)
//        adapter = StickerAdapter(emptyList()) // Initialize with empty list
//        binding.rcvStiker.adapter = adapter

        val imageUriString = intent.getStringExtra("selected_image_uri")
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            val imageView = findViewById<ImageView>(R.id.imgEditSelectImagess)

            if (imageView != null) {
                try {
                    Glide.with(this)
                        .load(imageUri)
                        .into(imageView)
                } catch (e: Exception) {
                    logErrorAndFinish("Glide error: ${e.message}")
                }
            } else {
                logErrorAndFinish("ImageView not found in layout")
            }
        } else {
            logErrorAndFinish("Image URI string is null")
        }
    }

    private fun logErrorAndFinish(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish() // Close the activity or handle it appropriately
    }

//    private fun loadData() {
//        // Simulate loading data (replace with your actual data loading logic)
//        val emojiList = listOf(
//            "https://emojicdn.elk.sh/🙂",
//            "https://emojicdn.elk.sh/😀",
//            "https://emojicdn.elk.sh/😄",
//            "https://emojicdn.elk.sh/😊",
//            "https://emojicdn.elk.sh/😍",
//            "https://emojicdn.elk.sh/😘",
//            "https://emojicdn.elk.sh/😎"
//        )
//
//        // Update adapter with loaded data
//        adapter.updateData(emojiList)
//    }
}