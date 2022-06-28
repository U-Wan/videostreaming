package com.tsu.videostreaming.agora
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RoomScreen(
    onNavigate: (String) -> Unit,
    viewModel: RoomViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.onJoinEvent.collectLatest { name ->
            onNavigate("video_screen/$name")
        }
    }
    Column(
        modifier = Modifier.background(Color(0xFF00638A)).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = viewModel.roomName.value.text,
            onValueChange = viewModel::onRoomEnter,
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.roomName.value.error != null,
            placeholder = {
                Text(text = "Enter a room name")
            }
        )
        viewModel.roomName.value.error?.let {
            Text(text = it, color = MaterialTheme.colors.error)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = viewModel::onJoinRoom,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF64B05)),
            modifier = Modifier.width(150.dp).height(50.dp),
            shape = RoundedCornerShape(24.dp)
        ) {

            Text(text = "Join")
        }
    }
}