package com.example.dicodingeventsubs.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingeventsubs.data.response.ListEventsItem
import com.example.dicodingeventsubs.databinding.FragmentFavoriteBinding
import com.example.dicodingeventsubs.helper.FavoriteViewModelFactory
import com.example.dicodingeventsubs.ui.EventsListAdapter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container,false)
        val root: View = binding.root

        val favoriteViewModel = obtainViewModel(this)

        val adapter = EventsListAdapter()

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvents.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvEvents.addItemDecoration(itemDecoration)

        favoriteViewModel.getAllFavoriteEvents().observe(viewLifecycleOwner) { users ->
            val items = arrayListOf<ListEventsItem>()
            users.map {
                val item = ListEventsItem(
                    id = it.id,
                    name = it.name,
                    imageLogo = it.mediaCover,
                    mediaCover = "",
                    beginTime = "",
                    summary = "",
                    registrants = 0,
                    link = "",
                    description = "",
                    ownerName = "",
                    cityName = "",
                    quota = 0,
                    endTime = "",
                    category = "")
                items.add(item)
            }
            adapter.submitList(items)
            binding.rvEvents.adapter = adapter
        }


        return root
    }

    private fun obtainViewModel(fragment: Fragment): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(fragment.requireActivity().application)
        return ViewModelProvider(fragment, factory).get(FavoriteViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}