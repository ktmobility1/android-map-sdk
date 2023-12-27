package com.kt.maps.sample.example.layer

import android.os.Bundle
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityPolygonLayerRemoteBinding
import com.kt.maps.style.LayerFactory
import com.kt.maps.style.sources.GeojsonSource
import com.kt.maps.style.styles.FillStylePaints
import com.kt.maps.style.styles.FillStylePaints.FillColor
import com.kt.maps.style.styles.FillStylePaints.FillOpacity
import com.kt.maps.style.styles.FillStylePaints.FillOutlineColor
import java.net.URI

class FillLayerRemoteActivity :
    BaseActivity<ActivityPolygonLayerRemoteBinding>(R.layout.activity_polygon_layer_remote),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@FillLayerRemoteActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            // 지도 초기 위치 이동
            jumpTo(
                cameraOptions = CameraPositionOptions(
                    zoom = 10f,
                    lngLat = LngLat(longitude = 127.017422, latitude = 37.49144)
                )
            )

            // 외부 GEOJSON 데이터 소스 생성
            val source =
                GeojsonSource(uri = URI("https://map.gis.kt.com/mapsdk/data/seoul_sub.geojson"))

            // 데이터 소스 추가
            addSource(source)
            // 레이어 추가
            addLayer(
                LayerFactory.fill(source = source.id)
                    .paint(
                        FillStylePaints(
                            // 색상 설정
                            FillColor("#00ff00"),
                            // 외곽선 설정
                            FillOutlineColor("#000"),
                            // 투명도 설정
                            FillOpacity(0.6f)
                        )
                    )
            )
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