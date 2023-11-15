package com.kt.maps.sample.example.layer

import android.os.Bundle
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.gesture.addOnMapLongTapListener
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityPolygonLayerRemoteBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback
import com.kt.maps.sdk.style.layers.FillLayer
import com.kt.maps.sdk.style.sources.GeojsonSource
import com.kt.maps.sdk.style.styles.FillAntialias
import com.kt.maps.sdk.style.styles.FillColor
import com.kt.maps.sdk.style.styles.FillOpacity
import com.kt.maps.sdk.style.styles.FillOutlineColor
import com.kt.maps.sdk.style.styles.FillStylePaints
import java.net.URI

class PolygonLayerRemoteActivity :
    BaseActivity<ActivityPolygonLayerRemoteBinding>(R.layout.activity_polygon_layer_remote),
    OnMapReadyCallback {

    private lateinit var mMap: KtMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapView = binding.map
        mapView.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        mMap = ktmap
        mMap.jumpTo(
            cameraOptions = CameraPositionOptions(
                zoom = 10.0,
                lngLat = LngLat(longitude = 127.017422, latitude = 37.49144)
            )
        )
        mMap.addSource(
            GeojsonSource(
                "geojsonRemote",
                uri = URI("https://map.gis.kt.com/mapsdk/data/seoul_sub.geojson")
            )
        )
        mMap.addLayer(
            FillLayer("test", "geojsonRemote")
                .paint(
                    FillStylePaints(
                        FillAntialias(true),
                        FillColor("#00ff00"),
                        FillOutlineColor("#000"),
                        FillOpacity(0.6)
                    )
                )
        )
    }
}