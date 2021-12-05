package com.danielturato.review_app.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.danielturato.review_app.R
import com.danielturato.review_app.databinding.FragmentRestaurantBinding
import com.danielturato.review_app.util.PermissionCode
import com.danielturato.review_app.view_model.RestaurantsViewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.scopes.FragmentScoped

/**
 * The restaurant fragment displays a Google Map marked with various
 * restaurant's around the user's location.
 *
 * User's are able to click the markers to view the restaurant's info & reviews
 *
 * @author danielturato
 */
@FragmentScoped
class RestaurantFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private val restaurantsViewModel: RestaurantsViewModel by viewModels()

    private var _binding: FragmentRestaurantBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentRestaurantBinding.inflate(layoutInflater, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkLocationPermissions()

        restaurantsViewModel.getRestaurantsLiveData().observe(viewLifecycleOwner, {
            for (r in it) {
                val loc = LatLng(r.location.latitude.toDouble(),
                                 r.location.longitude.toDouble())
                mMap.addMarker(MarkerOptions().position(loc).title(r.name))
            }
        })

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            PermissionCode.LOCATION.code -> {
                if (grantResults.isNotEmpty() && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {
                    requestLocation()
                } else {
                    Toast.makeText(requireContext(),
                              "We require your location to view local restaurants",
                                   Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.style_json))

    }

    private fun checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            requestLocation()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissionRequest, PermissionCode.LOCATION.code)
        }
    }

    private fun requestLocation() {
        restaurantsViewModel.getLocationLiveData().observe(viewLifecycleOwner, {
            val cur = LatLng(it.latitude.toDouble(), it.longitude.toDouble())

//            val nearbyRestaurants: Array<PlacesSearchResult> = NearbyRestaurants.search(it).results
//
//            for (restaurant in nearbyRestaurants) {
//                val loc = LatLng(restaurant.geometry.location.lat,
//                                 restaurant.geometry.location.lng)
//
//                mMap.addMarker(MarkerOptions().position(loc).title(restaurant.name))
//            }

            mMap.addMarker(MarkerOptions().position(cur).title("You"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 12.0f))
        })
    }
}