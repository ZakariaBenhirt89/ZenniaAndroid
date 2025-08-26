package com.mimi.ecommerceloginapp.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.*
import android.widget.Toast
import com.mimi.ecommerceloginapp.screens.Product
import com.mimi.ecommerceloginapp.components.ZenniaColors

@Composable
fun ProductViewer3D(
    product: Product,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var rotationX by remember { mutableFloatStateOf(0f) }
    var rotationY by remember { mutableFloatStateOf(0f) }
    var scale by remember { mutableFloatStateOf(1f) }
    var wireframeMode by remember { mutableStateOf(false) }
    var showInstructions by remember { mutableStateOf(true) }
    
    val context = LocalContext.current
    val density = LocalDensity.current
    
    // Auto-rotation animation
    val autoRotateEnabled = remember { mutableStateOf(true) }
    val autoRotation by produceState(0f) {
        while (autoRotateEnabled.value) {
            delay(50)
            value += 1f
        }
    }
    
    // Hide instructions after 4 seconds
    LaunchedEffect(Unit) {
        delay(4000)
        showInstructions = false
    }
    
    // Particle animation
    val particleAnimation by rememberInfiniteTransition(label = "particles").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particle_rotation"
    )
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(500.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1E1B4B),
                        Color(0xFF312E81),
                        Color(0xFF7C3AED),
                        Color(0xFF1E1B4B)
                    ),
                    center = Offset(0.3f, 0.3f),
                    radius = 1000f
                )
            )
    ) {
        // Background cosmic particles
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawCosmicParticles(particleAnimation)
        }
        
        // Main 3D Canvas
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->
                        autoRotateEnabled.value = false
                        rotationX += dragAmount.y * 0.5f
                        rotationY += dragAmount.x * 0.5f
                    }
                }
                .pointerInput(Unit) {
                    detectTransformGestures { _, _, zoom, _ ->
                        scale = (scale * zoom).coerceIn(0.5f, 3f)
                    }
                }
        ) {
            draw3DProduct(
                product = product,
                rotationX = if (autoRotateEnabled.value) autoRotation * 0.5f else rotationX,
                rotationY = if (autoRotateEnabled.value) autoRotation else rotationY,
                scale = scale,
                wireframe = wireframeMode
            )
        }
        
        // Product info overlay
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(20.dp)
        ) {
            Text(
                text = product.name,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                product.materials.take(2).forEach { material ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = ZenniaColors.accent.copy(alpha = 0.2f),
                        contentColor = ZenniaColors.accent
                    ) {
                        Text(
                            text = material,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
        
        // Controls overlay
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Reset view button
            Surface(
                onClick = {
                    rotationX = 0f
                    rotationY = 0f
                    scale = 1f
                    autoRotateEnabled.value = true
                },
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = Color.Black.copy(alpha = 0.4f),
                contentColor = Color.White
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Reset View",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            // Wireframe toggle
            Surface(
                onClick = { wireframeMode = !wireframeMode },
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = if (wireframeMode) ZenniaColors.accent.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.4f),
                contentColor = if (wireframeMode) ZenniaColors.accent else Color.White
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        if (wireframeMode) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle Wireframe",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            // Close button
            Surface(
                onClick = onClose,
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = Color.Black.copy(alpha = 0.4f),
                contentColor = Color.White
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close 3D View",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        
        // Instructions overlay
        if (showInstructions) {
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.Black.copy(alpha = 0.6f),
                contentColor = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🌟 3D Jewelry View",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• Drag to rotate • Pinch to zoom",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = "• Tap controls to customize view",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }
        
        // Floating category label
        Surface(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(20.dp),
            shape = RoundedCornerShape(20.dp),
            color = ZenniaColors.accent.copy(alpha = 0.9f),
            contentColor = Color.Black
        ) {
            Text(
                text = product.category.uppercase(),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun DrawScope.drawCosmicParticles(rotation: Float) {
    val particleCount = 100
    val centerX = size.width / 2
    val centerY = size.height / 2
    
    repeat(particleCount) { i ->
        val angle = (i * 360f / particleCount + rotation) * PI / 180
        val distance = (i % 3 + 1) * 100f
        val x = centerX + cos(angle) * distance
        val y = centerY + sin(angle) * distance
        
        val color = when (i % 3) {
            0 -> Color(0xFFFFD700) // Gold
            1 -> Color(0xFF9B59B6) // Purple
            else -> Color(0xFFFFFFFF) // White
        }
        
        drawCircle(
            color = color.copy(alpha = 0.6f),
            radius = (2f + sin(angle) * 1f).toFloat(),
            center = Offset(x.toFloat(), y.toFloat())
        )
    }
}

private fun DrawScope.draw3DProduct(
    product: Product,
    rotationX: Float,
    rotationY: Float,
    scale: Float,
    wireframe: Boolean
) {
    val centerX = size.width / 2
    val centerY = size.height / 2
    val baseSize = 100f * scale
    
    // Apply rotation and scale transformations
    rotate(rotationY, pivot = Offset(centerX, centerY)) {
        scale(scale, pivot = Offset(centerX, centerY)) {
            when (product.category) {
                "rings" -> drawRing(centerX, centerY, baseSize, product, wireframe)
                "necklaces" -> drawNecklace(centerX, centerY, baseSize, product, wireframe)
                "earrings" -> drawEarrings(centerX, centerY, baseSize, product, wireframe)
                "bracelets" -> drawBracelet(centerX, centerY, baseSize, product, wireframe)
                "watches" -> drawWatch(centerX, centerY, baseSize, product, wireframe)
                else -> drawGenericJewelry(centerX, centerY, baseSize, product, wireframe)
            }
        }
    }
}

private fun DrawScope.drawRing(
    centerX: Float,
    centerY: Float,
    size: Float,
    product: Product,
    wireframe: Boolean
) {
    val ringColor = getMaterialColor(product.materials)
    val strokeWidth = if (wireframe) 3.dp.toPx() else 8.dp.toPx()
    val style: DrawStyle = if (wireframe) androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth) else androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth)
    
    // Main ring
    drawCircle(
        color = ringColor,
        radius = size,
        center = Offset(centerX, centerY),
        style = style
    )
    
    // Inner shadow/highlight
    if (!wireframe) {
        drawCircle(
            color = Color.White.copy(alpha = 0.3f),
            radius = size - 5.dp.toPx(),
            center = Offset(centerX - 2.dp.toPx(), centerY - 2.dp.toPx()),
            style = androidx.compose.ui.graphics.drawscope.Stroke(2.dp.toPx())
        )
    }
    
    // Gem/diamond if applicable
    if (product.materials.contains("Diamond") || product.materials.contains("Emerald")) {
        val gemColor = if (product.materials.contains("Emerald")) Color(0xFF50C878) else Color(0xFFE8E8E8)
        drawCircle(
            color = gemColor,
            radius = size * 0.3f,
            center = Offset(centerX, centerY - size * 0.7f),
            style = if (wireframe) androidx.compose.ui.graphics.drawscope.Stroke(2.dp.toPx()) else androidx.compose.ui.graphics.drawscope.Fill
        )
    }
}

private fun DrawScope.drawNecklace(
    centerX: Float,
    centerY: Float,
    size: Float,
    product: Product,
    wireframe: Boolean
) {
    val chainColor = getMaterialColor(product.materials)
    val strokeWidth = if (wireframe) 2.dp.toPx() else 4.dp.toPx()
    
    // Chain links
    for (i in 0..8) {
        val x = centerX - size + (i * size * 0.25f)
        val y = centerY - size * 0.5f + sin(i * 0.5f) * size * 0.2f
        
        drawCircle(
            color = chainColor,
            radius = size * 0.1f,
            center = Offset(x, y),
            style = androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth)
        )
    }
    
    // Pendant
    val pendantColor = if (product.materials.contains("Diamond")) Color(0xFFE8E8E8) else chainColor
    drawCircle(
        color = pendantColor,
        radius = size * 0.4f,
        center = Offset(centerX, centerY + size * 0.2f),
        style = if (wireframe) androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth) else androidx.compose.ui.graphics.drawscope.Fill
    )
}

private fun DrawScope.drawEarrings(
    centerX: Float,
    centerY: Float,
    size: Float,
    product: Product,
    wireframe: Boolean
) {
    val earringColor = getMaterialColor(product.materials)
    val style: DrawStyle = if (wireframe) androidx.compose.ui.graphics.drawscope.Stroke(3.dp.toPx()) else androidx.compose.ui.graphics.drawscope.Fill
    
    // Left earring
    drawCircle(
        color = earringColor,
        radius = size * 0.6f,
        center = Offset(centerX - size, centerY),
        style = style
    )
    
    // Right earring
    drawCircle(
        color = earringColor,
        radius = size * 0.6f,
        center = Offset(centerX + size, centerY),
        style = style
    )
    
    // Pearl accents if applicable
    if (product.materials.contains("Pearl")) {
        drawCircle(
            color = Color(0xFFF8F6F0),
            radius = size * 0.3f,
            center = Offset(centerX - size, centerY + size * 0.5f),
            style = if (wireframe) androidx.compose.ui.graphics.drawscope.Stroke(2.dp.toPx()) else androidx.compose.ui.graphics.drawscope.Fill
        )
        drawCircle(
            color = Color(0xFFF8F6F0),
            radius = size * 0.3f,
            center = Offset(centerX + size, centerY + size * 0.5f),
            style = if (wireframe) androidx.compose.ui.graphics.drawscope.Stroke(2.dp.toPx()) else androidx.compose.ui.graphics.drawscope.Fill
        )
    }
}

private fun DrawScope.drawBracelet(
    centerX: Float,
    centerY: Float,
    size: Float,
    product: Product,
    wireframe: Boolean
) {
    val braceletColor = getMaterialColor(product.materials)
    val strokeWidth = if (wireframe) 4.dp.toPx() else 8.dp.toPx()
    
    // Main bracelet band
    drawArc(
        color = braceletColor,
        startAngle = -30f,
        sweepAngle = 240f,
        useCenter = false,
        topLeft = Offset(centerX - size * 1.2f, centerY - size * 0.6f),
        size = androidx.compose.ui.geometry.Size(size * 2.4f, size * 1.2f),
        style = androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth)
    )
    
    // Diamond links if applicable
    if (product.materials.contains("Diamond")) {
        repeat(5) { i ->
            val angle = -30f + (i * 48f)
            val radians = angle * PI / 180
            val x = centerX + cos(radians) * size * 1.1f
            val y = centerY + sin(radians) * size * 0.5f
            
            drawCircle(
                color = Color(0xFFE8E8E8),
                radius = size * 0.15f,
                center = Offset(x.toFloat(), y.toFloat()),
                style = if (wireframe) androidx.compose.ui.graphics.drawscope.Stroke(2.dp.toPx()) else androidx.compose.ui.graphics.drawscope.Fill
            )
        }
    }
}

private fun DrawScope.drawWatch(
    centerX: Float,
    centerY: Float,
    size: Float,
    product: Product,
    wireframe: Boolean
) {
    val watchColor = getMaterialColor(product.materials)
    val strokeWidth = if (wireframe) 3.dp.toPx() else 6.dp.toPx()
    
    // Watch case
    drawCircle(
        color = watchColor,
        radius = size,
        center = Offset(centerX, centerY),
        style = androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth)
    )
    
    // Watch face
    if (!wireframe) {
        drawCircle(
            color = Color.Black,
            radius = size * 0.8f,
            center = Offset(centerX, centerY),
            style = androidx.compose.ui.graphics.drawscope.Fill
        )
    }
    
    // Watch band
    drawArc(
        color = watchColor,
        startAngle = 45f,
        sweepAngle = 90f,
        useCenter = false,
        topLeft = Offset(centerX - size * 1.4f, centerY - size * 1.4f),
        size = androidx.compose.ui.geometry.Size(size * 2.8f, size * 2.8f),
        style = androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth)
    )
    
    drawArc(
        color = watchColor,
        startAngle = 225f,
        sweepAngle = 90f,
        useCenter = false,
        topLeft = Offset(centerX - size * 1.4f, centerY - size * 1.4f),
        size = androidx.compose.ui.geometry.Size(size * 2.8f, size * 2.8f),
        style = androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth)
    )
    
    // Diamond bezel if applicable
    if (product.materials.contains("Diamond")) {
        repeat(12) { i ->
            val angle = i * 30f
            val radians = angle * PI / 180
            val x = centerX + cos(radians) * size * 1.1f
            val y = centerY + sin(radians) * size * 1.1f
            
            drawCircle(
                color = Color(0xFFE8E8E8),
                radius = size * 0.08f,
                center = Offset(x.toFloat(), y.toFloat()),
                style = androidx.compose.ui.graphics.drawscope.Fill
            )
        }
    }
}

private fun DrawScope.drawGenericJewelry(
    centerX: Float,
    centerY: Float,
    size: Float,
    product: Product,
    wireframe: Boolean
) {
    val jewelryColor = getMaterialColor(product.materials)
    val style: DrawStyle = if (wireframe) androidx.compose.ui.graphics.drawscope.Stroke(4.dp.toPx()) else androidx.compose.ui.graphics.drawscope.Fill
    
    // Generic gem shape
    drawCircle(
        color = jewelryColor,
        radius = size,
        center = Offset(centerX, centerY),
        style = style
    )
    
    if (!wireframe) {
        // Highlight
        drawCircle(
            color = Color.White.copy(alpha = 0.4f),
            radius = size * 0.6f,
            center = Offset(centerX - size * 0.3f, centerY - size * 0.3f),
            style = androidx.compose.ui.graphics.drawscope.Fill
        )
    }
}

private fun getMaterialColor(materials: List<String>): Color {
    return when {
        materials.contains("Gold") || materials.contains("18K Gold") -> Color(0xFFFFD700)
        materials.contains("Diamond") -> Color(0xFFE8E8E8)
        materials.contains("Platinum") -> Color(0xFFE5E4E2)
        materials.contains("Emerald") -> Color(0xFF50C878)
        materials.contains("Pearl") -> Color(0xFFF8F6F0)
        else -> Color(0xFFC0C0C0)
    }
}