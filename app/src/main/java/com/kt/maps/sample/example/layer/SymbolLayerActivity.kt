package com.kt.maps.sample.example.layer

import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivitySymbolLayerBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback
import com.kt.maps.sdk.style.layers.SymbolLayer
import com.kt.maps.sdk.style.sources.GeojsonSource
import com.kt.maps.sdk.style.styles.*
import com.mapbox.geojson.MultiPoint
import com.mapbox.geojson.Point

class SymbolLayerActivity :
    BaseActivity<ActivitySymbolLayerBinding>(R.layout.activity_symbol_layer), OnMapReadyCallback {

    private lateinit var mMap: KtMap
    private lateinit var layer: SymbolLayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapView = binding.map
        mapView.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {

        binding.rotateSlider.addOnChangeListener { slider, value, fromUser ->
            layer.layout(
                IconRotate(value.toInt())
            )
        }

        mMap = ktmap

        val sourceId = "geojson"
        val source = GeojsonSource(

            sourceId, geometry = MultiPoint.fromLngLats(
                listOf(
                    Point.fromLngLat(127.029414, 37.471401),
                    Point.fromLngLat(127.030334, 37.473301),
                    Point.fromLngLat(127.028444, 37.471001)
                )
            )
        )

        mMap.apply {
            jumpTo(
                cameraOptions = CameraPositionOptions().zoom(16.0).lngLat(
                    LngLat(longitude = 127.029414, latitude = 37.471401)
                )
            )
            addSource(source)


            AppCompatResources.getDrawable(this@SymbolLayerActivity, R.drawable.cat)?.let {
                mMap.addImage("symbolDrawable", drawable = it)

                layer = SymbolLayer("triangle", sourceId)
                    .paint(
                        SymbolStylePaints(
                            SymbolIconStylePaints(IconOpacity(0.9))
                        )
                    )
                    .layout(
                        SymbolStyleLayouts(
                            symbolIconStyleLayouts = SymbolIconStyleLayouts(
                                IconImage("symbolDrawable"),
                                IconSize(0.2)
                            )
                        )
                    )

                addLayer(layer)
            }
        }
    }
}