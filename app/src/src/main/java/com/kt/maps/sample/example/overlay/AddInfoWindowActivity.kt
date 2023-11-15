package com.kt.maps.sample.example.overlay

import android.os.Bundle
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityAddInfowindowBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback
import com.kt.maps.sdk.style.layers.SymbolLayer
import com.kt.maps.sdk.style.sources.GeojsonSource
import com.kt.maps.sdk.style.styles.*
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point


class AddInfoWindowActivity : BaseActivity<ActivityAddInfowindowBinding>(R.layout.activity_add_infowindow),
    OnMapReadyCallback {
    private lateinit var map: KtMap
    private lateinit var layer: SymbolLayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.map.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        val sourceFeatures = FeatureCollection.fromFeatures(
            arrayOf(
                Feature.fromGeometry(
                    Point.fromLngLat(127.029414,37.471401),
                    featureProperties("textField","kt우면")
                ),
                Feature.fromGeometry(
                    Point.fromLngLat(126.978916, 37.572020),
                    featureProperties("textField","kt광화문")
                )
            )
        )

        val source = GeojsonSource(MARKER_CAPTION_SOURCE, sourceFeatures)

        map.apply {
            jumpTo(
                cameraOptions = CameraPositionOptions().zoom(16.0).lngLat(
                    LngLat(longitude = 127.029414, latitude = 37.471401)
                )
            )

            addSource(source)

            getDrawable(com.kt.maps.overlay.marker.R.drawable.ktmap_marker_icon_default)?.let {
                map.addImage("symbolDrawable", drawable = it)

                layer = SymbolLayer(MARKER_CAPTION_LAYER, MARKER_CAPTION_SOURCE)

                    .paint(
                        SymbolStylePaints(
                            SymbolIconStylePaints(IconOpacity(0.3)),
                            symbolTextStylePaints = SymbolTextStylePaints(
                                TextColor("#000000")
                            )
                        )
                    ).layout(
                        SymbolStyleLayouts(
                            symbolIconStyleLayouts = SymbolIconStyleLayouts(
                                IconImage("symbolDrawable"),
                                IconSize(0.2),
                                IconAllowOverlap(true),
                            ),

                            symbolTextStyleLayouts = SymbolTextStyleLayouts(
                                TextField(),
                                TextAllowOverlap(true),
                                TextIgnorePlacement(false)
                            )
                        )
                    )
            }
            
            addLayer(layer)

        }
    }

    private fun featureProperties(id: String, title: String): JsonObject {
        val `object` = JsonObject()
        `object`.add(ID_FEATURE_PROPERTY, JsonPrimitive(id))
        `object`.add(TITLE_FEATURE_PROPERTY, JsonPrimitive(title))
        return `object`
    }


    companion object {
        private const val MARKER_CAPTION_SOURCE = "marker-caption-source"
        private const val MARKER_CAPTION_LAYER = "marker-caption-layer"

        private const val ID_FEATURE_PROPERTY = "id"
        private const val TITLE_FEATURE_PROPERTY = "title"


    }

}