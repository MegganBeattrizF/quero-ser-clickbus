@file:Suppress("SENSELESS_COMPARISON")

package com.clickbus.moviesdbtest.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.clickbus.moviesdbtest.R
import com.clickbus.moviesdbtest.movies.models.Cast
import de.hdodenhof.circleimageview.CircleImageView

class CastAdapter (
    private var cast: List<Cast>
) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycler_item_actors, parent, false)
        return CastViewHolder(view)
    }

    override fun getItemCount(): Int = cast.size

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(cast[position])
    }

    fun updateMovies(casts: List<Cast>) {
        this.cast = casts
        notifyDataSetChanged()
    }

    inner class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val actorimage: CircleImageView = itemView.findViewById(R.id.actor_image)

        fun bind(cast: Cast) {

            if (cast.profile_path == null){
                Glide.with(itemView)
                    .load(R.drawable.ic_no_perfil)
                    .transform(CenterCrop())
                    .into(actorimage)
            }else{

                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w342${cast.profile_path}")
                    .transform(CenterCrop())
                    .into(actorimage)
            }
        }
    }
}