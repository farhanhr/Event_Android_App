package com.example.dicodingeventsubs.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingeventsubs.data.response.ListEventsItem
import com.example.dicodingeventsubs.databinding.FragmentHomeBinding
import com.example.dicodingeventsubs.ui.EventsListAdapter
import com.example.dicodingeventsubs.ui.finished.FinishedViewModel
import com.example.dicodingeventsubs.ui.upcoming.UpcomingViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var upcomingViewModel: UpcomingViewModel
    private lateinit var finishedViewModel: FinishedViewModel

    private val upcomingAdapter = EventsListAdapter()
    private val finishedAdapter = EventsListAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        upcomingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[UpcomingViewModel::class.java]
        finishedViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FinishedViewModel::class.java]


        upcomingViewModel.listevent.observe(viewLifecycleOwner) { event ->
            setUpcomingEventData(event)
        }
        finishedViewModel.listevent.observe(viewLifecycleOwner) { event ->
            setFinishedEventData(event)
        }

        binding.rvUpcomingEvents.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }

        binding.rvFinishedEvents.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = finishedAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }

        upcomingViewModel.isLoading.observe(viewLifecycleOwner) {
            showUpcomingEventLoading(it)
        }

        finishedViewModel.isLoading.observe(viewLifecycleOwner) {
            showFinishedEventLoading(it)
        }

        return root
    }

    private fun setUpcomingEventData(events: List<ListEventsItem>) {
        upcomingAdapter.submitList(events.take(5))
    }

    private fun setFinishedEventData(events: List<ListEventsItem>) {
        finishedAdapter.submitList(events.take(5))
    }

    private fun showUpcomingEventLoading(isLoading: Boolean) {
        binding.upcomingEventProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showFinishedEventLoading(isLoading: Boolean) {
        binding.finishedEventProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}