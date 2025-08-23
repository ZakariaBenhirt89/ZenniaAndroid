package com.mimi.ecommerceloginapp.screens

import androidx.compose.animation.core.*
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mimi.ecommerceloginapp.components.AnimatedBackground
import com.mimi.ecommerceloginapp.components.AnimatedZenniaLogo
import com.mimi.ecommerceloginapp.components.ZenniaColors
import android.widget.Toast
import androidx.compose.ui.platform.LocalFocusManager

// Enhanced validation states
data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val showPassword: Boolean = false,
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null
)

@Composable
fun EnhancedLoginScreen(
    onSwitchToSignup: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var formState by remember { mutableStateOf(LoginFormState()) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    // Enhanced validation functions
    fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email is required"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> 
                "Please enter a valid email address"
            else -> null
        }
    }

    fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Password is required"
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
    }

    fun validateForm(): Boolean {
        val emailError = validateEmail(formState.email)
        val passwordError = validatePassword(formState.password)
        
        formState = formState.copy(
            emailError = emailError,
            passwordError = passwordError
        )
        
        return emailError == null && passwordError == null
    }

    fun handleInputChange(field: String, value: String) {
        formState = when (field) {
            "email" -> formState.copy(
                email = value,
                emailError = if (formState.emailError != null) null else formState.emailError
            )
            "password" -> formState.copy(
                password = value,
                passwordError = if (formState.passwordError != null) null else formState.passwordError
            )
            else -> formState
        }
    }

    suspend fun handleLogin() {
        if (!validateForm()) return
        
        formState = formState.copy(isLoading = true)
        
        try {
            // Simulate login process with enhanced feedback
            kotlinx.coroutines.delay(1500)
            
            println("Enhanced Login attempt: ${formState.email}")
            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
            
            // Success animation delay before redirect
            kotlinx.coroutines.delay(500)
            onLoginSuccess()
        } catch (e: Exception) {
            Toast.makeText(context, "Login failed. Please try again.", Toast.LENGTH_LONG).show()
        } finally {
            formState = formState.copy(isLoading = false)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Enhanced Animated Background
        AnimatedBackground()

        // Content Overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Header with Enhanced Logo
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(800, delayMillis = 200)) +
                        slideInVertically(
                            animationSpec = tween(800, delayMillis = 200),
                            initialOffsetY = { 20 }
                        )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 32.dp)
                ) {
                    AnimatedZenniaLogo()

                    Spacer(modifier = Modifier.height(16.dp))

                    // Enhanced Welcome Text
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 800))
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Welcome Back",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Sign in to your account to continue shopping luxury jewelry",
                                fontSize = 16.sp,
                                color = Color.White.copy(alpha = 0.8f),
                                textAlign = TextAlign.Center,
                                lineHeight = 22.sp
                            )
                        }
                    }
                }
            }

            // Enhanced Login Form
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(800, delayMillis = 400)) +
                        slideInVertically(
                            animationSpec = tween(800, delayMillis = 400),
                            initialOffsetY = { 30 }
                        )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Enhanced Email Field
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 1200)) +
                                slideInHorizontally(
                                    animationSpec = tween(800, delayMillis = 1200),
                                    initialOffsetX = { -20 }
                                )
                    ) {
                        Column {
                            OutlinedTextField(
                                value = formState.email,
                                onValueChange = { handleInputChange("email", it) },
                                label = { 
                                    Text(
                                        "Email", 
                                        color = Color.White.copy(alpha = 0.9f)
                                    ) 
                                },
                                placeholder = { 
                                    Text(
                                        "Enter your email", 
                                        color = Color.White.copy(alpha = 0.5f)
                                    ) 
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Email,
                                        contentDescription = "Email",
                                        tint = ZenniaColors.accent.copy(alpha = 0.7f)
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = Color.White.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = if (formState.emailError != null) 
                                        Color.Red.copy(alpha = 0.6f) 
                                    else 
                                        ZenniaColors.accent.copy(alpha = 0.6f),
                                    unfocusedBorderColor = if (formState.emailError != null)
                                        Color.Red.copy(alpha = 0.4f)
                                    else
                                        Color.White.copy(alpha = 0.2f),
                                    cursorColor = ZenniaColors.accent,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Email,
                                    imeAction = ImeAction.Next
                                ),
                                shape = RoundedCornerShape(12.dp),
                                isError = formState.emailError != null
                            )
                            
                            // Animated Error Message
                            AnimatedVisibility(
                                visible = formState.emailError != null,
                                enter = fadeIn() + slideInVertically(initialOffsetY = { -20 }),
                                exit = fadeOut() + slideOutVertically(targetOffsetY = { -20 })
                            ) {
                                Text(
                                    text = formState.emailError ?: "",
                                    color = Color.Red.copy(alpha = 0.8f),
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                                )
                            }
                        }
                    }

                    // Enhanced Password Field
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 1400)) +
                                slideInHorizontally(
                                    animationSpec = tween(800, delayMillis = 1400),
                                    initialOffsetX = { -20 }
                                )
                    ) {
                        Column {
                            OutlinedTextField(
                                value = formState.password,
                                onValueChange = { handleInputChange("password", it) },
                                label = { 
                                    Text(
                                        "Password", 
                                        color = Color.White.copy(alpha = 0.9f)
                                    ) 
                                },
                                placeholder = { 
                                    Text(
                                        "Enter your password", 
                                        color = Color.White.copy(alpha = 0.5f)
                                    ) 
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Lock,
                                        contentDescription = "Password",
                                        tint = ZenniaColors.accent.copy(alpha = 0.7f)
                                    )
                                },
                                trailingIcon = {
                                    IconButton(
                                        onClick = { 
                                            formState = formState.copy(
                                                showPassword = !formState.showPassword
                                            )
                                        }
                                    ) {
                                        Icon(
                                            if (formState.showPassword) 
                                                Icons.Default.Visibility 
                                            else 
                                                Icons.Default.VisibilityOff,
                                            contentDescription = "Toggle password visibility",
                                            tint = ZenniaColors.accent.copy(alpha = 0.7f)
                                        )
                                    }
                                },
                                visualTransformation = if (formState.showPassword) 
                                    VisualTransformation.None 
                                else 
                                    PasswordVisualTransformation(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = Color.White.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = if (formState.passwordError != null) 
                                        Color.Red.copy(alpha = 0.6f) 
                                    else 
                                        ZenniaColors.accent.copy(alpha = 0.6f),
                                    unfocusedBorderColor = if (formState.passwordError != null)
                                        Color.Red.copy(alpha = 0.4f)
                                    else
                                        Color.White.copy(alpha = 0.2f),
                                    cursorColor = ZenniaColors.accent,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Done
                                ),
                                shape = RoundedCornerShape(12.dp),
                                isError = formState.passwordError != null
                            )
                            
                            // Animated Error Message
                            AnimatedVisibility(
                                visible = formState.passwordError != null,
                                enter = fadeIn() + slideInVertically(initialOffsetY = { -20 }),
                                exit = fadeOut() + slideOutVertically(targetOffsetY = { -20 })
                            ) {
                                Text(
                                    text = formState.passwordError ?: "",
                                    color = Color.Red.copy(alpha = 0.8f),
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                                )
                            }
                        }
                    }

                    // Enhanced Remember Me & Forgot Password
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 1600))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = formState.rememberMe,
                                    onCheckedChange = { 
                                        formState = formState.copy(rememberMe = it) 
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = ZenniaColors.accent,
                                        uncheckedColor = Color.White.copy(alpha = 0.3f),
                                        checkmarkColor = Color.White
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Remember me",
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 14.sp
                                )
                            }
                            
                            TextButton(
                                onClick = { 
                                    Toast.makeText(context, "Password reset link sent!", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Text(
                                    text = "Forgot password?",
                                    color = ZenniaColors.accent,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                    // Enhanced Login Button
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 1800)) +
                                slideInVertically(
                                    animationSpec = tween(800, delayMillis = 1800),
                                    initialOffsetY = { 20 }
                                )
                    ) {
                        val coroutineScope = rememberCoroutineScope()
                        
                        Button(
                            onClick = {
                                focusManager.clearFocus()
                                coroutineScope.launch {
                                    handleLogin()
                                }
                            },
                            enabled = !formState.isLoading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = if (formState.isLoading) {
                                            Brush.linearGradient(
                                                colors = listOf(
                                                    ZenniaColors.accent.copy(alpha = 0.5f),
                                                    Color(0xFF3B82F6).copy(alpha = 0.5f)
                                                )
                                            )
                                        } else {
                                            Brush.linearGradient(
                                                colors = listOf(
                                                    ZenniaColors.accent,
                                                    Color(0xFF3B82F6)
                                                )
                                            )
                                        },
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (formState.isLoading) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(20.dp),
                                            color = Color.White,
                                            strokeWidth = 2.dp
                                        )
                                        Text(
                                            text = "Signing in...",
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                } else {
                                    Text(
                                        text = "Sign In to Zennia",
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Enhanced Divider
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(800, delayMillis = 2000))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = Color.White.copy(alpha = 0.2f)
                    )
                    Text(
                        text = "Or continue with",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = Color.White.copy(alpha = 0.2f)
                    )
                }
            }

            // Enhanced Social Login Buttons
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(800, delayMillis = 2200)) +
                        slideInVertically(
                            animationSpec = tween(800, delayMillis = 2200),
                            initialOffsetY = { 20 }
                        )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Google Button
                    OutlinedButton(
                        onClick = { 
                            Toast.makeText(context, "Google sign-in coming soon!", Toast.LENGTH_SHORT).show()
                            // For demo purposes, redirect to success
                            onLoginSuccess()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp, 
                            Color.White.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Google Icon (simplified)
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Google",
                                modifier = Modifier.size(20.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Continue with Google",
                                fontSize = 16.sp
                            )
                        }
                    }

                    // Apple Button
                    OutlinedButton(
                        onClick = { 
                            Toast.makeText(context, "Apple sign-in coming soon!", Toast.LENGTH_SHORT).show()
                            // For demo purposes, redirect to success
                            onLoginSuccess()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp, 
                            Color.White.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Apple Icon (using phone as placeholder)
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Apple",
                                modifier = Modifier.size(20.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Continue with Apple",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            // Enhanced Sign Up Link
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(800, delayMillis = 2400))
            ) {
                Row(
                    modifier = Modifier.padding(top = 32.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Don't have an account? ",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    TextButton(
                        onClick = onSwitchToSignup
                    ) {
                        Text(
                            text = "Join Zennia",
                            color = ZenniaColors.accent,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Enhanced Footer
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = tween(800, delayMillis = 2600)),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "By signing in, you agree to our Terms of Service and Privacy Policy",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
