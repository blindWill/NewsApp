package com.example.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.data.Article
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.viewmodels.BreakingNewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(), NewsAdapter.Listener {

    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BreakingNewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        setUpObservers()
        viewModel.fetchBreakingNews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecycler() {
        binding.newsRV.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.newsRV.adapter = NewsAdapter(requireContext(), this)
    }

    private fun setUpObservers() {
        viewModel.breakingNews.observe(viewLifecycleOwner) { newData ->
            viewModel.updateDataForRecycler(newData)
        }
        viewModel.dataForRecycler.observe(viewLifecycleOwner) { dataForRecycler ->
           (binding.newsRV.adapter as NewsAdapter).differ.submitList(dataForRecycler)
        }
    }

    override fun onClick(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article", article)
        }
        findNavController().navigate(
            R.id.action_breakingNewsFragment_to_articleWebViewFragment,
            bundle
        )
    }
}