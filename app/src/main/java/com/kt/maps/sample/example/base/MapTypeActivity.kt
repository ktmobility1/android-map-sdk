package com.kt.maps.sample.example.base

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityMaptypeBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.KtMapOptions
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback

class MapTypeActivity : BaseActivity<ActivityMaptypeBinding>(R.layout.activity_maptype),
    OnMapReadyCallback {
    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@MapTypeActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap
        // MapType 기본값 적용
        map.setMapType(KtMapOptions.MapType.values()[MAP_TYPE_INDEX])

        // MapType 선택을 위한 Spinner
        binding.spinnerMapType.apply {
            // MapType item 설정
            adapter = ArrayAdapter(
                this@MapTypeActivity,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                KtMapOptions.MapType.values()
            )
            // MapType 기본값 반영
            setSelection(MAP_TYPE_INDEX)

            // spinner item 선택시 MapType 변경
            onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        map.setMapType(KtMapOptions.MapType.values()[position])
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
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

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    companion object {
        const val MAP_TYPE_INDEX = 0
    }
}