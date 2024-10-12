package com.pardess.directions.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pardess.directions.R
import com.pardess.directions.data.response.location.Location
import com.pardess.directions.domain.model.Paths
import com.pardess.directions.domain.model.RouteInfo
import com.pardess.directions.not_use.clickWithCoroutine
import com.pardess.directions.presentation.component.ErrorAlertDialog
import com.pardess.directions.presentation.theme.DirectionsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectionApp(
    viewModel: DirectionViewModel
) {
    DirectionsTheme {
        Surface {
            var showBottomSheet by remember { mutableStateOf(false) }

            val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

            var paths by remember { mutableStateOf(Paths("", "")) }

            var count by remember { mutableIntStateOf(0) }

            val straightDistance = viewModel.straightDistance

            val locationList = viewModel.locationList
            val routeInfo = viewModel.routeInfo

            val dataState = viewModel.dataState

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        text = {
                            Text("길찾기")
                        },
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
                Box(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                    Column(
                        modifier = Modifier.align(Alignment.TopCenter)
                    ){
                        SearchSection(
                            onClick = {
                                showBottomSheet = true
                            }
                        )
                        KakaoMapView(
                            viewModel = viewModel
                        )
                    }
                    NavigateInfoOverlay(
                        viewModel = viewModel,
                        modifier = Modifier.align(Alignment.BottomStart),
                        straightDistance = straightDistance,
                        routeInfo = routeInfo
                    )

                    if (dataState is DataState.Error) {
                        ErrorAlertDialog(
                            errorMessage = dataState.message,
                            errorCode = dataState.errorType.toString(),
                            onDismiss = {
                                viewModel.dataState = DataState.Success()
                            }
                        )
                    } else if (dataState is DataState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }



                    if (showBottomSheet) {
                        BottomSheetSection(
                            viewModel = viewModel,
                            locationList = locationList,
                            bottomSheetState = bottomSheetState,
                            setShowBottomSheet = { showStatus ->
                                count++
                                showBottomSheet = showStatus
                            },
                            setPaths = { pair ->
                                paths = Paths(origin = pair.first, destination = pair.second)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color(0xFF3D73FA)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 10.dp)
                .fillMaxWidth(0.7f)
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xFF5284FA), shape = RoundedCornerShape(5.dp))

            ) {
                Text(
                    text = "서울역", color = Color(0xFFFFFFFF),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                    fontSize = 16.sp,
                )
                Spacer(
                    modifier = Modifier
                        .height(1.5.dp)
                        .fillMaxWidth()
                        .background(Color(0xFF3D73FA))
                )
                Text(
                    text = "판교역 신분당선", color = Color(0xFFFFFFFF),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                    fontSize = 16.sp,
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onClick()
                }
                .background(
                    Color(0xFF5284FA),
                    shape = RoundedCornerShape(5.dp)
                ).padding(7.dp),
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                Icon(
                    painter = painterResource(R.drawable.ic_direction),
                    contentDescription = "ic_direction",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "길찾기",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
        Spacer(
            modifier = Modifier.width(16.dp)
        )
    }
}

@Composable
fun NavigateInfoOverlay(
    viewModel: DirectionViewModel,
    modifier: Modifier,
    straightDistance: Int,
    routeInfo: RouteInfo,
) {
    var routeInfoPair by remember { mutableStateOf(Utils.setRouteInfo(routeInfo)) }

    routeInfoPair = Utils.setRouteInfo(routeInfo)

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
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = Utils.meterWithComma("직선 거리", straightDistance),
            color = Color(0xFFFFD700),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
private fun sadfdaf() {
    SearchSection {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSection(
    viewModel: DirectionViewModel,
    locationList: List<Location>,
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
                            .height(22.dp)
                            .fillMaxWidth()
                            .background(
                                Color(0xFF454545).copy(0.4f)
                            )
                    )
                    LazyColumn(
                        modifier = Modifier.background(
                            Color(0xFF454545).copy(0.4f)
                        )
                    ) {
                        items(locationList.size) { index ->
                            DirectionComponent(
                                viewModel,
                                {
                                    setShowBottomSheet(false)
                                    println(locationList[index])
                                    viewModel.location = Pair(
                                        locationList[index].origin,
                                        locationList[index].destination,
                                    )
                                    viewModel.updateRouteInfo(
                                        locationList[index].origin,
                                        locationList[index].destination,
                                    )
                                    viewModel.updateRouteList(
                                        locationList[index].origin,
                                        locationList[index].destination,
                                    )
                                    setPaths(
                                        Pair(
                                            locationList[index].origin,
                                            locationList[index].destination
                                        )
                                    )
                                },
                                Paths(
                                    locationList[index].origin,
                                    locationList[index].destination,
                                ),
                            )
                            if (locationList.size == index + 1) {
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
    viewModel: DirectionViewModel,
    onClick: () -> Unit,
    paths: Paths
) {

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
}