package com.cinnox.itunes.view

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cinnox.itunes.databinding.ActivityMainBinding
import com.cinnox.itunes.entity.MusicEntity
import com.cinnox.itunes.repository.bean.SearchBean
import com.cinnox.itunes.repository.retrofit.RetrofitRepo
import com.cinnox.itunes.view.search.MusicAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = MusicAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMusic.layoutManager = LinearLayoutManager(this)
        binding.rvMusic.adapter = adapter

        binding.etTerm.setOnEditorActionListener { view, actionId, event ->
            hideSoftKeyboard()
            CoroutineScope(Dispatchers.Main).launch {
                val result = searchMusic(view.text.toString())
                val entities: List<MusicEntity> =
                    result.results.map {
                        MusicEntity(it.artworkUrl60, it.trackName, it.artistName)
                    }
                adapter.replaceDataAll(entities)
            }
            true
        }
    }

    private fun hideSoftKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    private suspend fun searchMusic(artisName: String): SearchBean = withContext(Dispatchers.IO) {
        val response: Response<SearchBean> =
            RetrofitRepo.getApis().searchMusicByArtisName(artisName)
        if (response.isSuccessful) {
            return@withContext response.body() ?: SearchBean(0, emptyList())
        } else {
            throw HttpException(response)
        }
    }
}