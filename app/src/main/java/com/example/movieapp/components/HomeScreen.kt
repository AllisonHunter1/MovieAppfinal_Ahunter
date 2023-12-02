/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.movieapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.network.MoviePhoto
import com.example.movieapp.utils_view_state.MovieUiState
import com.example.movieapp.utils_view_state.MoviesViewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun HomeScreen(
    viewModel: MoviesViewModel,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val movieUiState = viewModel.movieUiState
    val currentMovie = viewModel.currentMovie.value
    val isDetailScreenShowing by viewModel.isDetailScreenShowing

    when (movieUiState) {
        is MovieUiState.Loading -> LoadingScreen(modifier = modifier)
        is MovieUiState.Error -> ErrorScreen(retryAction, modifier = modifier)
        is MovieUiState.Success -> {
            if (currentMovie != null && !isDetailScreenShowing) {
                // Show the MovieDetail screen
                MovieDetail(photo = currentMovie, modifier = modifier)
            } else {
                // show the PhotosGridScreen
                PhotosGridScreen(
                    photos = movieUiState.photos,
                    modifier = modifier,
                    viewModel = viewModel,
                )
            }
        }
    }
}




@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }

    }
}





//@Preview(showBackground = true)
//@Composable
//fun PhotosGridScreenPreview() {
//   MovieAppTheme {
//        val mockData = List(10) { MoviePhoto("$it", "", description = "", " ,",",","","","")}
//        PhotosGridScreen(mockData)
//    }
//}