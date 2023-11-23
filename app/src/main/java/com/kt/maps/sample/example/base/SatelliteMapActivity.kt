package com.kt.maps.sample.example.base

import android.os.Bundle
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityNormalMapBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.KtMapOptions
import com.kt.maps.sdk.OnMapReadyCallback

class SatelliteMapActivity : BaseActivity<ActivityNormalMapBinding>(R.layout.activity_normal_map),
    OnMapReadyCallback {
    private lateinit var map: KtMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapView = binding.map
        mapView.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap
        map.setMapType(KtMapOptions.MapType.SATELLITE)
    }
}