package com.example.bitbnsclone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.bitbnsclone.adapters.MarketAdapter
import com.example.bitbnsclone.api.ApiInterface
import com.example.bitbnsclone.api.ApiUtils
import com.example.bitbnsclone.databinding.FragmentTopLossGainBinding
import com.example.bitbnsclone.models.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class TopLossGainFragment : Fragment() {

    lateinit var binding: FragmentTopLossGainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopLossGainBinding.inflate(layoutInflater)

        getMarketData()


        return binding.root

    }

    private fun getMarketData() {
        val position = requireArguments().getInt("position")
        lifecycleScope.launch(Dispatchers.IO) {
            val result = ApiUtils.getInstance().create(ApiInterface::class.java).getMarketData()

            if (result.body() != null) {
                withContext(Dispatchers.Main) {
                    val dataItem = result.body()!!.data.cryptoCurrencyList

                    Collections.sort(dataItem) { o1, o2 ->
                        (o2.quotes[0].percentChange24h.toInt())
                            .compareTo(o1.quotes[0].percentChange24h.toInt())
                    }

                    binding.spinKitView.visibility = GONE

                    val list = ArrayList<CryptoCurrency>()

                    if (position == 0) {
                        list.clear()
                        for (i in 0..9) {
                            list.add(dataItem[i])
                        }

                        binding.topGainLoseRecyclerView.adapter =
                            MarketAdapter(requireContext(), list, "home")

                    } else {
                        list.clear()
                        for (i in 0..9) {
                            list.add(dataItem[dataItem.size - 1 - i])
                        }

                        binding.topGainLoseRecyclerView.adapter =
                            MarketAdapter(requireContext(), list, "home")
                    }

                }
            }
        }


    }


}