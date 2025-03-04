package com.yesue2.pico.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yesue2.pico.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String,
    context: String,
    selectedDate: Long?,
    onDateSelected: (Long?) -> Unit
) {
    var showModal by remember { mutableStateOf(false) }
    val formattedDate = selectedDate?.let { convertMillisToDate(it) } ?: ""

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        OutlinedTextField(
            value = formattedDate,
            onValueChange = { },
            placeholder = {
                Text(
                    text = context,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 13.sp
                )
            },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showModal = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_schedule),
                        contentDescription = "Select date",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFDFEAF2),
                unfocusedBorderColor = Color(0xFFDFEAF2),
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        if (showModal) {
            DatePickerModal(
                onDateSelected = { date ->
                    onDateSelected(date)
                    showModal = false
                },
                onDismiss = { showModal = false }
            )
        }
    }
}



fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    return formatter.format(Date(millis))
}