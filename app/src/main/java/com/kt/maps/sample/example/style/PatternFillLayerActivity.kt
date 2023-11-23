package com.kt.maps.sample.example.style

import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityFillLayerBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback
import com.kt.maps.sdk.style.layers.FillLayer
import com.kt.maps.sdk.style.sources.GeojsonSource
import com.kt.maps.sdk.style.styles.FillStylePaints
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon

class PatternFillLayerActivity :
    BaseActivity<ActivityFillLayerBinding>(R.layout.activity_fill_layer), OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@PatternFillLayerActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.jumpTo(
            cameraOptions = CameraPositionOptions(
                zoom = 16.0,
                lngLat = LngLat(127.029414, 37.471401)
            )
        )

        val source = GeojsonSource(
            geometry = Polygon.fromLngLats(
                listOf(
                    listOf(
                        Point.fromLngLat(127.0285028, 37.4718480),
                        Point.fromLngLat(127.0285445, 37.4707325),
                        Point.fromLngLat(127.0302729, 37.4707581),
                        Point.fromLngLat(127.0302293, 37.4719246),
                        Point.fromLngLat(127.0285028, 37.4718480)
                    )
                )
            )
        )

        map.addSource(source)

        AppCompatResources.getDrawable(this@PatternFillLayerActivity, R.drawable.blackcat)?.let {
            map.addImage("pattern", drawable = it)

            val layer = FillLayer("pattern-polygon", source.id)
                .paint(
                    FillStylePaints(
                        FillStylePaints.FillPattern("pattern")
                    )
                )
            map.addLayer(layer)
        }
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

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}