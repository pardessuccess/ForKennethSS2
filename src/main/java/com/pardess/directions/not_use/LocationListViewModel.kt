package com.pardess.directions.not_use

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class LocationListViewModel @Inject constructor(
    private val getPathList: GetPathList,
    private val getRouteList: GetRouteList,
    private val getRouteInfo: GetRouteInfo
) : ViewModel() {

//    private val _pathList: MutableState<List<Location>> = MutableLiveData()
//    val pathList: LiveData<List<Location>> = _pathList

    var pathList by mutableStateOf<List<Location>>(emptyList())
        private set

    init {
        loadPathList()
//        loadRouteInfo("수서역", "마곡역")
    }

    fun loadPathList() = viewModelScope.launch {
        getPathList().let {
            pathList = it.locations
        }
        println("@@@@pathList $pathList")
    }

    private val _routeLineList = MutableLiveData<List<RouteLines>>(emptyList())
    val routeLineList: LiveData<List<RouteLines>> = _routeLineList

    fun loadRouteLineList(origin: String, destination: String) = viewModelScope.launch {
        getRouteList(origin = origin, destination = destination).let {
            _routeLineList.value = it
        }
    }

    var routeList by mutableStateOf<List<RouteLines>>(emptyList())
        private set

    fun loadRouteList(origin: String, destination: String) = viewModelScope.launch {
        getRouteList(origin = origin, destination = destination).let {
            routeList = it
        }
        println("@@@@routeList $routeList")
    }

    var routeInfo by mutableStateOf(RouteInfo(0, 0, 0, 0))
        private set

    fun loadRouteInfo(origin: String, destination: String) = viewModelScope.launch {
        getRouteInfo(origin = origin, destination = destination).let {
            routeInfo = it
        }
    }

}