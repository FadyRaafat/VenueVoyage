package com.fady.venuevoyage.presentation.ui.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fady.venuevoyage.R
import com.fady.venuevoyage.data.models.Venue
import com.fady.venuevoyage.databinding.DialogVenueDetailsBinding
import com.fady.venuevoyage.databinding.FragmentHomeBinding
import com.fady.venuevoyage.presentation.ui.adapters.VenuesAdapter
import com.fady.venuevoyage.presentation.utils.base.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
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
            viewModel.getVenuesByLocation()
            listTV.setOnClickListener {
                switchToList()
            }
            mapTV.setOnClickListener {
                switchToMap()
            }
            initMap()
            switchToList()
        }

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

    private fun initMap() {
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
        mMap.uiSettings.apply {
            isZoomGesturesEnabled = true
            isScrollGesturesEnabled = true
        }
        mMap.setOnMarkerClickListener { marker ->
            getSelectedVenue(marker.tag as String)
            true
        }
    }

    private fun getSelectedVenue(venueId: String) {
        viewModel.getVenueById(venueId)?.let {
            showVenueDetails(it)
        }
    }

    private fun showVenueDetails(venue: Venue) {
        val binding: DialogVenueDetailsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(
                requireContext()
            ), R.layout.dialog_venue_details, null, false
        )
        Dialog(requireContext()).apply {
            setContentView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
            )
            setCancelable(true)
            setCanceledOnTouchOutside(true)
            binding.apply {
                venueNameTv.text = venue.name
                venueAddressTv.text = venue.location?.getFormattedAddress()
                venue.categories?.firstOrNull()?.let {
                    categoryName.text = it.name ?: ""
                    categoryImage = it.icon?.getIconUrl()

                }
            }
            show()
        }
    }

    private fun setupMarkers(venues: List<Venue>?) {
        venues?.forEach { venue ->
            venue.location?.let {
                mMap.addMarker(
                    MarkerOptions().position(it.getLatLng())
                )?.tag = venue.id
            }
        }
        zoomToLocation(venues?.first())

    }

    private fun zoomToLocation(venue: Venue?) {
        venue?.location?.let {
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    it.getLatLng(), 15f
                )
            )
        }
    }

    private fun switchViews(
        textView: TextView, secondOtherTextView: TextView
    ) {
        (10 * requireContext().resources.displayMetrics.density).toInt().let { pixel ->
            textView.apply {
                background = ContextCompat.getDrawable(
                    requireContext(), R.drawable.rounded_white
                )
                setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.colorPrimary
                    )
                )
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                setPadding(pixel, pixel, pixel, pixel)
            }
            secondOtherTextView.apply {
                setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.grey_888892
                    )
                )
                background = ContextCompat.getDrawable(
                    requireContext(), R.drawable.rounded_grey
                )
                setPadding(pixel, pixel, pixel, pixel)
            }
        }

    }


}