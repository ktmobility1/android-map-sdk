package com.kt.maps.sample.ui.screen


import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kt.maps.sample.MainActivity
import com.kt.maps.sample.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StartScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val _screenState = MutableStateFlow<StartScreenState>(StartScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    private val _intentEvent = MutableSharedFlow<Intent>()
    val intentEvent = _intentEvent.asSharedFlow()

    private val _permissionEvent = MutableSharedFlow<Boolean>()
    val permissionEvent = _permissionEvent.asSharedFlow()

    private var storedExample : Example? = null

    /**
     * Package
     */
    private val examples: List<Example> = createExamples(
        application.packageManager
            .getPackageInfo(application.packageName, PackageManager.GET_ACTIVITIES or PackageManager.GET_META_DATA)
            .activities
            .filterNot { !it.name.startsWith(application.packageName) || it.name.contains(MainActivity::class.java.name) },
        application
    )

    init {
        _screenState.value = StartScreenState.Data(examples)
    }

    fun sendUiEvent(event: StartScreenEvent) {
        when (event) {
            is StartScreenEvent.ClickExample -> {
                if (event.example.needPermission) {
                    storedExample = event.example
                    emitPermissionEvent()
                } else {
                    emitIntentEvent(event.example)
                }
            }

            is StartScreenEvent.PermissionChecked -> {
                storedExample?.let {
                    emitIntentEvent(it)
                }
            }
        }
    }

    private fun emitIntentEvent(example: Example) {
        viewModelScope.launch {
            _intentEvent.emit(example.toIntent())
        }
    }

    private fun emitPermissionEvent() {
        viewModelScope.launch {
            _permissionEvent.emit(true)
        }
    }

    private fun createExamples(activityInfos: List<ActivityInfo>, context: Context): List<Example> {
        return activityInfos.map {
            Example(
                category = it.upperPackageName(),
                title = context.getString(it.labelRes),
                description = context.getString(it.descriptionRes),
                packageName = it.packageName,
                activityName = it.name,
                needPermission = it.isNeedPermission(context)
            )
        }
    }

    private fun Example.toIntent(): Intent = Intent().apply {
        component = ComponentName(packageName, activityName)
    }

    private fun ActivityInfo.upperPackageName(): String = this.name
        .split(".")
        .takeIf {
            it.size >= 2
        }?.let {
            it[it.size - 2]
        } ?: ""

    private fun ActivityInfo.isNeedPermission(context: Context): Boolean = this.metaData?.run {
        getBoolean(context.getString(R.string.activity_need_permission), false)
    } ?: false
}
