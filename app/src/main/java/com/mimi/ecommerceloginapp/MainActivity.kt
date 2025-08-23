package com.mimi.ecommerceloginapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mimi.ecommerceloginapp.screens.LoginScreen
import com.mimi.ecommerceloginapp.screens.SignUpScreen
import com.mimi.ecommerceloginapp.screens.EnhancedLoginScreen
import com.mimi.ecommerceloginapp.screens.EnhancedSignUpScreen
import com.mimi.ecommerceloginapp.screens.EcommerceDisplayScreen
import com.mimi.ecommerceloginapp.ui.theme.EcommerceLoginTheme

enum class Screen {
    LOGIN, SIGNUP, ECOMMERCE
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcommerceLoginTheme {
                var currentScreen by remember { mutableStateOf(Screen.LOGIN) }
                var useEnhanced by remember { mutableStateOf(true) } // Toggle for enhanced version
                
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Main content
                        when (currentScreen) {
                            Screen.LOGIN -> {
                                if (useEnhanced) {
                                    EnhancedLoginScreen(
                                        onSwitchToSignup = { currentScreen = Screen.SIGNUP },
                                        onLoginSuccess = { currentScreen = Screen.ECOMMERCE }
                                    )
                                } else {
                                    LoginScreen(
                                        onSwitchToSignup = { currentScreen = Screen.SIGNUP }
                                    )
                                }
                            }
                            Screen.SIGNUP -> {
                                if (useEnhanced) {
                                    EnhancedSignUpScreen(
                                        onSwitchToLogin = { currentScreen = Screen.LOGIN },
                                        onSignupSuccess = { currentScreen = Screen.ECOMMERCE }
                                    )
                                } else {
                                    SignUpScreen(
                                        onSwitchToLogin = { currentScreen = Screen.LOGIN }
                                    )
                                }
                            }
                            Screen.ECOMMERCE -> {
                                EcommerceDisplayScreen(
                                    onLogout = { currentScreen = Screen.LOGIN }
                                )
                            }
                        }

                        // Version Toggle (for development/demo purposes)
                        if (currentScreen != Screen.ECOMMERCE) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(16.dp)
                            ) {
                                TextButton(
                                    onClick = { useEnhanced = !useEnhanced },
                                    modifier = Modifier
                                        .background(
                                            color = Color.White.copy(alpha = 0.1f),
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = Color.White
                                    )
                                ) {
                                    Text(
                                        text = if (useEnhanced) "Enhanced" else "Original",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
