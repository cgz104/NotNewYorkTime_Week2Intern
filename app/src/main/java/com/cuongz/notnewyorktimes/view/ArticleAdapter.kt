package com.cuongz.notnewyorktimes.view

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cuongz.notnewyorktimes.R
import com.cuongz.notnewyorktimes.model.Doc

class ArticleAdapter(private var listDocs: List<Doc>): RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.item_news, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listDocs.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(listDocs[p1])
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var title = itemView.findViewById(R.id.title) as TextView
        private var newsImg = itemView.findViewById(R.id.newsImg) as ImageView
        private var btn_share = itemView.findViewById(R.id.btn_share) as Button

        fun bind(doc: Doc) {
            title.text = doc.headline?.main

            val multimed = doc.multimedia

            if (multimed != null) {
                if (multimed.isNotEmpty()) {
                    val mURL = multimed[0].url
                    if (mURL != null) {
                        Glide.with(itemView)
                            .load("https://static01.nyt.com/${multimed[0].url}")
                            .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
//                            .thumbnail(Glide.with(newsImg).load(R.drawable.icon_load))
                            .into(newsImg)
                    }

                } else {
                    newsImg.visibility = View.GONE
                    itemView.layoutParams.height = 420
                }
            }

            itemView.setOnClickListener { v ->
                val intent = Intent(v?.context, DetailActivity::class.java)
                intent.putExtra("url", doc.webUrl)
                v?.context?.startActivity(intent)

            }

            btn_share.setOnClickListener {v ->
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL")
                intent.putExtra(Intent.EXTRA_TEXT, doc.webUrl)
                v?.context?.startActivity(Intent.createChooser(intent, "Share URL"))
            }
        }
    }
}