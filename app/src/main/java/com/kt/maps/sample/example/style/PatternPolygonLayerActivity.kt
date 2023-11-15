package com.kt.maps.sample.example.style

import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityPolygonLayerBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback
import com.kt.maps.sdk.style.layers.FillLayer
import com.kt.maps.sdk.style.sources.GeojsonSource
import com.kt.maps.sdk.style.styles.FillPattern
import com.kt.maps.sdk.style.styles.FillStylePaints
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon

class PatternPolygonLayerActivity :
    BaseActivity<ActivityPolygonLayerBinding>(R.layout.activity_polygon_layer), OnMapReadyCallback {

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
                zoom = 16.0,
                lngLat = LngLat(127.029414, 37.471401)
            )
        )

        val source = GeojsonSource("geojson", geometry = Polygon.fromLngLats(
            listOf(
                listOf(
                    Point.fromLngLat(127.0285028, 37.4718480),
                    Point.fromLngLat(127.0285445, 37.4707325),
                    Point.fromLngLat(127.0302729, 37.4707581),
                    Point.fromLngLat(127.0302293, 37.4719246),
                    Point.fromLngLat(127.0285028, 37.4718480)
                )
            )
        ))

        mMap.addSource(source)

        AppCompatResources.getDrawable(this@PatternPolygonLayerActivity, R.drawable.blackcat)?.let {
            mMap.addImage("pattern", drawable = it)

             val layer = FillLayer("pattern-polygon", source.id)
                .paint(
                    FillStylePaints(
                        FillPattern("pattern")
                    )
                )
            mMap.addLayer(layer)
        }
    }
}