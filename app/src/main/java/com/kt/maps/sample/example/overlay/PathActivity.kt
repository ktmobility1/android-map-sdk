package com.kt.maps.sample.example.overlay

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.extensions.path.ArrowOverlay
import com.kt.maps.extensions.path.ArrowOverlayOptions
import com.kt.maps.extensions.path.PathOverlay
import com.kt.maps.extensions.path.PathOverlayOptions
import com.kt.maps.extensions.path.addArrowPathOverlay
import com.kt.maps.extensions.path.addPathOverlay
import com.kt.maps.extensions.path.removeArrowPathOverlay
import com.kt.maps.extensions.path.removePathOverlay
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityPathBinding

class PathActivity : BaseActivity<ActivityPathBinding>(R.layout.activity_path),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    private var path: PathOverlay? = null
    private var arrow: ArrowOverlay? = null

    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@PathActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.apply {
            path?.let {
                removePathOverlay(it)
            }
            path = addPathOverlay(
                PathOverlayOptions.Builder().apply {
                    links(LINKS)
                    linkColors(LINKS.map { randomColor() })
                    color(Color.GRAY)
                    width(16)
                    strokeColor(Color.BLACK)
                    strokeWidth(2)
                    pattern(
                        AppCompatResources.getDrawable(
                            this@PathActivity,
                            R.drawable.path_pattern
                        )
                    )
                }.build()
            )
        }

        // 인덱스 0으로 이동
        updateArrowOverlay(0)

        // 다음 버튼 탭한 경우 현재 인덱스 + 1로 이동
        binding.buttonArrowNext.setOnClickListener {
            if (index + 2 <= LINKS.size - 1)
                index++
            else
                return@setOnClickListener
            updateArrowOverlay(index)
        }

        // 이전 버튼 탭한 경우 현재 인덱스 - 1로 이동
        binding.buttonArrowPrev.setOnClickListener {
            if (index - 1 >= 0)
                index--
            else
                return@setOnClickListener
            updateArrowOverlay(index)
        }

        binding.buttonPathChangeColor.setOnClickListener {
            path?.linkColors(LINKS.map { randomColor(range = (Math.random() * COLOR_RANGE).toInt()) })
        }
    }

    /**
     *  화살표 Overlay 변경
     *  @param index path index
     */
    private fun updateArrowOverlay(index: Int) {
        arrow?.let {
            map.removeArrowPathOverlay(it)
        }

        arrow = map.addArrowPathOverlay(
            ArrowOverlayOptions.Builder().apply {
                lngLats(LINKS[index] + LINKS[index + 1])
                color(Color.LTGRAY)
                width(16)
                strokeColor(Color.BLACK)
                strokeWidth(2)
            }.build()
        )

        map.jumpTo(
            cameraOptions = CameraPositionOptions(zoom = 15f, lngLat = LINKS[index + 1][0])
        )
    }

    /**
     * @return @ColorInt
     */
    private fun randomColor(range: Int = 4): Int {
        return when ((Math.random() * range).toInt()) {
            0 -> Color.RED
            // orange
            1 -> Color.parseColor("#FFA500")
            2 -> Color.GREEN
            3 -> Color.YELLOW
            4 -> Color.WHITE
            5 -> Color.LTGRAY
            6 -> Color.DKGRAY
            7 -> Color.BLACK
            else -> Color.GRAY
        }
    }

    companion object {
        const val COLOR_RANGE = 8

        val LINKS = listOf(
            listOf(
                LngLat(latitude = 37.55320682259445, longitude = 126.9727695192017),
                LngLat(latitude = 37.55366600696644, longitude = 126.97289865443454)
            ),
            listOf(
                LngLat(latitude = 37.55366600696644, longitude = 126.97289865443454),
                LngLat(latitude = 37.55384673377112, longitude = 126.97294642354748),
                LngLat(latitude = 37.554076759518686, longitude = 126.9730140696073)
            ),
            listOf(
                LngLat(latitude = 37.55407675952285, longitude = 126.97301406961026),
                LngLat(latitude = 37.55425693306084, longitude = 126.97306028823073)
            ),
            listOf(
                LngLat(latitude = 37.55425693305634, longitude = 126.97306028823077),
                LngLat(latitude = 37.554567615317104, longitude = 126.97307771887283)
            ),
            listOf(
                LngLat(latitude = 37.554567615317104, longitude = 126.97307771887283),
                LngLat(latitude = 37.55497410712508, longitude = 126.97310052504895),
                LngLat(latitude = 37.555602108027095, longitude = 126.97311903763712)
            ),
            listOf(
                LngLat(latitude = 37.555602108027095, longitude = 126.97311903763712),
                LngLat(latitude = 37.55594542411706, longitude = 126.97312065059329),
                LngLat(latitude = 37.55603523215585, longitude = 126.97311397298779),
                LngLat(latitude = 37.55611700122478, longitude = 126.97310332177577),
                LngLat(latitude = 37.556222788277694, longitude = 126.97308242592811),
                LngLat(latitude = 37.55633223536163, longitude = 126.97304859377809),
                LngLat(latitude = 37.556415480723906, longitude = 126.97301961440316),
                LngLat(latitude = 37.556590820240906, longitude = 126.97295631306882)
            ),
            listOf(
                LngLat(latitude = 37.55659082024244, longitude = 126.97295631306869),
                LngLat(latitude = 37.556678754115254, longitude = 126.97294439408762)
            ),
            listOf(
                LngLat(latitude = 37.55667875411429, longitude = 126.97294439408974),
                LngLat(latitude = 37.556975394155366, longitude = 126.9729131923942),
                LngLat(latitude = 37.55706527562377, longitude = 126.97290473347815),
                LngLat(latitude = 37.55718735178771, longitude = 126.97289324462308),
                LngLat(latitude = 37.55726966876823, longitude = 126.97289139202248)
            ),
            listOf(
                LngLat(latitude = 37.55726966876823, longitude = 126.97289139202248),
                LngLat(latitude = 37.55733626201136, longitude = 126.97292503376518),
                LngLat(latitude = 37.55740633539673, longitude = 126.97297407409239),
                LngLat(latitude = 37.55747530737832, longitude = 126.973028783291),
                LngLat(latitude = 37.55787708693099, longitude = 126.97340696683148)
            ),
            listOf(
                LngLat(latitude = 37.55787708693099, longitude = 126.97340696683148),
                LngLat(latitude = 37.55858381150991, longitude = 126.97403283550557)
            ),
            listOf(
                LngLat(latitude = 37.55858381150991, longitude = 126.97403283550557),
                LngLat(latitude = 37.55896537349838, longitude = 126.97435629557542),
                LngLat(latitude = 37.55911919157572, longitude = 126.97448333006008)
            ),
            listOf(
                LngLat(latitude = 37.55911919157572, longitude = 126.97448333006008),
                LngLat(latitude = 37.55922006374338, longitude = 126.97456249235606),
                LngLat(latitude = 37.55942539007729, longitude = 126.97466523356537),
                LngLat(latitude = 37.559523271682345, longitude = 126.97469006654444),
                LngLat(latitude = 37.5596342464413, longitude = 126.97471422014543)
            ),
            listOf(
                LngLat(latitude = 37.5596342464413, longitude = 126.97471422014543),
                LngLat(latitude = 37.55976778922709, longitude = 126.97474569434816),
                LngLat(latitude = 37.55982498009752, longitude = 126.97476306728106),
                LngLat(latitude = 37.55991319761093, longitude = 126.97479597282252),
                LngLat(latitude = 37.559982541770914, longitude = 126.97482907680408),
                LngLat(latitude = 37.560058696014096, longitude = 126.97487136076845),
                LngLat(latitude = 37.56012876229174, longitude = 126.97491898942005),
                LngLat(latitude = 37.56018980906113, longitude = 126.97496526614479),
                LngLat(latitude = 37.560254273519234, longitude = 126.97502001081533),
                LngLat(latitude = 37.56032214306975, longitude = 126.97508039292961),
                LngLat(latitude = 37.56038711350108, longitude = 126.97514218062521)
            ),
            listOf(
                LngLat(latitude = 37.56038711350108, longitude = 126.97514218062521),
                LngLat(latitude = 37.56059189484163, longitude = 126.97533508999247)
            ),
            listOf(
                LngLat(latitude = 37.56059189484163, longitude = 126.97533508999247),
                LngLat(latitude = 37.5612252323772, longitude = 126.97587413426143)
            ),
            listOf(
                LngLat(latitude = 37.5612252323772, longitude = 126.97587413426143),
                LngLat(latitude = 37.561835315520455, longitude = 126.97611038302021)
            ),
            listOf(
                LngLat(latitude = 37.561835315520455, longitude = 126.97611038302021),
                LngLat(latitude = 37.56199332273405, longitude = 126.97617155161987)
            ),
            listOf(
                LngLat(latitude = 37.56199332273405, longitude = 126.97617155161987),
                LngLat(latitude = 37.56214262427972, longitude = 126.97622798251541),
                LngLat(latitude = 37.562297445306015, longitude = 126.9762864998325)
            ),
            listOf(
                LngLat(latitude = 37.562297445306015, longitude = 126.9762864998325),
                LngLat(latitude = 37.56259259682391, longitude = 126.9763980582096)
            ),
            listOf(
                LngLat(latitude = 37.56259259682391, longitude = 126.9763980582096),
                LngLat(latitude = 37.563123038184365, longitude = 126.97660098874447)
            ),
            listOf(
                LngLat(latitude = 37.563123038184365, longitude = 126.97660098874447),
                LngLat(latitude = 37.5635328564296, longitude = 126.97675735667534)
            ),
            listOf(
                LngLat(latitude = 37.5635328564296, longitude = 126.97675735667534),
                LngLat(latitude = 37.56357108952365, longitude = 126.97677194476886)
            ),
            listOf(
                LngLat(latitude = 37.56357108952365, longitude = 126.97677194476886),
                LngLat(latitude = 37.56398954116146, longitude = 126.97691099230948)
            ),
            listOf(
                LngLat(latitude = 37.56398954116146, longitude = 126.97691099230948),
                LngLat(latitude = 37.56448919784364, longitude = 126.9770708728453)
            ),
            listOf(
                LngLat(latitude = 37.56448919784364, longitude = 126.9770708728453),
                LngLat(latitude = 37.56459784507068, longitude = 126.97710445730687)
            ),
            listOf(
                LngLat(latitude = 37.564597845070054, longitude = 126.97710445730736),
                LngLat(latitude = 37.56478849320596, longitude = 126.97715832509839)
            ),
            listOf(
                LngLat(latitude = 37.564788493206606, longitude = 126.97715832509788),
                LngLat(latitude = 37.56501037083583, longitude = 126.97719537663178)
            ),
            listOf(
                LngLat(latitude = 37.56501037083583, longitude = 126.97719537663178),
                LngLat(latitude = 37.56596349439521, longitude = 126.97717583536038)
            ),
            listOf(
                LngLat(latitude = 37.56596349439521, longitude = 126.97717583536038),
                LngLat(latitude = 37.56664301344009, longitude = 126.97715810193179)
            ),
            listOf(
                LngLat(latitude = 37.56664301344009, longitude = 126.97715810193179),
                LngLat(latitude = 37.56705018058144, longitude = 126.97714721680308)
            ),
            listOf(
                LngLat(latitude = 37.56705018058144, longitude = 126.97714721680308),
                LngLat(latitude = 37.56716284542823, longitude = 126.97714642929321)
            ),
            listOf(
                LngLat(latitude = 37.56716284542823, longitude = 126.97714642929321),
                LngLat(latitude = 37.56737688355659, longitude = 126.97713927141373)
            ),
            listOf(
                LngLat(latitude = 37.56737688355659, longitude = 126.97713927141373),
                LngLat(latitude = 37.5679356384548, longitude = 126.97712121111405)
            ),
            listOf(
                LngLat(latitude = 37.5679356384548, longitude = 126.97712121111405),
                LngLat(latitude = 37.567989565871756, longitude = 126.97711961095979)
            ),
            listOf(
                LngLat(latitude = 37.567989565871756, longitude = 126.97711961095979),
                LngLat(latitude = 37.56861443493683, longitude = 126.97709619598069)
            ),
            listOf(
                LngLat(latitude = 37.56861443493683, longitude = 126.97709619598069),
                LngLat(latitude = 37.56884196143737, longitude = 126.97708186611496)
            ),
            listOf(
                LngLat(latitude = 37.56884196143737, longitude = 126.97708186611496),
                LngLat(latitude = 37.56906747740946, longitude = 126.97706723082283)
            ),
            listOf(
                LngLat(latitude = 37.56906747740946, longitude = 126.97706723082283),
                LngLat(latitude = 37.56926157501101, longitude = 126.97705563439489)
            ),
            listOf(
                LngLat(latitude = 37.56926157501101, longitude = 126.97705563439489),
                LngLat(latitude = 37.56941457573099, longitude = 126.97707963741969)
            ),
            listOf(
                LngLat(latitude = 37.56941457573099, longitude = 126.97707963741969),
                LngLat(latitude = 37.56961759281465, longitude = 126.97711248224448)
            ),
            listOf(
                LngLat(latitude = 37.569617592811674, longitude = 126.97711248224455),
                LngLat(latitude = 37.569754577315344, longitude = 126.97713464418288)
            ),
            listOf(
                LngLat(latitude = 37.569754577315344, longitude = 126.97713464418288),
                LngLat(latitude = 37.57014610823097, longitude = 126.9771765448699)
            ),
            listOf(
                LngLat(latitude = 37.57014610823097, longitude = 126.9771765448699),
                LngLat(latitude = 37.5702971384804, longitude = 126.97719270787199)
            ),
            listOf(
                LngLat(latitude = 37.5702971384804, longitude = 126.97719270787199),
                LngLat(latitude = 37.57067095315965, longitude = 126.97728134331632)
            ),
            listOf(
                LngLat(latitude = 37.57067095315965, longitude = 126.97728134331632),
                LngLat(latitude = 37.571629154215245, longitude = 126.97731335581231)
            ),
            listOf(
                LngLat(latitude = 37.571629154215245, longitude = 126.97731335581231),
                LngLat(latitude = 37.572006799816506, longitude = 126.97729812882523)
            )
        )
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