package com.kt.maps.sample.example.events

import android.graphics.Color
import android.os.Bundle
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.overlay.marker.MarkerOptions
import com.kt.maps.overlay.polygon.PolygonOverlayOptions
import com.kt.maps.overlay.polyline.PolylineOverlayOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityOverlayEventBinding
import com.kt.maps.sample.ui.common.showToast
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback

class OverlayEventActivity :
    BaseActivity<ActivityOverlayEventBinding>(R.layout.activity_overlay_event),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.apply {
            jumpTo(
                cameraOptions = CameraPositionOptions(
                    zoom = 14.0,
                    lngLat = LngLat(longitude = 126.97794, latitude = 37.57103)
                )
            )

            val marker1 = addOverlay(
                MarkerOptions.Builder().apply {
                    position(MARKER_POSITION)
                    title("Marker")
                    snippet("${MARKER_POSITION.longitude}, ${MARKER_POSITION.latitude} ")
                }.build()
            )
            selectMarker(marker1)

            addOverlay(
                PolylineOverlayOptions.Builder().apply {
                    coords(POLYLINE_OVERLAY_COORDS)
                    color(Color.GREEN)
                    width(10f)
                }.build()
            )

            addOverlay(
                PolygonOverlayOptions.Builder().apply {
                    coords(POLYGON_OVERLAY_COORDS)
                    color(Color.BLUE)
                }.build()
            )

            setOnMarkerClickListener {
                showToast("Marker clicked (ID : ${it.id})")
                false
            }

            setOnPolylineOverlayClickListener {
                showToast("PolylineOverlay clicked (ID : ${it.id})")
            }

            setOnPolygonOverlayClickListener {
                showToast("PolygonOverlay clicked (ID : ${it.id})")
            }

            setOnInfoWindowClickListener {
                showToast("InfoWindow Clicked (ID : ${it.id})")
                false
            }

            setOnInfoWindowLongClickListener {
                showToast("InfoWindow Long Clicked (ID : ${it.id})")
            }

            setOnInfoWindowCloseListener {
                showToast("InfoWindow Closed (ID : ${it.id})")
            }

        }

    }

    companion object {
        private val MARKER_POSITION = LngLat(longitude = 126.97687, latitude = 37.57581)
        private val POLYGON_OVERLAY_COORDS = listOf(
            LngLat(latitude = 37.57217, longitude = 126.98077),
            LngLat(latitude = 37.56834, longitude = 126.98226),
            LngLat(latitude = 37.57310, longitude = 126.98548),
        )
        private val POLYLINE_OVERLAY_COORDS = listOf(
            LngLat(latitude = 37.57069, longitude = 126.97365),
            LngLat(latitude = 37.56897, longitude = 126.97136),
            LngLat(latitude = 37.56872, longitude = 126.97534),
            LngLat(latitude = 37.56742, longitude = 126.97164),
        )

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

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}