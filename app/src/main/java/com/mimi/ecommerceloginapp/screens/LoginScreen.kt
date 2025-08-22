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
import androidx.compose.ui.graphics.drawscope.Stroke
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

@Composable
fun AnimatedZenniaLogo() {
    val infiniteTransition = rememberInfiniteTransition(label = "zennia_logo")
    val density = LocalDensity.current

    // Entrance animations
    var scaleState by remember { mutableStateOf(0f) }
    var rotationState by remember { mutableStateOf(-180f) }
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(500) // Initial delay like React
        isVisible = true
        animate(0f, 1f, animationSpec = tween(800)) { value, _ ->
            scaleState = value
        }
    }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(500)
        animate(-180f, 0f, animationSpec = tween(800)) { value, _ ->
            rotationState = value
        }
    }

    // Continuous animations
    val floatY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -8f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float_y"
    )

    val gentleRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gentle_rotation"
    )

    val containerScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "container_scale"
    )

    val ringRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring_rotation"
    )

    val ringOpacity by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ring_opacity"
    )

    val diamondSparkle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "diamond_sparkle"
    )

    if (isVisible) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .graphicsLayer(
                    scaleX = scaleState * containerScale,
                    scaleY = scaleState * containerScale,
                    rotationZ = rotationState + gentleRotation,
                    translationY = with(density) { floatY.dp.toPx() }
                ),
            contentAlignment = Alignment.Center
        ) {
            // Glowing ring effect (matching original design)
            Canvas(
                modifier = Modifier
                    .size(120.dp)
                    .blur(4.dp)
                    .scale(1.1f)
            ) {
                rotate(ringRotation) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                ZenniaColors.accent.copy(alpha = ringOpacity),
                                Color(0xFF3B82F6).copy(alpha = ringOpacity * 0.5f),
                                Color.Transparent
                            ),
                            center = center,
                            radius = size.minDimension / 2
                        ),
                        radius = size.minDimension / 2
                    )
                }
            }

            // Main container with glass morphism effect
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(40.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Zennia Diamond Logo
                Canvas(
                    modifier = Modifier
                        .size(40.dp)
                        .graphicsLayer(rotationZ = diamondSparkle / 5f) // Gentle rotation
                ) {
                    val centerX = size.width / 2f
                    val centerY = size.height / 2f
                    val scale = size.width / 40f
                    
                    val goldGradient = Brush.linearGradient(
                        colors = listOf(
                            ZenniaColors.accent,
                            Color(0xFFf4d03f),
                            ZenniaColors.accent
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, size.height)
                    )
                    
                    val diamondGradient = Brush.linearGradient(
                        colors = listOf(
                            ZenniaColors.diamond.copy(alpha = 0.9f),
                            Color.White,
                            ZenniaColors.diamond.copy(alpha = 0.9f)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, size.height)
                    )
                    
                    // Main diamond shape
                    val mainDiamondPath = Path().apply {
                        moveTo(centerX, centerY - 14f * scale) // Top
                        lineTo(centerX - 10f * scale, centerY) // Left
                        lineTo(centerX, centerY + 14f * scale) // Bottom
                        lineTo(centerX + 10f * scale, centerY) // Right
                        close()
                    }
                    
                    drawPath(
                        path = mainDiamondPath,
                        brush = diamondGradient
                    )
                    
                    drawPath(
                        path = mainDiamondPath,
                        color = ZenniaColors.accent,
                        style = Stroke(width = 1.5f)
                    )
                    
                    // Top facet
                    val topFacetPath = Path().apply {
                        moveTo(centerX - 8f * scale, centerY) // Left
                        lineTo(centerX, centerY - 10f * scale) // Top
                        lineTo(centerX + 8f * scale, centerY) // Right
                        lineTo(centerX, centerY + 3f * scale) // Bottom
                        close()
                    }
                    
                    drawPath(
                        path = topFacetPath,
                        brush = goldGradient,
                        alpha = 0.8f
                    )
                    
                    // Center highlight with sparkle
                    val sparkleAlpha = (0.6f + 0.4f * sin(diamondSparkle * PI / 180f).toFloat().absoluteValue).coerceIn(0f, 1f)
                    drawCircle(
                        color = Color.White.copy(alpha = sparkleAlpha),
                        radius = 3f * scale,
                        center = Offset(centerX, centerY)
                    )
                    
                    drawCircle(
                        color = ZenniaColors.accent.copy(alpha = 0.8f.coerceIn(0f, 1f)),
                        radius = 2f * scale,
                        center = Offset(centerX, centerY)
                    )
                }
            }

            // Floating particles around logo (matches original design)
            repeat(6) { i ->
                val particleAngle = i * 60f
                val radius = 50f
                
                val particleOpacity by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(3000, delayMillis = i * 500, easing = EaseInOut),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "particle_opacity_$i"
                )
                
                val particleScale by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(3000, delayMillis = i * 500, easing = EaseInOut),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "particle_scale_$i"
                )

                val xOffset = radius * cos(Math.toRadians(particleAngle.toDouble())).toFloat()
                val yOffset = radius * sin(Math.toRadians(particleAngle.toDouble())).toFloat()

                Box(
                    modifier = Modifier
                        .offset(x = xOffset.dp, y = yOffset.dp)
                        .size(4.dp)
                        .background(
                            color = ZenniaColors.accent.copy(alpha = particleOpacity),
                            shape = RoundedCornerShape(2.dp)
                        )
                        .scale(particleScale)
                )
            }
        }
    }
}

@Composable
fun LoginScreen(
    onSwitchToSignup: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

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
                            text = "Welcome Back",
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
                            text = "Sign in to your account to continue shopping luxury jewelry",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            // Login Form
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
                    // Email Field
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 1200)) +
                                slideInHorizontally(
                                    animationSpec = tween(800, delayMillis = 1200),
                                    initialOffsetX = { -20 }
                                )
                    ) {
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
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
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 1400)) +
                                slideInHorizontally(
                                    animationSpec = tween(800, delayMillis = 1400),
                                    initialOffsetX = { -20 }
                                )
                    ) {
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
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
                                imeAction = ImeAction.Done
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Remember Me & Forgot Password
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
                                    checked = rememberMe,
                                    onCheckedChange = { rememberMe = it },
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
                                onClick = { /* Handle forgot password */ }
                            ) {
                                Text(
                                    text = "Forgot password?",
                                    color = ZenniaColors.accent,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                    // Login Button
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 1800)) +
                                slideInVertically(
                                    animationSpec = tween(800, delayMillis = 1800),
                                    initialOffsetY = { 20 }
                                )
                    ) {
                        Button(
                            onClick = {
                                println("Login attempt: $email")
                            },
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
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                ZenniaColors.accent,
                                                Color(0xFF3B82F6)
                                            )
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Sign In",
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
                enter = fadeIn(animationSpec = tween(800, delayMillis = 2000))
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
                        text = "Or continue with",
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

            // Social Login Buttons
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
                        onClick = { /* Handle Google login */ },
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
                        onClick = { /* Handle Apple login */ },
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

            // Sign Up Link
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
                            text = "Sign up",
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