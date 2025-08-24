package com.mimi.ecommerceloginapp.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
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
        kotlinx.coroutines.delay(500)
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
            // Glowing ring effect
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
                        .graphicsLayer(rotationZ = diamondSparkle / 5f)
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
                        color = ZenniaColors.accent.copy(alpha = 0.8f),
                        radius = 2f * scale,
                        center = Offset(centerX, centerY)
                    )
                }
            }

            // Floating particles around logo
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