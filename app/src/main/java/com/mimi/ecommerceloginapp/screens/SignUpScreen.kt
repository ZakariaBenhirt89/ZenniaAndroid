package com.mimi.ecommerceloginapp.screens

import androidx.compose.animation.core.*
import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mimi.ecommerceloginapp.components.AnimatedBackground
import com.mimi.ecommerceloginapp.components.ZenniaColors
import kotlin.math.*

data class SignUpFormData(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)

@Composable
fun SignUpScreen(
    onSwitchToLogin: () -> Unit
) {
    var formData by remember { mutableStateOf(SignUpFormData()) }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    var agreeToTerms by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Animated Background
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
            // Header with Logo
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

                    // Welcome Text
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 800))
                    ) {
                        Text(
                            text = "Join Zennia",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }

                    // Subtitle
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 1000))
                    ) {
                        Text(
                            text = "Create your account and discover luxury jewelry",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            // SignUp Form
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
                    // Name Field
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 1100)) +
                                slideInHorizontally(
                                    animationSpec = tween(800, delayMillis = 1100),
                                    initialOffsetX = { -20 }
                                )
                    ) {
                        OutlinedTextField(
                            value = formData.name,
                            onValueChange = { formData = formData.copy(name = it) },
                            label = { 
                                Text(
                                    "Full Name", 
                                    color = Color.White.copy(alpha = 0.9f)
                                ) 
                            },
                            placeholder = { 
                                Text(
                                    "Enter your full name", 
                                    color = Color.White.copy(alpha = 0.5f)
                                ) 
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = "Name",
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
                                focusedBorderColor = ZenniaColors.accent.copy(alpha = 0.6f),
                                unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                                cursorColor = ZenniaColors.accent,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Email Field
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 1300)) +
                                slideInHorizontally(
                                    animationSpec = tween(800, delayMillis = 1300),
                                    initialOffsetX = { -20 }
                                )
                    ) {
                        OutlinedTextField(
                            value = formData.email,
                            onValueChange = { formData = formData.copy(email = it) },
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
                                focusedBorderColor = ZenniaColors.accent.copy(alpha = 0.6f),
                                unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                                cursorColor = ZenniaColors.accent,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Password Field
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 1500)) +
                                slideInHorizontally(
                                    animationSpec = tween(800, delayMillis = 1500),
                                    initialOffsetX = { -20 }
                                )
                    ) {
                        OutlinedTextField(
                            value = formData.password,
                            onValueChange = { formData = formData.copy(password = it) },
                            label = { 
                                Text(
                                    "Password", 
                                    color = Color.White.copy(alpha = 0.9f)
                                ) 
                            },
                            placeholder = { 
                                Text(
                                    "Create a password", 
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
                                    onClick = { showPassword = !showPassword }
                                ) {
                                    Icon(
                                        if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Toggle password visibility",
                                        tint = ZenniaColors.accent.copy(alpha = 0.7f)
                                    )
                                }
                            },
                            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color.White.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ZenniaColors.accent.copy(alpha = 0.6f),
                                unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                                cursorColor = ZenniaColors.accent,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Confirm Password Field
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 1700)) +
                                slideInHorizontally(
                                    animationSpec = tween(800, delayMillis = 1700),
                                    initialOffsetX = { -20 }
                                )
                    ) {
                        OutlinedTextField(
                            value = formData.confirmPassword,
                            onValueChange = { formData = formData.copy(confirmPassword = it) },
                            label = { 
                                Text(
                                    "Confirm Password", 
                                    color = Color.White.copy(alpha = 0.9f)
                                ) 
                            },
                            placeholder = { 
                                Text(
                                    "Confirm your password", 
                                    color = Color.White.copy(alpha = 0.5f)
                                ) 
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = "Confirm Password",
                                    tint = ZenniaColors.accent.copy(alpha = 0.7f)
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = { showConfirmPassword = !showConfirmPassword }
                                ) {
                                    Icon(
                                        if (showConfirmPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Toggle password visibility",
                                        tint = ZenniaColors.accent.copy(alpha = 0.7f)
                                    )
                                }
                            },
                            visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color.White.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ZenniaColors.accent.copy(alpha = 0.6f),
                                unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                                cursorColor = ZenniaColors.accent,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Terms Agreement
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 1900))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Checkbox(
                                checked = agreeToTerms,
                                onCheckedChange = { agreeToTerms = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = ZenniaColors.accent,
                                    uncheckedColor = Color.White.copy(alpha = 0.3f),
                                    checkmarkColor = Color.White
                                ),
                                modifier = Modifier.padding(top = 2.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "I agree to the Terms of Service and Privacy Policy",
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }

                    // SignUp Button
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 2100)) +
                                slideInVertically(
                                    animationSpec = tween(800, delayMillis = 2100),
                                    initialOffsetY = { 20 }
                                )
                    ) {
                        Button(
                            onClick = {
                                if (agreeToTerms) {
                                    println("SignUp attempt: $formData")
                                }
                            },
                            enabled = agreeToTerms,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .graphicsLayer {
                                    scaleX = if (agreeToTerms) 1f else 0.98f
                                    scaleY = if (agreeToTerms) 1f else 0.98f
                                    alpha = if (agreeToTerms) 1f else 0.5f
                                },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = if (agreeToTerms) {
                                                listOf(
                                                    ZenniaColors.accent,
                                                    Color(0xFF3B82F6)
                                                )
                                            } else {
                                                listOf(
                                                    ZenniaColors.accent.copy(alpha = 0.5f),
                                                    Color(0xFF3B82F6).copy(alpha = 0.5f)
                                                )
                                            }
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Create Account",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            // Divider
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(800, delayMillis = 2300))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(
                        modifier = Modifier.weight(1f),
                        color = Color.White.copy(alpha = 0.2f)
                    )
                    Text(
                        text = "Or sign up with",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Divider(
                        modifier = Modifier.weight(1f),
                        color = Color.White.copy(alpha = 0.2f)
                    )
                }
            }

            // Social SignUp Buttons
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(800, delayMillis = 2500)) +
                        slideInVertically(
                            animationSpec = tween(800, delayMillis = 2500),
                            initialOffsetY = { 20 }
                        )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Google Button
                    OutlinedButton(
                        onClick = { /* Handle Google signup */ },
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
                            Canvas(modifier = Modifier.size(20.dp)) {
                                drawCircle(
                                    color = Color.White,
                                    radius = size.minDimension / 2
                                )
                                drawCircle(
                                    color = Color(0xFF4285F4),
                                    radius = size.minDimension / 3,
                                    center = center
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Continue with Google",
                                fontSize = 16.sp
                            )
                        }
                    }

                    // Apple Button
                    OutlinedButton(
                        onClick = { /* Handle Apple signup */ },
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
                            // Apple Icon (simplified)
                            Icon(
                                imageVector = Icons.Default.Phone, // Using phone icon as placeholder
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

            // Login Link
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(800, delayMillis = 2700))
            ) {
                Row(
                    modifier = Modifier.padding(top = 32.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Already have an account? ",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    TextButton(
                        onClick = onSwitchToLogin
                    ) {
                        Text(
                            text = "Sign in",
                            color = ZenniaColors.accent,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Footer
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = tween(800, delayMillis = 2900)),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "By creating an account, you agree to our Terms of Service and Privacy Policy",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}