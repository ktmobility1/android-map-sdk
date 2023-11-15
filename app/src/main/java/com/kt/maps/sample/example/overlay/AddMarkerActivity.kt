package com.kt.maps.sample.example.overlay

import android.graphics.PointF
import android.os.Bundle
import com.kt.maps.geometry.LngLat
import com.kt.maps.gesture.*
import com.kt.maps.overlay.marker.Marker
import com.kt.maps.overlay.marker.MarkerOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityAddMarkerBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback
import java.text.DecimalFormat

class AddMarkerActivity : BaseActivity<ActivityAddMarkerBinding>(R.layout.activity_add_marker),
    OnMapReadyCallback, OnMapTapListener, OnMapLongTapListener {

    private lateinit var map: KtMap
    private var customMarker: Marker? = null
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.map.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.apply {
            addOnMapTapListener(this@AddMarkerActivity)
            addOnMapLongTapListener(this@AddMarkerActivity)
        }
    }

    override fun onTap(point: PointF, coord: LngLat): Boolean {
        count++
        map.apply {
            // 마커 새로 생성할 때
            customMarker = map.addOverlay(
                MarkerOptions.Builder().apply {
                    position(coord)
                    title("Custom marker $count")
                    snippet("${DecimalFormat("#.#####").format(coord.latitude)}, ${DecimalFormat("#.#####").format(coord.longitude)}")
                }.build()
            )
            return true
        }
    }

    override fun onMapLongTap(point: PointF, coord: LngLat): Boolean {
        // 기존 마커가 생성되어 있을 때
        customMarker?.let {
            map.removeOverlay(customMarker!!.id)
        }
        return true
    }
}