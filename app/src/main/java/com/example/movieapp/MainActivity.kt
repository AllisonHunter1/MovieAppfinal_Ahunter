package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieapp.components.HomeScreen
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.utils_view_state.MoviesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MoviePhotosApp()
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviePhotosApp() {
    val movieViewModel: MoviesViewModel = viewModel()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val isDetailScreenShowing by movieViewModel.isDetailScreenShowing

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MovieTopAppBar(
                scrollBehavior = scrollBehavior,
                showBackButton = isDetailScreenShowing,
                onBackButtonPressed = {
                    movieViewModel.showDetailScreen(false)
                    movieViewModel.setCurrentMovie(null)
                },
            )
        }
    ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp)
            ) {
                HomeScreen(
                    viewModel = movieViewModel,
                    retryAction = movieViewModel::getMoviePhotos,
                    modifier = Modifier.padding(it)
                )
            }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
        fun MovieTopAppBar(scrollBehavior: TopAppBarScrollBehavior, showBackButton: Boolean, onBackButtonPressed: () -> Unit, modifier: Modifier = Modifier) {
            TopAppBar(
                navigationIcon =
                    {
                        IconButton(onClick = onBackButtonPressed) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                ,
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Movie Buffs",
                textAlign= TextAlign.Start,
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}