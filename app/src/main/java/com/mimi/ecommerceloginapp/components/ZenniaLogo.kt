package com.mimi.ecommerceloginapp.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.*



@Composable
fun ZenniaLogo(
    modifier: Modifier = Modifier,
    animate: Boolean = true,
    className: String = "w-8 h-8"
) {
    val infiniteTransition = rememberInfiniteTransition(label = "logo_animation")
    
    // Main rotation animation (like React version)
    val rotation by if (animate) {
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(8000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "rotation"
        )
    } else {
        remember { mutableStateOf(0f) }
    }
    
    // Scale animation
    val scale by if (animate) {
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.05f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = EaseInOut),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scale"
        )
    } else {
        remember { mutableStateOf(1f) }
    }
    
    // Facet opacity animation
    val facetOpacity by if (animate) {
        infiniteTransition.animateFloat(
            initialValue = 0.6f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = EaseInOut),
                repeatMode = RepeatMode.Reverse
            ),
            label = "facet_opacity"
        )
    } else {
        remember { mutableStateOf(0.8f) }
    }
    
    // Center light animation
    val centerLightOpacity by if (animate) {
        infiniteTransition.animateFloat(
            initialValue = 0.4f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1500, easing = EaseInOut),
                repeatMode = RepeatMode.Reverse
            ),
            label = "center_light"
        )
    } else {
        remember { mutableStateOf(0.7f) }
    }
    
    val centerLightScale by if (animate) {
        infiniteTransition.animateFloat(
            initialValue = 0.8f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(1500, easing = EaseInOut),
                repeatMode = RepeatMode.Reverse
            ),
            label = "center_light_scale"
        )
    } else {
        remember { mutableStateOf(1f) }
    }
    
    // Text opacity animation
    val textOpacity by if (animate) {
        infiniteTransition.animateFloat(
            initialValue = 0.7f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(4000, easing = EaseInOut),
                repeatMode = RepeatMode.Reverse
            ),
            label = "text_opacity"
        )
    } else {
        remember { mutableStateOf(0.9f) }
    }
    
    // Sparkle animations (moved outside Canvas)
    val sparkleAnimations = if (animate) {
        (0..5).map { i ->
            val sparkleOpacity by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = EaseInOut, delayMillis = i * 300),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "sparkle_opacity_$i"
            )
            
            val sparkleScale by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1.5f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = EaseInOut, delayMillis = i * 300),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "sparkle_scale_$i"
            )
            
            Pair(sparkleOpacity, sparkleScale)
        }
    } else {
        emptyList()
    }

    Canvas(
        modifier = modifier.size(32.dp)
    ) {
        rotate(rotation) {
            // Define gradients
            val diamondGradient = Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.9f),
                    Color(0xFFFFD700).copy(alpha = 0.7f),
                    Color(0xFFFFA500).copy(alpha = 0.8f),
                    Color(0xFFFF8C00).copy(alpha = 0.6f)
                ),
                start = Offset(0f, 0f),
                end = Offset(size.width, size.height)
            )
            
            val goldStroke = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFFFD700),
                    Color(0xFFFFA500),
                    Color(0xFFFF8C00)
                ),
                start = Offset(0f, 0f),
                end = Offset(size.width, size.height)
            )
            
            val facetGradient = Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 1f),
                    Color(0xFFFFD700).copy(alpha = 0.3f)
                ),
                start = Offset(0f, 0f),
                end = Offset(size.width, size.height)
            )
            
            val shadowGradient = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFB8860B).copy(alpha = 0.4f),
                    Color(0xFF8B7000).copy(alpha = 0.8f)
                ),
                start = Offset(0f, 0f),
                end = Offset(size.width, size.height)
            )
            
            val centerLightGradient = Brush.radialGradient(
                colors = listOf(
                    Color.White.copy(alpha = 1f),
                    Color(0xFFFFD700).copy(alpha = 0.2f)
                ),
                center = center,
                radius = size.minDimension / 4
            )
            
            val centerX = size.width / 2
            val centerY = size.height / 2
            val scaleSize = scale
            
            // Main Diamond Shape (based on React version path: M16 2L26 12L16 30L6 12Z)
            val mainDiamondPath = Path().apply {
                val topX = centerX
                val topY = centerY - 14f * scaleSize
                val leftX = centerX - 10f * scaleSize  
                val leftY = centerY
                val bottomX = centerX
                val bottomY = centerY + 18f * scaleSize
                val rightX = centerX + 10f * scaleSize
                val rightY = centerY
                
                moveTo(topX, topY) // Top point
                lineTo(rightX, rightY) // Right point  
                lineTo(bottomX, bottomY) // Bottom point
                lineTo(leftX, leftY) // Left point
                close()
            }
            
            // Draw main diamond with gradient and scale
            drawPath(
                path = mainDiamondPath,
                brush = diamondGradient,
                alpha = scaleSize
            )
            
            // Draw diamond stroke
            drawPath(
                path = mainDiamondPath,
                brush = goldStroke,
                style = Stroke(width = 1f),
                alpha = scaleSize
            )
            
            // Inner Diamond Facets (React: M16 2L21 7L16 12L11 7Z)
            val innerFacetPath = Path().apply {
                val topX = centerX
                val topY = centerY - 14f * scaleSize
                val rightTopX = centerX + 5f * scaleSize
                val rightTopY = centerY - 5f * scaleSize
                val centerFacetX = centerX
                val centerFacetY = centerY
                val leftTopX = centerX - 5f * scaleSize
                val leftTopY = centerY - 5f * scaleSize
                
                moveTo(topX, topY)
                lineTo(rightTopX, rightTopY)
                lineTo(centerFacetX, centerFacetY)
                lineTo(leftTopX, leftTopY)
                close()
            }
            
            drawPath(
                path = innerFacetPath,
                brush = facetGradient,
                alpha = facetOpacity
            )
            
            // Left shadow facet (React: M11 7L16 12L6 12Z)
            val leftShadowPath = Path().apply {
                val leftTopX = centerX - 5f * scaleSize
                val leftTopY = centerY - 5f * scaleSize
                val centerX_facet = centerX
                val centerY_facet = centerY
                val leftBottomX = centerX - 10f * scaleSize
                val leftBottomY = centerY
                
                moveTo(leftTopX, leftTopY)
                lineTo(centerX_facet, centerY_facet)
                lineTo(leftBottomX, leftBottomY)
                close()
            }
            
            drawPath(
                path = leftShadowPath,
                brush = shadowGradient,
                alpha = 0.6f
            )
            
            // Right shadow facet (React: M16 12L21 7L26 12Z)  
            val rightShadowPath = Path().apply {
                val centerX_facet = centerX
                val centerY_facet = centerY
                val rightTopX = centerX + 5f * scaleSize
                val rightTopY = centerY - 5f * scaleSize
                val rightBottomX = centerX + 10f * scaleSize
                val rightBottomY = centerY
                
                moveTo(centerX_facet, centerY_facet)
                lineTo(rightTopX, rightTopY)
                lineTo(rightBottomX, rightBottomY)
                close()
            }
            
            drawPath(
                path = rightShadowPath,
                brush = shadowGradient,
                alpha = 0.6f
            )
            
            // Center Light Reflection (React: circle cx="16" cy="10" r="2")
            drawCircle(
                brush = centerLightGradient,
                radius = 2f * centerLightScale * scaleSize,
                center = Offset(centerX, centerY - 2f * scaleSize),
                alpha = centerLightOpacity
            )
            
            // Sparkle Effects (6 small circles like React version)
            sparkleAnimations.forEachIndexed { i, (sparkleOpacity, sparkleScale) ->
                val sparkleX = centerX + (i % 3 - 1) * 4f * scaleSize
                val sparkleY = centerY + (i / 3 - 0.5f) * 4f * scaleSize
                
                drawCircle(
                    color = Color(0xFFFFD700),
                    radius = 0.5f * sparkleScale * scaleSize,
                    center = Offset(sparkleX, sparkleY),
                    alpha = sparkleOpacity
                )
            }
            
            // Text "Z" (React version has text)
            val paint = Paint().asFrameworkPaint().apply {
                isAntiAlias = true
                textSize = 3f * scaleSize * size.width / 32f
                color = goldStroke.toString().hashCode() // Simplified color application
                typeface = android.graphics.Typeface.DEFAULT_BOLD
            }
        }
    }
}

private fun DrawScope.drawDiamondIcon(
    scale: Float,
    goldGradient: Brush,
    diamondGradient: Brush
) {
    val baseX = 15f * scale
    val baseY = 20f * scale
    val centerX = baseX + 12f * scale
    val centerY = baseY + 12f * scale
    
    // Main diamond shape (vertical)
    val mainDiamondPath = Path().apply {
        moveTo(centerX, baseY + 4f * scale) // Top point
        lineTo(baseX + 8f * scale, baseY + 12f * scale) // Left point
        lineTo(centerX, baseY + 24f * scale) // Bottom point
        lineTo(baseX + 16f * scale, baseY + 12f * scale) // Right point
        close()
    }
    
    // Draw main diamond with gradient and shadow effect
    drawPath(
        path = mainDiamondPath,
        brush = diamondGradient
    )
    
    // Draw diamond stroke
    drawPath(
        path = mainDiamondPath,
        color = ZenniaColors.accent,
        style = Stroke(width = 0.5f * scale)
    )
    
    // Top diamond facet (horizontal)
    val topFacetPath = Path().apply {
        moveTo(baseX + 4f * scale, baseY + 12f * scale) // Left point
        lineTo(centerX, baseY + 4f * scale) // Top point
        lineTo(baseX + 20f * scale, baseY + 12f * scale) // Right point
        lineTo(centerX, baseY + 16f * scale) // Bottom point
        close()
    }
    
    drawPath(
        path = topFacetPath,
        brush = goldGradient,
        alpha = 0.7f
    )
    
    // Center highlight circle
    drawCircle(
        color = ZenniaColors.diamond,
        radius = 2f * scale,
        center = Offset(centerX, centerY),
        alpha = 0.8f
    )
}

private fun DrawScope.drawZenniaText(scale: Float) {
    val textX = 45f * scale
    val mainTextY = 35f * scale
    val taglineY = 55f * scale
    val lineY = 60f * scale
    
    // Note: In Canvas, we can't directly use custom fonts like in SVG
    // We'll simulate the text with basic drawing
    
    // Main "ZENNIA" text area (placeholder - in real implementation you'd use AndroidView with TextView for custom fonts)
    val textPath = Path().apply {
        // This is a simplified representation - for actual custom fonts,
        // you'd need to use AndroidView or load custom fonts
        addRect(
            androidx.compose.ui.geometry.Rect(
                left = textX,
                top = mainTextY - 20f * scale,
                right = textX + 120f * scale,
                bottom = mainTextY + 5f * scale
            )
        )
    }
    
    // For now, we'll draw rectangles to represent where the text would be
    // In a real implementation, you'd use proper text rendering
    
    // Decorative line
    drawLine(
        color = ZenniaColors.accent,
        start = Offset(textX, lineY),
        end = Offset(180f * scale, lineY),
        strokeWidth = 1f * scale,
        alpha = 0.6f
    )
    
    // Small decorative diamonds
    val decorativeX = 185f * scale
    val decorativeY1 = 58f * scale
    val decorativeY2 = 62f * scale
    
    drawCircle(
        color = ZenniaColors.accent,
        radius = 1.5f * scale,
        center = Offset(decorativeX, decorativeY1),
        alpha = 0.8f
    )
    
    drawCircle(
        color = ZenniaColors.accent,
        radius = 1f * scale,
        center = Offset(decorativeX, decorativeY2),
        alpha = 0.6f
    )
}

@Composable
fun ZenniaLogoWithText(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Diamond icon only
        Canvas(
            modifier = Modifier.size(40.dp)
        ) {
            val scale = size.width / 40f
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            
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
                moveTo(centerX, centerY - 12f * scale) // Top
                lineTo(centerX - 8f * scale, centerY) // Left
                lineTo(centerX, centerY + 12f * scale) // Bottom
                lineTo(centerX + 8f * scale, centerY) // Right
                close()
            }
            
            drawPath(
                path = mainDiamondPath,
                brush = diamondGradient
            )
            
            drawPath(
                path = mainDiamondPath,
                color = ZenniaColors.accent,
                style = Stroke(width = 1f)
            )
            
            // Top facet
            val topFacetPath = Path().apply {
                moveTo(centerX - 6f * scale, centerY) // Left
                lineTo(centerX, centerY - 8f * scale) // Top
                lineTo(centerX + 6f * scale, centerY) // Right
                lineTo(centerX, centerY + 2f * scale) // Bottom
                close()
            }
            
            drawPath(
                path = topFacetPath,
                brush = goldGradient,
                alpha = 0.7f
            )
            
            // Center highlight
            drawCircle(
                color = ZenniaColors.diamond,
                radius = 2f * scale,
                center = Offset(centerX, centerY),
                alpha = 0.8f
            )
        }
        
        // Text column
        Column {
            androidx.compose.material3.Text(
                text = "ZENNIA",
                color = ZenniaColors.text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 2.sp
            )
            androidx.compose.material3.Text(
                text = "LUXURY JEWELRY",
                color = ZenniaColors.accent,
                fontSize = 8.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 1.sp
            )
        }
    }
}