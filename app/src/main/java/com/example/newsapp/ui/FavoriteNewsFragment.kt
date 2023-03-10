package com.example.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapter.Listener
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.data.Article
import com.example.newsapp.databinding.FragmentFavoriteNewsBinding
import com.example.newsapp.viewmodels.BreakingNewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteNewsFragment : Fragment(), Listener {

    private var _binding: FragmentFavoriteNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BreakingNewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        setupObservers()
        onSwipeDeleteArticle(view)

    }

    private fun setUpRecycler() {
        binding.favoriteNewsRV.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.favoriteNewsRV.adapter = NewsAdapter(requireContext(), this)
    }

    private fun setupObservers(){
        viewModel.getSavedNews().observe(viewLifecycleOwner){ articles ->
            (binding.favoriteNewsRV.adapter as NewsAdapter).differ.submitList(articles)
        }
    }

    private fun onSwipeDeleteArticle(view: View){
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.layoutPosition
                val article = (binding.favoriteNewsRV.adapter as NewsAdapter).differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.favoriteNewsRV)
        }
    }

    override fun onClick(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article", article)
        }
        findNavController().navigate(
            R.id.action_favoriteNewsFragment_to_articleWebViewFragment,
            bundle
        )
    }

}