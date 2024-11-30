package com.example.safetech.view.theme.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.safetech.R
import com.example.safetech.data.network.services.AuthData
import com.example.safetech.data.repositories.AuthRepository
import com.example.safetech.view.theme.ui.custom_composable.SafeTechButton
import com.example.safetech.view.theme.ui.custom_composable.SafeTechButtonStyle
import com.example.safetech.view.theme.ui.custom_composable.SafeTechTextField
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    val horizontalTextFieldPadding = 32.dp

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.intersect),
            contentDescription = "Иллюстрация авторизации",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            SafeTechTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = horizontalTextFieldPadding),
                label = "Email",
                text = email
            ) {
                email = it
                authViewModel.refreshEmail(it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            SafeTechTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = horizontalTextFieldPadding),
                label = "Пароль",
                text = password,
                visualTransformation = PasswordVisualTransformation()
            ) {
                password = it
                authViewModel.refreshPassword(it)
            }

            Spacer(modifier = Modifier.height(32.dp))

            SafeTechButton(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = "Войти",
                style = SafeTechButtonStyle.Orange
            ) {
                authViewModel.auth(email, password)
            }
        }
    }
}

class AuthViewModel(
    private val navHostController: NavHostController,
    private val authRepository: AuthRepository
): ViewModel() {

    private object State {
        var login: String = ""
        var password: String = ""
    }

    fun auth(email: String, password: String) {
        //Log.v("TamziF", "email $email, password $password")
        viewModelScope.launch {
            val response = authRepository.auth(AuthData(email, password))
            if (response.isSuccessful) {
                navHostController.navigate("checklists")
            }
        }
    }

    fun refreshPassword(password: String) {
        State.password = password
    }

    fun refreshEmail(login: String) {
        State.login = login
    }
}