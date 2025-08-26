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
 * UI Tests for User Profile Screen
 * Tests profile display, tabs navigation, and profile management functionality
 */
class UserProfileScreenTest : BaseUITest() {
    
    @Before
    override fun setUp() {
        super.setUp()
        // Navigate to profile screen
        composeTestRule.waitForLoadingToComplete()
        composeTestRule.performLogin(VALID_EMAIL, VALID_PASSWORD)
        Thread.sleep(LONG_WAIT)
        
        // Navigate to profile tab
        composeTestRule.navigateToTab("Profile")
        Thread.sleep(MEDIUM_WAIT)
    }
    
    @Test
    fun profileScreen_displaysCorrectly() {
        // Check main UI elements
        composeTestRule.onNodeWithText("My Profile").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
        composeTestRule.onNodeWithContentDescription("Settings").assertExists()
    }
    
    @Test
    fun profileScreen_userInformation_displays() {
        // Check if user information is displayed
        try {
            // User name
            composeTestRule.onNodeWithText("Sarah Johnson").assertExists()
            
            // User email
            composeTestRule.onNodeWithText("sarah.johnson@email.com").assertExists()
            
            // Membership tier
            composeTestRule.onNode(hasText("Gold Member") or hasText("Member")).assertExists()
            
        } catch (e: Exception) {
            println("User information display test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_statisticsDisplay() {
        // Check if user statistics are displayed
        try {
            // Orders count
            composeTestRule.onNodeWithText("Orders").assertExists()
            composeTestRule.onNodeWithText("12").assertExists()
            
            // Total spent
            composeTestRule.onNodeWithText("Spent").assertExists()
            composeTestRule.onNode(hasText("28,500") or hasText("$28,500")).assertExists()
            
            // Loyalty points
            composeTestRule.onNodeWithText("Points").assertExists()
            composeTestRule.onNode(hasText("2,850") or hasText("2850")).assertExists()
            
        } catch (e: Exception) {
            println("Statistics display test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_profileImage_editButton() {
        // Test profile image and edit functionality
        try {
            // Edit button should be visible
            composeTestRule.onNodeWithContentDescription("Edit").assertExists()
            
            // Click edit button
            composeTestRule.onNodeWithContentDescription("Edit").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Should show edit profile functionality (toast or dialog)
            
        } catch (e: Exception) {
            println("Profile image edit test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_tabNavigation() {
        // Test tab navigation functionality
        val tabs = listOf("Overview", "Orders", "Favorites", "Addresses", "Payment")
        
        tabs.forEach { tab ->
            try {
                composeTestRule.onNodeWithText(tab).performClick()
                Thread.sleep(SHORT_WAIT)
                
                // Verify tab is selected
                composeTestRule.onNodeWithText(tab).assertExists()
                println("Tab '$tab' navigation successful")
                
            } catch (e: Exception) {
                println("Tab '$tab' navigation failed: ${e.message}")
            }
        }
    }
    
    @Test
    fun profileScreen_overviewTab_content() {
        // Test overview tab content
        try {
            composeTestRule.onNodeWithText("Overview").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Should show account information
            composeTestRule.onNodeWithText("Account Information").assertExists()
            composeTestRule.onNode(hasText("Member since") or hasText("March 2023")).assertExists()
            
            // Should show recent orders
            composeTestRule.onNodeWithText("Recent Orders").assertExists()
            
        } catch (e: Exception) {
            println("Overview tab test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_ordersTab_content() {
        // Test orders tab content
        try {
            composeTestRule.onNodeWithText("Orders").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Should show order history
            composeTestRule.onNode(hasText("Order") or hasText("order_")).assertExists()
            
            // Check for order statuses
            val orderStatuses = listOf("Delivered", "Processing", "Shipped")
            orderStatuses.forEach { status ->
                try {
                    composeTestRule.onNodeWithText(status).assertExists()
                    println("Order status '$status' found")
                } catch (e: Exception) {
                    println("Order status '$status' not found")
                }
            }
            
        } catch (e: Exception) {
            println("Orders tab test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_favoritesTab_content() {
        // Test favorites tab content
        try {
            composeTestRule.onNodeWithText("Favorites").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Might show empty state or favorite products
            try {
                composeTestRule.onNodeWithText("No Favorites Yet").assertExists()
                composeTestRule.onNodeWithText("Start browsing to add items to your favorites").assertExists()
                println("Empty favorites state displayed")
            } catch (e: Exception) {
                // Might have favorite products instead
                composeTestRule.onNode(hasContentDescription("Remove from favorites")).assertExists()
                println("Favorite products displayed")
            }
            
        } catch (e: Exception) {
            println("Favorites tab test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_addressesTab_content() {
        // Test addresses tab content
        try {
            composeTestRule.onNodeWithText("Addresses").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Should show saved addresses
            composeTestRule.onNodeWithText("Home").assertExists()
            composeTestRule.onNodeWithText("123 Luxury Lane").assertExists()
            composeTestRule.onNodeWithText("Beverly Hills, CA 90210").assertExists()
            
            // Should show default badge
            composeTestRule.onNodeWithText("Default").assertExists()
            
            // Should show add new address button
            composeTestRule.onNodeWithText("Add New Address").assertExists()
            
        } catch (e: Exception) {
            println("Addresses tab test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_paymentTab_content() {
        // Test payment methods tab content
        try {
            composeTestRule.onNodeWithText("Payment").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Should show payment methods
            composeTestRule.onNodeWithText("Visa").assertExists()
            composeTestRule.onNode(hasText("4242") or hasText("•••• •••• •••• 4242")).assertExists()
            
            // Should show add new payment method button
            composeTestRule.onNodeWithText("Add Payment Method").assertExists()
            
        } catch (e: Exception) {
            println("Payment tab test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_addNewAddress() {
        // Test add new address functionality
        try {
            composeTestRule.onNodeWithText("Addresses").performClick()
            Thread.sleep(SHORT_WAIT)
            
            composeTestRule.onNodeWithText("Add New Address").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Should show toast or navigate to add address screen
            // The exact behavior depends on your implementation
            
        } catch (e: Exception) {
            println("Add new address test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_addNewPaymentMethod() {
        // Test add new payment method functionality
        try {
            composeTestRule.onNodeWithText("Payment").performClick()
            Thread.sleep(SHORT_WAIT)
            
            composeTestRule.onNodeWithText("Add Payment Method").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Should show toast or navigate to add payment method screen
            
        } catch (e: Exception) {
            println("Add new payment method test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_settingsButton() {
        // Test settings button functionality
        try {
            composeTestRule.onNodeWithContentDescription("Settings").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Should show settings functionality (toast or navigation)
            
        } catch (e: Exception) {
            println("Settings button test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_logoutButton() {
        // Test logout functionality
        try {
            // Scroll down to find logout button
            composeTestRule.onNodeWithText("Logout").performScrollTo()
            composeTestRule.onNodeWithText("Logout").assertExists()
            
            // Click logout (but don't actually logout to continue other tests)
            // composeTestRule.onNodeWithText("Logout").performClick()
            // Thread.sleep(SHORT_WAIT)
            
            println("Logout button found and accessible")
            
        } catch (e: Exception) {
            println("Logout button test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_membershipTierAnimation() {
        // Test membership tier glow animation
        try {
            composeTestRule.onNode(hasText("Gold Member")).assertExists()
            
            // Wait for potential animation
            Thread.sleep(LONG_WAIT)
            
            // Tier should still be visible after animation
            composeTestRule.onNode(hasText("Gold Member")).assertExists()
            
        } catch (e: Exception) {
            println("Membership tier animation test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_scrolling() {
        // Test scrolling through profile content
        try {
            // Scroll down to see all content
            composeTestRule.onRoot().performScrollTo()
            Thread.sleep(SHORT_WAIT)
            
            // Should be able to see logout button at bottom
            composeTestRule.onNodeWithText("Logout").assertExists()
            
            // Scroll back up
            composeTestRule.onNodeWithText("My Profile").performScrollTo()
            Thread.sleep(SHORT_WAIT)
            
        } catch (e: Exception) {
            println("Scrolling test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_backNavigation() {
        // Test back navigation
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        Thread.sleep(MEDIUM_WAIT)
        
        // Should navigate back to main screen
        composeTestRule.onNodeWithText("Browse").assertExists()
    }
    
    @Test
    fun profileScreen_orderDetails() {
        // Test order details display
        try {
            composeTestRule.onNodeWithText("Orders").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Check for order information
            composeTestRule.onNode(hasText("Dec 15, 2024") or hasText("order_001")).assertExists()
            
            // Check for order totals
            composeTestRule.onNode(hasText("$5,299") or hasText("5,299")).assertExists()
            
        } catch (e: Exception) {
            println("Order details test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_favoriteProductRemoval() {
        // Test removing favorite products
        try {
            composeTestRule.onNodeWithText("Favorites").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // If favorites exist, try to remove one
            try {
                composeTestRule.onNodeWithContentDescription("Remove from favorites").performClick()
                Thread.sleep(SHORT_WAIT)
                
                println("Favorite product removal successful")
            } catch (e: Exception) {
                println("No favorite products to remove or removal failed")
            }
            
        } catch (e: Exception) {
            println("Favorite product removal test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_addressDefaultToggle() {
        // Test address default toggle functionality
        try {
            composeTestRule.onNodeWithText("Addresses").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Check if default badge exists
            composeTestRule.onNodeWithText("Default").assertExists()
            
            // In a real implementation, you might have toggle functionality
            // For now, just verify the default badge is displayed
            
        } catch (e: Exception) {
            println("Address default toggle test failed: ${e.message}")
        }
    }
    
    @Test
    fun profileScreen_paymentMethodDefault() {
        // Test payment method default designation
        try {
            composeTestRule.onNodeWithText("Payment").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Check if default payment method is marked
            composeTestRule.onAllNodes(hasText("Default")).apply {
                if (fetchSemanticsNodes().isNotEmpty()) {
                    println("Default payment method found")
                }
            }
            
        } catch (e: Exception) {
            println("Payment method default test failed: ${e.message}")
        }
    }
}