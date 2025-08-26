package com.mimi.ecommerceloginapp.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class BottomNavTab(
    val id: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavTab("home", "Home", Icons.Default.Home)
    object Browse : BottomNavTab("browse", "Browse", Icons.Default.Star) // Using Star as Diamond alternative
    object Wishlist : BottomNavTab("wishlist", "Wishlist", Icons.Default.Favorite)
    object Profile : BottomNavTab("profile", "Profile", Icons.Default.AccountCircle)
}

@Composable
fun ZenniaBottomNavigation(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    wishlistCount: Int = 0,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        BottomNavTab.Home,
        BottomNavTab.Browse,
        BottomNavTab.Wishlist,
        BottomNavTab.Profile
    )

    // Bottom navigation container with glass morphism effect (like React version)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(
                color = Color.Black.copy(alpha = 0.3f),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .blur(1.dp) // Backdrop blur effect
    ) {
        // Glass overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.1f),
                            Color.White.copy(alpha = 0.05f)
                        )
                    ),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
        )
        
        // Navigation items
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEach { tab ->
                BottomNavItem(
                    tab = tab,
                    isSelected = selectedTab == tab.id,
                    onClick = { onTabSelected(tab.id) },
                    badge = if (tab is BottomNavTab.Wishlist) wishlistCount else null
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    tab: BottomNavTab,
    isSelected: Boolean,
    onClick: () -> Unit,
    badge: Int? = null,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "nav_item_${tab.id}")
    
    // Scale animation when selected (like React version)
    val scale by infiniteTransition.animateFloat(
        initialValue = if (isSelected) 1f else 1f,
        targetValue = if (isSelected) 1.1f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(300, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    // Color animation
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) ZenniaColors.accent else Color.White.copy(alpha = 0.7f),
        animationSpec = tween(300),
        label = "icon_color"
    )
    
    val textColor by animateColorAsState(
        targetValue = if (isSelected) ZenniaColors.accent else Color.White.copy(alpha = 0.7f),
        animationSpec = tween(300),
        label = "text_color"
    )
    
    Column(
        modifier = modifier
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .background(
                color = if (isSelected) {
                    ZenniaColors.accent.copy(alpha = 0.2f)
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon with badge
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                imageVector = tab.icon,
                contentDescription = tab.label,
                tint = iconColor,
                modifier = Modifier
                    .size(24.dp)
                    .graphicsLayer(
                        scaleX = if (isSelected) scale else 1f,
                        scaleY = if (isSelected) scale else 1f
                    )
            )
            
            // Badge for notifications (like React version)
            if (badge != null && badge > 0) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(
                            color = Color.Red,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (badge > 99) "99+" else badge.toString(),
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(2.dp))
        
        // Label
        Text(
            text = tab.label,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}