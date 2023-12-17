package com.kt.maps.sample.example.overlay

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.geometry.LngLat
import com.kt.maps.gesture.OnMapTapListener
import com.kt.maps.gesture.addOnMapTapListener
import com.kt.maps.overlay.marker.Marker
import com.kt.maps.overlay.marker.MarkerOptions
import com.kt.maps.overlay.polyline.PolylineOverlay
import com.kt.maps.overlay.polyline.PolylineOverlayOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityPolylineOverlayBinding
import kotlin.random.Random

class PolylineOverlayActivity :
    BaseActivity<ActivityPolylineOverlayBinding>(R.layout.activity_polyline_overlay),
    OnMapReadyCallback,
    OnMapTapListener {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    private var polyline: PolylineOverlay? = null
    private val points = mutableListOf<LngLat>()
    private var markers = mutableListOf<Marker>()

    private lateinit var pinBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pinBitmap = R.drawable.pin_red_dot.toBitmap()

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@PolylineOverlayActivity)
        }

        binding.btnInit.setOnClickListener { removeAllOverlays() }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap
        map.addOnMapTapListener(this)
    }

    override fun onTap(point: PointF, lngLat: LngLat): Boolean {
        addMarker(lngLat)
        drawPolyline(lngLat)
        return true
    }

    private fun addMarker(lngLat: LngLat) {
        map.addOverlay(
            MarkerOptions.Builder().apply {
                position(lngLat)
                icon(pinBitmap)
            }.build()
        ).let {
            markers.add(it)
        }
    }

    private fun drawPolyline(lngLat: LngLat) {
        points.add(lngLat)

        if (points.size < 2) {
            return
        } else if (points.size == 2) {
            createPolyline()
        } else {
            expandPolyline()
        }
    }

    private fun createPolyline() {
        map.addOverlay(
            PolylineOverlayOptions.Builder().apply {
                lngLats(points)
                color(randomColor())
                width(5f)
            }.build()
        ).let {
            polyline = it
        }
    }

    private fun expandPolyline() {
        polyline?.let {
            it.lngLats = points
        }
    }


    private fun removeAllOverlays() {
        map.removeAllOverlays()
        clearData()
    }

    private fun clearData() {
        polyline = null
        points.clear()
        markers.clear()
    }


    private fun Int.toBitmap(): Bitmap {
        val drawable = AppCompatResources.getDrawable(this@PolylineOverlayActivity, this)!!
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return bitmap
    }

    private fun randomColor(): Int =
        Color.rgb(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))


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