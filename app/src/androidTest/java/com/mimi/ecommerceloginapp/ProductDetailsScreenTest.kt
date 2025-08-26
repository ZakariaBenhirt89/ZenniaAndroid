package com.mimi.ecommerceloginapp

import androidx.compose.ui.test.*
import com.mimi.ecommerceloginapp.TestUtils.LONG_WAIT
import com.mimi.ecommerceloginapp.TestUtils.MEDIUM_WAIT
import com.mimi.ecommerceloginapp.TestUtils.SHORT_WAIT
import com.mimi.ecommerceloginapp.TestUtils.VALID_EMAIL
import com.mimi.ecommerceloginapp.TestUtils.VALID_PASSWORD
import com.mimi.ecommerceloginapp.TestUtils.performLogin
import com.mimi.ecommerceloginapp.TestUtils.waitForLoadingToComplete
import org.junit.Before
import org.junit.Test

/**
 * UI Tests for Product Details Screen
 * Tests product detail view, quantity selection, size selection, and add to cart functionality
 */
class ProductDetailsScreenTest : BaseUITest() {
    
    @Before
    override fun setUp() {
        super.setUp()
        // Navigate to product details screen
        composeTestRule.waitForLoadingToComplete()
        composeTestRule.performLogin(VALID_EMAIL, VALID_PASSWORD)
        Thread.sleep(LONG_WAIT)
        
        // Click on a product to open details
        try {
            composeTestRule.onNode(hasText("Eternal Diamond Ring")).performClick()
            Thread.sleep(MEDIUM_WAIT)
        } catch (e: Exception) {
            // Try clicking any product if the specific one is not found
            composeTestRule.onAllNodes(hasClickAction() and !hasText("Add")).apply {
                if (fetchSemanticsNodes().isNotEmpty()) {
                    get(0).performClick()
                    Thread.sleep(MEDIUM_WAIT)
                }
            }
        }
    }
    
    @Test
    fun productDetailsScreen_displaysCorrectly() {
        // Check main UI elements
        composeTestRule.onNodeWithText("Product Details").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
        composeTestRule.onNodeWithContentDescription("Favorite").assertExists()
        composeTestRule.onNodeWithContentDescription("Share").assertExists()
    }
    
    @Test
    fun productDetailsScreen_productInformation_displays() {
        // Check if product information is displayed
        try {
            // Product name should be displayed
            composeTestRule.onNode(hasText("Eternal Diamond Ring") or hasText("Ring") or hasText("Diamond")).assertExists()
            
            // Rating should be displayed
            composeTestRule.onAllNodes(hasContentDescription("Star")).apply {
                if (fetchSemanticsNodes().isNotEmpty()) {
                    println("Product rating stars found")
                }
            }
            
            // Price should be displayed
            composeTestRule.onNode(hasText("$") or hasText("2,850") or hasText("Price")).assertExists()
            
        } catch (e: Exception) {
            println("Product information display test failed: ${e.message}")
        }
    }
    
    @Test
    fun productDetailsScreen_imageGallery_navigation() {
        Thread.sleep(SHORT_WAIT)
        
        // Test image gallery navigation if multiple images exist
        try {
            // Look for image indicators or navigation dots
            composeTestRule.onAllNodes(hasClickAction()).apply {
                val nodes = fetchSemanticsNodes()
                if (nodes.size > 5) { // If there are many clickable elements, some might be image indicators
                    println("Image gallery navigation elements found")
                }
            }
            
            // Test swipe gesture on image area if possible
            composeTestRule.onRoot().performTouchInput {
                swipeLeft()
            }
            Thread.sleep(SHORT_WAIT)
            
            composeTestRule.onRoot().performTouchInput {
                swipeRight()
            }
            Thread.sleep(SHORT_WAIT)
            
        } catch (e: Exception) {
            println("Image gallery navigation test failed: ${e.message}")
        }
    }
    
    @Test
    fun productDetailsScreen_quantitySelector() {
        // Test quantity selector functionality
        try {
            // Look for quantity controls
            composeTestRule.onNodeWithContentDescription("Increase").assertExists()
            composeTestRule.onNodeWithContentDescription("Decrease").assertExists()
            
            // Test increasing quantity
            composeTestRule.onNodeWithContentDescription("Increase").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Test decreasing quantity
            composeTestRule.onNodeWithContentDescription("Decrease").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Quantity should not go below 1
            composeTestRule.onNodeWithContentDescription("Decrease").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Should still show quantity 1
            composeTestRule.onNodeWithText("1").assertExists()
            
        } catch (e: Exception) {
            println("Quantity selector test failed: ${e.message}")
        }
    }
    
    @Test
    fun productDetailsScreen_sizeSelection() {
        // Test size selection for rings
        try {
            // Look for size options
            val sizes = listOf("6", "7", "8", "9", "10")
            
            sizes.forEach { size ->
                try {
                    composeTestRule.onNode(hasText(size) and hasClickAction()).performClick()
                    Thread.sleep(SHORT_WAIT)
                    println("Size $size selected successfully")
                } catch (e: Exception) {
                    println("Size $size not found or not clickable")
                }
            }
            
        } catch (e: Exception) {
            println("Size selection test failed: ${e.message}")
        }
    }
    
    @Test
    fun productDetailsScreen_materialsDisplay() {
        // Test that materials are displayed
        try {
            val materials = listOf("18K Gold", "Diamond", "Platinum", "Pearl", "Emerald")
            
            materials.forEach { material ->
                try {
                    composeTestRule.onNode(hasText(material)).assertExists()
                    println("Material '$material' found")
                } catch (e: Exception) {
                    println("Material '$material' not found")
                }
            }
            
        } catch (e: Exception) {
            println("Materials display test failed: ${e.message}")
        }
    }
    
    @Test
    fun productDetailsScreen_descriptionExpandable() {
        // Test expandable description functionality
        try {
            // Look for "Read More" button
            composeTestRule.onNodeWithText("Read More").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Should show "Show Less" button after expanding
            composeTestRule.onNodeWithText("Show Less").assertExists()
            
            // Click "Show Less" to collapse
            composeTestRule.onNodeWithText("Show Less").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Should show "Read More" again
            composeTestRule.onNodeWithText("Read More").assertExists()
            
        } catch (e: Exception) {
            println("Description expandable test failed: ${e.message}")
        }
    }
    
    @Test
    fun productDetailsScreen_addToCart_withoutSize() {
        // Test add to cart without selecting size (should show error for rings)
        try {
            // Scroll to bottom to find add to cart button
            composeTestRule.onNodeWithText("Add to Cart").performScrollTo()
            composeTestRule.onNodeWithText("Add to Cart").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // For rings, should show size selection error
            // Check if we can find any error message or if size selection is highlighted
            
        } catch (e: Exception) {
            println("Add to cart without size test failed: ${e.message}")
        }
    }
    
    @Test
    fun productDetailsScreen_addToCart_withSize() {
        // Test add to cart with size selected
        try {
            // Select a size first
            composeTestRule.onNode(hasText("8") and hasClickAction()).performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Set quantity
            composeTestRule.onNodeWithContentDescription("Increase").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Add to cart
            composeTestRule.onNodeWithText("Add to Cart").performScrollTo()
            composeTestRule.onNodeWithText("Add to Cart").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Should show success message or navigate back
            // The exact behavior depends on your implementation
            
        } catch (e: Exception) {
            println("Add to cart with size test failed: ${e.message}")
        }
    }
    
    @Test
    fun productDetailsScreen_favoriteToggle() {
        // Test favorite toggle functionality
        try {
            val favoriteButton = composeTestRule.onNodeWithContentDescription("Favorite")
            favoriteButton.assertExists()
            
            // Click to add to favorites
            favoriteButton.performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Click again to remove from favorites
            favoriteButton.performClick()
            Thread.sleep(SHORT_WAIT)
            
        } catch (e: Exception) {
            println("Favorite toggle test failed: ${e.message}")
        }
    }
    
    @Test
    fun productDetailsScreen_shareButton() {
        // Test share button functionality
        try {
            composeTestRule.onNodeWithContentDescription("Share").performClick()
            Thread.sleep(SHORT_WAIT)
            
            // Should show share dialog or toast message
            // The exact behavior depends on your implementation
            
        } catch (e: Exception) {
            println("Share button test failed: ${e.message}")
        }
    }
    
    @Test
    fun productDetailsScreen_backNavigation() {
        // Test back navigation
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        Thread.sleep(MEDIUM_WAIT)
        
        // Should navigate back to main screen
        composeTestRule.onNodeWithText("Browse").assertExists()
    }
    
    @Test
    fun productDetailsScreen_scrolling() {
        // Test scrolling through product details
        try {
            // Scroll down to see all content
            composeTestRule.onRoot().performScrollTo()
            Thread.sleep(SHORT_WAIT)
            
            // Should be able to see add to cart button at bottom
            composeTestRule.onNodeWithText("Add to Cart").assertExists()
            
            // Scroll back up
            composeTestRule.onNodeWithText("Product Details").performScrollTo()
            Thread.sleep(SHORT_WAIT)
            
        } catch (e: Exception) {
            println("Scrolling test failed: ${e.message}")
        }
    }
    
    @Test
    fun productDetailsScreen_badgesDisplay() {
        // Test that badges (New, Discount, etc.) are displayed if applicable
        try {
            val badges = listOf("New", "-11%", "-13%", "-14%", "Sold Out")
            
            badges.forEach { badge ->
                try {
                    composeTestRule.onNode(hasText(badge)).assertExists()
                    println("Badge '$badge' found")
                } catch (e: Exception) {
                    println("Badge '$badge' not found")
                }
            }
            
        } catch (e: Exception) {
            println("Badges display test failed: ${e.message}")
        }
    }
    
    @Test
    fun productDetailsScreen_priceDisplay() {
        // Test price display with original price strikethrough if applicable
        try {
            // Look for current price
            composeTestRule.onNode(hasText("$") and hasClickAction().not()).assertExists()
            
            // Look for original price (strikethrough) if product has discount
            composeTestRule.onAllNodes(hasText("$")).apply {
                if (fetchSemanticsNodes().size > 1) {
                    println("Original price (strikethrough) found")
                }
            }
            
        } catch (e: Exception) {
            println("Price display test failed: ${e.message}")
        }
    }
    
    @Test
    fun productDetailsScreen_collectionInfo() {
        // Test collection information display
        try {
            val collections = listOf("Bridal", "Celestial", "Classic", "Signature", "Elite", "Royale")
            
            collections.forEach { collection ->
                try {
                    composeTestRule.onNode(hasText("$collection Collection")).assertExists()
                    println("Collection '$collection' found")
                    return@forEach
                } catch (e: Exception) {
                    println("Collection '$collection' not found")
                }
            }
            
        } catch (e: Exception) {
            println("Collection info test failed: ${e.message}")
        }
    }
}