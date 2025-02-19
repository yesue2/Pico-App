package com.yesue2.pico.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yesue2.pico.R

@Composable
fun DropdownField(
    label: String,
    placeholder: String,
    value: String,
    options: List<String>,
    onValueChange: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(value) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(bottom = 15.dp),
            fontSize = 16.sp,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFFDFEAF2),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 8.dp, vertical = 16.5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (selectedValue.isNotEmpty()) selectedValue else placeholder,
                color = if (selectedValue.isNotEmpty()) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                fontSize = 13.sp
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_dropdown),
                contentDescription = "드롭다운 아이콘",
                modifier = Modifier
                    .size(18.dp)
                    .padding(end = 8.dp),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
        if (expanded) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFDFEAF2),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(4.dp)
                    .heightIn(max = 200.dp)
            ) {
                items(options.size) { index ->
                    val option = options[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedValue = option
                                onValueChange(option)
                                expanded = false
                            }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = option,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}
