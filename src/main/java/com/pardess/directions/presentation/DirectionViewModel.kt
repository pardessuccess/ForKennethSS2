package com.pardess.directions.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pardess.directions.data.Resource
import com.pardess.directions.data.response.location.Location
import com.pardess.directions.domain.model.RouteInfo
import com.pardess.directions.domain.model.RouteLines
import com.pardess.directions.domain.usecase.distance_time.GetRouteInfo
import com.pardess.directions.domain.usecase.location.GetPathList
import com.pardess.directions.domain.usecase.route.GetRouteList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DirectionViewModel @Inject constructor(
    private val getPathList: GetPathList,
    private val getRouteList: GetRouteList,
    private val getRouteInfo: GetRouteInfo
) : ViewModel() {
//dasf
    init {
        updateLocationList()
    }

    var dataState by mutableStateOf<DataState>(DataState.Success())
        set

    var straightDistance by mutableIntStateOf(0)
        set

    var location by mutableStateOf(Pair("", ""))
        set

    var locationList by mutableStateOf<List<Location>>(emptyList())
        private set

    fun updateLocationList() = viewModelScope.launch {
//        dataState = DataState.Loading()
        val data = getPathList()
        Logger.d(data.data.toString())
        if (data is Resource.Success) {
            locationList = data.data!!.locations
            dataState = DataState.Success()
        } else {
            locationList = emptyList()
            dataState = DataState.Error(data.message.toString(), data.errorType)
        }
    }

    var routeInfo by mutableStateOf(RouteInfo(0, 0, 0, 0))
        private set

    fun updateRouteInfo(origin: String, destination: String) = viewModelScope.launch {
        dataState = DataState.Loading()
        val data = getRouteInfo(origin = origin, destination = destination)
        Logger.d(data.toString())
        if (data is Resource.Success) {
            routeInfo = data.data!!
            dataState = DataState.Success()
        } else {
            straightDistance = 0
            routeInfo = RouteInfo(0, 0, 0, 0)
            dataState = DataState.Error(data.message.toString(), data.errorType)
        }
    }

    var routeLineList by mutableStateOf<List<RouteLines>>(emptyList())
        private set

    fun updateRouteList(origin: String, destination: String) = viewModelScope.launch {
        dataState = DataState.Loading()
        val data = getRouteList(origin = origin, destination = destination)
        Logger.d(data.toString())
        if (data is Resource.Success) {
            routeLineList = data.data!!
            straightDistance = Utils.haversine(
                data.data.first().wayList.first(),
                data.data.last().wayList.last()
            )
            dataState = DataState.Success()
        } else {
            straightDistance = 0
            routeLineList = emptyList()
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