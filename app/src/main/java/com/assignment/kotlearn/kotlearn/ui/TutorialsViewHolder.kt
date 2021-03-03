package com.assignment.kotlearn.kotlearn.ui

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.assignment.kotlearn.kotlearn.R

class TutorialsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var productImage: ImageView = itemView.findViewById(R.id.imageView)
        var productTitle: TextView = itemView.findViewById(R.id.product_title)
        var productPrice: TextView = itemView.findViewById(R.id.product_price)
        var completed: CardView = itemView.findViewById(R.id.tickCardView)

        var buttonTutorialView: CardView= itemView.findViewById(R.id.placeCard)
    }
