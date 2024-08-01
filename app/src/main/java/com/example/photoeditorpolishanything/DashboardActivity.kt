package com.example.photoeditorpolishanything

import ImageAdapter
import LocaleHelper
import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photoeditorpolishanything.Model.ImageItem
import com.example.photoeditorpolishanything.Model.ImageViewModel
import com.example.photoeditorpolishanything.databinding.ActivityDashboardBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var imageUris: MutableList<Uri>
    private lateinit var viewModel: ImageViewModel

    private val REQUEST_CODE_PERMISSIONS = 101
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    private val REQUEST_CODE_GALLERY = 102
    private val REQUEST_CODE_CAMERA = 103
    private lateinit var currentPhotoPath: String

    private var bottomSheetDialog: BottomSheetDialog? = null


    private var selectedImageView: ImageView? = null
    private var selectedTextView: TextView? = null

    companion object{
        var context : Context? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.black)

        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        // Check and request all permissions on activity start
        checkPermissions()
//        allPermissionsGranted()
//        checkCameraPermissions(REQUEST_CODE_CAMERA)

        initView()
    }


    override fun updateUIForSelectedLanguage()
    {
        super.updateUIForSelectedLanguage()
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        binding.txtAppName.text = resources.getString(R.string.app_name)
        binding.txtExplore.text = resources.getString(R.string.Explore)
        binding.txtExplores.text = resources.getString(R.string.Explore)
        binding.txtGridArt.text = resources.getString(R.string.GridArt)
        binding.txtPro.text = resources.getString(R.string.Pro)
        binding.txtCollage.text = resources.getString(R.string.Collage)
        binding.txtTemplates.text = resources.getString(R.string.Template)
        binding.txtBeautify.text = resources.getString(R.string.Beautify)
        binding.txtCamera.text = resources.getString(R.string.Camera)

        val bottomSheetView = bottomSheetDialog?.findViewById<View>(R.id.btnCamera)
        bottomSheetView?.findViewById<TextView>(R.id.txtThisappcanonlyaccessPhotoSelected)?.text =
            resources.getString(R.string.ThisappcanonlyaccessPhotoSelected)
        bottomSheetView?.findViewById<TextView>(R.id.txtPhotos)?.text =
            resources.getString(R.string.Photos)
        bottomSheetView?.findViewById<TextView>(R.id.txtAlbums)?.text =
            resources.getString(R.string.Albums)
        bottomSheetView?.findViewById<TextView>(R.id.txtRecent)?.text =
            resources.getString(R.string.Recent)
    }

    private fun updateBottomSheetTextViews(view: View) {
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        view.findViewById<TextView>(R.id.txtThisappcanonlyaccessPhotoSelected).text =
            resources.getString(R.string.ThisappcanonlyaccessPhotoSelected)
        view.findViewById<TextView>(R.id.txtPhotos).text =
            resources.getString(R.string.Photos)
        view.findViewById<TextView>(R.id.txtAlbums).text =
            resources.getString(R.string.Albums)
        view.findViewById<TextView>(R.id.txtRecent).text =
            resources.getString(R.string.Recent)
    }

    private fun initView() {
        imageUris = mutableListOf()

        viewModel = ViewModelProvider(this)[ImageViewModel::class.java]

        binding.lnrCollage.setOnClickListener {toggleButton(binding.imgCollage, binding.txtCollage)}

        binding.lnrgallery.setOnClickListener {
            checkStoragePermission()
        }

        binding.imgStore.setOnClickListener {
            var i = Intent(this@DashboardActivity,StoreActivity::class.java)
            startActivity(i)
        }

        binding.lnrTemplates.setOnClickListener { toggleButton(binding.imgTemplates, binding.txtTemplates) }

        binding.lnrBeautify.setOnClickListener {toggleButton(binding.imgBeautify, binding.txtBeautify)}

        binding.lnrCamera.setOnClickListener {toggleButton(binding.imgCamera, binding.txtCamera)}

        binding.imgPro.setOnClickListener {
            var i = Intent(this@DashboardActivity, Polish_Pro_PaymentActivity::class.java)
            startActivity(i)
        }

        binding.lnrMenu.setOnClickListener {
            var i = Intent(this@DashboardActivity, SettingsActivity::class.java)
            startActivity(i)
        }

    }

    private fun checkPermissions() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun checkPermissionsBeautify() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        } else {
            checkStoragePermissionBeautify()
        }
    }

    private fun checkCameraPermissions(requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE_CAMERA
            )
        } else {
            when (requestCode) {
                REQUEST_CODE_GALLERY -> openGallery()
                REQUEST_CODE_CAMERA -> dispatchTakePictureIntent()
            }
        }
    }

    private fun toggleButton(imageView: ImageView, textView: TextView) {
        if (selectedImageView != imageView) {
            // Reset previously selected button's image and text color
            selectedImageView?.setColorFilter(
                ContextCompat.getColor(this, R.color.grey),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            selectedTextView?.setTextColor(ContextCompat.getColor(this, R.color.grey))

            // Set new selected button's image and text color
            imageView.setColorFilter(
                ContextCompat.getColor(this, R.color.white),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            textView.setTextColor(ContextCompat.getColor(this, R.color.white))

            // Update the selected button references
            selectedImageView = imageView
            selectedTextView = textView

            when (imageView.id) {
                R.id.imgCollage -> {
                    if (allPermissionsGranted())
                    {
                        showBottomSheet()
                    }
                    else
                    {
                        checkPermissions()
                    }
                }

                R.id.imgTemplates -> {
                    val intent = Intent(this@DashboardActivity, TemplatesActivity::class.java)
                    startActivity(intent)
                }

                R.id.imgBeautify -> {
//                    if (!allPermissionsGranted()) {
//                        checkPermissionsBeautify()
//                    } else {
//                        checkStoragePermissionBeautify()
//                    }

                    if (allPermissionsGranted())
                    {
                        checkStoragePermissionBeautify()
                    }
                    else
                    {
                        checkPermissions()
                    }
                }

                R.id.imgCamera -> {
//                    checkCameraPermissions(REQUEST_CODE_CAMERA)
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.CAMERA),
                            REQUEST_CODE_CAMERA
                        )
                    } else {
                        dispatchTakePictureIntent()
                    }
                }
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try
                {
                    createImageFile()
                }
                catch (ex: IOException)
                {
                    Toast.makeText(this, "Error occurred while creating the file", Toast.LENGTH_SHORT).show()
                    null
                }

                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(this, "${applicationContext.packageName}.fileprovider", it)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA)
                }
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = getExternalFilesDir(null)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    @SuppressLint("MissingInflatedId")
    fun showBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetStyle)
        val view = layoutInflater.inflate(R.layout.camera_dialog, null)
        view.findViewById<ImageView>(R.id.imgCross)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        // Set layout manager with 3 columns
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        updateBottomSheetTextViews(view)

        FetchImagesTask(this) { imageList ->
            recyclerView.adapter = ImageAdapter(imageList) { selectedImageUri ->
                bottomSheetDialog.dismiss()
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("selected_image_uri", selectedImageUri.toString())
                startActivity(intent)
            }
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.setOnShowListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bottomSheetDialog.window?.navigationBarColor =
                        ContextCompat.getColor(this, R.color.black)
                }
            }
            bottomSheetDialog.show()
        }.execute()
    }

//    fun updateTextViews(context: Context, view: View) {
//        val resources = context.resources
//
//        view.findViewById<TextView>(R.id.txtThisappcanonlyaccessPhotoSelected).text =
//            resources.getString(R.string.this_app_can_only_access_photo_selected)
//        view.findViewById<TextView>(R.id.txtPhotos).text =
//            resources.getString(R.string.photos)
//        view.findViewById<TextView>(R.id.txtAlbums).text =
//            resources.getString(R.string.albums)
//        view.findViewById<TextView>(R.id.txtRecent).text =
//            resources.getString(R.string.recent)
//    }

    @SuppressLint("MissingInflatedId")
    private fun showBottomSheetBeautify() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetStyle)
        val view = layoutInflater.inflate(R.layout.camera_dialog, null)
        view.findViewById<ImageView>(R.id.imgCross)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        updateBottomSheetTextViews(view)

        // Set layout manager with 3 columns
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        FetchBeautifyImagesTask(this) { imageList ->
            recyclerView.adapter = ImageAdapter(imageList) { selectedImageUri ->
                bottomSheetDialog.dismiss()
                val intent = Intent(this, Beautify_Activity::class.java)
                intent.putExtra("selected_image_uri", selectedImageUri.toString())
                startActivity(intent)
            }
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.setOnShowListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bottomSheetDialog.window?.navigationBarColor =
                        ContextCompat.getColor(this, R.color.black)
                }
            }
            bottomSheetDialog.show()
        }.execute()
    }

    private class FetchImagesTask(
        val context: DashboardActivity,
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

    private class FetchBeautifyImagesTask(
        val context: DashboardActivity,
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

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_GALLERY
            )
        } else {
            showBottomSheet()
        }
    }

    private fun checkStoragePermissionBeautify() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_GALLERY
            )
        } else {
            showBottomSheetBeautify()
        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CODE_PERMISSIONS || requestCode == REQUEST_CODE_CAMERA) {
//            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
//                if (requestCode == REQUEST_CODE_CAMERA) {
//                    dispatchTakePictureIntent()
//                } else {
//                    showBottomSheet()
//                }
//            } else {
//                Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                allPermissionsGranted()
            } else {
                Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
