package com.example.bitbnsclone.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import androidx.viewpager2.widget.ViewPager2.VISIBLE
import com.example.bitbnsclone.R
import com.example.bitbnsclone.adapters.TopLossGainPagerAdapter
import com.example.bitbnsclone.adapters.TopMarketAdapter
import com.example.bitbnsclone.api.ApiInterface
import com.example.bitbnsclone.api.ApiUtils
import com.example.bitbnsclone.databinding.ActivityMainBinding
import com.example.bitbnsclone.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        setTabLayout()
        getTopCurrencyList()


        return binding.root
    }

    private fun setTabLayout() {
        val adapter = TopLossGainPagerAdapter(this)
        binding.contentViewPager.adapter = adapter

        binding.contentViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                if(position==0)
                {
                    binding.topGainIndicator.visibility = VISIBLE
                    binding.topLoseIndicator.visibility = GONE
                }
                else
                {
                    binding.topGainIndicator.visibility = GONE
                    binding.topLoseIndicator.visibility = VISIBLE
                }
            }
        })
        TabLayoutMediator(binding.tabLayout,binding.contentViewPager){
            tab, position ->
            var title = if(position == 0)
            {
                "Top Gainers"
            }
            else{
                "Top Losers"
            }
            tab.text = title
        }.attach()
    }

    private fun getTopCurrencyList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = ApiUtils.getInstance().create(ApiInterface::class.java).getMarketData()

            withContext(Dispatchers.Main) {
                binding.topCurrencyRecyclerView.adapter =
                    TopMarketAdapter(requireContext(), res.body()!!.data.cryptoCurrencyList)
            }

            Log.d("result", "${res.body()?.data?.cryptoCurrencyList}")
        }
    }

}