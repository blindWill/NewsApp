package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.BreakingNewsAdapter
import com.example.newsapp.adapter.Listener
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.data.Article
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import com.example.newsapp.utils.Constants.SEARCH_NEWS_TIME_DELAY
import com.example.newsapp.viewmodels.BreakingNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment : Fragment(), Listener {

    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BreakingNewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        setUpObservers()
        searchForNews()
    }

    private fun setUpRecycler() {
        binding.searchNewsRV.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.searchNewsRV.adapter = NewsAdapter(requireContext(), this)
    }

    private fun setUpObservers(){
        viewModel.searchNews.observe(viewLifecycleOwner) { newData ->
            viewModel.updateDataForRecycler(newData)
        }
        viewModel.dataForRecycler.observe(viewLifecycleOwner) { dataForRecycler ->
            (binding.searchNewsRV.adapter as NewsAdapter).differ.submitList(dataForRecycler)
        }
    }

    private fun searchForNews(){
        var job: Job? = null
        binding.searchET.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        viewModel.searchForNews(editable.toString())
                    }
                }
            }
        }
    }

    override fun onClick(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article", article)
        }
        findNavController().navigate(
            R.id.action_searchNewsFragment_to_articleWebViewFragment,
            bundle
        )
    }
}