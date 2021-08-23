package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.AsteroidAdapter
import com.udacity.asteroidradar.AsteroidListener
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = AsteroidAdapter(AsteroidListener {
            asteroid -> viewModel.onAsteroidClicked(asteroid)
        }) // Initiate the data adapter
        binding.asteroidRecycler.adapter = adapter // Bind the adapter to the recyclerview

        viewModel.navigateToAsteroidDetail.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(
                    MainFragmentDirections.actionShowDetail(it)
                )
                viewModel.onAsteroidDetailNavigated()
            }
        })

        viewModel.asteroidProperties.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        }) // specify the data source the adapter is for

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
