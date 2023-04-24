package com.example.bitbnsclone.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.bitbnsclone.R
import com.example.bitbnsclone.databinding.FragmentDetailsBinding
import com.example.bitbnsclone.models.CryptoCurrency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val item: DetailsFragmentArgs by navArgs()

    private lateinit var oneMonth: AppCompatButton
    private lateinit var oneWeek: AppCompatButton
    private lateinit var oneDay: AppCompatButton
    private lateinit var fourHour: AppCompatButton
    private lateinit var oneHour: AppCompatButton
    private lateinit var fifteenMin: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater)

        val data: CryptoCurrency = item.args!!

        setUpDetails(data)



        oneMonth = binding.button
        oneWeek = binding.button1
        oneDay = binding.button2
        fourHour = binding.button3
        oneHour = binding.button4
        fifteenMin = binding.button5

        loadChart(data)

        binding.coinName.text = data.name
        binding.coinRank.text = data.cmcRank.toString()
        binding.coinMaxSupply.text = data.maxSupply.toString()
        binding.totalSupply.text = data.totalSupply.toString()
        if (data.isAudited) {
            binding.coinAudit.text = "YES"
        } else {
            binding.coinAudit.text = "NO"
        }
        binding.coinActiveStatus.text = data.isActive.toString()
        binding.coinCirculatingSupply.text = data.circulatingSupply.toString()
        binding.coinDate.text = data.dateAdded
        binding.coinTags.text = data.tags[0] + ", " + data.tags[1] + ", " + data.tags[2]




        oneMonth.setOnClickListener {
            it.setBackgroundResource(R.drawable.active_button)
            setChart(data, "M")
        }
        oneWeek.setOnClickListener {
            it.setBackgroundResource(R.drawable.active_button)
            setChart(data, "W")
        }
        oneDay.setOnClickListener {
            it.setBackgroundResource(R.drawable.active_button)
            setChart(data, "D")
        }
        fourHour.setOnClickListener {
            it.setBackgroundResource(R.drawable.active_button)
            setChart(data, "4H")
        }
        oneHour.setOnClickListener {
            it.setBackgroundResource(R.drawable.active_button)
            setChart(data, "1H")
        }
        fifteenMin.setOnClickListener {
            it.setBackgroundResource(R.drawable.active_button)
            setChart(data, "15")
        }


        addToWatchList(data)




        return binding.root
    }

    var watchList: ArrayList<String>? = null
    var isCheck = false

    private fun addToWatchList(data: CryptoCurrency) {
        readData()
        isCheck = if (watchList!!.contains(data.symbol)) {
            binding.addWatchlistButton.setImageResource(R.drawable.ic_star)
            true
        } else {
            binding.addWatchlistButton.setImageResource(R.drawable.ic_star_outline)
            false
        }

        binding.addWatchlistButton.setOnClickListener {
            isCheck = if (!isCheck) {
                if (!watchList!!.contains(data.symbol)) {
                    watchList!!.add(data.symbol)
                }
                storeData()
                binding.addWatchlistButton.setImageResource(R.drawable.ic_star)
                true
            } else {
                binding.addWatchlistButton.setImageResource(R.drawable.ic_star_outline)
                watchList!!.remove(data.symbol)
                storeData()
                false
            }
        }

    }

    private fun readData() {
        val sharedPreferences =
            requireContext().getSharedPreferences("watchlist", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("watchlist", ArrayList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>() {}.type
        watchList = gson.fromJson(json, type)
    }

    private fun storeData() {
        val sharedPreferences =
            requireContext().getSharedPreferences("watchlist", Context.MODE_PRIVATE)
        val gson = Gson()
        val editor = sharedPreferences.edit()
        val json = gson.toJson(watchList)
        editor.putString("watchlist", json)
        editor.apply()
        Log.e("added", watchList.toString())
    }

    private fun setChart(data: CryptoCurrency, s: String) {
        when (s) {
            "M" -> {
                fifteenMin.background = null
                oneWeek.background = null
                oneDay.background = null
                fourHour.background = null
                oneHour.background = null
            }

            "W" -> {
                fifteenMin.background = null
                oneMonth.background = null
                oneDay.background = null
                fourHour.background = null
                oneHour.background = null
            }

            "D" -> {
                fifteenMin.background = null
                oneMonth.background = null
                oneWeek.background = null
                fourHour.background = null
                oneHour.background = null
            }

            "1H" -> {
                fifteenMin.background = null
                oneMonth.background = null
                oneWeek.background = null
                fourHour.background = null
                oneDay.background = null
            }

            "4H" -> {
                fifteenMin.background = null
                oneMonth.background = null
                oneWeek.background = null
                oneHour.background = null
                oneDay.background = null
            }

            "15" -> {
                oneMonth.background = null
                oneWeek.background = null
                oneDay.background = null
                fourHour.background = null
                oneHour.background = null
            }
        }
        binding.detaillChartWebView.settings.javaScriptEnabled = true
        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        binding.detaillChartWebView.loadUrl(
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" + data.symbol
                    + "USD&interval=" + s + "&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        )
    }

    private fun loadChart(data: CryptoCurrency) {
        oneMonth.background = null
        oneWeek.background = null
        fifteenMin.background = null
        fourHour.background = null
        oneHour.background = null

        oneDay.setBackgroundResource(R.drawable.active_button)

        binding.detaillChartWebView.settings.javaScriptEnabled = true
        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        binding.detaillChartWebView.loadUrl(
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" + data.symbol
                .toString() + "USD&interval=" + "D" + "&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        )
    }

    private fun setUpDetails(data: CryptoCurrency) {
        binding.detailSymbolTextView.text = data.symbol

        Glide.with(requireContext())
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + data.id + ".png")
            .thumbnail(
                Glide.with(requireContext()).load(R.drawable.spinner)
            ).into(binding.detailImageView)

        binding.detailPriceTextView.text = "$ ${String.format("%.03f", data.quotes[0].price)}"

        if (data.quotes[0].percentChange24h > 0) {
            binding.detailChangeTextView.setTextColor(requireContext().resources.getColor(R.color.green))
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_up)
            binding.detailChangeTextView.text =
                "+ ${String.format("%.02f", data.quotes[0].percentChange24h)}%"
        } else {
            binding.detailChangeTextView.setTextColor(requireContext().resources.getColor(R.color.red))
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_down)
            binding.detailChangeTextView.text =
                "${String.format("%.02f", data.quotes[0].percentChange24h)}%"
        }

    }


}