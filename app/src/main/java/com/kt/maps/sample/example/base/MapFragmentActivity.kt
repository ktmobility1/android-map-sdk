package com.kt.maps.sample.example.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kt.maps.KtMap
import com.kt.maps.MapFragment
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.sample.R

class MapFragmentActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map_fragment)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment().newInstance().also {
                supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(ktmap: KtMap) {
    }


}
