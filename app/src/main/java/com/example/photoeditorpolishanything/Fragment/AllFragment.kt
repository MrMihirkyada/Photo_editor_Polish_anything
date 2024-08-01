package com.example.photoeditorpolishanything.Fragment

import ImageAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photoeditorpolishanything.Adapter.CollageTemplateAdapter
import com.example.photoeditorpolishanything.Api.Group
import com.example.photoeditorpolishanything.Api.OkHttpHelper
import com.example.photoeditorpolishanything.EditActivity
import com.example.photoeditorpolishanything.R
import com.example.photoeditorpolishanything.TemplatesActivity
import com.example.photoeditorpolishanything.TemplatesActivity.Companion.contexts
import com.example.photoeditorpolishanything.databinding.FragmentAllBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class AllFragment : Fragment() {

    private lateinit var binding: FragmentAllBinding
    lateinit var adapter: CollageTemplateAdapter
//    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.recyclerView.layoutManager = GridLayoutManager(contexts, 2)

        val url = "https://s3.eu-north-1.amazonaws.com/photoeditorbeautycamera.com/photoeditor/templates.json"

        OkHttpHelper.fetchTemplates(url) { categorysApi ->
            categorysApi?.let {
                val groupsList = mutableListOf<Group>()

                it.templates.forEach { template ->

                    template?.birthday?.let { birthday ->
                        birthday.group1?.let { groupsList.add(it) }
                        birthday.group2?.let { groupsList.add(it) }
                        birthday.group3?.let { groupsList.add(it) }
                        birthday.group4?.let { groupsList.add(it) }
                        birthday.group5?.let { groupsList.add(it) }
                        birthday.group6?.let { groupsList.add(it) }
                        birthday.group7?.let { groupsList.add(it) }
                        birthday.group8?.let { groupsList.add(it) }
                        birthday.group9?.let { groupsList.add(it) }
                        birthday.group10?.let { groupsList.add(it) }
                        birthday.group11?.let { groupsList.add(it) }
                        birthday.group12?.let { groupsList.add(it) }
                        birthday.group13?.let { groupsList.add(it) }
                        birthday.group14?.let { groupsList.add(it) }
                        birthday.group15?.let { groupsList.add(it) }
                        birthday.group16?.let { groupsList.add(it) }
                        birthday.group17?.let { groupsList.add(it) }
                        birthday.group18?.let { groupsList.add(it) }
                        birthday.group19?.let { groupsList.add(it) }
                        birthday.group20?.let { groupsList.add(it) }
                        // Repeat for all groups in the birthday category
                    }

                    template?.calendar?.let { calendar ->
                        calendar.group1?.let { groupsList.add(it) }
                        calendar.group2?.let { groupsList.add(it) }
                        calendar.group3?.let { groupsList.add(it) }
                        calendar.group4?.let { groupsList.add(it) }
                        calendar.group5?.let { groupsList.add(it) }
                        calendar.group6?.let { groupsList.add(it) }
                        calendar.group7?.let { groupsList.add(it) }
                        calendar.group8?.let { groupsList.add(it) }
                        calendar.group9?.let { groupsList.add(it) }
                        calendar.group10?.let { groupsList.add(it) }
                        calendar.group11?.let { groupsList.add(it) }
                        calendar.group12?.let { groupsList.add(it) }
                        calendar.group13?.let { groupsList.add(it) }
                        calendar.group14?.let { groupsList.add(it) }
                        calendar.group15?.let { groupsList.add(it) }
                        calendar.group16?.let { groupsList.add(it) }
                        calendar.group17?.let { groupsList.add(it) }
                        calendar.group18?.let { groupsList.add(it) }
                        calendar.group19?.let { groupsList.add(it) }
                        // Repeat for all groups in the childhood category
                    }

                    template?.christmas?.let { christmas ->
                        christmas.group1?.let { groupsList.add(it) }
                        christmas.group2?.let { groupsList.add(it) }
                        christmas.group3?.let { groupsList.add(it) }
                        christmas.group4?.let { groupsList.add(it) }
                        christmas.group5?.let { groupsList.add(it) }
                        christmas.group6?.let { groupsList.add(it) }
                        christmas.group7?.let { groupsList.add(it) }
                        christmas.group8?.let { groupsList.add(it) }
                        christmas.group9?.let { groupsList.add(it) }
                        christmas.group10?.let { groupsList.add(it) }
                        christmas.group11?.let { groupsList.add(it) }
                        christmas.group12?.let { groupsList.add(it) }
                        christmas.group13?.let { groupsList.add(it) }
                        christmas.group14?.let { groupsList.add(it) }
                        christmas.group15?.let { groupsList.add(it) }
                        christmas.group16?.let { groupsList.add(it) }
                        christmas.group17?.let { groupsList.add(it) }
                        christmas.group18?.let { groupsList.add(it) }
                        christmas.group19?.let { groupsList.add(it) }
//                        christmas.group20?.let { groupsList.add(it) }
                        // Repeat for all groups in the childhood category
                    }

                    template?.family?.let { family ->
                        family.group1?.let { groupsList.add(it) }
                        family.group2?.let { groupsList.add(it) }
                        family.group3?.let { groupsList.add(it) }
                        family.group4?.let { groupsList.add(it) }
                        family.group5?.let { groupsList.add(it) }
                        family.group6?.let { groupsList.add(it) }
                        family.group7?.let { groupsList.add(it) }
                        family.group8?.let { groupsList.add(it) }
                        family.group9?.let { groupsList.add(it) }
                        family.group10?.let { groupsList.add(it) }
                        family.group11?.let { groupsList.add(it) }
                        family.group12?.let { groupsList.add(it) }
                        family.group13?.let { groupsList.add(it) }
                        family.group14?.let { groupsList.add(it) }
                        family.group15?.let { groupsList.add(it) }
                        family.group16?.let { groupsList.add(it) }
                        family.group17?.let { groupsList.add(it) }
                        family.group18?.let { groupsList.add(it) }
                        family.group19?.let { groupsList.add(it) }
                        family.group20?.let { groupsList.add(it) }
                        // Repeat for all groups in the childhood category
                    }

                    template?.frame?.let { frame ->
                        frame.group1?.let { groupsList.add(it) }
                        frame.group2?.let { groupsList.add(it) }
                        frame.group3?.let { groupsList.add(it) }
                        frame.group4?.let { groupsList.add(it) }
                        frame.group5?.let { groupsList.add(it) }
                        frame.group6?.let { groupsList.add(it) }
                        frame.group7?.let { groupsList.add(it) }
                        frame.group8?.let { groupsList.add(it) }
                        frame.group9?.let { groupsList.add(it) }
                        frame.group10?.let { groupsList.add(it) }
                        frame.group11?.let { groupsList.add(it) }
                        frame.group12?.let { groupsList.add(it) }
                        frame.group13?.let { groupsList.add(it) }
                        frame.group14?.let { groupsList.add(it) }
                        frame.group15?.let { groupsList.add(it) }
                        frame.group16?.let { groupsList.add(it) }
                        frame.group17?.let { groupsList.add(it) }
                        frame.group18?.let { groupsList.add(it) }
                        frame.group19?.let { groupsList.add(it) }
                        frame.group20?.let { groupsList.add(it) }
                        // Repeat for all groups in the frame category
                    }

                    template?.graduation?.let { graduation ->
                        graduation.group1?.let { groupsList.add(it) }
                        graduation.group2?.let { groupsList.add(it) }
                        graduation.group3?.let { groupsList.add(it) }
                        graduation.group4?.let { groupsList.add(it) }
                        graduation.group5?.let { groupsList.add(it) }
                        graduation.group6?.let { groupsList.add(it) }
                        graduation.group7?.let { groupsList.add(it) }
                        graduation.group8?.let { groupsList.add(it) }
                        graduation.group9?.let { groupsList.add(it) }
                        graduation.group10?.let { groupsList.add(it) }
                        graduation.group11?.let { groupsList.add(it) }
                        graduation.group12?.let { groupsList.add(it) }
                        graduation.group13?.let { groupsList.add(it) }
                        graduation.group14?.let { groupsList.add(it) }
                        graduation.group15?.let { groupsList.add(it) }
                        graduation.group16?.let { groupsList.add(it) }
                        graduation.group17?.let { groupsList.add(it) }
                        graduation.group18?.let { groupsList.add(it) }
//                        graduation.group19?.let { groupsList.add(it) }
//                        graduation.group20?.let { groupsList.add(it) }
                        // Repeat for all groups in the frame category
                    }

                    template?.love?.let { love ->
                        love.group1?.let { groupsList.add(it) }
                        love.group2?.let { groupsList.add(it) }
                        love.group3?.let { groupsList.add(it) }
                        love.group4?.let { groupsList.add(it) }
                        love.group5?.let { groupsList.add(it) }
                        love.group6?.let { groupsList.add(it) }
                        love.group7?.let { groupsList.add(it) }
                        love.group8?.let { groupsList.add(it) }
                        love.group9?.let { groupsList.add(it) }
                        love.group10?.let { groupsList.add(it) }
                        love.group11?.let { groupsList.add(it) }
                        love.group12?.let { groupsList.add(it) }
                        love.group13?.let { groupsList.add(it) }
                        love.group14?.let { groupsList.add(it) }
                        love.group15?.let { groupsList.add(it) }
                        love.group16?.let { groupsList.add(it) }
                        love.group17?.let { groupsList.add(it) }
                        love.group18?.let { groupsList.add(it) }
                        love.group19?.let { groupsList.add(it) }
                        love.group20?.let { groupsList.add(it) }
                        // Repeat for all groups in the love category
                    }

                    template?.motherday?.let { motherday ->
                        motherday.group1?.let { groupsList.add(it) }
                        motherday.group2?.let { groupsList.add(it) }
                        motherday.group3?.let { groupsList.add(it) }
                        motherday.group4?.let { groupsList.add(it) }
                        motherday.group5?.let { groupsList.add(it) }
                        motherday.group6?.let { groupsList.add(it) }
                        motherday.group7?.let { groupsList.add(it) }
                        motherday.group8?.let { groupsList.add(it) }
                        motherday.group9?.let { groupsList.add(it) }
                        motherday.group10?.let { groupsList.add(it) }
                        motherday.group11?.let { groupsList.add(it) }
                        motherday.group12?.let { groupsList.add(it) }
                        motherday.group13?.let { groupsList.add(it) }
                        motherday.group14?.let { groupsList.add(it) }
                        motherday.group15?.let { groupsList.add(it) }
                        motherday.group16?.let { groupsList.add(it) }
                        motherday.group17?.let { groupsList.add(it) }
                        motherday.group18?.let { groupsList.add(it) }
                        motherday.group19?.let { groupsList.add(it) }
                        motherday.group20?.let { groupsList.add(it) }
                        // Repeat for all groups in the love category
                    }

                    template?.newyear?.let { newyear ->
                        newyear.group1?.let { groupsList.add(it) }
                        newyear.group2?.let { groupsList.add(it) }
                        newyear.group3?.let { groupsList.add(it) }
                        newyear.group4?.let { groupsList.add(it) }
                        newyear.group5?.let { groupsList.add(it) }
                        newyear.group6?.let { groupsList.add(it) }
                        newyear.group7?.let { groupsList.add(it) }
                        newyear.group8?.let { groupsList.add(it) }
                        newyear.group9?.let { groupsList.add(it) }
                        newyear.group10?.let { groupsList.add(it) }
                        newyear.group11?.let { groupsList.add(it) }
                        newyear.group12?.let { groupsList.add(it) }
                        newyear.group13?.let { groupsList.add(it) }
                        newyear.group14?.let { groupsList.add(it) }
                        newyear.group15?.let { groupsList.add(it) }
                        newyear.group16?.let { groupsList.add(it) }
                        newyear.group17?.let { groupsList.add(it) }
                        newyear.group18?.let { groupsList.add(it) }
                        newyear.group19?.let { groupsList.add(it) }
//                        newyear.group20?.let { groupsList.add(it) }
                        // Repeat for all groups in the love category
                    }

                    template?.pride?.let { pride ->
                        pride.group1?.let { groupsList.add(it) }
                        pride.group2?.let { groupsList.add(it) }
                        pride.group3?.let { groupsList.add(it) }
                        pride.group4?.let { groupsList.add(it) }
                        pride.group5?.let { groupsList.add(it) }
                        pride.group6?.let { groupsList.add(it) }
                        pride.group7?.let { groupsList.add(it) }
                        pride.group8?.let { groupsList.add(it) }
                        pride.group9?.let { groupsList.add(it) }
                        pride.group10?.let { groupsList.add(it) }
                        pride.group11?.let { groupsList.add(it) }
                        pride.group12?.let { groupsList.add(it) }
                        pride.group13?.let { groupsList.add(it) }
                        pride.group14?.let { groupsList.add(it) }
                        pride.group15?.let { groupsList.add(it) }
                        pride.group16?.let { groupsList.add(it) }
                        pride.group17?.let { groupsList.add(it) }
                        pride.group18?.let { groupsList.add(it) }
                        pride.group19?.let { groupsList.add(it) }
//                        pride.group20?.let { groupsList.add(it) }
                        // Repeat for all groups in the pridemonth category
                    }

                    template?.schoollife?.let { schoollife ->
                        schoollife.group1?.let { groupsList.add(it) }
                        schoollife.group2?.let { groupsList.add(it) }
                        schoollife.group3?.let { groupsList.add(it) }
                        schoollife.group4?.let { groupsList.add(it) }
                        schoollife.group5?.let { groupsList.add(it) }
                        schoollife.group6?.let { groupsList.add(it) }
                        schoollife.group7?.let { groupsList.add(it) }
                        schoollife.group8?.let { groupsList.add(it) }
                        schoollife.group9?.let { groupsList.add(it) }
                        schoollife.group10?.let { groupsList.add(it) }
                        schoollife.group11?.let { groupsList.add(it) }
                        schoollife.group12?.let { groupsList.add(it) }
                        schoollife.group13?.let { groupsList.add(it) }
                        schoollife.group14?.let { groupsList.add(it) }
                        schoollife.group15?.let { groupsList.add(it) }
                        schoollife.group16?.let { groupsList.add(it) }
                        schoollife.group17?.let { groupsList.add(it) }
                        schoollife.group18?.let { groupsList.add(it) }
                        schoollife.group19?.let { groupsList.add(it) }
//                        schoollife.group20?.let { groupsList.add(it) }
                        // Repeat for all groups in the school category
                    }

                    template?.summer?.let { summer ->
                        summer.group1?.let { groupsList.add(it) }
                        summer.group2?.let { groupsList.add(it) }
                        summer.group3?.let { groupsList.add(it) }
                        summer.group4?.let { groupsList.add(it) }
                        summer.group5?.let { groupsList.add(it) }
                        summer.group6?.let { groupsList.add(it) }
                        summer.group7?.let { groupsList.add(it) }
                        summer.group8?.let { groupsList.add(it) }
                        summer.group9?.let { groupsList.add(it) }
                        summer.group10?.let { groupsList.add(it) }
                        summer.group11?.let { groupsList.add(it) }
                        summer.group12?.let { groupsList.add(it) }
                        summer.group13?.let { groupsList.add(it) }
                        summer.group14?.let { groupsList.add(it) }
                        summer.group15?.let { groupsList.add(it) }
                        summer.group16?.let { groupsList.add(it) }
                        summer.group17?.let { groupsList.add(it) }
                        summer.group18?.let { groupsList.add(it) }
                        summer.group19?.let { groupsList.add(it) }
//                        summer.group20?.let { groupsList.add(it) }
                        // Repeat for all groups in the summer category
                    }

                    template?.travel?.let { travel ->
                        travel.group1?.let { groupsList.add(it) }
                        travel.group2?.let { groupsList.add(it) }
                        travel.group3?.let { groupsList.add(it) }
                        travel.group4?.let { groupsList.add(it) }
                        travel.group5?.let { groupsList.add(it) }
                        travel.group6?.let { groupsList.add(it) }
                        travel.group7?.let { groupsList.add(it) }
                        travel.group8?.let { groupsList.add(it) }
                        travel.group9?.let { groupsList.add(it) }
                        travel.group10?.let { groupsList.add(it) }
                        travel.group11?.let { groupsList.add(it) }
                        travel.group12?.let { groupsList.add(it) }
                        travel.group13?.let { groupsList.add(it) }
                        travel.group14?.let { groupsList.add(it) }
                        travel.group15?.let { groupsList.add(it) }
                        travel.group16?.let { groupsList.add(it) }
                        travel.group17?.let { groupsList.add(it) }
                        travel.group18?.let { groupsList.add(it) }
                        travel.group19?.let { groupsList.add(it) }
                        travel.group20?.let { groupsList.add(it) }
                        // Repeat for all groups in the lgStory category
                    }

                    template?.winter?.let { winter ->
                        winter.group1?.let { groupsList.add(it) }
                        winter.group2?.let { groupsList.add(it) }
                        winter.group3?.let { groupsList.add(it) }
                        winter.group4?.let { groupsList.add(it) }
                        winter.group5?.let { groupsList.add(it) }
                        winter.group6?.let { groupsList.add(it) }
                        winter.group7?.let { groupsList.add(it) }
                        winter.group8?.let { groupsList.add(it) }
                        winter.group9?.let { groupsList.add(it) }
                        winter.group10?.let { groupsList.add(it) }
                        winter.group11?.let { groupsList.add(it) }
                        winter.group12?.let { groupsList.add(it) }
                        winter.group13?.let { groupsList.add(it) }
                        winter.group14?.let { groupsList.add(it) }
                        winter.group15?.let { groupsList.add(it) }
                        winter.group16?.let { groupsList.add(it) }
                        winter.group17?.let { groupsList.add(it) }
                        winter.group18?.let { groupsList.add(it) }
                        winter.group19?.let { groupsList.add(it) }
                        winter.group20?.let { groupsList.add(it) }
                        // Repeat for all groups in the selfie category
                    }
                }

                requireActivity().runOnUiThread {
                    adapter = CollageTemplateAdapter(requireContext(), groupsList) /*{ selectedGroup ->
                        showBottomSheetForImageEditurl()
                    }*/
                    binding.recyclerView.adapter = adapter
                }
            }
        }
    }




    @SuppressLint("MissingInflatedId")
    fun showBottomSheetForImageEditurl() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
        val view = layoutInflater.inflate(R.layout.camera_dialog, null)
        view.findViewById<ImageView>(R.id.imgCross)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        // Set layout manager with 3 columns
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        // Fetch images using AsyncTask and populate the RecyclerView
        TemplatesActivity.FetchImagesTasks(requireContext()) { imageList ->
            recyclerView.adapter = ImageAdapter(imageList) { selectedImageUri ->
                bottomSheetDialog.dismiss()
                val intent = Intent(requireContext(), EditActivity::class.java)
                intent.putExtra("selected_image_uri", selectedImageUri)
                startActivity(intent)
            }

            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.setOnShowListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bottomSheetDialog.window?.navigationBarColor =
                        ContextCompat.getColor(requireContext(), R.color.black)
                }
            }

            bottomSheetDialog.show()
        }.execute()
    }
}