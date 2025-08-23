package com.mimi.ecommerceloginapp.screens

import androidx.compose.animation.core.*
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mimi.ecommerceloginapp.components.AnimatedBackground
import com.mimi.ecommerceloginapp.components.ZenniaColors
import android.widget.Toast
import coil.compose.AsyncImage
import coil.request.ImageRequest

// Product data model
data class Product(
    val id: String,
    val name: String,
    val price: Int,
    val originalPrice: Int? = null,
    val image: String,
    val rating: Float,
    val reviewCount: Int,
    val category: String,
    val collection: String,
    val description: String,
    val materials: List<String>,
    val inStock: Boolean,
    val isNew: Boolean,
    val isFeatured: Boolean,
    val discount: Int? = null
)

// Cart item model
data class CartItem(
    val product: Product,
    val quantity: Int
)

// Category model
data class Category(
    val id: String,
    val name: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcommerceDisplayScreen(
    onLogout: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var cartItems by remember { mutableStateOf<List<CartItem>>(emptyList()) }
    var favorites by remember { mutableStateOf<Set<String>>(emptySet()) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    // Simulate loading
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1500)
        isLoading = false
    }

    // Sample Zennia products
    val zenniaProducts = remember {
        listOf(
            Product(
                id = "ZEN001",
                name = "Eternal Diamond Ring",
                price = 2850,
                originalPrice = 3200,
                image = "https://images.unsplash.com/photo-1602752250015-52934bc45613?w=400",
                rating = 4.9f,
                reviewCount = 156,
                category = "rings",
                collection = "bridal",
                description = "Stunning solitaire diamond ring with platinum setting",
                materials = listOf("18K Gold", "Diamond", "Platinum"),
                inStock = true,
                isNew = false,
                isFeatured = true,
                discount = 11
            ),
            Product(
                id = "ZEN002",
                name = "Celestial Gold Pendant",
                price = 1899,
                image = "https://images.unsplash.com/photo-1602752250055-5ebb552fc3ae?w=400",
                rating = 4.8f,
                reviewCount = 89,
                category = "necklaces",
                collection = "celestial",
                description = "Elegant 18k gold pendant with star constellation design",
                materials = listOf("18K Gold", "Diamond"),
                inStock = true,
                isNew = true,
                isFeatured = false
            ),
            Product(
                id = "ZEN003",
                name = "Pearl Diamond Drops",
                price = 1299,
                originalPrice = 1499,
                image = "https://images.unsplash.com/photo-1591241193546-8c6823c3958a?w=400",
                rating = 4.7f,
                reviewCount = 124,
                category = "earrings",
                collection = "classic",
                description = "Lustrous pearl drop earrings with diamond accents",
                materials = listOf("Pearl", "Diamond", "Gold"),
                inStock = true,
                isNew = false,
                isFeatured = true,
                discount = 13
            ),
            Product(
                id = "ZEN004",
                name = "Luxury Tennis Bracelet",
                price = 3299,
                image = "https://images.unsplash.com/photo-1655707063496-e1c00b3280de?w=400",
                rating = 5.0f,
                reviewCount = 67,
                category = "bracelets",
                collection = "signature",
                description = "Exquisite tennis bracelet with premium diamonds",
                materials = listOf("Diamond", "18K Gold"),
                inStock = false,
                isNew = false,
                isFeatured = false
            ),
            Product(
                id = "ZEN005",
                name = "Diamond Elite Timepiece",
                price = 8599,
                originalPrice = 9999,
                image = "https://images.unsplash.com/photo-1704961237262-a97295a6fea8?w=400",
                rating = 4.9f,
                reviewCount = 203,
                category = "watches",
                collection = "elite",
                description = "Premium timepiece with diamond bezel and luxury movement",
                materials = listOf("Diamond", "Platinum", "Sapphire"),
                inStock = true,
                isNew = true,
                isFeatured = true,
                discount = 14
            ),
            Product(
                id = "ZEN006",
                name = "Emerald Royale Ring",
                price = 4599,
                image = "https://images.unsplash.com/photo-1583937443351-f2f669fbe2cf?w=400",
                rating = 4.8f,
                reviewCount = 78,
                category = "rings",
                collection = "royale",
                description = "Magnificent emerald centerpiece with diamond halo",
                materials = listOf("Emerald", "Diamond", "Platinum"),
                inStock = true,
                isNew = false,
                isFeatured = false
            )
        )
    }

    val categories = remember {
        listOf(
            Category("All", "All", Icons.Default.List),
            Category("rings", "Rings", Icons.Default.Favorite),
            Category("necklaces", "Necklaces", Icons.Default.Star),
            Category("earrings", "Earrings", Icons.Default.FavoriteBorder),
            Category("bracelets", "Bracelets", Icons.Default.Star),
            Category("watches", "Watches", Icons.Default.AccessTime)
        )
    }

    val filteredProducts = zenniaProducts.filter { product ->
        val matchesSearch = product.name.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategory == "All" || product.category == selectedCategory
        matchesSearch && matchesCategory
    }

    fun addToCart(product: Product) {
        cartItems = cartItems.toMutableList().apply {
            val existingItemIndex = indexOfFirst { it.product.id == product.id }
            if (existingItemIndex != -1) {
                this[existingItemIndex] = this[existingItemIndex].copy(quantity = this[existingItemIndex].quantity + 1)
            } else {
                add(CartItem(product, 1))
            }
        }
        Toast.makeText(context, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
    }

    fun toggleFavorite(productId: String) {
        favorites = if (favorites.contains(productId)) {
            favorites - productId
        } else {
            favorites + productId
        }
    }

    val cartItemCount = cartItems.sumOf { it.quantity }
    val cartTotal = cartItems.sumOf { it.product.price * it.quantity }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedBackground()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                CircularProgressIndicator(
                    color = ZenniaColors.accent,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Loading Zennia Collection...",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        return
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedBackground()
        
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Zennia",
                            tint = ZenniaColors.accent,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Zennia",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                },
                actions = {
                    BadgedBox(
                        badge = {
                            if (cartItemCount > 0) {
                                Badge(
                                    containerColor = ZenniaColors.accent,
                                    contentColor = Color.Black
                                ) {
                                    Text(text = cartItemCount.toString())
                                }
                            }
                        }
                    ) {
                        IconButton(
                            onClick = {
                                Toast.makeText(context, "Cart: $cartItemCount items - $${cartTotal}", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = "Cart",
                                tint = Color.White
                            )
                        }
                    }
                    
                    IconButton(onClick = onLogout) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black.copy(alpha = 0.3f)
                )
            )

            // Search Bar
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { 
                        Text(
                            "Search luxury jewelry...",
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = ZenniaColors.accent
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = ZenniaColors.accent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
            }

            // Category Filter
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        onClick = { selectedCategory = category.id },
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    category.icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(category.name)
                            }
                        },
                        selected = selectedCategory == category.id,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ZenniaColors.accent,
                            selectedLabelColor = Color.Black,
                            containerColor = Color.White.copy(alpha = 0.1f),
                            labelColor = Color.White
                        )
                    )
                }
            }

            // Products Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredProducts) { product ->
                    ProductCard(
                        product = product,
                        isFavorite = favorites.contains(product.id),
                        onAddToCart = { addToCart(product) },
                        onToggleFavorite = { toggleFavorite(product.id) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(
    product: Product,
    isFavorite: Boolean,
    onAddToCart: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.75f),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.05f)
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Column {
            // Product Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentScale = ContentScale.Crop
                )

                // Badges
                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                ) {
                    if (product.isNew) {
                        Badge(
                            containerColor = Color(0xFF3B82F6),
                            contentColor = Color.White
                        ) {
                            Text("New", fontSize = 10.sp)
                        }
                    }
                    if (product.discount != null) {
                        Badge(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ) {
                            Text("-${product.discount}%", fontSize = 10.sp)
                        }
                    }
                    if (!product.inStock) {
                        Badge(
                            containerColor = Color.Gray,
                            contentColor = Color.White
                        ) {
                            Text("Sold Out", fontSize = 10.sp)
                        }
                    }
                }

                // Favorite button
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        if (isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.White
                    )
                }
            }

            // Product Details
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = product.name,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Rating
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    repeat(5) { index ->
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = if (index < product.rating.toInt()) 
                                ZenniaColors.accent 
                            else 
                                Color.Gray,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Text(
                        text = " (${product.reviewCount})",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                }

                // Price
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "$${product.price}",
                        color = ZenniaColors.accent,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (product.originalPrice != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "$${product.originalPrice}",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 12.sp,
                            textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                        )
                    }
                }

                // Add to Cart Button
                Button(
                    onClick = onAddToCart,
                    enabled = product.inStock,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ZenniaColors.accent,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add to Cart", fontSize = 12.sp)
                }
            }
        }
    }
}
