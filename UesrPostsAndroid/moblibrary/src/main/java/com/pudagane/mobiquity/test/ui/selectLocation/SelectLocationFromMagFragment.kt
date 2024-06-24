package com.pudagane.mobiquity.test.ui.selectLocation

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.pudagane.mobiquity.test.R
import kotlinx.android.synthetic.main.fragment_maps.*
import java.io.IOException


class SelectLocationFromMagFragment : Fragment(), OnMapReadyCallback {
    private lateinit var viewModel: SelectLocationFromMagViewModel
    private lateinit var geocoder: Geocoder
    private  var marker: Marker?=null
    private lateinit var mMap: GoogleMap
    private var city:String?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_maps, container, false)
       val mapFragment= childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        viewModel = ViewModelProvider(this).get(SelectLocationFromMagViewModel::class.java)

        return root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnCancel.setOnClickListener { view ->
            Navigation.findNavController(view).navigateUp()
        }

        btnOK.setOnClickListener { view ->
            if (city==null){
                Toast.makeText(activity,"Please select city",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.saveCity(city!!)
                Navigation.findNavController(view).navigateUp()
            }

        }
    }
    override fun onMapReady(googleMap: GoogleMap?) {
        Log.d("SelectLocation","onMapReady")
        mMap = googleMap!!

        mMap.setOnMapLongClickListener { latLng ->
            Log.d("SelectLocation","setOnMapLongClickListener")
            // First check if myMarker is null
            if (marker == null) {

                // Marker was not set yet. Add marker:
                marker = mMap.addMarker(
                        MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                )
            } else {
                // Marker already exists, just update it's position
                marker?.setPosition(latLng)
            }

            try {
                geocoder = Geocoder(activity)
                val addressList: List<Address> = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                Log.d("Address","add="+ addressList[0].subAdminArea)
                city=addressList[0].subAdminArea
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        }

}
