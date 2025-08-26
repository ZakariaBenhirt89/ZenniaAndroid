package com.mimi.ecommerceloginapp.screens

import androidx.compose.animation.core.*
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProductDetailsScreen(
    product: Product,
    onBack: () -> Unit,
    onAddToCart: (Product) -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: (String) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var selectedImageIndex by remember { mutableStateOf(0) }
    var quantity by remember { mutableStateOf(1) }
    var selectedSize by remember { mutableStateOf("") }
    var showFullDescription by remember { mutableStateOf(false) }
    var show3DView by remember { mutableStateOf(false) }
    
    val infiniteTransition = rememberInfiniteTransition(label = "product_details")
    
    // Sparkle animation for luxury feel
    val sparkleOpacity by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sparkle"
    )
    
    // Mock additional product images
    val productImages = remember {
        listOf(
            product.image,
            product.image, // In real app, these would be different images
            product.image
        )
    }
    
    // Mock available sizes
    val availableSizes = remember { listOf("6", "7", "8", "9", "10") }
    
    val pagerState = rememberPagerState(pageCount = { productImages.size })
    
    LaunchedEffect(selectedImageIndex) {
        pagerState.animateScrollToPage(selectedImageIndex)
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedBackground()
        
        if (show3DView) {
            // 3D Viewer Mode
            ProductViewer3D(
                product = product,
                onClose = { show3DView = false },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Regular Product Details Mode
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
            // Top App Bar
            TopAppBar(
                title = { Text("Product Details", color = Color.White) },
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
                    IconButton(onClick = { onToggleFavorite(product.id) }) {
                        Icon(
                            if (isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color.Red else Color.White
                        )
                    }
                    IconButton(onClick = { 
                        Toast.makeText(context, "Share functionality", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "Share",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black.copy(alpha = 0.3f)
                )
            )
            
            // Product Images Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.05f)
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Main image pager
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        Box(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(productImages[page])
                                    .crossfade(true)
                                    .build(),
                                contentDescription = product.name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(20.dp)),
                                contentScale = ContentScale.Crop
                            )
                            
                            // Luxury sparkle overlay
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.radialGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                ZenniaColors.accent.copy(alpha = sparkleOpacity * 0.1f),
                                                Color.Transparent
                                            ),
                                            radius = 300f
                                        )
                                    )
                            )
                        }
                    }
                    
                    // Image indicators
                    if (productImages.size > 1) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            productImages.forEachIndexed { index, _ ->
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(
                                            color = if (pagerState.currentPage == index) 
                                                ZenniaColors.accent 
                                            else 
                                                Color.White.copy(alpha = 0.5f),
                                            shape = CircleShape
                                        )
                                        .clickable { selectedImageIndex = index }
                                )
                            }
                        }
                    }
                    
                    // Badges
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                    ) {
                        if (product.isNew) {
                            Badge(
                                containerColor = Color(0xFF3B82F6),
                                contentColor = Color.White
                            ) {
                                Text("New", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        if (product.discount != null) {
                            Badge(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ) {
                                Text("-${product.discount}%", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    
                    // 3D View Button
                    FloatingActionButton(
                        onClick = { show3DView = true },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp),
                        containerColor = ZenniaColors.accent,
                        contentColor = Color.Black,
                        elevation = FloatingActionButtonDefaults.elevation(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Rotate90DegreesCcw,
                            contentDescription = "View in 3D",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            
            // Product Information Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.05f)
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Product Name
                    Text(
                        text = product.name,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 32.sp
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Collection
                    Text(
                        text = "${product.collection.replaceFirstChar { it.uppercase() }} Collection",
                        color = ZenniaColors.accent,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Rating
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(5) { index ->
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = if (index < product.rating.toInt()) 
                                    ZenniaColors.accent 
                                else 
                                    Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${product.rating} (${product.reviewCount} reviews)",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Price
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "$${String.format("%,d", product.price)}",
                            color = ZenniaColors.accent,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (product.originalPrice != null) {
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "$${String.format("%,d", product.originalPrice)}",
                                color = Color.White.copy(alpha = 0.5f),
                                fontSize = 18.sp,
                                textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Materials
                    Text(
                        text = "Materials",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(product.materials) { material ->
                            Surface(
                                color = ZenniaColors.accent.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(20.dp),
                                border = BorderStroke(1.dp, ZenniaColors.accent.copy(alpha = 0.3f))
                            ) {
                                Text(
                                    text = material,
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Size Selection (for rings)
                    if (product.category == "rings") {
                        Text(
                            text = "Size",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(availableSizes) { size ->
                                Surface(
                                    color = if (selectedSize == size) 
                                        ZenniaColors.accent 
                                    else 
                                        Color.White.copy(alpha = 0.1f),
                                    shape = CircleShape,
                                    border = BorderStroke(
                                        1.dp, 
                                        if (selectedSize == size) 
                                            ZenniaColors.accent 
                                        else 
                                            Color.White.copy(alpha = 0.3f)
                                    ),
                                    modifier = Modifier.clickable { selectedSize = size }
                                ) {
                                    Text(
                                        text = size,
                                        color = if (selectedSize == size) Color.Black else Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(16.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    
                    // Description
                    Text(
                        text = "Description",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (showFullDescription) {
                            "${product.description}\n\nThis exquisite piece represents the pinnacle of luxury craftsmanship. Each element is carefully selected and meticulously crafted by our master artisans. The design combines timeless elegance with contemporary sophistication, making it perfect for both special occasions and everyday luxury."
                        } else {
                            product.description
                        },
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                    TextButton(
                        onClick = { showFullDescription = !showFullDescription }
                    ) {
                        Text(
                            text = if (showFullDescription) "Show Less" else "Read More",
                            color = ZenniaColors.accent
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(100.dp)) // Space for bottom section
        }
        
        // Bottom Action Section
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = 0.8f),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // Quantity Selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quantity",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        IconButton(
                            onClick = { if (quantity > 1) quantity-- },
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    Color.White.copy(alpha = 0.1f),
                                    CircleShape
                                )
                        ) {
                            Icon(
                                Icons.Default.Remove,
                                contentDescription = "Decrease",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        
                        Text(
                            text = quantity.toString(),
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.widthIn(min = 30.dp),
                            textAlign = TextAlign.Center
                        )
                        
                        IconButton(
                            onClick = { quantity++ },
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    ZenniaColors.accent.copy(alpha = 0.2f),
                                    CircleShape
                                )
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Increase",
                                tint = ZenniaColors.accent,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Add to Cart Button
                Button(
                    onClick = {
                        if (product.category == "rings" && selectedSize.isEmpty()) {
                            Toast.makeText(context, "Please select a size", Toast.LENGTH_SHORT).show()
                        } else {
                            repeat(quantity) {
                                onAddToCart(product)
                            }
                            Toast.makeText(
                                context, 
                                "$quantity x ${product.name} added to cart", 
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    enabled = product.inStock,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ZenniaColors.accent,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (product.inStock) {
                            "Add to Cart • $${String.format("%,d", product.price * quantity)}"
                        } else {
                            "Out of Stock"
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        } // Close the if-else block
    }
}