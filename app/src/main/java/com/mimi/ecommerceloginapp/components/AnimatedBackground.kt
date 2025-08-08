package com.mimi.ecommerceloginapp.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "background")
    
    // Gradient animation
    val gradientRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "gradient"
    )
    
    // Star animation
    val starAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "stars"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        // Gradient background
        val gradient = Brush.linearGradient(
            colors = listOf(
                Color(0xFF667eea),
                Color(0xFF764ba2)
            ),
            start = Offset(0f, 0f),
            end = Offset(size.width, size.height)
        )
        
        drawRect(brush = gradient)
        
        // Animated stars
        repeat(50) { index ->
            val x = (index * 7.3f) % size.width
            val y = (index * 11.7f) % size.height
            drawCircle(
                color = Color.White.copy(alpha = starAlpha),
                radius = 2f,
                center = Offset(x, y)
            )
        }
    }
}