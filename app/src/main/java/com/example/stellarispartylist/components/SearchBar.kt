package com.example.stellarispartylist.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.stellarispartylist.R

@Composable
fun SearchBar(searchQuery: String, onSearchQueryChange: (String) -> Unit) {
    /*TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        label = { Text("Pesquisar por nome ou telefone") },
        singleLine = true
    )*/
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = { Text("Pesquisar por nome ou telefone", color = Color(0xFFCFCFCF)) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(30.dp)),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF626262),
            unfocusedContainerColor = Color(0xFF626262),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedTextColor = Color(0xFFCFCFCF),
            cursorColor = Color(0xFFCFCFCF)
        ),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Ícone de busca",
                tint = Color(0xFFCFCFCF)
            )
        }
    )
}