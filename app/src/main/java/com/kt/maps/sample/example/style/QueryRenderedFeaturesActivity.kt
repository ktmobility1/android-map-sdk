package com.kt.maps.sample.example.style

import android.os.Bundle
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.gesture.addOnMapTapListener
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityPolygonLayerRemoteBinding
import com.kt.maps.sample.ui.common.showSnackbar
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback
import com.kt.maps.sdk.style.layers.FillLayer
import com.kt.maps.sdk.style.sources.GeojsonSource
import com.kt.maps.sdk.style.styles.FillStylePaints
import com.kt.maps.sdk.style.styles.FillStylePaints.FillColor
import com.kt.maps.sdk.style.styles.FillStylePaints.FillOpacity
import com.kt.maps.sdk.style.styles.FillStylePaints.FillOutlineColor
import com.mapbox.geojson.FeatureCollection
import java.net.URI
import java.util.Collections

class QueryRenderedFeaturesActivity :
    BaseActivity<ActivityPolygonLayerRemoteBinding>(R.layout.activity_polygon_layer_remote),
    OnMapReadyCallback {

    companion object {
        // thanks to - 원자료: 통계청 통계지리정보서비스, 서비스: KOSTAT(https://github.com/southkorea/southkorea-maps)
        val DATA_URL =
            URI("https://raw.githubusercontent.com/southkorea/southkorea-maps/master/kostat/2018/json/skorea-municipalities-2018-geo.json")
        val PROPERTY_KEY = "name_eng"
        val DEFAULT_MESSAGE = "unKnown"
    }

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    private val source: GeojsonSource by lazy { createSource() }
    private val highListSource: GeojsonSource by lazy { createHighListSource() }

    private val layer: FillLayer by lazy { createLayer(source) }
    private val highlightLayer: FillLayer by lazy { createHighLightLayer(highListSource) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@QueryRenderedFeaturesActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            addOnMapTapListener { point, lngLat ->
                layer.queryRenderedFeatures(point)?.let { featureList ->
                    // Source 데이터를 Tap한 feature로 업데이트한다.
                    highListSource.features = FeatureCollection.fromFeatures(featureList)

                    val message =
                        if (featureList.isNotEmpty()) featureList[0].getStringProperty(PROPERTY_KEY) else DEFAULT_MESSAGE
                    mapView.showSnackbar("Tap feature: $message")
                }
                true
            }

            jumpTo(cameraOptions = CameraPositionOptions(zoom = 10.0))
            addSource(source)
            addLayer(layer)
            addSource(highListSource)
            addLayer(highlightLayer)
        }
    }

    private fun createSource() = GeojsonSource(uri = DATA_URL)

    private fun createHighListSource() =
        GeojsonSource(features = FeatureCollection.fromFeatures(Collections.emptyList()))

    private fun createLayer(source: GeojsonSource) =
        FillLayer(source = source.id)
            .paint(
                FillStylePaints(
                    FillColor("#00ff00"),
                    FillOutlineColor("#000"),
                    FillOpacity(0.4f)
                )
            )

    private fun createHighLightLayer(source: GeojsonSource) =
        FillLayer(source = source.id)
            .paint(
                FillStylePaints(
                    FillColor("#ff0000"),
                    FillOutlineColor("#000"),
                    FillOpacity(0.8f)
                )
            )

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