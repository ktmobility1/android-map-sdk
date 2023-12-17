package com.kt.maps.sample.example.style

import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityPatternFillLayerBinding
import com.kt.maps.style.LayerFactory
import com.kt.maps.style.layers.FillLayer
import com.kt.maps.style.sources.GeojsonSource
import com.kt.maps.style.styles.FillStylePaints
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon

class PatternFillLayerActivity :
    BaseActivity<ActivityPatternFillLayerBinding>(R.layout.activity_pattern_fill_layer),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    private val source: GeojsonSource by lazy { createSource() }
    private val layer: FillLayer by lazy { createLayer(source) }

    private fun createSource() = GeojsonSource(
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

    private fun createLayer(source: GeojsonSource) = LayerFactory.fill(source.id)
        .paint(
            FillStylePaints(
                FillStylePaints.FillPattern("pattern")
            )
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@PatternFillLayerActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            jumpTo(
                cameraOptions = CameraPositionOptions(
                    zoom = 16f,
                    lngLat = LngLat(127.029414, 37.471401)
                )
            )

            AppCompatResources.getDrawable(this@PatternFillLayerActivity, R.drawable.blackcat)
                ?.let {
                    addImage("pattern", drawable = it)
                    addSource(source)
                    addLayer(layer)
                }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}