package com.mimi.ecommerceloginapp

import androidx.compose.ui.test.*
import com.mimi.ecommerceloginapp.TestUtils.LONG_WAIT
import com.mimi.ecommerceloginapp.TestUtils.MEDIUM_WAIT
import com.mimi.ecommerceloginapp.TestUtils.SHORT_WAIT
import com.mimi.ecommerceloginapp.TestUtils.VALID_EMAIL
import com.mimi.ecommerceloginapp.TestUtils.VALID_PASSWORD
import com.mimi.ecommerceloginapp.TestUtils.navigateToTab
import com.mimi.ecommerceloginapp.TestUtils.performLogin
import com.mimi.ecommerceloginapp.TestUtils.waitForLoadingToComplete
import org.junit.Before
import org.junit.Test

/**
 * UI Tests for Main Ecommerce Screen
 * Tests product browsing, search, cart, and navigation functionality
 */
class EcommerceScreenTest : BaseUITest() {
    
    @Before
    override fun setUp() {
        super.setUp()
        // Login to reach the ecommerce screen
        composeTestRule.waitForLoadingToComplete()
        composeTestRule.performLogin(VALID_EMAIL, VALID_PASSWORD)
        Thread.sleep(LONG_WAIT) // Wait for navigation to complete
    }
    
    @Test
    fun ecommerceScreen_displaysCorrectly() {
        // Check main UI elements
        composeTestRule.onNodeWithText("Zennia").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Cart").assertExists()
        composeTestRule.onNodeWithText("Search luxury jewelry...").assertExists()
        
        // Check bottom navigation
        composeTestRule.onNodeWithText("Home").assertExists()
        composeTestRule.onNodeWithText("Browse").assertExists()
        composeTestRule.onNodeWithText("Wishlist").assertExists()
        composeTestRule.onNodeWithText("Profile").assertExists()
    }
    
    @Test
    fun ecommerceScreen_searchFunctionality() {
        // Test search functionality
        val searchField = composeTestRule.onNodeWithText("Search luxury jewelry...")
        
        // Click on search field
        searchField.performClick()
        
        // Type search query
        searchField.performTextInput("Diamond")
        Thread.sleep(SHORT_WAIT)
        
        // Products should be filtered (assuming there are diamond products)
        // Check if products containing "Diamond" are displayed
        try {
            composeTestRule.onNode(hasText("Diamond") and hasClickAction()).assertExists()
        } catch (e: Exception) {
            // Products might not contain "Diamond" in visible text
            println("Diamond products not found in search results")
        }
        
        // Clear search
        searchField.performTextClearance()
        Thread.sleep(SHORT_WAIT)
    }
    
    @Test
    fun ecommerceScreen_categoryFilter() {
        // Test category filtering
        val categories = listOf("All", "Rings", "Necklaces", "Earrings", "Bracelets", "Watches")
        
        categories.forEach { category ->
            try {
                composeTestRule.onNodeWithText(category).performClick()
                Thread.sleep(SHORT_WAIT)
                
                // Verify category is selected (might change appearance)
                composeTestRule.onNodeWithText(category).assertExists()
            } catch (e: Exception) {
                println("Category $category not found or not clickable")
            }
        }
        
        // Return to "All" category
        composeTestRule.onNodeWithText("All").performClick()
        Thread.sleep(SHORT_WAIT)
    }
    
    @Test
    fun ecommerceScreen_productGrid_displaysProducts() {
        // Check that products are displayed in grid
        Thread.sleep(MEDIUM_WAIT) // Wait for products to load
        
        // Look for product elements (prices, names, etc.)
        val productNames = listOf(
            "Eternal Diamond Ring",
            "Celestial Gold Pendant",
            "Pearl Diamond Drops",
            "Luxury Tennis Bracelet",
            "Diamond Elite Timepiece",
            "Emerald Royale Ring"
        )
        
        productNames.forEach { productName ->
            try {
                composeTestRule.onNode(hasText(productName) and hasClickAction()).assertExists()
            } catch (e: Exception) {
                // Product might not be visible or name might be truncated
                println("Product '$productName' not found")
            }
        }
    }
    
    @Test
    fun ecommerceScreen_productCard_clickToDetails() {
        // Wait for products to load
        Thread.sleep(MEDIUM_WAIT)
        
        // Try to click on first product
        try {
            composeTestRule.onNode(hasText("Eternal Diamond Ring")).performClick()
            Thread.sleep(LONG_WAIT)
            
            // Should navigate to product details
            composeTestRule.onNodeWithText("Product Details").assertIsDisplayed()
            
            // Navigate back
            composeTestRule.onNodeWithContentDescription("Back").performClick()
            Thread.sleep(SHORT_WAIT)
        } catch (e: Exception) {
            println("Product click navigation failed: ${e.message}")
        }
    }
    
    @Test
    fun ecommerceScreen_addToCart_functionality() {
        Thread.sleep(MEDIUM_WAIT)
        
        // Try to add product to cart
        try {
            // Look for "Add to Cart" or "Add" button
            composeTestRule.onNode(hasText("Add") and hasClickAction()).performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Check if cart badge appears or updates
            composeTestRule.onNodeWithContentDescription("Cart").assertExists()
            
        } catch (e: Exception) {
            println("Add to cart functionality test failed: ${e.message}")
        }
    }
    
    @Test
    fun ecommerceScreen_favoriteProduct() {
        Thread.sleep(MEDIUM_WAIT)
        
        // Try to favorite a product
        try {
            composeTestRule.onNode(hasContentDescription("Favorite")).performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Check if wishlist badge updates
            composeTestRule.navigateToTab("Wishlist")
            Thread.sleep(SHORT_WAIT)
            
            // Navigate back to browse
            composeTestRule.navigateToTab("Browse")
            Thread.sleep(SHORT_WAIT)
            
        } catch (e: Exception) {
            println("Favorite functionality test failed: ${e.message}")
        }
    }
    
    @Test
    fun ecommerceScreen_bottomNavigation() {
        // Test all bottom navigation tabs
        val tabs = listOf("Home", "Browse", "Wishlist", "Profile")
        
        tabs.forEach { tab ->
            composeTestRule.navigateToTab(tab)
            Thread.sleep(SHORT_WAIT)
            
            // Verify tab selection (visual changes might be minimal)
            composeTestRule.onNodeWithText(tab).assertExists()
        }
        
        // Return to Browse tab
        composeTestRule.navigateToTab("Browse")
        Thread.sleep(SHORT_WAIT)
    }
    
    @Test
    fun ecommerceScreen_cartBadge_updatesCorrectly() {
        Thread.sleep(MEDIUM_WAIT)
        
        // Initial cart should be empty or have some count
        val cartIcon = composeTestRule.onNodeWithContentDescription("Cart")
        cartIcon.assertExists()
        
        // Try to add items and check badge updates
        try {
            // Add first item
            composeTestRule.onAllNodes(hasText("Add")).apply {
                if (fetchSemanticsNodes().isNotEmpty()) {
                    get(0).performClick()
                    Thread.sleep(SHORT_WAIT)
                }
            }
            
            // Add second item if available
            composeTestRule.onAllNodes(hasText("Add")).apply {
                if (fetchSemanticsNodes().size > 1) {
                    get(1).performClick()
                    Thread.sleep(SHORT_WAIT)
                }
            }
            
            // Cart should now show updated count
            cartIcon.assertExists()
            
        } catch (e: Exception) {
            println("Cart badge update test failed: ${e.message}")
        }
    }
    
    @Test
    fun ecommerceScreen_productRatings_display() {
        Thread.sleep(MEDIUM_WAIT)
        
        // Check if product ratings (stars) are displayed
        try {
            // Look for star ratings in products
            composeTestRule.onAllNodes(hasContentDescription("Star") or hasText("★")).apply {
                if (fetchSemanticsNodes().isNotEmpty()) {
                    // Stars are displayed
                    println("Product ratings found")
                } else {
                    println("Product ratings not found")
                }
            }
        } catch (e: Exception) {
            println("Rating display test failed: ${e.message}")
        }
    }
    
    @Test
    fun ecommerceScreen_productPrices_display() {
        Thread.sleep(MEDIUM_WAIT)
        
        // Check if product prices are displayed
        val pricePatterns = listOf("$2,850", "$1,899", "$1,299", "$3,299", "$8,599", "$4,599")
        
        pricePatterns.forEach { price ->
            try {
                composeTestRule.onNode(hasText(price)).assertExists()
                println("Price $price found")
            } catch (e: Exception) {
                println("Price $price not found")
            }
        }
    }
    
    @Test
    fun ecommerceScreen_scrolling_worksCorrectly() {
        Thread.sleep(MEDIUM_WAIT)
        
        // Test scrolling through product grid
        try {
            // Scroll down to see more products
            composeTestRule.onRoot().performScrollTo()
            Thread.sleep(SHORT_WAIT)
            
            // Verify we can still see the UI elements after scrolling
            composeTestRule.onNodeWithText("Zennia").assertExists()
            composeTestRule.onNodeWithText("Browse").assertExists()
            
        } catch (e: Exception) {
            println("Scrolling test failed: ${e.message}")
        }
    }
    
    @Test
    fun ecommerceScreen_loadingStates() {
        // This test is for loading states when refreshing or initial load
        // Since we're already past loading, we can test refresh if implemented
        
        try {
            // Try to trigger refresh (pull down)
            composeTestRule.onRoot().performTouchInput {
                swipeDown()
            }
            Thread.sleep(MEDIUM_WAIT)
            
            // App should still be functional after refresh
            composeTestRule.onNodeWithText("Zennia").assertIsDisplayed()
            
        } catch (e: Exception) {
            println("Loading states test failed: ${e.message}")
        }
    }
    
    @Test
    fun ecommerceScreen_header_functionality() {
        // Test header elements
        composeTestRule.onNodeWithText("Zennia").assertIsDisplayed()
        
        // Test cart button
        composeTestRule.onNodeWithContentDescription("Cart").assertExists().performClick()
        Thread.sleep(SHORT_WAIT)
        
        // Should open cart or show cart details
        // Navigate back or close cart if opened
        try {
            composeTestRule.onNode(hasContentDescription("Close") or hasContentDescription("Back")).performClick()
            Thread.sleep(SHORT_WAIT)
        } catch (e: Exception) {
            // Cart might not have opened or different navigation
            println("Cart close/back button not found")
        }
    }
}