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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.graphics.graphicsLayer

// StarData class to hold individual star properties
private data class StarData(
    val id: Int,
    val initialX: Float,
    val initialY: Float,
    val size: Float,
    val baseOpacity: Float,
    val speed: Float,
    val angle: Float // Angle in degrees for movement direction
)

@Composable
fun AnimatedLunarBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "lunar_bg_transition")

    // Moon orbital animation
    val moonX by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "moonX_lunar"
    )

    val moonY by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "moonY_lunar"
    )

    val moonOverallOpacity by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "moonOverallOpacity_lunar"
    )

    val moonOverallScale by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "moonOverallScale_lunar"
    )

    // Moon glow effect
    val moonGlowIntensity by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "moonGlow_lunar"
    )

    // Moon rotation
    val moonRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(25000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "moonRotation_lunar"
    )

    // Moon breathing scale (applied to moon body)
    val moonBreathing by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "moonBreathing_lunar"
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

        // --- Modified Stars Section ---
        val starInfiniteTransition = rememberInfiniteTransition(label = "stars_transition")
        val starOffset by starInfiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(60000, easing = LinearEasing), // Slower, longer cycle
                repeatMode = RepeatMode.Restart
            ),
            label = "star_offset"
        )

        val starTwinkleAlpha by starInfiniteTransition.animateFloat(
            initialValue = 0.6f,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(2500, easing = EaseInOut),
                repeatMode = RepeatMode.Reverse
            ),
            label = "star_twinkle_alpha"
        )

        var stars by remember { mutableStateOf<List<StarData>>(emptyList()) }
        var canvasSizeForStars by remember { mutableStateOf(Size.Zero) }

        Canvas(modifier = Modifier.fillMaxSize()) {
            if (canvasSizeForStars != size) {
                canvasSizeForStars = size
                val starCount = 100
                val starMinSize = 0.5f
                val starMaxSize = 2.5f
                val starMinSpeed = 0.02f
                val starMaxSpeed = 0.1f

                stars = List(starCount) { index ->
                    StarData(
                        id = index,
                        initialX = Random.nextFloat() * size.width,
                        initialY = Random.nextFloat() * size.height,
                        size = starMinSize + Random.nextFloat() * (starMaxSize - starMinSize),
                        baseOpacity = 0.4f + Random.nextFloat() * 0.6f,
                        speed = starMinSpeed + Random.nextFloat() * (starMaxSpeed - starMinSpeed),
                        angle = Random.nextFloat() * 360f
                    )
                }
            }

            stars.forEach { star ->
                val dx = cos(Math.toRadians(star.angle.toDouble())).toFloat()
                val dy = sin(Math.toRadians(star.angle.toDouble())).toFloat()

                val animatedX = (star.initialX + starOffset * star.speed * dx).let {
                    (it % size.width + size.width) % size.width
                }
                val animatedY = (star.initialY + starOffset * star.speed * dy).let {
                    (it % size.height + size.height) % size.height
                }

                val currentStarOpacity = (star.baseOpacity * starTwinkleAlpha).coerceIn(0f, 1f)
                val glowRadius = star.size * 2.5f

                // Draw Glow
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = currentStarOpacity * 0.5f),
                            Color.Transparent
                        ),
                        center = Offset(animatedX, animatedY),
                        radius = glowRadius
                    ),
                    radius = glowRadius,
                    center = Offset(animatedX, animatedY)
                )

                // Draw Star
                drawCircle(
                    color = Color.White.copy(alpha = currentStarOpacity),
                    radius = star.size,
                    center = Offset(animatedX, animatedY)
                )
            }
        }
        // --- End of Modified Stars Section ---

        // Moon with orbital movement
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            Box(
                modifier = Modifier
                    //.fillMaxSize() // This Box is for the moon's positioning context
                    .offset(x = screenWidth * moonX, y = screenHeight * moonY)
                    .graphicsLayer( // Apply opacity and scale to the whole moon group
                        alpha = moonOverallOpacity,
                        scaleX = moonOverallScale,
                        scaleY = moonOverallScale
                    )
            ) {
                // Moon Glow Effect Canvas
                Canvas(
                    modifier = Modifier
                        .size(112.dp)
                        .blur(2.dp)
                        .scale(1.3f) // This scale is part of the glow visual, distinct from moonOverallScale
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

                // Main Moon Body Canvas
                Canvas(
                    modifier = Modifier
                        .size(112.dp)
                        .scale(moonBreathing) // Apply breathing to the moon body directly
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
                        // Moon craters (static)
                        drawCircle(color = Color(0xFFfcd34d).copy(alpha = 0.4f), radius = 12f, center = Offset(20f, 16f))
                        drawCircle(color = Color(0xFFfcd34d).copy(alpha = 0.35f), radius = 8f, center = Offset(28f, 40f))
                        drawCircle(color = Color(0xFFfcd34d).copy(alpha = 0.3f), radius = 10f, center = Offset(40f, 32f))
                        drawCircle(color = Color(0xFFfcd34d).copy(alpha = 0.25f), radius = 6f, center = Offset(32f, 64f))
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedLogo() {
    val infiniteTransition = rememberInfiniteTransition(label = "logo_infinite_transition") // Unique label
    val density = LocalDensity.current // Get density here

    // Entrance animation states
    var scaleState by remember { mutableStateOf(0f) }
    var rotationState by remember { mutableStateOf(-180f) }

    LaunchedEffect(Unit) {
        animate(0f, 1f, animationSpec = tween(800, delayMillis = 500)) { value, _ ->
            scaleState = value
        }
    }
    LaunchedEffect(Unit) {
        animate(-180f, 0f, animationSpec = tween(800, delayMillis = 500)) { value, _ ->
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
        label = "logo_floatY"
    )

    val gentleRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f, // Range from -5 to 5 if reversed, or 0 to 10 and back
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_gentleRotation"
    )

    val containerScaleBreath by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_containerScaleBreath"
    )

    val ringRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "logo_ringRotation"
    )

    val ringOpacity by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_ringOpacity"
    )

    val iconInternalRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "logo_iconInternalRotation"
    )

    // Particle animations
    val particleAnimations = List(6) { i ->
        val opacity by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, delayMillis = i * 500, easing = EaseInOut),
                repeatMode = RepeatMode.Reverse
            ),
            label = "logo_particleOpacity_$i"
        )
        val scale by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, delayMillis = i * 500, easing = EaseInOut),
                repeatMode = RepeatMode.Reverse
            ),
            label = "logo_particleScale_$i"
        )
        opacity to scale // Pair of animated values
    }


    Box(
        modifier = Modifier
            .size(140.dp)
            .graphicsLayer( // Apply entrance and continuous animations here
                scaleX = scaleState * containerScaleBreath,
                scaleY = scaleState * containerScaleBreath,
                rotationZ = rotationState + gentleRotation,
                translationY = with(density) { floatY.dp.toPx() } // Corrected here
            ),
        contentAlignment = Alignment.Center
    ) {
        // Glowing ring effect
        Canvas(
            modifier = Modifier
                .size(140.dp) // Ring slightly larger than main logo body
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
                        radius = size.minDimension / 2 // Fills the canvas
                    ),
                    radius = size.minDimension / 2
                )
            }
        }

        // Main container
        Box(
            modifier = Modifier
                .size(180.dp) // Actual logo body size
                .background(
                    color = Color.White.copy(alpha = 0.9f), // bg-white/90
                    shape = RoundedCornerShape(90.dp) // Fully rounded
                )
                .background( // Simulates border
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(90.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingBag,
                contentDescription = "Logo",
                modifier = Modifier
                    .size(80.dp)
                    .graphicsLayer(
                        rotationZ = iconInternalRotation
                    ),
                tint = Color(0xFF9333EA)
            )
        }

        // Floating particles
        particleAnimations.forEachIndexed { i, (particleOpacityAnim, particleScaleAnim) ->
            val particleAngle = i * 60f // Spread particles evenly
            val radius = 55f // Distance from center, slightly outside main logo
            // Calculate position based on the center of the 140.dp Box
            val xOffset = (radius * cos(Math.toRadians(particleAngle.toDouble()))).toFloat()
            val yOffset = (radius * sin(Math.toRadians(particleAngle.toDouble()))).toFloat()

            Canvas(
                modifier = Modifier
                    .size(6.dp) // Particle canvas size
                    .offset(x = xOffset.dp, y = yOffset.dp) // Position relative to parent Box center
            ) {
                drawCircle(
                    color = Color.White.copy(alpha = particleOpacityAnim),
                    radius = with(density) { 3.dp.toPx() * particleScaleAnim } // Corrected here
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
                            placeholder = { Text("Enter your email", color = Color.White.copy(alpha = 0.5f)) },
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
                                // textColor and placeholderColor removed
                            ),
                            textStyle = LocalTextStyle.current.copy(color = Color.White),
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
                            placeholder = { Text("Enter your password", color = Color.White.copy(alpha = 0.5f)) },
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
                                // textColor and placeholderColor removed
                            ),
                            textStyle = LocalTextStyle.current.copy(color = Color.White),
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
