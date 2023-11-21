package com.vinutest.notificationscompose

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vinutest.notificationscompose.viewmodel.ReminderViewModel
import com.vinutest.notificationscompose.viewmodel.ReminderViewModelFactory
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderDialog(name: String, onDismiss: () -> Unit) {

    val viewModel: ReminderViewModel = viewModel(
        factory = ReminderViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    )

    val schedules = listOf(
        R.string.schedule_5_seconds to 5000L,
        R.string.schedule_8_minutes to 8 * 60 * 1000L,
        R.string.schedule_1_day to 24 * 60 * 60 * 1000L,
        R.string.schedule_1_week to 7 * 24 * 60 * 60 * 1000L
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth().testTag("reminderDialog")) {
                Text(
                    text = stringResource(R.string.title_reminder),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth()
                )

                schedules.forEach { (scheduleTextId, delayMillis) ->
                    ListItem(
                        headlineText = { Text(text = stringResource(scheduleTextId)) },
                        modifier = Modifier.clickable {
                            viewModel.scheduleReminder(delayMillis, TimeUnit.MILLISECONDS, name)
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}