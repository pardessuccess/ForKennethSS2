package com.pardess.directions.not_use

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pardess.directions.domain.model.Paths
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

val lst = listOf(
    Paths(
        "서울역",
        "판교역"
    ),
    Paths(
        "대전역",
        "성심당"
    ),
    Paths(
        "수서역",
        "마곡역"
    ),
    Paths(
        "강남역",
        "알파돔타워"
    ),
    Paths(
        "서울역",
        "판교역"
    ),
    Paths(
        "대전역",
        "성심당"
    ),
    Paths(
        "수서역",
        "마곡역"
    ),
    Paths(
        "강남역",
        "알파돔타워"
    ),
    Paths(
        "서울역",
        "판교역"
    ),
    Paths(
        "대전역",
        "성심당"
    ),
    Paths(
        "수서역",
        "마곡역"
    ),
    Paths(
        "강남역",
        "알파돔타워"
    ),
)

@Composable
fun LocationListScreen(
    viewModel: LocationListViewModel
) {


    LaunchedEffect(true) {
        println("@@@" + viewModel.pathList.toString())
    }


    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            LazyColumn() {
                items(viewModel.pathList.size) { index ->
                    DirectionComponent(
                        paths = Paths(
                            viewModel.pathList[index].destination,
                            viewModel.pathList[index].origin,
                        ),
                        viewModel = viewModel,
                        onClick = {

                        }
                    )
                }
            }
        }
    }
}



@Preview
@Composable
fun ScreenPreview() {
    LocationListScreen(
        hiltViewModel()
    )
}

fun Modifier.clickWithCoroutine(
    coroutineScope: CoroutineScope,
    onClick: suspend () -> Unit
): Modifier {
    return this.clickable {
        coroutineScope.launch {
            onClick()
        }
    }
}