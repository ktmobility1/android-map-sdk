package com.kt.maps.sample.example.layer

import android.os.Bundle
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityLineLayerVectorBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback
import com.kt.maps.sdk.style.layers.LineLayer
import com.kt.maps.sdk.style.sources.VectorSource
import com.kt.maps.sdk.style.styles.LineColor
import com.kt.maps.sdk.style.styles.LineOpacity
import com.kt.maps.sdk.style.styles.LineStyleLayouts
import com.kt.maps.sdk.style.styles.LineStylePaints
import com.kt.maps.sdk.style.styles.LineWidth
import com.kt.maps.style.sources.VectorSourceProperties
import com.kt.maps.style.styles.Visibility

class LineLayerVectorSourceActivity :
    BaseActivity<ActivityLineLayerVectorBinding>(R.layout.activity_line_layer_vector),
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
            cameraOptions = CameraPositionOptions().zoom(6.0)
                .lngLat(LngLat(longitude = 127.017422, latitude = 37.49144))
        )

        val source = VectorSource(
            sourceId = "VectorSource",
            options = VectorSourceProperties(
                VectorSourceProperties.Tiles(arrayOf("https://tile.gis.kt.com/image/mvt_9/{z}/{x}/{y}.pbf")),
                VectorSourceProperties.Minzoom(6),
                VectorSourceProperties.Maxzoom(10)
            )
        )

        mMap.addSource(source)

        val layer =
            LineLayer("triangle", sourceId = "VectorSource", sourceLayerId = "wd2_bound_line_1004")
                .paint(
                    LineStylePaints(
                        LineColor("#ff0000"),
                        LineOpacity(1.0),
                        LineWidth(2)
                    )
                ).layout(
                    LineStyleLayouts(Visibility.VISIBLE)
                )

        mMap.addLayer(layer)
    }
}