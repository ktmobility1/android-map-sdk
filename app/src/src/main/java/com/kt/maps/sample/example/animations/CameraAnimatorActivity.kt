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
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityCameraAnimatorBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback

class CameraAnimatorActivity :
    BaseActivity<ActivityCameraAnimatorBinding>(R.layout.activity_camera_animator),
    OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private lateinit var map: KtMap

    private var interpolator: TimeInterpolator = FastOutSlowInInterpolator()
    private var cameraMoving: CameraMoving = CameraMoving.MOVE_TO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.map.getMapAsync(this)

        initSpinner()
        initFab()
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.apply {
            jumpTo(
                cameraOptions = CameraPositionOptions(
                    lngLat = LngLat(longitude = 126.97794, latitude = 37.57103),
                    zoom = 15.0
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
        currentZoom: Double,
        targetZoom: Double,
        interpolator: TimeInterpolator
    ): Animator {
        val zoomAnimator = ValueAnimator.ofFloat(currentZoom.toFloat(), targetZoom.toFloat())
        zoomAnimator.duration = ANIMATION_DURATION.toLong()
        zoomAnimator.interpolator = interpolator
        zoomAnimator.addUpdateListener { animation: ValueAnimator ->
            map.jumpTo(CameraPositionOptions(zoom = (animation.animatedValue as Float).toDouble()))
        }
        return zoomAnimator
    }

    private fun createBearingAnimator(
        currentBearing: Double,
        targetBearing: Double,
        interpolator: TimeInterpolator
    ): Animator {
        val bearingAnimator =
            ValueAnimator.ofFloat(currentBearing.toFloat(), targetBearing.toFloat())
        bearingAnimator.duration = ANIMATION_DURATION.toLong()
        bearingAnimator.interpolator = interpolator
        bearingAnimator.addUpdateListener { animation: ValueAnimator ->
            map.jumpTo(CameraPositionOptions(bearing = (animation.animatedValue as Float).toDouble()))
        }
        return bearingAnimator
    }

    private fun createPitchAnimator(
        currentPitch: Double,
        targetPitch: Double,
        interpolator: TimeInterpolator
    ): Animator {
        val pitchAnimator = ValueAnimator.ofFloat(currentPitch.toFloat(), targetPitch.toFloat())
        pitchAnimator.duration = ANIMATION_DURATION.toLong()
        pitchAnimator.interpolator = interpolator
        pitchAnimator.addUpdateListener { animation: ValueAnimator ->
            map.jumpTo(CameraPositionOptions(pitch = (animation.animatedValue as Float).toDouble()))
        }
        return pitchAnimator
    }

    companion object {
        private const val ANIMATION_DURATION = 3000
        private val START_POSITION = LngLat(longitude = 126.97794, latitude = 37.57103)
        private val TO_POSITION = LngLat(longitude = 127.02936, latitude = 37.47123)
        private const val START_ZOOM = 10.0
        private const val TO_ZOOM = 15.0
        private const val START_PITCH = 0.0
        private const val TO_PITCH = 60.0
        private const val START_BEARING = 0.0
        private const val TO_BEARING = 360.0
    }

    enum class CameraMoving {
        MOVE_TO, ZOOM_TO, PITCH_TO, BEARING_TO
    }


}