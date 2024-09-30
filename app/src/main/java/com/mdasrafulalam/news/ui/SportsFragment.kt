package com.mdasrafulalam.news.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdasrafulalam.news.R
import com.mdasrafulalam.news.adapter.NewsRecyclerViewAdapter
import com.mdasrafulalam.news.databinding.FragmentSportsBinding
import com.mdasrafulalam.news.model.News
import com.mdasrafulalam.news.utils.Constants
import com.mdasrafulalam.news.viewmodel.NewsViewmodel
import org.aviran.cookiebar2.CookieBar

class SportsFragment : Fragment() {

    private lateinit var binding: FragmentSportsBinding
    private val viewModel: NewsViewmodel by activityViewModels()
    private lateinit var adapter: NewsRecyclerViewAdapter
    private lateinit var list: List<News>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSportsBinding.inflate(layoutInflater, container, false)
        list = listOf()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = NewsRecyclerViewAdapter(viewLifecycleOwner,::updateBookmark)
        Constants.ISLINEARLYOUT.observe(viewLifecycleOwner) {
            if (it) {
                binding.sportsNewsRV.layoutManager = LinearLayoutManager(requireContext())
            } else {
                binding.sportsNewsRV.layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }
        binding.sportsNewsRV.adapter = adapter
        viewModel.getSportsNews(Constants.CATEGORY_SPORTS, Constants.COUNTRY.value.toString())
            .observe(viewLifecycleOwner) {
                list = it
            }
        adapter.submitList(list)
        binding.sportsSwipRefreshLayout.setOnRefreshListener {
            binding.sportsSwipRefreshLayout.isRefreshing = false
            if (!Constants.verifyAvailableNetwork(requireContext())) {
                CookieBar.build(requireActivity())
                    .setTitle(getString(R.string.network_conncetion))
                    .setBackgroundColor(R.color.swipe_color_4)
                    .setTitleColor(R.color.white)
                    .setMessage("No Active Internet!")
                    .setDuration(5000)
                    .setSwipeToDismiss(true)
                    .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                    .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                    .show()
            } else {
                viewModel.refreshSportsNews()
                viewModel.getSportsNews(
                    Constants.CATEGORY_SPORTS,
                    Constants.COUNTRY.value.toString()
                ).observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
                CookieBar.build(requireActivity())
                    .setMessage("News Updated!")
                    .setDuration(5000)
                    .setBackgroundColor(R.color.color_tab_text)
                    .setIcon(R.drawable.success)
                    .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                    .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                    .show()
            }

        }
    }

    private fun updateBookmark(news: News) {
        viewModel.updateBookMark(news)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar_menu, menu)
        val search = menu.findItem(R.id.action_search)
        val searchView = search?.actionView as androidx.appcompat.widget.SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchAction(query!!)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                searchAction(newText!!)
                return true
            }
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_voice -> displaySpeechRecognizer()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displaySpeechRecognizer() {
        //Starts an activity that will prompt the user for speech and send it through a speech recognizer.
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, //Informs the recognizer which speech model to prefer when performing ACTION_RECOGNIZE_SPEECH.
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM //Use a language model based on free-form speech recognition.
            )
        }
        // This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, 6)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 6 && resultCode == Activity.RESULT_OK) {
            val spokenText =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results?.get(0) ?: return
                }
            Log.d("voice", spokenText)
            //setting voice text into input field
            searchAction(spokenText)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun searchAction(query: String) {
        var newsList: List<News>
        viewModel.getSportsNews(Constants.CATEGORY_SPORTS, Constants.COUNTRY.value.toString())
            .observe(viewLifecycleOwner) {
                newsList = it
                val collectionSearch: List<News> = newsList.filter {
                    it.title!!.uppercase().contains(query.uppercase())
                }.toList()
                adapter.submitList(collectionSearch)
            }
    }
}