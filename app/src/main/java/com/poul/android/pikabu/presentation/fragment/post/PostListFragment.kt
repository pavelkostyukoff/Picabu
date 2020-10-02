
package com.poul.android.pikabu.presentation.fragment.post

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.poul.android.pikabu.R
import com.poul.android.pikabu.presentation.adapter.PostAdapter
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class PostListFragment : Fragment() {

  private val viewModel: PostListViewModel by viewModels { PostListViewModelFactory }
  private var isLoadingMoreItems = false

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_list, container, false)
    val adapter = createAdapter()

    setupRecyclerView(view, adapter)
    observeViewModel(adapter)

    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setExitToFullScreenTransition()
    setReturnFromFullScreenTransition()
  }

  private fun createAdapter(): PostAdapter {
    return PostAdapter { view, it ->
      val extraInfoForSharedElement = FragmentNavigatorExtras(
        (view to it.picture!!)
      )
     /* val toPostFragment =
        it.picture?.let { picture -> it.title?.let {
            title ->
          val toPostFragment = it.body?.let {
              body ->
            PostListFragmentDirections.toPostFragment(
              picture, title, body, it.isFavourite
            )
          }
          toPostFragment
        } }

      if (toPostFragment != null) {
        navigate(toPostFragment, extraInfoForSharedElement)
      }*/

      val toPostFragment =
        PostListFragmentDirections.toPostFragment(it.picture, it.title!!, it.body!!, it.isFavourite)
      navigate(toPostFragment, extraInfoForSharedElement)
    }
  }

  private fun setupRecyclerView(view: View, postAdapter: PostAdapter) {
    view.recycler_view.run {
      adapter = postAdapter

      setHasFixedSize(true)
      addOnScrollListener(createInfiniteScrollListener(layoutManager as GridLayoutManager))

      postponeEnterTransition()
      viewTreeObserver.addOnPreDrawListener {
        startPostponedEnterTransition()
        true
      }
    }
  }

  private fun createInfiniteScrollListener(
      gridLayoutManager: GridLayoutManager
  ): RecyclerView.OnScrollListener {
    return object : InfiniteScrollListener(gridLayoutManager, PostListViewModel.PAGE_SIZE) {
      override fun loadMoreItems() {
        isLoadingMoreItems = true
        viewModel.getMorePost()
      }

      override fun isLoading(): Boolean = isLoadingMoreItems
    }
  }

  private fun observeViewModel(postAdapter: PostAdapter) {
    viewModel.postList.observe(viewLifecycleOwner) {
      postAdapter.submitList(it)
      isLoadingMoreItems = false
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