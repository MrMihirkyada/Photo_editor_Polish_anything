package com.example.photoeditorpolishanything.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photoeditorpolishanything.Adapter.CollageTemplateAdapter
import com.example.photoeditorpolishanything.Api.Group
import com.example.photoeditorpolishanything.Api.OkHttpHelper
import com.example.photoeditorpolishanything.TemplatesActivity
import com.example.photoeditorpolishanything.databinding.FragmentGraduationBinding

class GraduationFragment : Fragment() {

    private lateinit var binding: FragmentGraduationBinding
    lateinit var adapter: CollageTemplateAdapter
//    lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGraduationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.recyclerView.layoutManager = GridLayoutManager(TemplatesActivity.contexts, 2)

        val url =
            "https://s3.eu-north-1.amazonaws.com/photoeditorbeautycamera.com/photoeditor/templates.json"

        OkHttpHelper.fetchTemplates(url) { categorysApi ->
            categorysApi?.let {
                val groupsList = mutableListOf<Group>()

                it.templates.forEach { template ->
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


                        requireActivity().runOnUiThread {
                            adapter = CollageTemplateAdapter(requireContext(), groupsList) /*{ selectedGroup ->
                        showBottomSheetForImageEditurl()
                    }*/
                            binding.recyclerView.adapter = adapter
                        }
                    }
                }
            }
        }
    }
}