package com.carrati.chucknorrisfacts.presentation.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.carrati.chucknorrisfacts.R
import com.carrati.chucknorrisfacts.domain.entities.Fact
import com.carrati.chucknorrisfacts.presentation.extension.inflate
import kotlinx.android.synthetic.main.adapter_main.view.*
import kotlinx.android.synthetic.main.item_category.view.*

class FactsAdapter(): RecyclerView.Adapter<FactsAdapter.ViewHolder>() {

    var facts: MutableList<Fact> = mutableListOf()

    inner class ViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.adapter_main)) {

        fun bind(fact: Fact) = with(itemView) {

            tvFact.text = fact.value
            if(fact.value.length >= 80) tvFact.textSize = 22F
            else tvFact.textSize = 28F

            var text = "uncategorized"
            llCategories.removeAllViews()
            if(fact.categories.isEmpty()){
                val view = LayoutInflater.from(context).inflate(R.layout.item_category, null)
                view.tvFactCategory.text = text
                llCategories.addView(view)
            } else {
                for (i in 0..fact.categories.size - 1) {
                    val view = LayoutInflater.from(context).inflate(R.layout.item_category, null)
                    if (fact.categories[i].isNotBlank()) text = fact.categories[i]
                    view.tvFactCategory.text = text
                    llCategories.addView(view)
                }
            }

            btShareFact.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Ei, você sabia?\n\"${fact.value}\"\n${fact.url}")
                    putExtra(Intent.EXTRA_SUBJECT, "Share a fact")
                }
                val shareIntent = Intent.createChooser(sendIntent, "Share using:")
                context.startActivity(shareIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent)
    override fun getItemCount(): Int = facts.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(facts[position])
}