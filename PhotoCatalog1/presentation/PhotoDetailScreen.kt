package com.example.photocatalog.presentation

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.photocatalog.domain.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.foundation.layout.height

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailScreen(
    photo: Photo,
    viewModel: PhotoViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val createFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("image/jpeg")
    ) { uri ->
        if (uri != null) {
            scope.launch {
                savePhoto(context, uri, photo, viewModel)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Фото") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = photo.fullImageUrl,
                contentDescription = photo.author,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Text("Автор: ${photo.author}")
            Text("Размер: ${photo.width} × ${photo.height}")
            Text("Ссылка: ${photo.url}")

            Button(
                onClick = {
                    createFileLauncher.launch("photo_${photo.id}.jpg")
                }
            ) {
                Text("Скачать фото")
            }
        }
    }
}

private suspend fun savePhoto(
    context: Context,
    uri: Uri,
    photo: Photo,
    viewModel: PhotoViewModel
) {
    withContext(Dispatchers.IO) {
        val safeUrl = "https://picsum.photos/id/${photo.id}/${photo.width}/${photo.height}.jpg"
        val body = viewModel.repository.downloadPhoto(safeUrl)

        val input = body.byteStream()
        val output = context.contentResolver.openOutputStream(uri)

        input.use { inputStream ->
            output?.use { outputStream ->
                inputStream.copyTo(outputStream)
                outputStream.flush()
            }
        }
    }
}
