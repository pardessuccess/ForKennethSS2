package com.pardess.directions.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pardess.directions.data.Result
import com.pardess.directions.data.response.location.Location
import com.pardess.directions.domain.model.Route
import com.pardess.directions.domain.model.RouteInfo
import com.pardess.directions.domain.model.RouteLine
import com.pardess.directions.domain.usecase.distance_time.GetRouteInfoUseCase
import com.pardess.directions.domain.usecase.location.GetRouteListUseCase
import com.pardess.directions.domain.usecase.route.GetRouteLineListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DirectionViewModel @Inject constructor(
    private val getRouteListUseCase: GetRouteListUseCase,
    private val getRouteLineListUseCase: GetRouteLineListUseCase,
    private val getRouteInfoUseCase: GetRouteInfoUseCase
) : ViewModel() {

    var dataState by mutableStateOf<DataState>(DataState.Initial())
        set

    init {
        updateLocationList()
        dataState = DataState.Loading()
    }

    var straightDistance by mutableIntStateOf(0)
        set

    var route by mutableStateOf(Route("", ""))
        set

    var locationList by mutableStateOf<List<Location>>(emptyList())
        private set

    fun updateLocationList() = viewModelScope.launch {
        val data = getRouteListUseCase()
        Logger.d(data.data.toString())
        if (data is Result.Success) {
            locationList = data.data!!.locations
            dataState = DataState.Success(
                message = "GET ROUTE LIST USE CASE"
            )
        } else {
            locationList = emptyList()
            dataState = DataState.Error(data.message.toString(), data.errorType)
        }
    }

    var routeInfo by mutableStateOf(RouteInfo(0, 0, 0, 0))
        private set

    fun updateRouteInfo(origin: String, destination: String) = viewModelScope.launch {
        dataState = DataState.Loading()
        val data = getRouteInfoUseCase(origin = origin, destination = destination)
        Logger.d(data.toString())
        route = Route(origin, destination)
        if (data is Result.Success) {
            routeInfo = data.data!!
            dataState = DataState.Success(
                message = "GET ROUTE INFO USE CASE"
            )
        } else {
            straightDistance = 0
            routeLineList = emptyList()
            routeInfo = RouteInfo(0, 0, 0, 0)
            dataState = DataState.Error(data.message.toString(), data.errorType)
        }
    }

    var routeLineList by mutableStateOf<List<RouteLine>>(emptyList())
        private set

    fun updateRouteList(origin: String, destination: String) = viewModelScope.launch {
        dataState = DataState.Loading()
        val data = getRouteLineListUseCase(origin = origin, destination = destination)
        Logger.d(data.toString())
        if (data is Result.Success) {
            routeLineList = data.data!!
            straightDistance = Utils.haversine(
                data.data.first().wayList.first(),
                data.data.last().wayList.last()
            )
            dataState = DataState.Success(
                message = "GET ROUTELINE INFO USE CASE"
            )
        } else {
            straightDistance = 0
            routeLineList = emptyList()
            routeInfo = RouteInfo(0, 0, 0, 0)
            dataState = DataState.Error(data.message.toString(), data.errorType)
        }
    }

    var pathList by mutableStateOf<List<Location>>(emptyList())
        private set


//    fun loadPathList() = viewModelScope.launch {
//        getPathList().let {
////            pathList = it.locations
//        }
//        println("@@@@pathList $pathList")
//    }
//
//    private val _routeLineList = MutableLiveData<List<RouteLines>>(emptyList())
//    val routeLineList2: LiveData<List<RouteLines>> = _routeLineList
//
//    fun loadRouteLineList(origin: String, destination: String) = viewModelScope.launch {
////        getRouteList(origin = origin, destination = destination).let {
////            _routeLineList.value = it
////            Logger.d(it.toString())
////        }
//    }
//
//    var routeList2 by mutableStateOf<List<RouteLines>>(emptyList())
//        private set
//
//    fun loadRouteList(origin: String, destination: String) = viewModelScope.launch {
////        getRouteList(origin = origin, destination = destination).let {
////            routeList2 = it
////        }
////        println("@@@@routeList $routeList2")
//    }
//
//    var routeInfo2 by mutableStateOf(RouteInfo(0, 0, 0, 0))
//        private set
//
//    fun loadRouteInfo(origin: String, destination: String) = viewModelScope.launch {
////        getRouteInfo(origin = origin, destination = destination).let {
////            routeInfo2 = it
////        }
//    }

}