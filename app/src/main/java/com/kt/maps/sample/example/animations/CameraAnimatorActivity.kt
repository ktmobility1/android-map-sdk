package com.kt.maps.sample.example.animations

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.BounceInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityCameraAnimatorBinding

class CameraAnimatorActivity :
    BaseActivity<ActivityCameraAnimatorBinding>(R.layout.activity_camera_animator),
    OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    private var interpolator: TimeInterpolator = FastOutSlowInInterpolator()
    private var cameraMoving: CameraMoving = CameraMoving.MOVE_TO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@CameraAnimatorActivity)
        }

        initSpinner()
        initFab()
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            jumpTo(
                cameraOptions = CameraPositionOptions(
                    lngLat = LngLat(longitude = 126.97794, latitude = 37.57103),
                    zoom = 15f
                )
            )
        }
    }

    private fun initFab() {
        binding.fab.setOnClickListener { _ ->
            when (cameraMoving) {
                CameraMoving.MOVE_TO -> createLatLngAnimator(
                    START_POSITION,
                    TO_POSITION,
                    interpolator
                ).start()

                CameraMoving.ZOOM_TO -> createZoomAnimator(
                    START_ZOOM,
                    TO_ZOOM,
                    interpolator
                ).start()

                CameraMoving.PITCH_TO -> createPitchAnimator(
                    START_PITCH,
                    TO_PITCH,
                    interpolator
                ).start()

                CameraMoving.BEARING_TO -> createBearingAnimator(
                    START_BEARING,
                    TO_BEARING,
                    interpolator
                ).start()
            }
        }
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.interpolate_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.interpolateSpinner.adapter = adapter
        }
        binding.interpolateSpinner.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            this,
            R.array.movetype_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.movetypeSpinner.adapter = adapter
        }
        binding.movetypeSpinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        if (parent.id == binding.interpolateSpinner.id) {
            when (pos) {
                0 -> interpolator = AccelerateDecelerateInterpolator()
                1 -> interpolator = BounceInterpolator()
                2 -> interpolator = AnticipateInterpolator()
            }
        } else {
            when (pos) {
                0 -> cameraMoving = CameraMoving.MOVE_TO
                1 -> cameraMoving = CameraMoving.ZOOM_TO
                2 -> cameraMoving = CameraMoving.PITCH_TO
                3 -> cameraMoving = CameraMoving.BEARING_TO
            }
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>) {
    }

    private class LatLngEvaluator : TypeEvaluator<LngLat> {
        private val lngLat = LngLat()
        override fun evaluate(fraction: Float, startValue: LngLat, endValue: LngLat): LngLat {
            lngLat.latitude =
                startValue.latitude + (endValue.latitude - startValue.latitude) * fraction
            lngLat.longitude =
                startValue.longitude + (endValue.longitude - startValue.longitude) * fraction
            return lngLat
        }
    }

    private fun createLatLngAnimator(
        currentPosition: LngLat,
        targetPosition: LngLat,
        interpolator: TimeInterpolator
    ): Animator {
        val latLngAnimator =
            ValueAnimator.ofObject(LatLngEvaluator(), currentPosition, targetPosition)
        latLngAnimator.duration = ANIMATION_DURATION.toLong()
        latLngAnimator.interpolator = interpolator
        latLngAnimator.addUpdateListener { animation: ValueAnimator ->
            map.jumpTo(CameraPositionOptions(lngLat = animation.animatedValue as LngLat))
        }
        return latLngAnimator
    }

    private fun createZoomAnimator(
        currentZoom: Float,
        targetZoom: Float,
        interpolator: TimeInterpolator
    ): Animator {
        val zoomAnimator = ValueAnimator.ofFloat(currentZoom.toFloat(), targetZoom.toFloat())
        zoomAnimator.duration = ANIMATION_DURATION.toLong()
        zoomAnimator.interpolator = interpolator
        zoomAnimator.addUpdateListener { animation: ValueAnimator ->
            map.jumpTo(CameraPositionOptions(zoom = (animation.animatedValue as Float)))
        }
        return zoomAnimator
    }

    private fun createBearingAnimator(
        currentBearing: Float,
        targetBearing: Float,
        interpolator: TimeInterpolator
    ): Animator {
        val bearingAnimator =
            ValueAnimator.ofFloat(currentBearing, targetBearing)
        bearingAnimator.duration = ANIMATION_DURATION.toLong()
        bearingAnimator.interpolator = interpolator
        bearingAnimator.addUpdateListener { animation: ValueAnimator ->
            map.jumpTo(CameraPositionOptions(bearing = (animation.animatedValue as Float)))
        }
        return bearingAnimator
    }

    private fun createPitchAnimator(
        currentPitch: Float,
        targetPitch: Float,
        interpolator: TimeInterpolator
    ): Animator {
        val pitchAnimator = ValueAnimator.ofFloat(currentPitch, targetPitch)
        pitchAnimator.duration = ANIMATION_DURATION.toLong()
        pitchAnimator.interpolator = interpolator
        pitchAnimator.addUpdateListener { animation: ValueAnimator ->
            map.jumpTo(CameraPositionOptions(pitch = (animation.animatedValue as Float)))
        }
        return pitchAnimator
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
        private const val ANIMATION_DURATION = 3000
        private val START_POSITION = LngLat(longitude = 126.97794, latitude = 37.57103)
        private val TO_POSITION = LngLat(longitude = 127.02936, latitude = 37.47123)
        private const val START_ZOOM = 10f
        private const val TO_ZOOM = 15f
        private const val START_PITCH = 0f
        private const val TO_PITCH = 60f
        private const val START_BEARING = 0f
        private const val TO_BEARING = 360f
    }

    enum class CameraMoving {
        MOVE_TO, ZOOM_TO, PITCH_TO, BEARING_TO
    }
}