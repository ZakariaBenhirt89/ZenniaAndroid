package com.mimi.ecommerceloginapp.screens

import androidx.compose.animation.core.*
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mimi.ecommerceloginapp.components.*
import android.widget.Toast
import coil.compose.AsyncImage
import coil.request.ImageRequest

// User data model
data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val profileImage: String?,
    val memberSince: String,
    val totalOrders: Int,
    val totalSpent: Int,
    val loyaltyPoints: Int,
    val membershipTier: String,
    val addresses: List<Address>,
    val paymentMethods: List<PaymentMethod>
)

data class Address(
    val id: String,
    val label: String,
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val isDefault: Boolean
)

data class PaymentMethod(
    val id: String,
    val type: String, // "card", "paypal", etc.
    val lastFour: String,
    val expiryMonth: Int,
    val expiryYear: Int,
    val isDefault: Boolean
)

data class OrderHistory(
    val id: String,
    val date: String,
    val items: List<Product>,
    val total: Int,
    val status: String // "delivered", "processing", "shipped"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit,
    favoriteProducts: List<Product> = emptyList()
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf("overview") }
    
    // Mock user data - in real app this would come from API/database
    val userProfile = remember {
        UserProfile(
            id = "user_001",
            name = "Sarah Johnson",
            email = "sarah.johnson@email.com",
            profileImage = "https://images.unsplash.com/photo-1494790108755-2616b612b639?w=150",
            memberSince = "March 2023",
            totalOrders = 12,
            totalSpent = 28500,
            loyaltyPoints = 2850,
            membershipTier = "Gold",
            addresses = listOf(
                Address(
                    id = "addr_1",
                    label = "Home",
                    street = "123 Luxury Lane",
                    city = "Beverly Hills",
                    state = "CA",
                    zipCode = "90210",
                    isDefault = true
                ),
                Address(
                    id = "addr_2",
                    label = "Office",
                    street = "456 Business Blvd",
                    city = "Los Angeles",
                    state = "CA",
                    zipCode = "90028",
                    isDefault = false
                )
            ),
            paymentMethods = listOf(
                PaymentMethod(
                    id = "pm_1",
                    type = "Visa",
                    lastFour = "4242",
                    expiryMonth = 12,
                    expiryYear = 2026,
                    isDefault = true
                ),
                PaymentMethod(
                    id = "pm_2",
                    type = "Mastercard",
                    lastFour = "8888",
                    expiryMonth = 8,
                    expiryYear = 2025,
                    isDefault = false
                )
            )
        )
    }
    
    // Mock order history
    val orderHistory = remember {
        listOf(
            OrderHistory(
                id = "order_001",
                date = "Dec 15, 2024",
                items = favoriteProducts.take(2),
                total = 5299,
                status = "delivered"
            ),
            OrderHistory(
                id = "order_002",
                date = "Nov 28, 2024", 
                items = favoriteProducts.take(1),
                total = 2850,
                status = "delivered"
            ),
            OrderHistory(
                id = "order_003",
                date = "Nov 10, 2024",
                items = favoriteProducts.take(3),
                total = 8999,
                status = "processing"
            )
        )
    }
    
    val infiniteTransition = rememberInfiniteTransition(label = "profile")
    
    // Glow animation for membership tier
    val tierGlow by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "tier_glow"
    )
    
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedBackground()
        
        Column(modifier = Modifier.fillMaxSize()) {
            // Top App Bar
            TopAppBar(
                title = { Text("My Profile", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black.copy(alpha = 0.3f)
                )
            )
            
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    // Profile Header Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.05f)
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Profile Image
                            Box {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(userProfile.profileImage)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Profile",
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(CircleShape)
                                        .border(3.dp, ZenniaColors.accent, CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                                
                                // Edit button
                                IconButton(
                                    onClick = { 
                                        Toast.makeText(context, "Edit Profile", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .size(32.dp)
                                        .background(ZenniaColors.accent, CircleShape)
                                ) {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = "Edit",
                                        tint = Color.Black,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Name
                            Text(
                                text = userProfile.name,
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            
                            // Email
                            Text(
                                text = userProfile.email,
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 14.sp
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            // Membership Tier
                            Surface(
                                color = when (userProfile.membershipTier) {
                                    "Gold" -> Color(0xFFFFD700).copy(alpha = tierGlow * 0.3f)
                                    "Silver" -> Color(0xFFC0C0C0).copy(alpha = tierGlow * 0.3f)
                                    "Platinum" -> Color(0xFFE5E4E2).copy(alpha = tierGlow * 0.3f)
                                    else -> ZenniaColors.accent.copy(alpha = tierGlow * 0.3f)
                                },
                                shape = RoundedCornerShape(20.dp),
                                border = BorderStroke(1.dp, ZenniaColors.accent.copy(alpha = tierGlow))
                            ) {
                                Text(
                                    text = "${userProfile.membershipTier} Member",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Stats Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                ProfileStatCard("Orders", userProfile.totalOrders.toString())
                                ProfileStatCard("Spent", "$${String.format("%,d", userProfile.totalSpent)}")
                                ProfileStatCard("Points", String.format("%,d", userProfile.loyaltyPoints))
                            }
                        }
                    }
                }
                
                item {
                    // Tab Navigation
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.05f)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        LazyRow(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val tabs = listOf(
                                "overview" to "Overview",
                                "orders" to "Orders",
                                "favorites" to "Favorites",
                                "addresses" to "Addresses",
                                "payments" to "Payment"
                            )
                            
                            items(tabs) { (id, label) ->
                                FilterChip(
                                    onClick = { selectedTab = id },
                                    label = { Text(label) },
                                    selected = selectedTab == id,
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = ZenniaColors.accent,
                                        selectedLabelColor = Color.Black,
                                        containerColor = Color.Transparent,
                                        labelColor = Color.White
                                    )
                                )
                            }
                        }
                    }
                }
                
                // Tab Content
                when (selectedTab) {
                    "overview" -> {
                        item {
                            ProfileOverviewSection(userProfile, orderHistory.take(3))
                        }
                    }
                    "orders" -> {
                        items(orderHistory) { order ->
                            OrderHistoryCard(order)
                        }
                    }
                    "favorites" -> {
                        if (favoriteProducts.isNotEmpty()) {
                            items(favoriteProducts) { product ->
                                FavoriteProductCard(product)
                            }
                        } else {
                            item {
                                EmptyStateCard(
                                    icon = Icons.Default.FavoriteBorder,
                                    title = "No Favorites Yet",
                                    description = "Start browsing to add items to your favorites"
                                )
                            }
                        }
                    }
                    "addresses" -> {
                        items(userProfile.addresses) { address ->
                            AddressCard(address)
                        }
                        item {
                            AddNewItemCard("Add New Address") {
                                Toast.makeText(context, "Add Address", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    "payments" -> {
                        items(userProfile.paymentMethods) { payment ->
                            PaymentMethodCard(payment)
                        }
                        item {
                            AddNewItemCard("Add Payment Method") {
                                Toast.makeText(context, "Add Payment Method", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                
                item {
                    // Logout Button
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Red.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        ListItem(
                            headlineContent = { 
                                Text(
                                    "Logout", 
                                    color = Color.Red,
                                    fontWeight = FontWeight.Medium
                                ) 
                            },
                            leadingContent = {
                                Icon(
                                    Icons.Default.ExitToApp,
                                    contentDescription = null,
                                    tint = Color.Red
                                )
                            },
                            modifier = Modifier.clickable { onLogout() },
                            colors = ListItemDefaults.colors(
                                containerColor = Color.Transparent
                            )
                        )
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(100.dp)) // Bottom navigation space
                }
            }
        }
    }
}

@Composable
private fun ProfileStatCard(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            color = ZenniaColors.accent,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp
        )
    }
}

@Composable
private fun ProfileOverviewSection(
    userProfile: UserProfile,
    recentOrders: List<OrderHistory>
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Account Info
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.05f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Account Information",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Member since ${userProfile.memberSince}",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                Text(
                    text = "Loyalty Points: ${String.format("%,d", userProfile.loyaltyPoints)}",
                    color = ZenniaColors.accent,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        
        // Recent Orders
        if (recentOrders.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.05f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Recent Orders",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    recentOrders.forEach { order ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = order.id,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = order.date,
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 12.sp
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "$${String.format("%,d", order.total)}",
                                    color = ZenniaColors.accent,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = order.status.replaceFirstChar { it.uppercase() },
                                    color = when (order.status) {
                                        "delivered" -> Color.Green
                                        "processing" -> Color(0xFFFFA500)
                                        "shipped" -> Color.Blue
                                        else -> Color.Gray
                                    },
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderHistoryCard(order: OrderHistory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.05f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "Order ${order.id}",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = order.date,
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 14.sp
                    )
                }
                Surface(
                    color = when (order.status) {
                        "delivered" -> Color.Green.copy(alpha = 0.2f)
                        "processing" -> Color(0xFFFFA500).copy(alpha = 0.2f)
                        "shipped" -> Color.Blue.copy(alpha = 0.2f)
                        else -> Color.Gray.copy(alpha = 0.2f)
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = order.status.replaceFirstChar { it.uppercase() },
                        color = when (order.status) {
                            "delivered" -> Color.Green
                            "processing" -> Color(0xFFFFA500)
                            "shipped" -> Color.Blue
                            else -> Color.Gray
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "${order.items.size} item${if (order.items.size != 1) "s" else ""}",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
            
            Text(
                text = "Total: $${String.format("%,d", order.total)}",
                color = ZenniaColors.accent,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun FavoriteProductCard(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.05f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.image)
                    .crossfade(true)
                    .build(),
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = product.collection.replaceFirstChar { it.uppercase() },
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${String.format("%,d", product.price)}",
                    color = ZenniaColors.accent,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            IconButton(
                onClick = { /* Remove from favorites */ }
            ) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Remove from favorites",
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
private fun AddressCard(address: Address) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.05f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = address.label,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                if (address.isDefault) {
                    Surface(
                        color = ZenniaColors.accent.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Default",
                            color = ZenniaColors.accent,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${address.street}\n${address.city}, ${address.state} ${address.zipCode}",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun PaymentMethodCard(payment: PaymentMethod) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.05f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = payment.type,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (payment.isDefault) {
                        Surface(
                            color = ZenniaColors.accent.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Default",
                                color = ZenniaColors.accent,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
                Text(
                    text = "•••• •••• •••• ${payment.lastFour}",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                Text(
                    text = "Expires ${payment.expiryMonth}/${payment.expiryYear}",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 12.sp
                )
            }
            
            Icon(
                Icons.Default.CreditCard,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun AddNewItemCard(text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.05f)
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, ZenniaColors.accent.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                tint = ZenniaColors.accent,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = ZenniaColors.accent,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun EmptyStateCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.05f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.3f),
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}