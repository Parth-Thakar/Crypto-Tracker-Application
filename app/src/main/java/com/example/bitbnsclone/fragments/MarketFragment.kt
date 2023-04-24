package com.example.bitbnsclone.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.bitbnsclone.adapters.MarketAdapter
import com.example.bitbnsclone.api.ApiInterface
import com.example.bitbnsclone.api.ApiUtils
import com.example.bitbnsclone.databinding.FragmentMarketBinding
import com.example.bitbnsclone.models.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MarketFragment : Fragment() {

    private lateinit var binding: FragmentMarketBinding
    private lateinit var list : List<CryptoCurrency>
    private lateinit var adapter: MarketAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMarketBinding.inflate(layoutInflater)

        list = listOf()

        adapter = MarketAdapter(requireContext(),list,"market")
        binding.currencyRecyclerView.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO){
            val result = ApiUtils.getInstance().create(ApiInterface::class.java).getMarketData()

            if(result.body()!=null){
                withContext(Dispatchers.Main){
                    list = result.body()!!.data.cryptoCurrencyList
                    adapter.updateDataList(list)
                    binding.spinKitView.visibility = GONE
                }
            }
        }

        searchCoin()

        return binding.root
    }

    private lateinit var searchText : String

    private fun searchCoin() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                searchText = s.toString().toLowerCase()

                updateRecyclerView()

            }
        })
    }

    private fun updateRecyclerView() {
        val data = ArrayList<CryptoCurrency>()
        for(item in list){
            var coinName = item.name.toLowerCase()
            val coinSymbol = item.symbol.toLowerCase()

            if(coinName.contains(searchText) || coinSymbol.contains(searchText)){
                data.add(item)
            }
        }
        adapter.updateDataList(data)
    }


}