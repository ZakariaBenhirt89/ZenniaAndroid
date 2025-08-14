package com.mimi.ecommerceloginapp.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.*
import kotlin.random.Random

data class Star(
    val id: Int,
    val x: Float,
    val y: Float,
    val size: Float,
    val opacity: Float,
    val duration: Float
)

data class Particle(
    val id: Int,
    val x: Float,
    val y: Float
)

data class ShootingStar(
    val id: Int,
    val startX: Float,
    val startY: Float
)

@Composable
fun AnimatedBackground() {
    var stars by remember { mutableStateOf<List<Star>>(emptyList()) }
    var particles by remember { mutableStateOf<List<Particle>>(emptyList()) }
    var shootingStars by remember { mutableStateOf<List<ShootingStar>>(emptyList()) }
    
    val density = LocalDensity.current
    
    // Initialize stars, particles, and shooting stars
    LaunchedEffect(Unit) {
        stars = List(150) { i ->
            Star(
                id = i,
                x = Random.nextFloat() * 100f,
                y = Random.nextFloat() * 100f,
                size = Random.nextFloat() * 3f + 1f,
                opacity = Random.nextFloat() * 0.8f + 0.2f,
                duration = Random.nextFloat() * 3f + 2f
            )
        }
        
        particles = List(20) { i ->
            Particle(
                id = i,
                x = Random.nextFloat() * 100f,
                y = Random.nextFloat() * 100f
            )
        }
        
        shootingStars = List(3) { i ->
            ShootingStar(
                id = i,
                startX = 20f + i * 30f,
                startY = 10f + i * 20f
            )
        }
    }
    
    val infiniteTransition = rememberInfiniteTransition(label = "background")
    
    // Moon orbital movement
    val moonXProgress = remember { 
        listOf(0.8f, 0.7f, 0.5f, 0.3f, 0.2f, 0.3f, 0.5f, 0.7f, 0.8f)
    }
    val moonYProgress = remember { 
        listOf(0.1f, 0.15f, 0.25f, 0.2f, 0.15f, 0.1f, 0.08f, 0.12f, 0.1f)
    }
    val moonOpacityProgress = remember { 
        listOf(0f, 0.8f, 1f, 1f, 1f, 1f, 1f, 0.8f, 0f)
    }
    val moonScaleProgress = remember { 
        listOf(0f, 1f, 1.1f, 1f, 0.9f, 1f, 1.05f, 1f, 0f)
    }
    
    val moonAnimationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = EaseInOut),
            repeatMode = RepeatMode.Restart
        ),
        label = "moon_orbit"
    )
    
    fun interpolateFromList(progress: Float, values: List<Float>): Float {
        if (values.isEmpty()) return 0f
        if (values.size == 1) return values[0]
        
        val scaledProgress = progress * (values.size - 1)
        val index = scaledProgress.toInt().coerceIn(0, values.size - 2)
        val fraction = scaledProgress - index
        
        return values[index] * (1f - fraction) + values[index + 1] * fraction
    }
    
    val moonX = interpolateFromList(moonAnimationProgress, moonXProgress)
    val moonY = interpolateFromList(moonAnimationProgress, moonYProgress)
    val moonOpacity = interpolateFromList(moonAnimationProgress, moonOpacityProgress)
    val moonScale = interpolateFromList(moonAnimationProgress, moonScaleProgress)
    
    // Moon glow animation
    val moonGlow by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "moon_glow"
    )
    
    // Moon rotation
    val moonRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(25000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "moon_rotation"
    )
    
    // Moon breathing
    val moonBreathing by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "moon_breathing"
    )
    
    // Moon phase effect
    val moonPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = EaseInOut),
            repeatMode = RepeatMode.Restart
        ),
        label = "moon_phase"
    )
    
    val moonPhaseOpacity = when {
        moonPhase <= 0.2f -> moonPhase * 5f * 0.3f
        moonPhase <= 0.4f -> 0.1f + (moonPhase - 0.2f) * 5f * 0.3f
        moonPhase <= 0.6f -> 0.4f + (moonPhase - 0.4f) * 5f * 0.3f
        moonPhase <= 0.8f -> 0.7f - (moonPhase - 0.6f) * 5f * 0.3f
        else -> 0.4f - (moonPhase - 0.8f) * 5f * 0.4f
    }
    val moonPhaseScale = sin(moonPhase * PI * 2).toFloat().absoluteValue
    
    // Nebula animation
    val nebulaOpacity by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "nebula"
    )
    
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Gradient Background
            val gradientBrush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF1e1b4b), // indigo-950
                    Color(0xFF581c87), // purple-900
                    Color(0xFF0f172a)  // slate-900
                )
            )
            drawRect(brush = gradientBrush)
            
            // Nebula Effect
            val nebulaColors = listOf(
                Color(0xFF8B4513).copy(alpha = 0.3f), // Brown
                Color(0xFF4B0082).copy(alpha = 0.4f), // Indigo
                Color(0xFF191970).copy(alpha = 0.3f)  // MidnightBlue
            )
            
            nebulaColors.forEachIndexed { index, color ->
                val centerX = size.width * (0.3f + index * 0.2f)
                val centerY = size.height * (0.4f + index * 0.15f)
                val radius = size.width * 0.25f
                
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            color.copy(alpha = color.alpha * nebulaOpacity),
                            Color.Transparent
                        ),
                        center = Offset(centerX, centerY),
                        radius = radius
                    ),
                    radius = radius,
                    center = Offset(centerX, centerY)
                )
            }
            
            // Stars
            stars.forEach { star ->
                val starX = size.width * star.x / 100f
                val starY = size.height * star.y / 100f
                
                val animatedOpacity = star.opacity * 
                    (0.5f + 0.5f * sin((System.currentTimeMillis() / (star.duration * 1000f)) % (2f * PI)).toFloat())
                
                drawCircle(
                    color = Color.White.copy(alpha = animatedOpacity),
                    radius = star.size,
                    center = Offset(starX, starY)
                )
            }
            
            // Floating Particles
            particles.forEach { particle ->
                val particleX = size.width * particle.x / 100f
                val particleY = size.height * particle.y / 100f
                val time = System.currentTimeMillis() / 10000f
                
                val animatedY = particleY + sin(time * 2f + particle.id) * 100f
                val animatedX = particleX + cos(time + particle.id) * 25f
                val animatedOpacity = 0.3f + 0.3f * sin(time * 3f + particle.id)
                
                drawCircle(
                    color = Color(0xFF93C5FD).copy(alpha = animatedOpacity), // blue-200
                    radius = 1f,
                    center = Offset(animatedX, animatedY)
                )
            }
            
            // Shooting Stars
            shootingStars.forEach { shootingStar ->
                val time = (System.currentTimeMillis() / 3000f) % (shootingStar.id * 8f + 2f)
                if (time < 3f) {
                    val progress = time / 3f
                    val startX = size.width * shootingStar.startX / 100f
                    val startY = size.height * shootingStar.startY / 100f
                    val endX = startX + 400f * progress
                    val endY = startY + 200f * progress
                    
                    val opacity = when {
                        progress < 0.3f -> progress / 0.3f
                        progress > 0.7f -> (1f - progress) / 0.3f
                        else -> 1f
                    }
                    
                    // Main shooting star
                    drawCircle(
                        color = Color.White.copy(alpha = opacity),
                        radius = 2f,
                        center = Offset(endX, endY)
                    )
                    
                    // Tail
                    val tailLength = 60f
                    val tailStartX = endX - tailLength * cos(0.4363f) // 25 degrees
                    val tailStartY = endY - tailLength * sin(0.4363f)
                    
                    drawLine(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.White.copy(alpha = opacity * 0.8f),
                                Color(0xFF93C5FD).copy(alpha = opacity * 0.4f),
                                Color.Transparent
                            ),
                            start = Offset(endX, endY),
                            end = Offset(tailStartX, tailStartY)
                        ),
                        start = Offset(endX, endY),
                        end = Offset(tailStartX, tailStartY),
                        strokeWidth = 4f
                    )
                }
            }
        }
        
        // Moon
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            
            Box(
                modifier = Modifier
                    .offset(
                        x = screenWidth * moonX - 56.dp,
                        y = screenHeight * moonY - 56.dp
                    )
                    .size(112.dp)
            ) {
                if (moonOpacity > 0f) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer(
                                alpha = moonOpacity,
                                scaleX = moonScale,
                                scaleY = moonScale
                            )
                    ) {
                        // Moon Glow Effect
                        val glowRadius = size.minDimension * 0.65f
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFfef08a).copy(alpha = moonGlow * 0.4f),
                                    Color(0xFFfef3c7).copy(alpha = moonGlow * 0.2f),
                                    Color.Transparent
                                ),
                                center = center,
                                radius = glowRadius
                            ),
                            radius = glowRadius
                        )
                        
                        rotate(moonRotation * moonBreathing) {
                            // Main Moon Body
                            drawCircle(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFfef08a), // yellow-200
                                        Color(0xFFfef3c7), // yellow-100
                                        Color(0xFFfefce8)  // yellow-50
                                    ),
                                    center = center,
                                    radius = size.minDimension / 2.2f
                                ),
                                radius = size.minDimension / 2.2f
                            )
                            
                            // Moon craters with individual animations
                            val craterTime = System.currentTimeMillis() / 4000f
                            
                            drawCircle(
                                color = Color(0xFFfcd34d).copy(
                                    alpha = 0.3f + 0.3f * sin(craterTime)
                                ),
                                radius = 6f * (0.8f + 0.3f * sin(craterTime)),
                                center = center + Offset(-16f, -20f)
                            )
                            
                            drawCircle(
                                color = Color(0xFFfcd34d).copy(
                                    alpha = 0.25f + 0.25f * sin(craterTime + 1f)
                                ),
                                radius = 4f * (0.9f + 0.3f * sin(craterTime + 1f)),
                                center = center + Offset(12f, -8f)
                            )
                            
                            drawCircle(
                                color = Color(0xFFfcd34d).copy(
                                    alpha = 0.2f + 0.25f * sin(craterTime + 2f)
                                ),
                                radius = 5f * (0.7f + 0.3f * sin(craterTime + 2f)),
                                center = center + Offset(-4f, 16f)
                            )
                            
                            drawCircle(
                                color = Color(0xFFfcd34d).copy(
                                    alpha = 0.15f + 0.25f * sin(craterTime + 0.5f)
                                ),
                                radius = 3f * (0.8f + 0.5f * sin(craterTime + 0.5f)),
                                center = center + Offset(-12f, 8f)
                            )
                        }
                        
                        // Moon phase effect
                        if (moonPhaseOpacity > 0f && moonPhaseScale > 0f) {
                            drawOval(
                                color = Color(0xFF64748b).copy(alpha = moonPhaseOpacity), // slate-600
                                topLeft = Offset(
                                    center.x + size.minDimension / 4f * (1f - moonPhaseScale),
                                    center.y - size.minDimension / 4f
                                ),
                                size = Size(
                                    size.minDimension / 2f * moonPhaseScale,
                                    size.minDimension / 2f
                                )
                            )
                        }
                        
                        // Moonbeams
                        repeat(8) { i ->
                            val angle = i * 45f * PI / 180f
                            val beamLength = 48f
                            val beamOpacity = 0.3f + 0.3f * sin(System.currentTimeMillis() / 4000f + i * 0.2f)
                            val beamScale = 0.5f + 0.7f * sin(System.currentTimeMillis() / 4000f + i * 0.2f)
                            
                            rotate(i * 45f) {
                                drawLine(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFFfef08a).copy(alpha = beamOpacity),
                                            Color.Transparent
                                        ),
                                        start = center,
                                        end = center + Offset(0f, -beamLength * beamScale)
                                    ),
                                    start = center,
                                    end = center + Offset(0f, -beamLength * beamScale),
                                    strokeWidth = 2f * (0.5f + 0.5f * beamScale)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}