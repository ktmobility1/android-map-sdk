package com.kt.maps.sample.example.layer

import android.os.Bundle
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityPolygonLayerBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback
import com.kt.maps.sdk.style.layers.FillLayer
import com.kt.maps.sdk.style.sources.GeojsonSource
import com.kt.maps.sdk.style.styles.FillColor
import com.kt.maps.sdk.style.styles.FillOpacity
import com.kt.maps.sdk.style.styles.FillStylePaints
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon

class PolygonLayerActivity :
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
                lngLat = LngLat(longitude = 127.017422, latitude = 37.49144)
            )
        )

        val polygon = Polygon.fromLngLats(
            listOf(
                listOf(
                    Point.fromLngLat(127.017422, 37.49144),
                    Point.fromLngLat(127.018522, 37.49144),
                    Point.fromLngLat(127.018522, 37.49294),
                    Point.fromLngLat(127.017422, 37.49144)
                )
            )
        )

        val source = GeojsonSource("geojson", geometry = polygon)

        mMap.addSource(source)


        val layer = FillLayer("triangle", source.id)
            .paint(
                FillStylePaints(
                    FillColor("#ff0000"),
                    FillOpacity(0.6)
                )
            )

        mMap.addLayer(layer)
    }
}