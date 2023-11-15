package com.kt.maps.sample.example.layer

import android.os.Bundle
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityBackgroundLayerBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback
import com.kt.maps.sdk.style.layers.BackgroundLayer
import com.kt.maps.sdk.style.styles.BackgroundColor
import com.kt.maps.sdk.style.styles.BackgroundOpacity
import com.kt.maps.sdk.style.styles.BackgroundStyleLayouts
import com.kt.maps.sdk.style.styles.BackgroundStylePaints
import com.kt.maps.style.styles.Visibility

class BackgroundLayerActivity :
    BaseActivity<ActivityBackgroundLayerBinding>(R.layout.activity_background_layer),
    OnMapReadyCallback {

    private lateinit var mMap: KtMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapView = binding.map
        mapView.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        mMap = ktmap

        val backgroundLayer = BackgroundLayer("test")
            .paint(
                BackgroundStylePaints(
                    BackgroundOpacity(0.5),
                    BackgroundColor("#000")
                )
            ).layout(
                BackgroundStyleLayouts(
                    Visibility.VISIBLE
                )
            )
        mMap.addLayer(backgroundLayer)
    }
}