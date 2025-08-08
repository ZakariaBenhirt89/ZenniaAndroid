package com.mimi.ecommerceloginapp.screens

import androidx.compose.animation.core.*
import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.graphics.graphicsLayer
@Composable
fun AnimatedLunarBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "lunar")

    // Moon orbital animation
    val moonX by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "moonX"
    )

    val moonY by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "moonY"
    )

    val moonOpacity by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "moonOpacity"
    )

    val moonScale by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "moonScale"
    )

    // Moon glow effect
    val moonGlowIntensity by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "moonGlow"
    )

    // Moon rotation
    val moonRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(25000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "moonRotation"
    )

    // Moon breathing scale
    val moonBreathing by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "moonBreathing"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Gradient Background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val gradient = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF1e1b4b), // indigo-950
                    Color(0xFF581c87), // purple-900
                    Color(0xFF0f172a)  // slate-900
                )
            )
            drawRect(brush = gradient)
        }

        // Moon with orbital movement
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = (moonX * 300).dp,
                    top = (moonY * 200).dp
                )
        ) {
            // Moon Glow Effect
            Canvas(
                modifier = Modifier
                    .size(112.dp)
                    .blur(2.dp)
                    .scale(1.3f)
            ) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFfef08a).copy(alpha = moonGlowIntensity),
                            Color(0xFFfef3c7).copy(alpha = moonGlowIntensity * 0.5f),
                            Color.Transparent
                        ),
                        center = center,
                        radius = size.minDimension / 2
                    ),
                    radius = size.minDimension / 2
                )
            }

            // Main Moon Body
            Canvas(
                modifier = Modifier
                    .size(112.dp)
                    .scale(moonBreathing * moonScale)
            ) {
                rotate(moonRotation) {
                    // Main moon gradient
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFfef08a),
                                Color(0xFFfef3c7),
                                Color(0xFFfefce8)
                            ),
                            center = center,
                            radius = size.minDimension / 2
                        ),
                        radius = size.minDimension / 2
                    )

                    // Moon craters
                    drawCircle(
                        color = Color(0xFFfcd34d).copy(alpha = 0.4f),
                        radius = 12f,
                        center = Offset(20f, 16f)
                    )
                    drawCircle(
                        color = Color(0xFFfcd34d).copy(alpha = 0.35f),
                        radius = 8f,
                        center = Offset(28f, 40f)
                    )
                    drawCircle(
                        color = Color(0xFFfcd34d).copy(alpha = 0.3f),
                        radius = 10f,
                        center = Offset(40f, 32f)
                    )
                    drawCircle(
                        color = Color(0xFFfcd34d).copy(alpha = 0.25f),
                        radius = 6f,
                        center = Offset(32f, 64f)
                    )
                }
            }
        }

        // Stars
        Canvas(modifier = Modifier.fillMaxSize()) {
            repeat(50) { index ->
                val x = (index * 7.3f) % size.width
                val y = (index * 11.7f) % size.height
                val starSize = (index % 3 + 1).toFloat()
                val starOpacity = 0.3f + (index % 3) * 0.2f

                drawCircle(
                    color = Color.White.copy(alpha = starOpacity),
                    radius = starSize,
                    center = Offset(x, y)
                )
            }
        }
    }
}

@Composable
fun AnimatedLogo() {
    val infiniteTransition = rememberInfiniteTransition(label = "logo")

    // 1. Initial entrance animation (scale: 0->1, rotate: -180->0)
    val entranceScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(800, delayMillis = 500),
        label = "entrance"
    )

    val entranceRotation by animateFloatAsState(
        targetValue = 0f,
        animationSpec = tween(800, delayMillis = 500),
        label = "entranceRot"
    )

    // 2. Floating animation (y: [0, -8, 0])
    val floatY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -8f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    // 3. Gentle rotation (rotate: [0, 5, -5, 0])
    val gentleRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gentleRot"
    )

    // 4. Container scale breathing (scale: [1, 1.05, 1])
    val containerScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "containerScale"
    )

    // 5. Glowing ring rotation (rotate: [0, 360])
    val ringRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ringRotation"
    )

    // 6. Ring opacity (opacity: [0.3, 0.7, 0.3])
    val ringOpacity by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ringOpacity"
    )

    // 7. Icon rotation (rotate: [0, 360])
    val iconRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "iconRotation"
    )

    Box(
        modifier = Modifier
            .size(200.dp) // Made larger
            .offset(y = floatY.dp)
            .scale(entranceScale * containerScale),
        contentAlignment = Alignment.Center
    ) {
        // Glowing ring effect (exact React translation)
        Canvas(
            modifier = Modifier
                .size(200.dp) // Made larger
                .blur(4.dp)
        ) {
            rotate(ringRotation) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF9333EA).copy(alpha = ringOpacity), // purple-400
                            Color(0xFF3B82F6).copy(alpha = ringOpacity * 0.5f), // blue-400
                            Color.Transparent
                        ),
                        center = center,
                        radius = size.minDimension / 2
                    ),
                    radius = size.minDimension / 2
                )
            }
        }

        // Main container (exact React styling)
        Box(
            modifier = Modifier
                .size(180.dp) // Made larger
                .background(
                    color = Color.White.copy(alpha = 0.9f), // bg-white/90
                    shape = RoundedCornerShape(90.dp)
                )
                .background(
                    color = Color.White.copy(alpha = 0.2f), // border-white/20
                    shape = RoundedCornerShape(90.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            // Icon with rotation - Fixed approach using graphicsLayer
            Icon(
                Icons.Default.ShoppingBag,
                contentDescription = "Logo",
                modifier = Modifier
                    .size(80.dp) // Made much larger
                    .graphicsLayer(
                        rotationZ = iconRotation
                    ),
                tint = Color(0xFF9333EA) // text-purple-600
            )
        }

        // Floating particles (exact React translation)
        repeat(6) { i ->
            val particleAngle = i * 60f
            val radius = 60f // Made larger
            val x = (radius * cos(Math.toRadians(particleAngle.toDouble()))).toFloat()
            val y = (radius * sin(Math.toRadians(particleAngle.toDouble()))).toFloat()

            val particleOpacity by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, delayMillis = i * 500, easing = EaseInOut),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "particle$i"
            )

            val particleScale by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, delayMillis = i * 500, easing = EaseInOut),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "particleScale$i"
            )

            Canvas(
                modifier = Modifier
                    .size(6.dp) // Made larger
                    .offset(x = x.dp, y = y.dp)
            ) {
                drawCircle(
                    color = Color.White.copy(alpha = particleOpacity),
                    radius = 3f * particleScale // Made larger
                )
            }
        }
    }
}

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Lunar Background
        AnimatedLunarBackground()

        // Login Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo section - ALWAYS VISIBLE with animations
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedLogo() // Always visible with continuous animations

                Spacer(modifier = Modifier.height(16.dp))

                // Title (exact React timing)
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(800, delayMillis = 800))
                ) {
                    Text(
                        text = "Welcome Back",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Subtitle (exact React timing)
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(800, delayMillis = 1000))
                ) {
                    Text(
                        text = "Sign in to your account to continue shopping",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Form (exact React timing)
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
                    // Email field (exact React timing)
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
                            label = { Text("Email") },
                            placeholder = { Text("Enter your email") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Email,
                                    contentDescription = "Email",
                                    tint = Color.Gray.copy(alpha = 0.4f)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White.copy(alpha = 0.2f),
                                unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                                focusedLabelColor = Color.White.copy(alpha = 0.9f),
                                unfocusedLabelColor = Color.White.copy(alpha = 0.9f),
                                cursorColor = Color.White
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Password field (exact React timing)
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
                            label = { Text("Password") },
                            placeholder = { Text("Enter your password") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = "Password",
                                    tint = Color.Gray.copy(alpha = 0.4f)
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = { showPassword = !showPassword }
                                ) {
                                    Icon(
                                        if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Toggle password visibility",
                                        tint = Color.Gray.copy(alpha = 0.4f)
                                    )
                                }
                            },
                            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White.copy(alpha = 0.2f),
                                unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                                focusedLabelColor = Color.White.copy(alpha = 0.9f),
                                unfocusedLabelColor = Color.White.copy(alpha = 0.9f),
                                cursorColor = Color.White
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Login button (exact React timing)
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(800, delayMillis = 1600)) +
                                slideInVertically(
                                    animationSpec = tween(800, delayMillis = 1600),
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
                                containerColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Sign In",
                                color = Color(0xFF667eea),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}