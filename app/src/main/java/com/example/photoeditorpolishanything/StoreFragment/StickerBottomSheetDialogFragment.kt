package com.example.photoeditorpolishanything.StoreFragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoeditorpolishanything.Adapter.Sticker_Sub_Image_Adapter
import com.example.photoeditorpolishanything.ImageSizeFetcher
import com.example.photoeditorpolishanything.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StickerBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imgStickers: ImageView
    private lateinit var txtName : TextView
    private lateinit var txtNumber : TextView
    private lateinit var adapter: Sticker_Sub_Image_Adapter
    private var data: List<String?>? = null
    private val imageSizeFetcher = ImageSizeFetcher()
    private lateinit var datas: String
    private lateinit var mainImageUrl : String
    var textCategory : TextView? = null


    companion object {
        private const val ARG_DATA = "data"
        private const val ARG_COLOR = "navigation_bar_color"
        private const val ARG_MAIN_IMAGE_URL = "main_image_url"
        private const val baseUrl = "https://s3.eu-north-1.amazonaws.com/photoeditorbeautycamera.com/photoeditor/sticker/"
        private const val ARG_TEXT_CATEGORY = "https://s3.eu-north-1.amazonaws.com/photoeditorbeautycamera.com/photoeditor/sticker/"

        fun newInstance(
            data: List<String?>?,
            navigationBarColor: Int,
            mainImageUrl: String,
            textCategory : String?
        ): StickerBottomSheetDialogFragment {
            val fragment = StickerBottomSheetDialogFragment()
            val args = Bundle()
            args.putStringArrayList(ARG_DATA, ArrayList(data))
            args.putInt(ARG_COLOR, navigationBarColor)
            args.putString(ARG_MAIN_IMAGE_URL, mainImageUrl)
            args.putString(ARG_TEXT_CATEGORY , textCategory)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = arguments?.getStringArrayList(ARG_DATA)
        datas = arguments?.getString(ARG_TEXT_CATEGORY)!!
        mainImageUrl = arguments?.getString(ARG_MAIN_IMAGE_URL)!!
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.sticker_bottom_sheet_dialog, container,false)
        recyclerView = view.findViewById(R.id.recyclerViews)
        imgStickers = view.findViewById(R.id.imgStickers)
        txtName = view.findViewById(R.id.txtName)
        txtNumber = view.findViewById(R.id.txtNumbers)
        Glide.with(requireContext()).load(baseUrl + mainImageUrl).into(imgStickers)

        Log.e("imgStickers", "onCreateView: " + baseUrl + mainImageUrl)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        data = arguments?.getStringArrayList(ARG_DATA)
        adapter = Sticker_Sub_Image_Adapter(data ?: emptyList())
        recyclerView.adapter = adapter

        txtName.text = datas

        CoroutineScope(Dispatchers.IO).launch {
            val sizeText = imageSizeFetcher.fetchImageSizes(baseUrl, data)
            withContext(Dispatchers.Main) {
                txtNumber.text = "Size :- $sizeText"
            }
        }

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.isHideable = false
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            bottomSheet.requestLayout()

            dialog.window?.navigationBarColor = ContextCompat.getColor(requireContext(), arguments?.getInt(ARG_COLOR) ?: R.color.black)
        }
        return dialog
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialogTheme
    }
}

