
package com.poul.android.pikabu.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poul.android.pikabu.R.layout
import com.poul.android.pikabu.domain.Post
import com.poul.android.pikabu.presentation.adapter.PostAdapter.PostViewHolder
import com.poul.android.pikabu.presentation.fragment.extensions.load
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recycler_view_item.*

class PostAdapter(
    private val onPostClickListener: ((view: View, post: Post) -> Unit)? = null
) : ListAdapter<Post, PostViewHolder>(ITEM_COMPARATOR) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
    val inflater = LayoutInflater.from(parent.context)

    return PostViewHolder(
        inflater.inflate(
            layout.recycler_view_item,
            parent,
            false
        )
    )
  }

  override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
    val item: Post = getItem(position)
    holder.bind(item, onPostClickListener)
  }

  // Need to implement LayoutContainer so that views are cached correctly
  class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
      LayoutContainer {

    override val containerView: View?
      get() = itemView

    fun bind(
        item: Post,
        onPostClickListener: ((view: View, post: Post) -> Unit)?
    ) {
        with(container) {
          // text = item.title
            setOnClickListener {
                onPostClickListener?.let {
                    it(this, item)
                }
            }
        }

        with(container)
        {
            /*setOnClickListener {
                onPostClickListener?.let {
                    it(this, item)
                }
            }*/
        }
      with(image_view) {
        item.picture?.let {
            load(it) {
                onPostClickListener?.let {
                it(this, item)
                }
            }
        }
        transitionName = item.picture
      }
    }
  }
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Post>() {
  override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
    return oldItem.picture == newItem.picture
  }

  override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
    return oldItem == newItem
  }
}