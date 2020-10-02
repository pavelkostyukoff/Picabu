
package com.poul.android.pikabu.presentation.fragment.postdetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.onNavDestinationSelected
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.poul.android.pikabu.R
import kotlinx.android.synthetic.main.fragment.*

class PostFragment : Fragment() {

  private val args: PostFragmentArgs by navArgs()
  private val viewModel: PicabuViewModel by viewModels { PicabuViewModelFactory }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    setHasOptionsMenu(true)

    return inflater.inflate(R.layout.fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val (picture,title, body, isFavorite) = args

    setSharedElementTransitionOnEnter()
    postponeEnterTransition()
    setupFavoriteButton(picture, isFavorite)
      setText(title,body)

    image_view_full_screen.apply {
      transitionName = picture
      startEnterTransitionAfterLoadingImage(picture, this)
    }
      container.apply {
      transitionName = picture
          startEnterTransitionAfterLoadingConst(picture, this)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.menu_about, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(item)
  }

  private fun setupFavoriteButton(picture: String, pictureIsFavorite: Boolean) {
    updateButtonBackground(pictureIsFavorite)
    button_favorite.isChecked = pictureIsFavorite
    button_favorite.setOnCheckedChangeListener { _, isChecked ->
      viewModel.updatePostFavoriteStatus(picture, isChecked)
      updateButtonBackground(isChecked)
    }
  }

    private fun setText(title: String, body: String) {
        subcriprion.text = body
        titlePost.text = title
    }

  private fun updateButtonBackground(pictureIsFavorite: Boolean) {
    val buttonImageResource: Int = if (pictureIsFavorite) {
      R.drawable.ic_star_full_42dp
    } else {
      R.drawable.ic_star_border_42dp
    }

    button_favorite.background = resources.getDrawable(buttonImageResource, null)
  }

  private fun setSharedElementTransitionOnEnter() {
    sharedElementEnterTransition = TransitionInflater.from(context)
        .inflateTransition(R.transition.shared_element_transition)
  }

  private fun startEnterTransitionAfterLoadingImage(imageAddress: String, imageView: ImageView) {
    Glide.with(this)
        .load(imageAddress)
        .placeholder(R.drawable.corner_background)
        .error(R.drawable.splash_background)
        .dontAnimate()
        .listener(object : RequestListener<Drawable> {
          override fun onLoadFailed(
              e: GlideException?,
              model: Any?,
              target: Target<Drawable>?,
              isFirstResource: Boolean
          ): Boolean {
            startPostponedEnterTransition()
            return false
          }

          override fun onResourceReady(
              resource: Drawable,
              model: Any,
              target: Target<Drawable>,
              dataSource: DataSource,
              isFirstResource: Boolean
          ): Boolean {
            startPostponedEnterTransition()
            return false
          }
        })
        .into(imageView)
  }

    private fun startEnterTransitionAfterLoadingConst(imageAddress: String, imageView: ConstraintLayout) {
        startPostponedEnterTransition()
    }
}