package com.pardess.directions.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraAnimation
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelLayer
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.label.LabelTextBuilder
import com.kakao.vectormap.label.LabelTextStyle
import com.kakao.vectormap.label.LabelTransition
import com.kakao.vectormap.label.Transition
import com.kakao.vectormap.route.RouteLine
import com.kakao.vectormap.route.RouteLineLayer
import com.kakao.vectormap.route.RouteLineOptions
import com.kakao.vectormap.route.RouteLineSegment
import com.kakao.vectormap.route.RouteLineStyle
import com.pardess.directions.R
import com.pardess.directions.domain.model.Paths
import com.pardess.directions.domain.model.TrafficState
import com.pardess.directions.not_use.LocationListViewModel
import com.pardess.directions.not_use.clickWithCoroutine
import com.pardess.directions.presentation.theme.DirectionsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity : ComponentActivity() {

    private lateinit var layer: RouteLineLayer
    private lateinit var multiStyleLine: RouteLine
    private lateinit var labelLayer: LabelLayer
    private lateinit var kakaoMap: KakaoMap
    private val viewModel by viewModels<LocationListViewModel>()

    private var mapView: MapView? = null

    private val duration = 500
//    private var second = 32652L
//    private var distance = 24427L

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView = MapView(this)

        setContent {
            mapView = remember { MapView(this) }
            DirectionsTheme {
                var showBottomSheet by remember { mutableStateOf(true) }
                val bottomSheetState = rememberModalBottomSheetState(
                    skipPartiallyExpanded = true
                )

                var paths by remember { mutableStateOf(Paths("", "")) }

                var count by remember { mutableStateOf(0) }
                LaunchedEffect(count) {
                    println(count)
                }

                LaunchedEffect(viewModel.routeInfo) {
                    println("@@@@@" + viewModel.routeInfo.toString())
                }

                viewModel.routeLineList.observe(this) {
                    if (it.isNotEmpty()) {
                        println("@@@@ viewModel.routeLineList.observe")
                        drawRouteLine(
                            paths.origin,
                            paths.destination
                        )
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        ExtendedFloatingActionButton(
                            text = { Text("길찾기") },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_car),
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                showBottomSheet = true
                            },
                        )
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it.calculateTopPadding())
                    ) {
                        LectureKakaoMap(
                            mapView = mapView!!,
                            viewModel = viewModel,
                            locationX = 126.97227318174524,
                            locationY = 37.55595957732287
                        )

                        NavigateInfoOverlay(
                            viewModel = viewModel,
                            modifier = Modifier.align(Alignment.BottomStart),
                        )

                        if (showBottomSheet) {
                            BottomSheetSection(
                                viewModel = viewModel,
                                bottomSheetState = bottomSheetState,
                                setShowBottomSheet = { showStatus ->
                                    count++
                                    showBottomSheet = showStatus
                                },
                                setPaths = { pair ->
                                    paths = Paths(pair.first, pair.second)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun NavigateInfoOverlay(
        viewModel: LocationListViewModel,
        modifier: Modifier
    ) {
        var routeInfoPair by remember { mutableStateOf(Pair("", "")) }

        LaunchedEffect(viewModel.routeInfo) {
            routeInfoPair = Utils.setRouteInfo(viewModel.routeInfo)
        }
        Column(
            modifier = modifier
                .width(220.dp)
                .padding(vertical = 10.dp, horizontal = 5.dp)
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Gray.copy(0.85f)
                )
                .padding(16.dp)
        ) {
            Row {
                Text(
                    text = routeInfoPair.first,
                    color = Color(0xFFFFD700),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = routeInfoPair.second,
                color = Color(0xFFFFD700),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    private fun drawRouteLine(
        origin: String,
        destination: String,
    ) {

        val routeSegmentList = mutableListOf<RouteLineSegment>()

        println("@@@@@ drawRouteLine()")

        if (::multiStyleLine.isInitialized) {
            layer.remove(multiStyleLine)
        }

        if (::labelLayer.isInitialized) {
            labelLayer.remove(labelLayer.getLabel("start_label"))
            labelLayer.remove(labelLayer.getLabel("end_label"))
        }

        viewModel.routeLineList.value?.forEach {
            val style = when (it.trafficState) {
                TrafficState.UNKNOWN -> TrafficState.UNKNOWN.styleRes
                TrafficState.JAM -> TrafficState.JAM.styleRes
                TrafficState.DELAY -> TrafficState.DELAY.styleRes
                TrafficState.SLOW -> TrafficState.SLOW.styleRes
                TrafficState.NORMAL -> TrafficState.NORMAL.styleRes
                TrafficState.BLOCK -> TrafficState.BLOCK.styleRes
            }

            val routeLineStyle = RouteLineStyle.from(this@TestActivity, style)

            routeSegmentList.add(
                RouteLineSegment.from(
                    it.wayList, routeLineStyle
                )
            )
        }

        val options = RouteLineOptions.from(
            routeSegmentList
        )

        multiStyleLine = layer.addRouteLine(options)

        setLabelWithText(
            "start_label",
            "출발",
            origin,
            routeSegmentList[0].lats[0],
            routeSegmentList[0].lngs[0]
        )

        setLabelWithText(
            "end_label",
            "도착",
            destination,
            routeSegmentList.last().lats.last(),
            routeSegmentList.last().lngs.last()
        )

        val cameraLatitude = (routeSegmentList[0].lats[0] + routeSegmentList.last().lats.last()) / 2
        val cameraLongitude =
            (routeSegmentList[0].lngs[0] + routeSegmentList.last().lngs.last()) / 2

        kakaoMap.moveCamera(
            CameraUpdateFactory.newCenterPosition(
                LatLng.from(cameraLatitude, cameraLongitude), 11
            )
        )
    }

    private fun drawLabel() {

    }


    @Composable
    fun LectureKakaoMap(
        modifier: Modifier = Modifier,
        mapView: MapView,
        viewModel: LocationListViewModel,
        locationX: Double, // 서버에서 제공하는 X 값 (경도)
        locationY: Double, // 서버에서 제공하는 Y 값 (위도)
    ) {

        AndroidView(
            modifier = modifier.fillMaxSize(), // AndroidView의 높이 임의 설정
            factory = { context ->
                mapView.apply {
                    mapView.start(
                        object : MapLifeCycleCallback() {
                            // 지도 생명 주기 콜백: 지도가 파괴될 때 호출
                            override fun onMapDestroy() {
                                Toast.makeText(context, "Destroy", Toast.LENGTH_SHORT).show()
                            }

                            // 지도 생명 주기 콜백: 지도 로딩 중 에러가 발생했을 때 호출
                            override fun onMapError(exception: Exception?) {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                            }
                        },
                        object : KakaoMapReadyCallback() {
                            override fun onMapReady(map: KakaoMap) {
                                kakaoMap = map
                                Toast.makeText(context, "Ready", Toast.LENGTH_SHORT).show()
                                labelLayer = kakaoMap.labelManager!!.layer!!
                                layer = kakaoMap.routeLineManager!!.layer

                                kakaoMap.moveCamera(
                                    CameraUpdateFactory.newCenterPosition(
                                        LatLng.from(37.55595957732287, 126.97227318174524), 16
                                    )
                                )
                            }

                            override fun getPosition(): LatLng {
                                // 현재 위치를 반환
                                return LatLng.from(locationY, locationX)
                            }
                        },
                    )
                }
            },
        )
    }

    private fun setLabelWithText(
        labelId: String,
        text: String,
        locationName: String,
        lat: Double,
        lng: Double
    ) {

        val pos = LatLng.from(lat, lng)

        val marker = when (text) {
            "출발" -> {
                Triple(
                    R.drawable.blue_marker,
                    LabelTextStyle.from(baseContext, R.style.labelTextStyleBlack),
                    LabelTextStyle.from(baseContext, R.style.labelTextStyleBlue)
                )
            }

            "도착" -> {
                Triple(
                    R.drawable.pink_marker,
                    LabelTextStyle.from(baseContext, R.style.labelTextStyleBlack),
                    LabelTextStyle.from(baseContext, R.style.labelTextStyleRed)
                )
            }

            else -> {
                Triple(
                    R.drawable.green_marker,
                    LabelTextStyle.from(baseContext, R.style.labelTextStyleBlack),
                    LabelTextStyle.from(baseContext, R.style.labelTextStyleBlue)
                )
            }
        }

        val styles = kakaoMap.labelManager
            ?.addLabelStyles(
                LabelStyles.from(
                    LabelStyle.from(
                        marker.first
                    ).setTextStyles(
                        marker.second,
                        marker.third
                    )
                        .setIconTransition(LabelTransition.from(Transition.None, Transition.None))
                )
            )

        labelLayer.addLabel(
            LabelOptions.from(labelId, pos).setStyles(styles)
                .setTexts(
                    LabelTextBuilder().setTexts(
                        text,
                        locationName,
                    )
                )
        )
        kakaoMap.moveCamera(
            CameraUpdateFactory.newCenterPosition(pos, 15),
            CameraAnimation.from(duration)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.finish()
    }

    override fun onResume() {
        super.onResume()
        println("@@@@@@onResume()")
        if (mapView != null) {
//            println("Map View is Null")
            mapView!!.resume()
        }
    }

    override fun onPause() {
        super.onPause()
        println("@@@@@@onPause()")
        mapView?.pause()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSection(
    viewModel: LocationListViewModel,
    bottomSheetState: SheetState,
    setShowBottomSheet: (Boolean) -> Unit,
    setPaths: (Pair<String, String>) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = {
            setShowBottomSheet(false)
        },
        sheetState = bottomSheetState,
        dragHandle = null,
        modifier = Modifier.statusBarsPadding(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column {
                    Spacer(
                        modifier = Modifier
                            .height(22.dp).fillMaxWidth()
                            .background(
                                Color(0xFF454545).copy(0.4f)
                            )
                    )
                    LazyColumn(
                        modifier = Modifier.background(
                            Color(0xFF454545).copy(0.4f)
                        )
                    ) {
                        items(viewModel.pathList.size) { index ->
                            DirectionComponent(
                                viewModel = viewModel,
                                onClick = {
                                    setShowBottomSheet(false)
                                    println(viewModel.pathList[index])
                                    println(viewModel.routeList.toString())
                                    viewModel.loadRouteInfo(
                                        viewModel.pathList[index].origin,
                                        viewModel.pathList[index].destination,
                                    )
                                    viewModel.loadRouteLineList(
                                        viewModel.pathList[index].origin,
                                        viewModel.pathList[index].destination,
                                    )
                                    setPaths(
                                        Pair(
                                            viewModel.pathList[index].origin,
                                            viewModel.pathList[index].destination
                                        )
                                    )
//                                viewModel.loadRouteList(
//                                    viewModel.pathList[index].origin,
//                                    viewModel.pathList[index].destination,
//                                )
                                },
                                paths = Paths(
                                    viewModel.pathList[index].origin,
                                    viewModel.pathList[index].destination,
                                ),
                            )
                            if (viewModel.pathList.size == index + 1) {
                                Spacer(
                                    modifier = Modifier
                                        .height(35.dp)
                                        .fillMaxWidth()
                                        .background(color = Color.Black.copy(0.5f))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun DirectionComponent(
    viewModel: LocationListViewModel,
    onClick: () -> Unit,
    paths: Paths
) {
//    Text(text = viewModel.routeInfo.toString())
//    Text(text = viewModel.routeList.toString())

    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .clickWithCoroutine(coroutineScope) {
                onClick()
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 25.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_car),
                contentDescription = "icon_car",
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = paths.origin,
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "right arrow")
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = paths.destination,
                fontSize = 20.sp
            )
        }
    }
//    Column(
//        modifier = Modifier
//            .clickWithCoroutine(coroutineScope) {
//                onClick()
//            }
//            .padding(
//                horizontal = 20.dp,
//                vertical = 20.dp
//            )
//    ) {
//        Row {
//            Text(text = "출발지 : ${paths.destination}")
//        }
//
//        Row {
//            Text(text = "도착지 : ${paths.origin}")
//        }
//        Spacer(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(1.dp)
//                .background(color = Color.Black.copy(0.5f)),
//        )
//    }
}