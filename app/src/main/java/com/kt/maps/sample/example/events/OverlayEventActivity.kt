package com.kt.maps.sample.example.events

import android.graphics.Color
import android.os.Bundle
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.overlay.marker.MarkerOptions
import com.kt.maps.overlay.polygon.PolygonOverlayOptions
import com.kt.maps.overlay.polyline.PolylineOverlayOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityOverlayEventBinding
import com.kt.maps.sample.ui.common.showSnackbar

class OverlayEventActivity :
    BaseActivity<ActivityOverlayEventBinding>(R.layout.activity_overlay_event),
    OnMapReadyCallback {

    companion object {
        private val MARKER_LNGLAT = LngLat(longitude = 126.97687, latitude = 37.57581)
        private val POLYGON_OVERLAY_LNGLATS = listOf(
            LngLat(latitude = 37.57217, longitude = 126.98077),
            LngLat(latitude = 37.56834, longitude = 126.98226),
            LngLat(latitude = 37.57310, longitude = 126.98548)
        )
        private val POLYLINE_OVERLAY_LNGLATS = listOf(
            LngLat(latitude = 37.57069, longitude = 126.97365),
            LngLat(latitude = 37.56897, longitude = 126.97136),
            LngLat(latitude = 37.56872, longitude = 126.97534),
            LngLat(latitude = 37.56742, longitude = 126.97164)
        )
    }

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@OverlayEventActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            jumpTo(
                cameraOptions = CameraPositionOptions(
                    zoom = 14f,
                    lngLat = LngLat(longitude = 126.97794, latitude = 37.57103)
                )
            )

            // marker 오버레이 지도 추가
            val marker1 = addOverlay(
                MarkerOptions.Builder().apply {
                    position(MARKER_LNGLAT)
                    title("Marker")
                    snippet("${MARKER_LNGLAT.longitude}, ${MARKER_LNGLAT.latitude} ")
                }.build()
            )
            // marker 오버레이 InfoWindow open
            selectMarker(marker1)

            // Polyline 오버레이 지도 추가
            addOverlay(
                PolylineOverlayOptions.Builder().apply {
                    lngLats(POLYLINE_OVERLAY_LNGLATS)
                    color(Color.GREEN)
                    width(10f)
                }.build()
            )

            // Polygon 오버레이 지도 추가
            addOverlay(
                PolygonOverlayOptions.Builder().apply {
                    lngLats(POLYGON_OVERLAY_LNGLATS)
                    color(Color.BLUE)
                }.build()
            )

            // Marker 오버레이 Tap 이벤트 처리 리스너 등록
            setOnMarkerTapListener {
                mapView.showSnackbar("Marker tapped (ID : ${it.id})")
                false
            }

            // Polyline 오버레이 Tap 이벤트 처리 리스너 등록
            setOnPolylineOverlayTapListener {
                mapView.showSnackbar("PolylineOverlay tapped (ID : ${it.id})")
            }

            // Polygon 오버레이 Tap 이벤트 처리 리스너 등록
            setOnPolygonOverlayTapListener {
                mapView.showSnackbar("PolygonOverlay tapped (ID : ${it.id})")
            }

            // InfoWindow Tap 이벤트 처리 리스너 등록
            setOnInfoWindowTapListener {
                mapView.showSnackbar("InfoWindow tapped (marker ID : ${it.id})")
                true
            }

            // InfoWindow LongPress 이벤트 처리 리스너 등록
            setOnInfoWindowLongClickListener {
                mapView.showSnackbar("InfoWindow longPressed (marker ID : ${it.id})")
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