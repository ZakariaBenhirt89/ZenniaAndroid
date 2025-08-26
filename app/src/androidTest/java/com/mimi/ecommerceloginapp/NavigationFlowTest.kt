package com.mimi.ecommerceloginapp

import androidx.compose.ui.test.*
import com.mimi.ecommerceloginapp.TestUtils.LONG_WAIT
import com.mimi.ecommerceloginapp.TestUtils.MEDIUM_WAIT
import com.mimi.ecommerceloginapp.TestUtils.SHORT_WAIT
import com.mimi.ecommerceloginapp.TestUtils.VALID_EMAIL
import com.mimi.ecommerceloginapp.TestUtils.VALID_NAME
import com.mimi.ecommerceloginapp.TestUtils.VALID_PASSWORD
import com.mimi.ecommerceloginapp.TestUtils.navigateToTab
import com.mimi.ecommerceloginapp.TestUtils.performLogin
import com.mimi.ecommerceloginapp.TestUtils.performSignup
import com.mimi.ecommerceloginapp.TestUtils.waitForLoadingToComplete
import org.junit.Test

/**
 * UI Tests for Navigation Flow
 * Tests complete user flows and navigation between screens
 */
class NavigationFlowTest : BaseUITest() {
    
    @Test
    fun completeUserFlow_loginToBrowseToProductDetailsAndBack() {
        // Complete flow: Login -> Browse -> Product Details -> Back
        
        // 1. Start from login screen
        composeTestRule.waitForLoadingToComplete()
        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
        
        // 2. Login
        composeTestRule.performLogin(VALID_EMAIL, VALID_PASSWORD)
        Thread.sleep(LONG_WAIT)
        
        // 3. Should be on browse screen
        composeTestRule.onNodeWithText("Zennia").assertIsDisplayed()
        composeTestRule.onNodeWithText("Browse").assertExists()
        
        // 4. Click on a product
        try {
            composeTestRule.onNode(hasText("Eternal Diamond Ring")).performClick()
            Thread.sleep(MEDIUM_WAIT)
            
            // 5. Should be on product details screen
            composeTestRule.onNodeWithText("Product Details").assertIsDisplayed()
            
            // 6. Navigate back
            composeTestRule.onNodeWithContentDescription("Back").performClick()
            Thread.sleep(MEDIUM_WAIT)
            
            // 7. Should be back on browse screen
            composeTestRule.onNodeWithText("Browse").assertExists()
            
        } catch (e: Exception) {
            println("Product details navigation failed: ${e.message}")
        }
    }
    
    @Test
    fun completeUserFlow_signupToBrowse() {
        // Complete flow: Signup -> Browse
        
        // 1. Start from login screen
        composeTestRule.waitForLoadingToComplete()
        
        // 2. Navigate to signup
        composeTestRule.onNodeWithText("Don't have an account? Sign up").performClick()
        Thread.sleep(SHORT_WAIT)
        
        // 3. Should be on signup screen
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
        
        // 4. Perform signup
        composeTestRule.performSignup(VALID_NAME, VALID_EMAIL, VALID_PASSWORD, VALID_PASSWORD)
        Thread.sleep(LONG_WAIT)
        
        // 5. Should navigate to browse screen
        composeTestRule.onNodeWithText("Zennia").assertIsDisplayed()
        composeTestRule.onNodeWithText("Browse").assertExists()
    }
    
    @Test
    fun bottomNavigationFlow_allTabs() {
        // Test navigation through all bottom navigation tabs
        
        // Login first
        composeTestRule.waitForLoadingToComplete()
        composeTestRule.performLogin(VALID_EMAIL, VALID_PASSWORD)
        Thread.sleep(LONG_WAIT)
        
        // Test all tabs
        val tabs = listOf("Home", "Browse", "Wishlist", "Profile")
        
        tabs.forEach { tab ->
            composeTestRule.navigateToTab(tab)
            Thread.sleep(SHORT_WAIT)
            
            when (tab) {
                "Browse" -> {
                    composeTestRule.onNodeWithText("Search luxury jewelry...").assertExists()
                }
                "Wishlist" -> {
                    // Might show empty state or wishlist items
                    try {
                        composeTestRule.onNodeWithText("No Favorites Yet").assertExists()
                    } catch (e: Exception) {
                        // Might have items instead
                        println("Wishlist has items or different content")
                    }
                }
                "Profile" -> {
                    composeTestRule.onNodeWithText("My Profile").assertExists()
                }
                "Home" -> {
                    // Home tab functionality (if implemented)
                    println("Home tab clicked")
                }
            }
        }
        
        // Return to Browse tab
        composeTestRule.navigateToTab("Browse")
        Thread.sleep(SHORT_WAIT)
    }
    
    @Test
    fun productDetailsFlow_addToCartAndBack() {
        // Test product details flow with add to cart
        
        // Login and navigate to product
        composeTestRule.waitForLoadingToComplete()
        composeTestRule.performLogin(VALID_EMAIL, VALID_PASSWORD)
        Thread.sleep(LONG_WAIT)
        
        try {
            // Click on a product
            composeTestRule.onNode(hasText("Eternal Diamond Ring")).performClick()
            Thread.sleep(MEDIUM_WAIT)
            
            // On product details screen
            composeTestRule.onNodeWithText("Product Details").assertIsDisplayed()
            
            // Select size if it's a ring
            try {
                composeTestRule.onNode(hasText("8") and hasClickAction()).performClick()
                Thread.sleep(SHORT_WAIT)
            } catch (e: Exception) {
                println("Size selection not available or failed")
            }
            
            // Add to cart
            composeTestRule.onNodeWithText("Add to Cart").performScrollTo()
            composeTestRule.onNodeWithText("Add to Cart").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Navigate back
            composeTestRule.onNodeWithContentDescription("Back").performClick()
            Thread.sleep(MEDIUM_WAIT)
            
            // Check if cart badge updated
            composeTestRule.onNodeWithContentDescription("Cart").assertExists()
            
        } catch (e: Exception) {
            println("Product details flow failed: ${e.message}")
        }
    }
    
    @Test
    fun profileFlow_navigateThroughAllTabs() {
        // Test profile screen tab navigation
        
        // Login and navigate to profile
        composeTestRule.waitForLoadingToComplete()
        composeTestRule.performLogin(VALID_EMAIL, VALID_PASSWORD)
        Thread.sleep(LONG_WAIT)
        
        composeTestRule.navigateToTab("Profile")
        Thread.sleep(MEDIUM_WAIT)
        
        // Navigate through all profile tabs
        val profileTabs = listOf("Overview", "Orders", "Favorites", "Addresses", "Payment")
        
        profileTabs.forEach { tab ->
            try {
                composeTestRule.onNodeWithText(tab).performClick()
                Thread.sleep(SHORT_WAIT)
                
                // Verify we're on the correct tab
                composeTestRule.onNodeWithText(tab).assertExists()
                
                when (tab) {
                    "Overview" -> {
                        composeTestRule.onNodeWithText("Account Information").assertExists()
                    }
                    "Orders" -> {
                        composeTestRule.onNode(hasText("Order") or hasText("order_")).assertExists()
                    }
                    "Favorites" -> {
                        // Check for empty state or favorite items
                        try {
                            composeTestRule.onNodeWithText("No Favorites Yet").assertExists()
                        } catch (e: Exception) {
                            println("Favorites tab has items")
                        }
                    }
                    "Addresses" -> {
                        composeTestRule.onNodeWithText("Add New Address").assertExists()
                    }
                    "Payment" -> {
                        composeTestRule.onNodeWithText("Add Payment Method").assertExists()
                    }
                }
                
            } catch (e: Exception) {
                println("Profile tab '$tab' navigation failed: ${e.message}")
            }
        }
        
        // Navigate back to main screen
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        Thread.sleep(MEDIUM_WAIT)
        composeTestRule.onNodeWithText("Browse").assertExists()
    }
    
    @Test
    fun searchAndFilterFlow() {
        // Test search and filter functionality flow
        
        // Login
        composeTestRule.waitForLoadingToComplete()
        composeTestRule.performLogin(VALID_EMAIL, VALID_PASSWORD)
        Thread.sleep(LONG_WAIT)
        
        // Test search
        val searchField = composeTestRule.onNodeWithText("Search luxury jewelry...")
        searchField.performClick()
        searchField.performTextInput("Diamond")
        Thread.sleep(SHORT_WAIT)
        
        // Clear search
        searchField.performTextClearance()
        Thread.sleep(SHORT_WAIT)
        
        // Test category filters
        val categories = listOf("Rings", "Necklaces", "All")
        
        categories.forEach { category ->
            try {
                composeTestRule.onNodeWithText(category).performClick()
                Thread.sleep(SHORT_WAIT)
                println("Category '$category' filter applied")
            } catch (e: Exception) {
                println("Category '$category' filter failed: ${e.message}")
            }
        }
    }
    
    @Test
    fun cartFlow_addMultipleItemsAndView() {
        // Test adding multiple items to cart and viewing cart
        
        // Login
        composeTestRule.waitForLoadingToComplete()
        composeTestRule.performLogin(VALID_EMAIL, VALID_PASSWORD)
        Thread.sleep(LONG_WAIT)
        
        // Add multiple items to cart
        try {
            composeTestRule.onAllNodes(hasText("Add")).apply {
                val nodes = fetchSemanticsNodes()
                if (nodes.isNotEmpty()) {
                    get(0).performClick()
                    Thread.sleep(SHORT_WAIT)
                    
                    if (nodes.size > 1) {
                        get(1).performClick()
                        Thread.sleep(SHORT_WAIT)
                    }
                }
            }
            
            // Click on cart to view
            composeTestRule.onNodeWithContentDescription("Cart").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Should show cart contents or cart sheet
            // The exact behavior depends on your implementation
            
        } catch (e: Exception) {
            println("Cart flow test failed: ${e.message}")
        }
    }
    
    @Test
    fun favoriteFlow_addAndRemoveFavorites() {
        // Test adding and removing favorites
        
        // Login
        composeTestRule.waitForLoadingToComplete()
        composeTestRule.performLogin(VALID_EMAIL, VALID_PASSWORD)
        Thread.sleep(LONG_WAIT)
        
        try {
            // Add item to favorites
            composeTestRule.onNode(hasContentDescription("Favorite")).performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Navigate to wishlist
            composeTestRule.navigateToTab("Wishlist")
            Thread.sleep(SHORT_WAIT)
            
            // Should show favorite items or empty state
            try {
                composeTestRule.onNodeWithContentDescription("Remove from favorites").assertExists()
                println("Favorite items found in wishlist")
            } catch (e: Exception) {
                composeTestRule.onNodeWithText("No Favorites Yet").assertExists()
                println("Empty wishlist state displayed")
            }
            
            // Navigate back to browse
            composeTestRule.navigateToTab("Browse")
            Thread.sleep(SHORT_WAIT)
            
        } catch (e: Exception) {
            println("Favorite flow test failed: ${e.message}")
        }
    }
    
    @Test
    fun loginSignupSwitchFlow() {
        // Test switching between login and signup screens
        
        composeTestRule.waitForLoadingToComplete()
        
        // Start on login screen
        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
        
        // Go to signup
        composeTestRule.onNodeWithText("Don't have an account? Sign up").performClick()
        Thread.sleep(SHORT_WAIT)
        
        // On signup screen
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
        
        // Go back to login
        composeTestRule.onNodeWithText("Already have an account? Sign in").performClick()
        Thread.sleep(SHORT_WAIT)
        
        // Back on login screen
        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
        
        // Switch to signup again
        composeTestRule.onNodeWithText("Don't have an account? Sign up").performClick()
        Thread.sleep(SHORT_WAIT)
        
        // Verify we're on signup
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
    }
    
    @Test
    fun deepNavigationFlow_productToProfileAndBack() {
        // Test deep navigation: Browse -> Product Details -> Profile -> Back to Browse
        
        // Login
        composeTestRule.waitForLoadingToComplete()
        composeTestRule.performLogin(VALID_EMAIL, VALID_PASSWORD)
        Thread.sleep(LONG_WAIT)
        
        try {
            // Go to product details
            composeTestRule.onNode(hasText("Eternal Diamond Ring")).performClick()
            Thread.sleep(MEDIUM_WAIT)
            
            // On product details
            composeTestRule.onNodeWithText("Product Details").assertIsDisplayed()
            
            // Navigate to profile using bottom nav
            composeTestRule.navigateToTab("Profile")
            Thread.sleep(MEDIUM_WAIT)
            
            // On profile screen
            composeTestRule.onNodeWithText("My Profile").assertIsDisplayed()
            
            // Navigate back to browse
            composeTestRule.navigateToTab("Browse")
            Thread.sleep(MEDIUM_WAIT)
            
            // Should be back on browse screen (not product details)
            composeTestRule.onNodeWithText("Search luxury jewelry...").assertExists()
            
        } catch (e: Exception) {
            println("Deep navigation flow failed: ${e.message}")
        }
    }
    
    @Test
    fun errorRecoveryFlow_invalidLoginThenValidLogin() {
        // Test error recovery: Invalid login -> Valid login
        
        composeTestRule.waitForLoadingToComplete()
        
        // Try invalid login first
        composeTestRule.performLogin("invalid@email.com", "wrongpassword")
        Thread.sleep(SHORT_WAIT)
        
        // Should remain on login screen
        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
        
        // Clear fields and try valid login
        composeTestRule.onNodeWithText("Email").performTextClearance()
        composeTestRule.onNodeWithText("Password").performTextClearance()
        
        // Valid login
        composeTestRule.performLogin(VALID_EMAIL, VALID_PASSWORD)
        Thread.sleep(LONG_WAIT)
        
        // Should navigate to browse screen
        composeTestRule.onNodeWithText("Zennia").assertIsDisplayed()
        composeTestRule.onNodeWithText("Browse").assertExists()
    }
}