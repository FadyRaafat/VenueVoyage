package com.fady.venuevoyage.presentation.ui.home

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fady.venuevoyage.R
import com.fady.venuevoyage.data.models.Venue
import com.fady.venuevoyage.databinding.FragmentHomeBinding
import com.fady.venuevoyage.presentation.ui.adapters.VenuesAdapter
import com.fady.venuevoyage.presentation.utils.base.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentHome : BaseFragment<FragmentHomeBinding, VenuesViewModel>(), OnMapReadyCallback {

    override fun layout(): Int = R.layout.fragment_home

    override val viewModel: VenuesViewModel by viewModels()

    private val venuesAdapter: VenuesAdapter by lazy { VenuesAdapter() }

    private lateinit var mMap: GoogleMap

    override fun FragmentHomeBinding.initializeUI() {
        binding.apply {
            mainViewModel = mainVM
            switchToList()
            listTV.setOnClickListener {
                switchToList()
            }
            mapTV.setOnClickListener {
                switchToMap()
            }
        }
        viewModel.getVenues(
            location = arrayOf(23.0340847, 72.508472)
        )
        iniTMap()
    }

    private fun switchToList() {
        switchViews(binding.listTV, binding.mapTV)
        binding.venuesRecyclerView.show()
        binding.mapCardView.hide()
    }

    private fun switchToMap() {
        switchViews(binding.mapTV, binding.listTV)
        binding.venuesRecyclerView.hide()
        binding.mapCardView.show()
    }

    private fun iniTMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this@FragmentHome)
    }

    override fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getVenuesSuccess.collect { apiSuccess ->
                if (apiSuccess) {
                    viewModel.getVenuesResponse()?.response?.venues?.let { venues ->
                        setVenuesRV(venues)
                        setupMarkers(venues)

                    }
                }
            }
        }
    }

    private fun setVenuesRV(venues: List<Venue>?) {
        binding.venuesRecyclerView.apply {
            adapter = venuesAdapter.apply { submitList(venues) }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.addMarker(
            MarkerOptions().position(
                LatLng(
                    23.0340847, 72.508472

                )
            )
        )
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    23.0340847, 72.508472
                ), 15f
            )
        )

    }

    private fun setupMarkers(data: List<Venue>?) {
        data?.forEach { venue ->
            venue.location?.let {
                mMap.addMarker(
                    MarkerOptions().position(it.getLatLng())
                )?.tag = venue.id
            }
        }
    }

    private fun switchViews(
        textView: TextView, secondOtherTextView: TextView
    ) {
        val density = requireContext().resources.displayMetrics.density
        val paddingPixel = (10 * density).toInt()

        textView.background = ContextCompat.getDrawable(
            requireContext(), R.drawable.rounded_white
        )
        textView.setTextColor(
            ContextCompat.getColor(
                requireContext(), R.color.colorPrimary
            )
        )
        textView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        textView.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel)

        secondOtherTextView.setTextColor(
            ContextCompat.getColor(
                requireContext(), R.color.grey_888892
            )
        )
        secondOtherTextView.background = ContextCompat.getDrawable(
            requireContext(), R.drawable.rounded_grey
        );
        secondOtherTextView.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);

    }


}