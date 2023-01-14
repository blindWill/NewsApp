package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.data.Article
import com.example.newsapp.databinding.FragmentArticleWebViewBinding
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.viewmodels.BreakingNewsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleWebViewFragment : Fragment() {

    private val viewModel: BreakingNewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView: WebView = view.findViewById(R.id.webView)
        val article = arguments?.getSerializable("article") as Article

        webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }

        val saveButton: FloatingActionButton = view.findViewById(R.id.saveFAB)
        saveButton.setOnClickListener{
            viewModel.saveArticle(article)
        }

    }
}


