package com.kt.maps.sample.example.overlay

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.overlay.infowindow.InfoWindowAdapter
import com.kt.maps.overlay.marker.Marker
import com.kt.maps.overlay.marker.MarkerOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityCustomInfowindowBinding
import kotlin.random.Random

class CustomInfoWindowActivity :
    BaseActivity<ActivityCustomInfowindowBinding>(R.layout.activity_custom_infowindow),
    OnMapReadyCallback {
    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@CustomInfoWindowActivity)
        }

    }

    private val infoWindowAdapter = InfoWindowAdapter<Marker> { marker ->

        View.inflate(applicationContext, R.layout.layout_custom_infowindow, null).apply {
            findViewById<TextView>(R.id.title).text = marker.title
            findViewById<TextView>(R.id.desc).text = marker.snippet
            findViewById<ImageView>(R.id.iv).setOnClickListener {
                findViewById<TextView>(R.id.title).setTextColor(Random.Default.randomColor())
            }
        }

    }


    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.apply {
            jumpTo(
                cameraOptions = CameraPositionOptions().zoom(15f)
                    .lngLat(LngLat(longitude = 126.97794, latitude = 37.57103))
            )

            setInfoWindowAdapter(infoWindowAdapter)

            val marker1 = addOverlay(
                MarkerOptions.Builder().apply {
                    position(MARKER1_POSITION)
                    title("Marker 1")
                    snippet("${MARKER1_POSITION.longitude}, ${MARKER1_POSITION.latitude} ")
                }.build()
            )

            val marker2 = addOverlay(
                MarkerOptions.Builder().apply {
                    position(MARKER2_POSITION)
                    title("Marker 2")
                    snippet("${MARKER2_POSITION.longitude}, ${MARKER2_POSITION.latitude} ")
                }.build()
            )
            marker2.isSelected = true

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

    companion object {
        private val MARKER1_POSITION = LngLat(longitude = 126.97794, latitude = 37.57103)
        private val MARKER2_POSITION = LngLat(longitude = 126.97620, latitude = 37.56945)
    }

    private fun Random.Default.randomColor() = run {
        Color.argb(255, this.nextInt(256), this.nextInt(256), this.nextInt(256))
    }

}