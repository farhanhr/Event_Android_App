package com.example.dicodingeventsubs.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.dicodingeventsubs.R
import com.example.dicodingeventsubs.data.database.FavoriteEvent
import com.example.dicodingeventsubs.data.response.EventsResponse
import com.example.dicodingeventsubs.data.response.ListEventsItem
import com.example.dicodingeventsubs.data.retrofit.ApiConfig
import com.example.dicodingeventsubs.databinding.ActivityDetailBinding
import com.example.dicodingeventsubs.helper.FavoriteViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel

    private lateinit var binding: ActivityDetailBinding

    companion object {
        private const val TAG = "DetailActivity"
        const val EVENT_KEY = "event_key"
    }


    private lateinit var registerEventLink: String
    private lateinit var eventName: String
    private lateinit var eventCover: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = obtainViewModel(this@DetailActivity)

        val idEvent = intent.getIntExtra(EVENT_KEY, -1)


        showDetailEvent(idEvent)

        binding.btnRegister.setOnClickListener {
            openRegisterLink(registerEventLink)
        }



        detailViewModel.isFavorite(idEvent).observe(this) { favoriteEvent ->

            if (favoriteEvent != null) {
                binding.btnFavorite.setImageResource(R.drawable.ic_favorite_full_24)
                binding.btnFavorite.setOnClickListener {
                    detailViewModel.delete(favoriteEvent)
                }
            }else {
                binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border_24)
                binding.btnFavorite.setOnClickListener {
                    val addFavorite = FavoriteEvent().apply {
                        id = idEvent
                        name = eventName
                        mediaCover = eventCover
                    }
                    detailViewModel.insert(addFavorite)
                }
            }
        }



    }

    private fun showDetailEvent(idEvent : Int) {
        showLoading(true)
        val client = ApiConfig.getApiService().getDetailEvent(idEvent)
        client.enqueue(object : Callback<EventsResponse> {
            override fun onResponse(
                call: Call<EventsResponse>,
                response: Response<EventsResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setEventData(responseBody.event)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setEventData(event: ListEventsItem) {
        binding.tvDetailEventTitle.text = event.name
        binding.tvOwner.text = event.ownerName
        binding.tvBeginTime.text = event.beginTime
        binding.tvQuota.text = this@DetailActivity.resources.getString(R.string.quota_event,  event.quota  - event.registrants )
        binding.tvDescriptionEvent.text = HtmlCompat.fromHtml(
            event.description,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        Glide.with(this@DetailActivity)
            .load(event.mediaCover)
            .into(binding.ivDetailEventCover)

        binding.btnRegister.visibility = View.VISIBLE
        registerEventLink = event.link
        eventName = event.name
        eventCover = event.mediaCover
    }

    private fun openRegisterLink(link: String) {
        val linkIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(linkIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }
}