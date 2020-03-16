package com.carrati.chucknorrisfacts.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carrati.chucknorrisfacts.R
import com.carrati.chucknorrisfacts.domain.entities.Fact
import com.carrati.chucknorrisfacts.presentation.extension.inflate
import com.carrati.chucknorrisfacts.utils.CardTextEditor
import com.carrati.chucknorrisfacts.utils.ShareHandler
import kotlinx.android.synthetic.main.adapter_main.view.*
import kotlinx.android.synthetic.main.item_category.view.*

class FactsAdapter(): RecyclerView.Adapter<FactsAdapter.ViewHolder>() {

    var facts: MutableList<Fact> = mutableListOf()

    inner class ViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.adapter_main)) {

        fun bind(fact: Fact) = with(itemView) {

            tvFact.text = fact.value
            tvFact.textSize = CardTextEditor().getCardTextSize(fact.value)

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
                ShareHandler().share(fact, context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent)
    override fun getItemCount(): Int = facts.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(facts[position])
}