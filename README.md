# KT Map SDK For Android <img alt="Android" src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=Android&logoColor=white">

## 개요

- KT Map SDK를 사용해서 Android 단말에서 지도 데이터, 지도 표시, 지도 동작을 지원합니다.
- 지도 데이터는 KT Map에서 제공하는 배경, 도로, 항공지도 등애 대한 데이터이며, 특정 좌표, 영역을 표시/강조하기 위한 다양한 Overlay를 제공합니다.
- 지도 동작은 기본으로 제공하는 UIControl 및 사용자 터치를 통해 지도 확대/축소, 회전, 기울기, 지도 이동을 편리하고 직관적으로 제공합니다.
- 또한 사용자 데이터를 KT Map에 올리고, 스타일을 적용할 수 있는 레이어를 제공합니다.

## 준비하기

- KT Map SDK for Android는 API KEY를 발급 받아야 사용할 수 있습니다.
- KEY를 발급받기 위해서는 KT API 사이트 계정이 필요합니다.

### 키 발급 방법
1. [KT API 사이트](https://apilink.kt.co.kr/) 접속
2. 계정 가입 및 로그인
3. 권한신청 메뉴로 이동
4. API 신청 > 일반개발자 | 개인사업자 | 법인사업자 | GiGA Genie 제휴법인 중 해당 소속 선택
5. 정보입력 후 권한 신청

## 지원

### 지원 OS 버전
| 구분 | Version                                                                     | API Level                                                        |
|-|-----------------------------------------------------------------------------|------------------------------------------------------------------|
| 최소버전 | [Android 6](https://www.android.com/versions/marshmallow-6-0/)(Marshmallow) | [23](https://developer.android.com/tools/releases/platforms#6.0) |
| 권장버전 | [Android 13](https://developer.android.com/about/versions/13) (TIRAMISU)    | [33](https://developer.android.com/tools/releases/platforms#13)                                                           |

### 지원 언어
- KT Map SDK for Android 에서는 [Kotlin](https://developer.android.com/kotlin) 과 Java 모두를 지원하고 있습니다.
- 현재 [Kotlin](https://developer.android.com/kotlin) 사용을 권장합니다.

## 시작하기

### Manifest 설정
- KT Map SDK는 지도 서버와 네트워크로 통신하며 지도 데이터를 Android 단말에 저장하고 지도를 표시하고 있습니다.
- 따라서 앱에서 네트워크 연결 권한이 필요하기 때문에 Manifest에서 권한을 추가헙니다.

```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/> <!-- Always include this permission -->
```

- 추가로 현재 위치 정보를 이용하기 위해서는 위치 정보에 대한 권한을 추가합니다.

```xml
<uses-permission
android:name="android.permission.ACCESS_COARSE_LOCATION"/> <!-- Include only if your app benefits from precise location access. -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>

```

### Gradle 설정
- maven repository를 통해 KT Map SDK 라이브러리를 제공하지 않습니다.

#### **`build.gradle.kt`**
```
implementation("com.kt.maps:sdk:2.0.0-alpha2)
// Path,ArraowOverlay 사용
implementation("com.kt.maps:extension-path:2.0.0-alpha2)
```

### Key 등록
- 발급 받은 키를 KT Map SDK에 등록합니다.
- Key 등록 가이드는 추가로 제공할 예정 (현재는 베타 버전이라 등록하지 않음)


## 지도 추가하기

- 지도를 Android 단말에 표시하기 위해 지도 추가해야합니다.

### View 방식

- layout.xml에 KT MapView를 추가합니다.

#### **`layout/view_activity.xml`**
```xml
<com.kt.maps.sdk.MapView
  android:id="@+id/map"
  android:layout_width="match_parent"
  android:layout_height="match_parent"/>

```

- Activity에서 MapView를 찾고 해당 MapView를 통해 KtMap 객체를 얻어옵니다.
- MapView는 지도를 표시하는 역할을 수행하고, 실제 지도에 대한 제어를 위해 KtMap 객체를 사용합니다.

#### **`ViewActivity.kt`**
```
    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView = findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap
    }
```


## Reference
더 자세한 사항은 아래 링크를 참고하시기 바랍니다.

- [개발 가이드](https://map.gis.kt.com/mapsdk/android/tutorial/)
- [API 레퍼런스](https://map.gis.kt.com/mapsdk/android/apidoc/)
- [Releases](https://github.com/ktmobility1/android-map-sdk/releases)

## License

Copyright (c) 2023 kt corp. All rights reserved.

This is a proprietary software of kt corp, and you may not use this file
except in compliance with license agreement with kt corp. Any redistribution
or use of this software, with or without modification shall be strictly
prohibited without prior written approval of kt corp, and the copyright
notice above does not evidence any actual or intended publication of such
software.