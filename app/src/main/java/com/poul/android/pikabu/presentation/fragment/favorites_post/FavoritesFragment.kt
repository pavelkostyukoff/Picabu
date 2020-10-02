
package com.poul.android.pikabu.presentation.fragment.favorites_post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.poul.android.pikabu.R
import com.poul.android.pikabu.presentation.adapter.PostAdapter
import com.poul.android.pikabu.presentation.fragment.post.PostListFragmentDirections
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_favorites.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class FavoritesFragment : Fragment() {
  private val viewModel: FavoritesViewModel by viewModels { FavoritesViewModelFactory }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_favorites, container, false)
    val adapter = createAdapter()

    setupRecyclerView(view, adapter)
    observeViewModel(adapter)

    return view
  }

  private fun createAdapter(): PostAdapter {
    return PostAdapter { view, it ->
      val extraInfoForSharedElement = FragmentNavigatorExtras(
        (view to it.title) as Pair<View, String>
      )
      val toPostFragment =
        it.picture?.let { pictue ->
          it.title?.let { title ->
            val toPostFragment = it.body?.let {
                body ->
              PostListFragmentDirections.toPostFragment(
                pictue, title, body, it.isFavourite
              )
            }
          toPostFragment
        } }

      if (toPostFragment != null) {
        navigate(toPostFragment, extraInfoForSharedElement)
      }
    }
  }

  private fun setupRecyclerView(view: View, postAdapter: PostAdapter) {
    view.recycler_view_favorite.run {
      adapter = postAdapter
      setHasFixedSize(true)
    }
  }

  private fun observeViewModel(postAdapter: PostAdapter) {
    viewModel.favoritePosts.observe(viewLifecycleOwner) {
      postAdapter.submitList(it)

      if (it.isEmpty()) {
        text_view_no_favorites.visibility = View.VISIBLE
      } else {
        text_view_no_favorites.visibility = View.INVISIBLE
      }
    }
  }


  private fun setExitToFullScreenTransition() {
    exitTransition =
      TransitionInflater.from(context).inflateTransition(R.transition.list_exit_transition)
  }

  private fun setReturnFromFullScreenTransition() {
    reenterTransition =
      TransitionInflater.from(context).inflateTransition(R.transition.list_return_transition)
  }

  private fun navigate(destination: NavDirections, extraInfo: FragmentNavigator.Extras) = with(findNavController()) {
    currentDestination?.getAction(destination.actionId)
      ?.let { navigate(destination, extraInfo) }
  }
}