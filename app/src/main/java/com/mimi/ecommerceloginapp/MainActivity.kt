package com.mimi.ecommerceloginapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.mimi.ecommerceloginapp.screens.LoginScreen
import com.mimi.ecommerceloginapp.screens.SignUpScreen
import com.mimi.ecommerceloginapp.ui.theme.EcommerceLoginTheme

enum class Screen {
    LOGIN, SIGNUP
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcommerceLoginTheme {
                var currentScreen by remember { mutableStateOf(Screen.LOGIN) }
                
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    when (currentScreen) {
                        Screen.LOGIN -> {
                            LoginScreen(
                                onSwitchToSignup = { currentScreen = Screen.SIGNUP }
                            )
                        }
                        Screen.SIGNUP -> {
                            SignUpScreen(
                                onSwitchToLogin = { currentScreen = Screen.LOGIN }
                            )
                        }
                    }
                }
            }
        }
    }
}