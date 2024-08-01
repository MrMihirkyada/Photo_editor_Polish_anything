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
import com.example.photoeditorpolishanything.databinding.FragmentChristmasBinding

class FamilyFragment : Fragment() {

    private lateinit var binding: FragmentChristmasBinding
    lateinit var adapter: CollageTemplateAdapter
//    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentChristmasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView()
    {
        binding.recyclerView.layoutManager = GridLayoutManager(TemplatesActivity.contexts, 2)

        val url = "https://s3.eu-north-1.amazonaws.com/photoeditorbeautycamera.com/photoeditor/templates.json"

        OkHttpHelper.fetchTemplates(url) { categorysApi ->
            categorysApi?.let {
                val groupsList = mutableListOf<Group>()

                it.templates.forEach { template ->
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
}