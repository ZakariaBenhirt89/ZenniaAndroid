package com.mimi.ecommerceloginapp.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object ZenniaColors {
    val accent = Color(0xFFFFD700) // Gold color
    val diamond = Color(0xFFE8E8E8) // Light diamond color
    val text = Color.White
}

@Composable
fun ZenniaLogo(
    modifier: Modifier = Modifier,
    width: Float = 200f,
    height: Float = 80f
) {
    Canvas(
        modifier = modifier.size((width * 0.8f).dp, (height * 0.8f).dp)
    ) {
        val scale = size.width / width
        
        // Create gradients
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
                ZenniaColors.diamond.copy(alpha = 0.9f.coerceIn(0f, 1f)),
                Color.White,
                ZenniaColors.diamond.copy(alpha = 0.9f.coerceIn(0f, 1f))
            ),
            start = Offset(0f, 0f),
            end = Offset(size.width, size.height)
        )
        
        // Draw diamond/gem icon
        drawDiamondIcon(
            scale = scale,
            goldGradient = goldGradient,
            diamondGradient = diamondGradient
        )
        
        // Draw text elements
        drawZenniaText(scale = scale)
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