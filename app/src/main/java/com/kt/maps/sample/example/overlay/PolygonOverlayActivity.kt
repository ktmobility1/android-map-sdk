package com.kt.maps.sample.example.overlay

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.gesture.OnMapTapListener
import com.kt.maps.gesture.addOnMapTapListener
import com.kt.maps.overlay.marker.Marker
import com.kt.maps.overlay.marker.MarkerOptions
import com.kt.maps.overlay.polygon.PolygonOverlay
import com.kt.maps.overlay.polygon.PolygonOverlayOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityPolygonOverlayBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback
import kotlin.random.Random

class PolygonOverlayActivity :
    BaseActivity<ActivityPolygonOverlayBinding>(R.layout.activity_polygon_overlay),
    OnMapReadyCallback,
    OnMapTapListener {

    private lateinit var map: KtMap

    private val outerPoints = mutableListOf<LngLat>()
    private val innerPoints = mutableListOf<LngLat>()
    private val innerPointsGroup = mutableListOf<List<LngLat>>()
    private val polygons = mutableListOf<PolygonOverlay>()
    private val markers = mutableListOf<Marker>()

    private lateinit var outerBitmap: Bitmap
    private lateinit var innerBitmap: Bitmap
    private lateinit var innerBitmapDone: Bitmap

    private var clickMode = ClickMode.Outer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        innerBitmap = R.drawable.pin_yello_dot.toBitmap()
        innerBitmapDone = R.drawable.pin_blue_dot.toBitmap()
        outerBitmap = R.drawable.pin_red_dot.toBitmap()

        binding.run {
            map.getMapAsync(this@PolygonOverlayActivity)
            btnCreate.setOnClickListener { createPolygon() }
            btnRemove.setOnClickListener { removeLastPolygon() }
            btnNew.setOnClickListener { newPinGroup() }
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.radio_outer -> changeOuterClickMode()
                    R.id.radio_inner -> changeInnerClickMode()
                }
            }
        }
        invalidateButtons()
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.apply {
            addOnMapTapListener(this@PolygonOverlayActivity)

            jumpTo(
                cameraOptions = CameraPositionOptions(
                    zoom = 15.0,
                    lngLat = LngLat(longitude = 126.97794, latitude = 37.57103)
                )
            )

            addOverlay(
                PolygonOverlayOptions.Builder().apply {
                    lngLats(POLYGON_OVERLAY_1_LNGLATS)
                    color(Color.GREEN)
                    holes(POLYGON_OVERLAY_1_HOLES)
                }.build()
            )

            addOverlay(
                PolygonOverlayOptions.Builder().apply {
                    lngLats(POLYGON_OVERLAY_2_LNGLATS)
                    color(Color.YELLOW)
                    outlineColor(Color.RED)
                    opacity(0.5f)
                }.build()
            )

        }

    }

    override fun onTap(point: PointF, lngLat: LngLat): Boolean {
        addMarker(lngLat)
        addLngLatInPoints(lngLat)
        invalidateButtons()
        return true
    }

    private fun createPolygon() {
        movePointsToGroup()

        map.addOverlay(
            PolygonOverlayOptions.Builder().apply {
                lngLats(outerPoints)
                holes(innerPointsGroup)
                color(randomColor())
            }.build()
        ).let {
            polygons.add(it)
        }

        removeAllMarkers()
        clearPinDatas()
        invalidateButtons()
    }

    private fun removeLastPolygon() {
        if (polygons.isNotEmpty()) {
            map.removeOverlay(polygons.last())
            polygons.removeLast()
        }
    }

    private fun newPinGroup() {
        val points = innerPoints.toMutableList()
        movePointsToGroup()
        changeMarkerColors(points)
    }

    private fun changeInnerClickMode() {
        clickMode = ClickMode.Inner
        invalidateButtons()
    }

    private fun changeOuterClickMode() {
        clickMode = ClickMode.Outer
        invalidateButtons()
    }

    private fun addLngLatInPoints(lngLat: LngLat) {
        when (clickMode) {
            ClickMode.Inner -> innerPoints.add(lngLat)
            ClickMode.Outer -> outerPoints.add(lngLat)
        }
    }

    private fun clearPinDatas() {
        outerPoints.clear()
        innerPoints.clear()
        innerPointsGroup.clear()
    }


    private fun addMarker(lngLat: LngLat) {
        map.addOverlay(
            MarkerOptions.Builder().apply {
                position(lngLat)
                icon(clickMode.matchBitmap())
            }.build()
        ).let {
            markers.add(it)
        }
    }

    private fun changeMarkerColors(list: List<LngLat>) {
        markers.filter {
            list.contains(it.position)
        }.forEach {
            it.icon = innerBitmapDone
        }
    }

    private fun removeAllMarkers() {
        map.removeOverlays(markers)
        markers.clear()
    }

    private fun movePointsToGroup() {
        innerPointsGroup.add(innerPoints.toMutableList())
        innerPoints.clear()
    }

    private fun invalidateButtons() {
        binding.btnCreate.isEnabled = outerPoints.size >= 3
        binding.btnNew.isEnabled = clickMode == ClickMode.Inner && innerPoints.size >= 3
    }

    private fun randomColor(): Int =
        Color.rgb(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))

    private fun Int.toBitmap(): Bitmap {
        val drawable = AppCompatResources.getDrawable(this@PolygonOverlayActivity, this)!!
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

    private fun ClickMode.matchBitmap(): Bitmap {
        return when (this) {
            ClickMode.Inner -> innerBitmap
            ClickMode.Outer -> outerBitmap
        }
    }

    companion object {
        private val POLYGON_OVERLAY_1_LNGLATS = listOf(
            LngLat(latitude = 37.57427, longitude = 126.97796),
            LngLat(latitude = 37.57256, longitude = 126.97513),
            LngLat(latitude = 37.57067, longitude = 126.97664),
            LngLat(latitude = 37.57079, longitude = 126.97924),
            LngLat(latitude = 37.57264, longitude = 126.97958)
        )
        private val POLYGON_OVERLAY_1_HOLES = listOf(
            listOf(
                LngLat(latitude = 37.57316, longitude = 126.97783),
                LngLat(latitude = 37.57171, longitude = 126.97695),
                LngLat(latitude = 37.57159, longitude = 126.97855)
            )
        )

        private val POLYGON_OVERLAY_2_LNGLATS = listOf(
            LngLat(latitude = 37.56945, longitude = 126.97815),
            LngLat(latitude = 37.56974, longitude = 126.98025),
            LngLat(latitude = 37.56863, longitude = 126.98005),
            LngLat(latitude = 37.56842, longitude = 126.97795)
        )
    }

    enum class ClickMode {
        Inner, Outer
    }

}