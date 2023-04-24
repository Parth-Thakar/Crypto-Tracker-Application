package com.example.bitbnsclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bitbnsclone.R
import com.example.bitbnsclone.databinding.CurrencyItemLayoutBinding
import com.example.bitbnsclone.fragments.HomeFragmentDirections
import com.example.bitbnsclone.fragments.MarketFragmentDirections
import com.example.bitbnsclone.fragments.WatchlistFragment
import com.example.bitbnsclone.fragments.WatchlistFragmentDirections
import com.example.bitbnsclone.models.CryptoCurrency

class MarketAdapter(var context: Context, var list: List<CryptoCurrency>, var fragType: String) : RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        return MarketViewHolder(LayoutInflater.from(context).inflate(R.layout.currency_item_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        val item = list[position]

        holder.binding.currencyNameTextView.text = item.name
        holder.binding.currencySymbolTextView.text = item.symbol
        Glide.with(context).load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png").thumbnail(
            Glide.with(context).load(R.drawable.spinner)).into(holder.binding.currencyImageView)

        //holder.binding.currencyPriceTextView.text = item.quotes[0].price.toString()
        holder.binding.currencyPriceTextView.text = "${String.format("$%.02f",item.quotes[0].price)}"

        if(item.quotes!![0].percentChange24h>0)
        {
            holder.binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.green))
            holder.binding.currencyChangeTextView.text = "+ ${String.format("%.02f",item.quotes[0].percentChange24h)}%"
        }
        else
        {
            holder.binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.red))
            holder.binding.currencyChangeTextView.text ="${String.format("%.02f",item.quotes[0].percentChange24h)}%"
        }

        Glide.with(context).load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + item.id + ".png").thumbnail(
            Glide.with(context).load(R.drawable.spinner)).into(holder.binding.currencyChartImageView)

        holder.itemView.setOnClickListener {

            if(fragType == "home"){
                findNavController(it).navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item)
                )
            }

            if(fragType == "market"){
                findNavController(it).navigate(
                    MarketFragmentDirections.actionMarketFragmentToDetailsFragment(item)
                )
            }

            if(fragType == "watchlist"){
                findNavController(it).navigate(
                   WatchlistFragmentDirections.actionWatchlistFragmentToDetailsFragment(item)
                )
            }


        }

    }

      fun updateDataList(dataItem : List<CryptoCurrency>){
        list = dataItem
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MarketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = CurrencyItemLayoutBinding.bind(view)
    }

}