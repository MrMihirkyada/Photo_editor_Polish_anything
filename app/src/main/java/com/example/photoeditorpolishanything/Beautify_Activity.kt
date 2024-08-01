package com.example.photoeditorpolishanything

import LocaleHelper
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.photoeditorpolishanything.FaceDetaction.FaceDetectionHelper
import com.example.photoeditorpolishanything.FaceDetaction.JawlineEditor
import com.example.photoeditorpolishanything.databinding.ActivityBeautifyBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Beautify_Activity : BaseActivity() {

    lateinit var binding: ActivityBeautifyBinding
    private var detectedFace: com.google.mlkit.vision.face.Face? = null
    private var faceRect: Rect? = null // Declare a variable to hold the face rectangle
    private var isSeekBarUpdating = false
    lateinit var originalBitmap: Bitmap
    var faceDetectionHelper : FaceDetectionHelper? = null
    private var detectedFaces: List<Face>? = null
    val jawlineEditor = JawlineEditor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeautifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.black)

        }

        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)

        faceDetectionHelper = FaceDetectionHelper()

        initView()
    }

    override fun updateUIForSelectedLanguage() {
        super.updateUIForSelectedLanguage()
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        binding.txtOpen.text = resources.getString(R.string.Open)
        binding.txtBeautify.text = resources.getString(R.string.Beautify)
        binding.txtFace.text = resources.getString(R.string.Face)
        binding.txtRetouch.text = resources.getString(R.string.Retouch)
        binding.txtMakeup.text = resources.getString(R.string.Makeup)
        binding.txtBlemish.text = resources.getString(R.string.Blemish)
        binding.txtWrinkle.text = resources.getString(R.string.Wrinkle)
        binding.txtDarkCircles.text = resources.getString(R.string.DarkCircles)
        binding.txtReshape.text = resources.getString(R.string.Reshape)


        binding.txtThin.text = resources.getString(R.string.Thin)
        binding.txtWidth.text = resources.getString(R.string.Width)
        binding.txtJaw.text = resources.getString(R.string.Jaw)
        binding.txtForehead.text = resources.getString(R.string.Forehead)
        binding.txtSizes.text = resources.getString(R.string.Size)
        binding.txtWidths.text = resources.getString(R.string.Width)
        binding.txtDistance.text = resources.getString(R.string.Distance)
        binding.txtHeights.text = resources.getString(R.string.Height)
        binding.txtTilt.text = resources.getString(R.string.Tilt)
        binding.txtSize.text = resources.getString(R.string.Size)
        binding.txtWidthes.text = resources.getString(R.string.Width)
        binding.txtHeightes.text = resources.getString(R.string.Height)
        binding.txtMThickness.text = resources.getString(R.string.Thickness)
        binding.txtNThickness.text = resources.getString(R.string.Thickness)
        binding.txtWidthess.text = resources.getString(R.string.Width)
        binding.txtTip.text = resources.getString(R.string.Tip)
        binding.txtLift.text = resources.getString(R.string.Lift)
        binding.txtThickness.text = resources.getString(R.string.Thickness)
        binding.txtDistances.text = resources.getString(R.string.Distance)
        binding.txtLifts.text = resources.getString(R.string.Lift)
        binding.txtTilts.text = resources.getString(R.string.Tilt)


        binding.txtSmooth.text = resources.getString(R.string.Smooth)
        binding.txtBrighten.text = resources.getString(R.string.Brighten)
        binding.txtSharpen.text = resources.getString(R.string.Sharpen)


        binding.txtSets.text = resources.getString(R.string.Sets)
        binding.txtLipColor.text = resources.getString(R.string.LipColor)
        binding.txtBlush.text = resources.getString(R.string.Blush)
        binding.txtContour.text = resources.getString(R.string.Contour)


        binding.txtOriginal.text = resources.getString(R.string.Original)
        binding.txtBright.text = resources.getString(R.string.Bright)
        binding.txtStory.text = resources.getString(R.string.Story)
        binding.txtNatural.text = resources.getString(R.string.Natural)
        binding.txtWarm.text = resources.getString(R.string.Warm)
        binding.txtDew.text = resources.getString(R.string.Dew)
        binding.txtGold.text = resources.getString(R.string.Gold)
        binding.txtLomo.text = resources.getString(R.string.Lomo)
        binding.txtPink.text = resources.getString(R.string.Pink)


        binding.txtMa01.text = resources.getString(R.string.Ma01)
        binding.txtMa02.text = resources.getString(R.string.Ma02)
        binding.txtMa03.text = resources.getString(R.string.Ma03)
        binding.txtMa04.text = resources.getString(R.string.Ma04)
        binding.txtMa05.text = resources.getString(R.string.Ma05)
        binding.txtMa06.text = resources.getString(R.string.Ma06)
        binding.txtMa07.text = resources.getString(R.string.Ma07)
        binding.txtMa08.text = resources.getString(R.string.Ma08)
        binding.txtMa09.text = resources.getString(R.string.Ma09)



        binding.txtFlush.text = resources.getString(R.string.Flush)
        binding.txtSquare.text = resources.getString(R.string.Square)
        binding.txtPear.text = resources.getString(R.string.Pear)
        binding.txtOval.text = resources.getString(R.string.Oval)
        binding.txtSunburns.text = resources.getString(R.string.Sunburn)
        binding.txtAngled.text = resources.getString(R.string.Angled)
        binding.txtNormals.text = resources.getString(R.string.Normal)
        binding.txtApple.text = resources.getString(R.string.Apple)
        binding.txtFull.text = resources.getString(R.string.Full)



        binding.txtFlushs.text = resources.getString(R.string.Flush)
        binding.txtSquares.text = resources.getString(R.string.Square)
        binding.txtPears.text = resources.getString(R.string.Pear)
        binding.txtOvals.text = resources.getString(R.string.Oval)
        binding.txtSunburn.text = resources.getString(R.string.Sunburn)
        binding.txtAngleds.text = resources.getString(R.string.Angled)
        binding.txtNormal.text = resources.getString(R.string.Normal)



        binding.txtTapareastoremoveblemishes.text =
            resources.getString(R.string.Tapareastoremoveblemishes)


        binding.txtManual.text = resources.getString(R.string.Manual)
        binding.txtEraser.text = resources.getString(R.string.Eraser)



        binding.txtReshapes.text = resources.getString(R.string.Reshape)
        binding.txtDetail.text = resources.getString(R.string.Detail)
        binding.txtResize.text = resources.getString(R.string.Resize)
        binding.txtRestore.text = resources.getString(R.string.Restore)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        val imageUriString = intent.getStringExtra("selected_image_uri")
        val imageUri = Uri.parse(imageUriString)

        val getImageLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    binding.imgEditBeautifySelectImage.setImageURI(imageUri)
                }
            }

        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            loadImageAsync(imageUri) // Load the image
            detectFace(imageUri) // Detect faces in the image
        } else {
            logErrorAndFinish("Image URI string is null")
        }

//        binding.lnrThin.setOnClickListener {
//            val thinFactor = 0.1f // Replace with user-defined value
//            detectedFace?.let { face ->
//                faceRect?.let { originalRect ->
//                    val newRect = thinFace(originalRect, thinFactor)
//                    displayFaceRectangle(newRect)
//                }
//            }
//        }

        originalBitmap = getBitmapFromUri(imageUri)
        detectFaces(originalBitmap)


//        binding.lnrskbface.setOnSeekBarChangeListener(object : CustomSeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(progress: Int) {
//                detectedFaces?.let { faces ->
//                    // Create a mutable copy of the original bitmap
//                    var adjustedBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
//
//                    // Iterate through each detected face
//                    faces.forEach { face ->
//                        val faceRect = face.boundingBox // Extract the bounding box
//                        val faceThinFactor = 1.0f - (progress / 100.0f)
//
//                        // Apply the face thinning adjustment
//                        adjustedBitmap = adjustFaceThin(adjustedBitmap, faceRect, faceThinFactor)
//                    }
//
//                    // Update the image view with the adjusted bitmap
//                    binding.imgEditBeautifySelectImage.setImageBitmap(adjustedBitmap)
//                }
//            }
//
//            override fun onStartTrackingTouch() {}
//
//            override fun onStopTrackingTouch() {}
//        })

        binding.lnrskbface.setOnSeekBarChangeListener(object : CustomSeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(progress: Int) {
                detectedFaces?.firstOrNull()?.let { face ->
                    val jawlineFactor = (progress - 50) / 50.0f // Adjust this scale based on your requirements
                    val face = face.boundingBox
//                    val adjustedBitmap = JawlineEditor().adjustJawline(originalBitmap, face, jawlineFactor)
//                    binding.imgEditBeautifySelectImage.setImageBitmap(adjustedBitmap)
                }
            }

            override fun onStartTrackingTouch() {}

            override fun onStopTrackingTouch() {}
        })



        binding.imgcross.setOnClickListener {
            binding.lnrMainlayoutBeautify.visibility = View.VISIBLE
            binding.txtOpen.visibility = View.VISIBLE

            binding.rtlVisibilitycrossright.visibility = View.GONE
            binding.rtlVisibilityPro.visibility = View.GONE

            //---------------face View---------------//
            binding.lnrskbface.visibility = View.GONE
            binding.lnrFaceLayout.visibility = View.GONE
            //---------------face View---------------//


            //---------------Retouch View---------------//
            binding.lnrskbRetouch.visibility = View.GONE
            binding.lnrRetouchLayout.visibility = View.GONE
            //---------------Retouch View---------------//


            //---------------Make up View---------------//

            binding.rtlVisibilitycrossright.visibility = View.GONE
            binding.lnrvisibility.visibility = View.GONE

            //------------Setting View---------//
            binding.lnrskbSettingvisibility.visibility = View.GONE
            binding.lnrSettingvisibility.visibility = View.GONE
            //------------Setting View---------//

            //------------Lip View---------//
            binding.lnrskbvisibilityLip.visibility = View.GONE
            binding.lnrvisibilityLipcolor.visibility = View.GONE
            //------------Lip View---------//

            //------------Blush View---------//
            binding.lnrskbvisibilityBlush.visibility = View.GONE
            binding.lnrvisibilityBlush.visibility = View.GONE
            //------------Blush View---------//


            //------------Blush View---------//
            binding.lnrskbvisibilityContour.visibility = View.GONE
            binding.lnrvisibilityContour.visibility = View.GONE
            //------------Blush View---------//

            //---------------Make up View---------------//


            //---------------Blemish View---------------//
            binding.lnrBlemishLayout.visibility = View.GONE
            //---------------Blemish View---------------//


            //---------------Wrinkle View---------------//
            binding.lnrWrinkleReturn.visibility = View.GONE
            binding.lnrWrinkleLayout.visibility = View.GONE
            binding.rtlVisibilityPro.visibility = View.GONE
            //---------------Wrinkle View---------------//


            //---------------Reshape View---------------//
            binding.lnrReshapeView.visibility = View.GONE
            //---------------Reshape View---------------//

        }

        binding.imgcrosss.setOnClickListener {
            binding.lnrMainlayoutBeautify.visibility = View.VISIBLE
            binding.txtOpen.visibility = View.VISIBLE

            //---------------Wrinkle View---------------//
            binding.lnrWrinkleReturn.visibility = View.GONE
            binding.lnrWrinkleLayout.visibility = View.GONE
            binding.rtlVisibilityPro.visibility = View.GONE
            //---------------Wrinkle View---------------//
        }

        binding.lnrPro.setOnClickListener {
            var i = Intent(this@Beautify_Activity, Polish_Pro_PaymentActivity::class.java)
            startActivity(i)
        }

        binding.lnrFace.setOnClickListener {
//            var i = Intent(this@Beautify_Activity,FaceActivity::class.java)
//            startActivity(i)

            binding.lnrMainlayoutBeautify.visibility = View.GONE
            binding.txtOpen.visibility = View.GONE

            binding.rtlVisibilitycrossright.visibility = View.VISIBLE
            binding.lnrskbfaces.visibility = View.VISIBLE
            binding.lnrskbface.visibility = View.VISIBLE
            binding.lnrFaceLayout.visibility = View.VISIBLE
        }

        binding.lnrRetouch.setOnClickListener {
//            var i = Intent(this@Beautify_Activity,Retouch_Activity::class.java)
//            startActivity(i)

            binding.lnrMainlayoutBeautify.visibility = View.GONE
            binding.txtOpen.visibility = View.GONE

            binding.rtlVisibilitycrossright.visibility = View.VISIBLE
            binding.lnrskbRetouch.visibility = View.VISIBLE
            binding.lnrRetouchLayout.visibility = View.VISIBLE
        }

        binding.lnrMakeup.setOnClickListener {
//            var i = Intent(this@Beautify_Activity,Makeup_Activity::class.java)
//            startActivity(i)

            binding.lnrMainlayoutBeautify.visibility = View.GONE
            binding.txtOpen.visibility = View.GONE

            binding.rtlVisibilitycrossright.visibility = View.VISIBLE
            binding.lnrvisibility.visibility = View.VISIBLE
        }

//---------------------------------------MAKE UP VIEW-----------------------------------------//
        binding.lnrSetting.setOnClickListener {
            binding.lnrvisibility.visibility = View.GONE
            binding.lnrskbSettingvisibility.visibility = View.VISIBLE
            binding.lnrSettingvisibility.visibility = View.VISIBLE
        }

        binding.lnrLipColor.setOnClickListener {
            binding.lnrvisibility.visibility = View.GONE
            binding.lnrskbvisibilityLip.visibility = View.VISIBLE
            binding.lnrvisibilityLipcolor.visibility = View.VISIBLE
        }

        binding.lnrBlush.setOnClickListener {
            binding.lnrvisibility.visibility = View.GONE
            binding.lnrskbvisibilityBlush.visibility = View.VISIBLE
            binding.lnrvisibilityBlush.visibility = View.VISIBLE
        }

        binding.lnrContour.setOnClickListener {
            binding.lnrvisibility.visibility = View.GONE
            binding.lnrskbvisibilityContour.visibility = View.VISIBLE
            binding.lnrvisibilityContour.visibility = View.VISIBLE
        }
//---------------------------------------MAKE UP VIEW-----------------------------------------//

        binding.lnrBlemish.setOnClickListener {
//            var i = Intent(this@Beautify_Activity,Blemish_Activity::class.java)
//            startActivity(i)
            binding.lnrMainlayoutBeautify.visibility = View.GONE
            binding.txtOpen.visibility = View.GONE

            binding.rtlVisibilitycrossright.visibility = View.VISIBLE
            binding.lnrBlemishLayout.visibility = View.VISIBLE
        }

        binding.lnrWrinkle.setOnClickListener {
//            var i = Intent(this@Beautify_Activity,Wrinkle_DarkCircle_Activity::class.java)
//            startActivity(i)
            binding.lnrMainlayoutBeautify.visibility = View.GONE
            binding.txtOpen.visibility = View.GONE

            binding.rtlVisibilityPro.visibility = View.VISIBLE
            binding.lnrWrinkleReturn.visibility = View.VISIBLE
            binding.lnrWrinkleLayout.visibility = View.VISIBLE

        }

        binding.lnrDarkCircles.setOnClickListener {
//            var i = Intent(this@Beautify_Activity,Wrinkle_DarkCircle_Activity::class.java)
//            startActivity(i)
            binding.lnrMainlayoutBeautify.visibility = View.GONE
            binding.txtOpen.visibility = View.GONE

            binding.rtlVisibilityPro.visibility = View.VISIBLE
            binding.lnrWrinkleReturn.visibility = View.VISIBLE
            binding.lnrWrinkleLayout.visibility = View.VISIBLE

        }

        binding.lnrReshape.setOnClickListener {
//            var i = Intent(this@Beautify_Activity, Wrinkle_DarkCircle_Activity::class.java)
//            startActivity(i)

            binding.lnrMainlayoutBeautify.visibility = View.GONE
            binding.txtOpen.visibility = View.GONE

            binding.rtlVisibilitycrossright.visibility = View.VISIBLE
            binding.lnrReshapeView.visibility = View.VISIBLE
        }

        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            val imageView = findViewById<ImageView>(R.id.imgEditBeautifySelectImage)

            if (imageView != null) {
                try {
                    Glide.with(this).load(imageUri).into(imageView)
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

    private fun detectFace(imageUri: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            val bitmap = loadResizedBitmap(imageUri, 800, 800) // Load a resized bitmap
            val image = InputImage.fromBitmap(bitmap!!, 0)

            val highAccuracyOpts = FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .build()

            val detector = FaceDetection.getClient(highAccuracyOpts)

            detector.process(image)
                .addOnSuccessListener { faces ->
                    onFacesDetected(faces)
                }
                .addOnFailureListener { e ->
                    showToast("Face detection failed")
                    logErrorAndFinish("Face detection error: ${e.message}")
                }
        }
    }

    fun getBitmapFromUri(uri: Uri): Bitmap {
        val inputStream = contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream)
    }
    fun loadResizedBitmap(uri: Uri, maxWidth: Int, maxHeight: Int): Bitmap? {
        val inputStream = contentResolver.openInputStream(uri)
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeStream(inputStream, null, this)
            inSampleSize = calculateInSampleSize(this, maxWidth, maxHeight)
            inJustDecodeBounds = false
        }
        inputStream?.close()
        val resizedInputStream = contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(resizedInputStream, null, options)
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun loadImageAsync(imageUri: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            val bitmap = decodeSampledBitmapFromUri(imageUri, 800, 800) // Resize as needed
            withContext(Dispatchers.Main) {
                originalBitmap = bitmap
                binding.imgEditBeautifySelectImage.setImageBitmap(bitmap)
                detectFaces(bitmap)
            }
        }
    }

    private fun decodeSampledBitmapFromUri(uri: Uri, reqWidth: Int, reqHeight: Int): Bitmap {
        val inputStream = contentResolver.openInputStream(uri) ?: throw Exception("Unable to open InputStream")
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeStream(inputStream, null, this)
            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)
            inJustDecodeBounds = false
        }

        inputStream.close()
        val newInputStream = contentResolver.openInputStream(uri) ?: throw Exception("Unable to open InputStream")
        return BitmapFactory.decodeStream(newInputStream, null, options)!!
    }



    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.outHeight to options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private fun detectFaces(bitmap: Bitmap) {
        if (faceDetectionHelper == null) {
            logErrorAndFinish("FaceDetectionHelper is null when detecting faces")
            return
        }

        faceDetectionHelper!!.detectFaces(
            bitmap,
            onSuccess = { faces ->
                detectedFaces = faces
                Toast.makeText(this, "Faces detected: ${faces.size}", Toast.LENGTH_SHORT).show()
            },
            onFailure = { exception ->
                Toast.makeText(this, "Face detection failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun adjustFaceSize(bitmap: Bitmap, faces: List<Face>, progress: Int): Bitmap {
        val adjustedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(adjustedBitmap)

        for (face in faces) {
            val faceRect = face.boundingBox
            val scaleFactor = 1.0 - (progress / 100.0)

            val newWidth = (faceRect.width() * scaleFactor).toInt()
            val newHeight = (faceRect.height() * scaleFactor).toInt()

            val left = maxOf(0, faceRect.left + (faceRect.width() - newWidth) / 2)
            val top = maxOf(0, faceRect.top + (faceRect.height() - newHeight) / 2)
            val right = minOf(bitmap.width, left + newWidth)
            val bottom = minOf(bitmap.height, top + newHeight)

            if (right > left && bottom > top) {
                val faceArea = Bitmap.createBitmap(bitmap, left, top, right - left, bottom - top)
                canvas.drawBitmap(faceArea, Rect(left, top, right, bottom), Rect(left, top, right, bottom), null)
            }
        }

        return adjustedBitmap
    }


    private fun onFacesDetected(faces: List<Face>) {
        if (faces.isNotEmpty()) {
            showToast("Face detected")
            detectedFace = faces[0]
            faceRect = detectedFace?.boundingBox
//            displayFaceRectangle(faceRect)
        } else {
            showToast("No face detected")
        }
    }

//  face changes is working but it create other image and size is changing in that image not in current face
//    private fun adjustFaceThin(bitmap: Bitmap, faces: List<Face>, progress: Int): Bitmap {
//        val adjustedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
//        val canvas = Canvas(adjustedBitmap)
//
//        for (face in faces) {
//            val faceRect = face.boundingBox
//            val scaleFactor = 1.0 - (progress / 100.0) // Example logic for thinning effect
//            val newWidth = (faceRect.width() * scaleFactor).toInt()
//
//            // Adjust the face rectangle based on the new width
//            val newLeft = faceRect.left + (faceRect.width() - newWidth) / 2
//            val newRight = newLeft + newWidth
//
//            val updatedFaceRect = Rect(newLeft, faceRect.top, newRight, faceRect.bottom)
//
//            canvas.drawBitmap(bitmap, faceRect, updatedFaceRect, null)
//        }
//
//        return adjustedBitmap
//    }

    private fun adjustFaceThin(bitmap: Bitmap, faceRect: Rect, faceThinFactor: Float): Bitmap {
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)

        // Ensure faceRect is within bounds
        val rect = Rect(
            maxOf(0, faceRect.left),
            maxOf(0, faceRect.top),
            minOf(mutableBitmap.width, faceRect.right),
            minOf(mutableBitmap.height, faceRect.bottom)
        )

        // Extract face pixels
        val facePixels = IntArray(rect.width() * rect.height())
        mutableBitmap.getPixels(facePixels, 0, rect.width(), rect.left, rect.top, rect.width(), rect.height())

        // Apply desired modifications here
        for (y in 0 until rect.height()) {
            for (x in 0 until rect.width()) {
                val index = y * rect.width() + x
                // Modify pixels based on your requirement (here we keep it simple)
                val color = facePixels[index]
                // Example: Just leave the color unchanged or apply different logic
                facePixels[index] = color
            }
        }

        // Set the modified pixels back to the bitmap
        mutableBitmap.setPixels(facePixels, 0, rect.width(), rect.left, rect.top, rect.width(), rect.height())

        return mutableBitmap
    }

//          jaw code
//    private fun adjustFaceJaw(originalBitmap: Bitmap, face: Face, jawAdjustmentFactor: Float): Bitmap {
//        // Get face rectangle
//        val faceRect = face.boundingBox
//
//        // Define the area of the jawline you want to adjust
//        val jawRect = Rect(
//            faceRect.left,
//            faceRect.bottom - (faceRect.height() / 3),  // Adjust this value as needed to define the jaw area
//            faceRect.right,
//            faceRect.bottom
//        )
//
//        // Ensure jawRect is within the bounds of the original bitmap
//        val rectLeft = maxOf(0, jawRect.left)
//        val rectTop = maxOf(0, jawRect.top)
//        val rectRight = minOf(originalBitmap.width, jawRect.right)
//        val rectBottom = minOf(originalBitmap.height, jawRect.bottom)
//
//        // Check if the jaw rectangle is valid
//        if (rectRight <= rectLeft || rectBottom <= rectTop) {
//            // If the jaw rectangle is not valid, return the original bitmap
//            return originalBitmap
//        }
//
//        // Extract the jaw area from the original bitmap
//        val jawBitmap = Bitmap.createBitmap(originalBitmap, rectLeft, rectTop, rectRight - rectLeft, rectBottom - rectTop)
//
//        // Calculate new dimensions for the jaw adjustment
//        val jawWidth = jawBitmap.width
//        val jawHeight = jawBitmap.height
//        val newJawHeight = (jawHeight * jawAdjustmentFactor).toInt().coerceIn(1, originalBitmap.height - rectTop)
//
//        // Scale the jaw region
//        val scaledJawBitmap = Bitmap.createScaledBitmap(jawBitmap, jawWidth, newJawHeight, true)
//
//        // Create a new bitmap with the same size as the original
//        val adjustedBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)
//
//        // Copy original bitmap to adjusted bitmap
//        val canvas = Canvas(adjustedBitmap)
//        canvas.drawBitmap(originalBitmap, 0f, 0f, null)
//
//        // Calculate the position for placing the scaled jaw bitmap
//        val scaledJawRect = Rect(
//            rectLeft,
//            rectTop + (jawHeight - newJawHeight),
//            rectLeft + jawWidth,
//            rectTop + jawHeight
//        )
//
//        // Ensure the scaled jaw rectangle is within the bounds of the original bitmap
//        val boundedScaledJawRect = Rect(
//            maxOf(0, scaledJawRect.left),
//            maxOf(0, scaledJawRect.top),
//            minOf(adjustedBitmap.width, scaledJawRect.right),
//            minOf(adjustedBitmap.height, scaledJawRect.bottom)
//        )
//
//        // Draw the scaled jaw bitmap onto the adjusted bitmap
//        canvas.drawBitmap(scaledJawBitmap, null, boundedScaledJawRect, null)
//
//        return adjustedBitmap
//    }



    // Function to adjust the jawline of a detected face in the bitmap
    private fun adjustJawline(bitmap: Bitmap, face: Rect, jawlineFactor: Float): Bitmap {
        val faceWidth = face.width()
        val faceHeight = face.height()

        // Create a mutable bitmap to work on
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)

        // Extract the face region from the bitmap
        val faceBitmap = Bitmap.createBitmap(mutableBitmap, face.left, face.top, faceWidth, faceHeight)
        val mutableFaceBitmap = faceBitmap.copy(Bitmap.Config.ARGB_8888, true)

        // Create a canvas to draw on the mutable face bitmap
        val canvas = Canvas(mutableFaceBitmap)
        val paint = Paint()

        // Modify the jawline directly (this is a simplified version)
        // This code just scales the face bitmap to simulate a jawline adjustment.
        // You will need to use more sophisticated image processing to specifically adjust the jawline.
        val matrix = android.graphics.Matrix()
        matrix.postScale(jawlineFactor, jawlineFactor, faceWidth / 2f, faceHeight / 2f)
        canvas.drawBitmap(mutableFaceBitmap, matrix, paint)

        // Replace the modified face region back into the original bitmap
        val resultBitmap = mutableBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val resultCanvas = Canvas(resultBitmap)
        resultCanvas.drawBitmap(mutableFaceBitmap, face.left.toFloat(), face.top.toFloat(), null)

        // Clean up
        faceBitmap.recycle()
        mutableFaceBitmap.recycle()

        return resultBitmap
    }

    private fun convertProgressToJawlineFactor(progress: Int): Float {
        // Implement this based on how you want the seek bar progress to affect jawline adjustment
        // For example, normalize progress to a scale factor between 0.5 and 1.5
        return 1.0f + (progress - 50) / 50f // Example logic
    }

    private fun thinFace(faceRect: Rect, thinFactor: Float): Rect {
        val newWidth = (faceRect.width() * (1 - thinFactor)).toInt()
        val centerX = (faceRect.left + faceRect.right) / 2
        return Rect(centerX - newWidth / 2, faceRect.top, centerX + newWidth / 2, faceRect.bottom)
    }

    override fun onBackPressed() {
        if (binding.lnrskbSettingvisibility.visibility == View.VISIBLE ||
            binding.lnrSettingvisibility.visibility == View.VISIBLE ||
            binding.lnrskbvisibilityLip.visibility == View.VISIBLE ||
            binding.lnrvisibilityLipcolor.visibility == View.VISIBLE ||
            binding.lnrskbvisibilityBlush.visibility == View.VISIBLE ||
            binding.lnrvisibilityBlush.visibility == View.VISIBLE ||
            binding.lnrskbvisibilityContour.visibility == View.VISIBLE ||
            binding.lnrvisibilityContour.visibility == View.VISIBLE
        ) {
            // Hide all the views
            binding.lnrskbSettingvisibility.visibility = View.GONE
            binding.lnrSettingvisibility.visibility = View.GONE
            binding.lnrskbvisibilityLip.visibility = View.GONE
            binding.lnrvisibilityLipcolor.visibility = View.GONE
            binding.lnrskbvisibilityBlush.visibility = View.GONE
            binding.lnrvisibilityBlush.visibility = View.GONE
            binding.lnrskbvisibilityContour.visibility = View.GONE
            binding.lnrvisibilityContour.visibility = View.GONE

            // Show the main view
            binding.lnrvisibility.visibility = View.VISIBLE
        } else {
            // If all views are already hidden, proceed with the default back button behavior
            super.onBackPressed()
        }
    }

    private fun logErrorAndFinish(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish() // Close the activity or handle it appropriately
    }
}