package com.assignment.kotlearn.kotlearn.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.assignment.kotlearn.kotlearn.R
import com.bumptech.glide.load.engine.Resource
import com.github.florent37.materialviewpager.header.MaterialViewPagerImageHelper.setImageDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.assignment.kotlearn.kotlearn.R.id.imageView

class TutorialListAdapter(private val chapterRecords: List<TutorialRecord>) : RecyclerView.Adapter<TutorialsViewHolder>() {

    private var mContext: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialsViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.tutorial_button, parent, false)
        mContext=parent.context
        return TutorialsViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: TutorialsViewHolder, position: Int) {
        if (position < chapterRecords.size) {
            val product = chapterRecords[position]
            holder.productTitle.text = product.title
            //change price to rating
            holder.productPrice.text = product.rating


            val image = mContext!!.resources.getIdentifier(product.image, "drawable", mContext!!.getPackageName())
            holder.productImage.setImageResource(image)

            /*if(product.completed) {
                holder.completed.setCardBackgroundColor(Color.BLUE);
            }*/
            holder.buttonTutorialView.setOnClickListener{

                val intent = Intent(mContext, ChapterActivity::class.java)
                intent.putExtra("Chapter", product.title)
                mContext!!.startActivity(intent)
            }

           // ImageRequester.setImageFromUrl(holder.productImage, product.url)
        }
    }

    override fun getItemCount(): Int {
        return chapterRecords.size
    }
}