package com.kt.maps.sample.example.base

import android.os.Bundle
import com.kt.maps.KtMap
import com.kt.maps.KtMapOptions
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityNormalMapBinding

class SatelliteMapActivity : BaseActivity<ActivityNormalMapBinding>(R.layout.activity_normal_map),
    OnMapReadyCallback {
    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap
        map.setMapType(KtMapOptions.MapType.SATELLITE)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}