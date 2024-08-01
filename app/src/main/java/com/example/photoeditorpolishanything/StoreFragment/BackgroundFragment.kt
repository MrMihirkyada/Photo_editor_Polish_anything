package com.example.photoeditorpolishanything.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.photoeditorpolishanything.Adapter.StickerAdapter
import com.example.photoeditorpolishanything.Adapter.Store_Background_Adaper
import com.example.photoeditorpolishanything.databinding.FragmentBackgroundBinding
import com.google.android.material.tabs.TabLayout

class BackgroundFragment : Fragment() {

    private lateinit var binding: FragmentBackgroundBinding
    lateinit var adapter: StickerAdapter
//    lateinit var recyclerView: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBackgroundBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView()
    {
          binding.tabLayout.addTab(binding.tabLayout.newTab().setText("All"))
          binding.tabLayout.addTab(binding.tabLayout.newTab().setText("birthday"))
////        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Calendar"))
////        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Christmas"))
////        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Family"))
////        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Frame"))
////        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Graduation"))
////        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Love"))
////        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Motherday"))
////        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Newyear"))
////        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Pride"))
////        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Schoollife"))
////        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Summer"))
////        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Travel"))
////        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Winter"))

        val Myadapter = Store_Background_Adaper(childFragmentManager)
        binding.viewpager.adapter = Myadapter
//
        binding.viewpager.addOnPageChangeListener(object : TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout) {})

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null)
                {
                    binding.viewpager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?)
            {

            }

            override fun onTabReselected(tab: TabLayout.Tab?)
            {

            }
        })

        for (i in 0 until binding.tabLayout.tabCount) {
            val tab = (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(i) as ViewGroup
            val layoutParams = tab.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(15, 0, 15, 0) // Adjust margins as needed
            tab.requestLayout()
        }
    }
}