package com.example.photoeditorpolishanything

import LocaleHelper
import android.content.ContentUris
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.photoeditorpolishanything.Model.ImageItem
import com.example.photoeditorpolishanything.databinding.ActivityAdjustBinding

class Adjust_Activity : BaseActivity() {

    lateinit var binding : ActivityAdjustBinding
    private lateinit var originalBitmap: Bitmap
    private lateinit var filteredBitmap: Bitmap

    private lateinit var buttonsLayout: LinearLayout // Adjust to your layout type

    private var selectedButton: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdjustBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.black)

        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        initView()
    }

    private fun initView() {

        val imageUriString = intent.getStringExtra("selected_image_uri")
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            val imageView = findViewById<ImageView>(R.id.imgAdjustSelectImage)

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

        val selectedImageUriString = intent.getStringExtra("selected_image_uri")
        var selectedImageUri = Uri.parse(selectedImageUriString)

        // Load and display the selected image using Glide
        // Load the selected image into originalBitmap for editing
        originalBitmap =
            BitmapFactory.decodeStream(contentResolver.openInputStream(selectedImageUri))

        // Make a copy of the original bitmap to apply filters
        filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)

        // Load the selected image into originalBitmap for editing
        Glide.with(this)
            .asBitmap()
            .load(selectedImageUri)
            .apply(RequestOptions().override(800, 800)) // Adjust size as needed
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    originalBitmap = resource

                    // Make a copy of the original bitmap to apply filters
                    filteredBitmap = originalBitmap.copy(originalBitmap.config, true)

                    // Display the original image in ImageView
                    binding.imgAdjustSelectImage.setImageBitmap(originalBitmap)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectImages)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectBright)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectStory)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectNatural)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectWarm)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectBright)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectDew)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectGold)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectLomo)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectPink)

        // Display the original image in ImageView
        binding.imgAdjustSelectImage.setImageBitmap(originalBitmap)

        // Example: Apply brightness filter when "Bright Filter" button is clicked
        binding.imgAdjustSelectImages.setOnClickListener {
            resetToOriginalImage()
        }

        binding.imgAdjustSelectBright.setOnClickListener {
            applyBrightFilter()
        }

        binding.imgAdjustSelectStory.setOnClickListener {
            applyStoryFilter()
        }

        binding.imgAdjustSelectNatural.setOnClickListener {
            applyNaturalFilter()
        }

        binding.imgAdjustSelectWarm.setOnClickListener {
            applyWarmFilter()
        }

        binding.imgAdjustSelectDew.setOnClickListener {
            applyDewFilter(selectedImageUri)
        }

        binding.imgAdjustSelectGold.setOnClickListener {
            applyGoldFilter()
        }

        binding.imgAdjustSelectLomo.setOnClickListener {
            applyLomoFilter()
        }

        binding.imgAdjustSelectPink.setOnClickListener {
            applyPinkFilter()
        }

        binding.imgback.setOnClickListener {
            onBackPressed()
        }

        binding.lnrFilter.setOnClickListener { toggleButton(binding.lnrFilter) }
        binding.lnrAdjust.setOnClickListener { toggleButton(binding.lnrAdjust) }
    }

    override fun updateUIForSelectedLanguage() {
        super.updateUIForSelectedLanguage()
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        binding.txtMore.text = resources.getString(R.string.More)
        binding.txtOriginal.text = resources.getString(R.string.Original)
        binding.txtBright.text = resources.getString(R.string.Bright)
        binding.txtStory.text = resources.getString(R.string.Story)
        binding.txtNatural.text = resources.getString(R.string.Natural)
        binding.txtWarm.text = resources.getString(R.string.Warm)
        binding.txtDew.text = resources.getString(R.string.Dew)
        binding.txtGold.text = resources.getString(R.string.Gold)
        binding.txtLomo.text = resources.getString(R.string.Lomo)
        binding.txtPink.text = resources.getString(R.string.Pink)

        binding.txtFilter.text = resources.getString(R.string.Filter)
        binding.txtAdjust.text = resources.getString(R.string.Adjust)

        binding.txtBrightness.text = resources.getString(R.string.Brightness)
        binding.txtContrast.text = resources.getString(R.string.Contrast)
        binding.txtWarmth.text = resources.getString(R.string.Warmth)
        binding.txtSaturation.text = resources.getString(R.string.Saturation)
        binding.txtFade.text = resources.getString(R.string.Fade)
        binding.txtHighlight.text = resources.getString(R.string.Highlight)
        binding.txtShadow.text = resources.getString(R.string.Shadow)
        binding.txtTint.text = resources.getString(R.string.Tint)
        binding.txtHue.text = resources.getString(R.string.Hue)
        binding.txtGrain.text = resources.getString(R.string.Grain)
    }

    private fun toggleButton(button: LinearLayout?) {
        if (selectedButton != button) {
            // Deselect previously selected button
            selectedButton?.let {
                it.backgroundTintList = ContextCompat.getColorStateList(this, R.color.white)
                val prevTextView = it.findViewById<TextView>(R.id.txtFilter) ?: it.findViewById<TextView>(R.id.txtAdjust)
                prevTextView?.setTextColor(ContextCompat.getColor(this, R.color.black))
            }

            // Toggle the state of the button
            selectedButton = button
            selectedButton?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.blue)
            val newTextView = button?.findViewById<TextView>(R.id.txtFilter) ?: button?.findViewById<TextView>(R.id.txtAdjust)
            newTextView?.setTextColor(ContextCompat.getColor(this, R.color.white))

            // Handle actions based on selected button (if needed)
            when (selectedButton?.id) {
                R.id.lnrFilter -> {
                    binding.rtllayouts.visibility = View.VISIBLE

                    binding.lnrskbseek.visibility = View.GONE
                    binding.hsvlayout.visibility = View.GONE
                }
                R.id.lnrAdjust -> {
                    binding.lnrskbseek.visibility = View.VISIBLE
                    binding.hsvlayout.visibility = View.VISIBLE

                    binding.rtllayouts.visibility = View.GONE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // Select a specific button in onResume (example: lnrFilter)
//        val lnrFilter = findViewById<LinearLayout>(R.id.lnrFilter)
        toggleButton(binding.lnrFilter)
    }

    private fun logErrorAndFinish(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish() // Close the activity or handle it appropriately
    }


    private class FetchImagesTask(
        val context: EditActivity,
        val callback: (List<ImageItem>) -> Unit
    ) : AsyncTask<Void, Void, List<ImageItem>>() {
        override fun doInBackground(vararg params: Void?): List<ImageItem> {
            val images = mutableListOf<ImageItem>()
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.Images.Media._ID)
            val cursor = context.contentResolver.query(
                uri,
                projection,
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED + " DESC"
            )

            cursor?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val imageUri =
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    images.add(ImageItem(imageUri))
                }
            }
            return images
        }

        override fun onPostExecute(result: List<ImageItem>) {
            callback(result)
        }
    }

    private fun applyBrightFilter() {
        // Example: Increase brightness by 20%
        val brightness = 1.2f

        // Create a color matrix for brightness adjustment
        val colorMatrix = ColorMatrix().apply {
            setScale(brightness, brightness, brightness, 1f)
        }

        // Create a color filter with the color matrix
        val colorFilter = ColorMatrixColorFilter(colorMatrix)

        // Apply the color filter to the filtered bitmap
        val canvas = android.graphics.Canvas(filteredBitmap)
        val paint = android.graphics.Paint()
        paint.colorFilter = colorFilter
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Update the ImageView with the filtered bitmap
        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyStoryFilter() {
        // Apply contrast filter
        val contrast = 1.5f
        val contrastMatrix = ColorMatrix().apply {
            setSaturation(contrast)
        }

        val colorFilter = ColorMatrixColorFilter(contrastMatrix)
        val canvas = android.graphics.Canvas(filteredBitmap)
        val paint = android.graphics.Paint()
        paint.colorFilter = colorFilter
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyNaturalFilter() {
        val colorMatrix = ColorMatrix().apply {
            // Increase saturation slightly
            setSaturation(1.2f)
            // Apply brightness and contrast adjustments
            val contrast = 1.1f
            val brightness = 1.05f
            val scale = floatArrayOf(
                contrast, 0f, 0f, 0f, brightness,
                0f, contrast, 0f, 0f, brightness,
                0f, 0f, contrast, 0f, brightness,
                0f, 0f, 0f, 1f, 0f
            )
            postConcat(ColorMatrix(scale))
        }

        val colorFilter = ColorMatrixColorFilter(colorMatrix)
        val canvas = android.graphics.Canvas(filteredBitmap)
        val paint = android.graphics.Paint()
        paint.colorFilter = colorFilter
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyWarmFilter() {
        val colorMatrix = ColorMatrix().apply {
            // Warm filter: Increase red and yellow tones
            set(
                floatArrayOf(
                    1.2f, 0.0f, 0.0f, 0.0f, 0.0f,
                    0.0f, 1.1f, 0.0f, 0.0f, 0.0f,
                    0.0f, 0.0f, 0.9f, 0.0f, 0.0f,
                    0.0f, 0.0f, 0.0f, 1.0f, 0.0f
                )
            )
        }

        val colorFilter = ColorMatrixColorFilter(colorMatrix)
        val canvas = android.graphics.Canvas(filteredBitmap)
        val paint = android.graphics.Paint()
        paint.colorFilter = colorFilter
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyDewFilter(imageUri: Uri) {
        val filteredBitmap =
            Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)

        // Apply color adjustments using ColorMatrix
        val colorMatrix = ColorMatrix().apply {
            // Increase saturation slightly
            setSaturation(1.2f)
            // Apply brightness and contrast adjustments
            val contrast = 1.1f
            val brightness = 1.05f
            val scale = floatArrayOf(
                contrast, 0f, 0f, 0f, brightness,
                0f, contrast, 0f, 0f, brightness,
                0f, 0f, contrast, 0f, brightness,
                0f, 0f, 0f, 1f, 0f
            )
            postConcat(ColorMatrix(scale))
        }

        // Create a Paint object with the color filter
        val paint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(colorMatrix)
        }

        // Draw the original bitmap onto the filteredBitmap using Canvas
        val canvas = Canvas(filteredBitmap)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Display the filtered image on ImageView
        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyGoldFilter() {
        // Create a copy of the original bitmap
        val filteredBitmap =
            Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)

        // Apply color adjustments using ColorMatrix for a less intense gold tone
        val colorMatrix = ColorMatrix().apply {
            // Adjust the red and green channels to create a toned-down golden hue
            set(
                floatArrayOf(
                    1.5f, 0f, 0f, 0f, 0f,
                    0f, 1.2f, 0f, 0f, 0f,
                    0f, 0f, 0.8f, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        }

        // Create a Paint object with the color filter
        val paint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(colorMatrix)
        }

        // Draw the original bitmap onto the filteredBitmap using Canvas
        val canvas = Canvas(filteredBitmap)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Display the filtered image on ImageView
        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyLomoFilter() {
        // Create a copy of the original bitmap
        val filteredBitmap =
            Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)

        // Apply color adjustments using ColorMatrix for a less intense Lomo effect
        val colorMatrix = ColorMatrix().apply {
            // Adjust contrast and brightness to achieve a subtle Lomo effect
            set(
                floatArrayOf(
                    1.1f, 0f, 0f, 0f, 0f,
                    0f, 1.1f, 0f, 0f, 0f,
                    0f, 0f, 1.1f, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        }

        // Create a Paint object with the color filter
        val paint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(colorMatrix)
        }

        // Draw the original bitmap onto the filteredBitmap using Canvas
        val canvas = Canvas(filteredBitmap)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Display the filtered image on ImageView
        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyPinkFilter() {
        // Create a copy of the original bitmap
        val filteredBitmap =
            Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)

        // Apply color adjustments using ColorMatrix for a less intense pink filter effect
        val colorMatrix = ColorMatrix().apply {
            // Increase red and blue channels slightly for a subtle pink tint
            set(
                floatArrayOf(
                    1.1f, 0f, 0f, 0f, 0f,
                    0f, 0.9f, 0f, 0f, 0f,
                    0f, 0f, 1.1f, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        }

        // Create a Paint object with the color filter
        val paint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(colorMatrix)
        }

        // Draw the original bitmap onto the filteredBitmap using Canvas
        val canvas = Canvas(filteredBitmap)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Display the filtered image on ImageView
        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    fun onResetButtonClick(view: View) {
        resetToOriginalImage()
    }

    private fun resetToOriginalImage() {
        // Ensure originalBitmap is initialized and not null
        originalBitmap?.let {
            binding.imgAdjustSelectImage.setImageBitmap(it)
        }
    }

    fun onButtonClick(view: View) {}
}