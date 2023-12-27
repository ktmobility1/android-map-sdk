package com.kt.maps.sample.example.base

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.kt.maps.KtMap
import com.kt.maps.KtMapOptions
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityMaptypeBinding

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

        // 지도 타입 선택을 위한 Spinner
        binding.spinnerMapType.run {
            // 지도 타입 item 설정
            adapter = ArrayAdapter(
                this@MapTypeActivity,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                KtMapOptions.MapType.values()
            )
            // 지도 타입 기본값으로 반영
            setSelection(map.getMapType().ordinal)

            // spinner item 선택시 지도 타입 변경
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}