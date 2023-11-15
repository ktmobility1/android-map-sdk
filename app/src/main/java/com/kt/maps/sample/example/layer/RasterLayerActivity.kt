package com.kt.maps.sample.example.layer

import android.os.Bundle
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityRasterLayerBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback
import com.kt.maps.sdk.style.layers.RasterLayer
import com.kt.maps.sdk.style.sources.RasterSource
import com.kt.maps.sdk.style.styles.RasterOpacity
import com.kt.maps.sdk.style.styles.RasterStyleLayouts
import com.kt.maps.sdk.style.styles.RasterStylePaints
import com.kt.maps.style.sources.RasterSourceProperties
import com.kt.maps.style.styles.Visibility

class RasterLayerActivity :
    BaseActivity<ActivityRasterLayerBinding>(R.layout.activity_raster_layer),
    OnMapReadyCallback {

    private lateinit var mMap: KtMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapView = binding.map
        mapView.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        mMap = ktmap

        val source = RasterSource(
            "raster-tiles", options = RasterSourceProperties(
                RasterSourceProperties.Tiles(arrayOf("https://tileserver.memomaps.de/tilegen/{z}/{x}/{y}.png")),
                RasterSourceProperties.TileSize(256)
            )
        )

        mMap.addSource(source)


        val rasterLayer = RasterLayer("test", "raster-tiles")
            .paint(
                RasterStylePaints(
                    RasterOpacity(0.7)
                )
            ).layout(
                RasterStyleLayouts(
                    Visibility.VISIBLE
                )
            )
        mMap.addLayer(rasterLayer)
    }
}