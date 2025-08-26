package com.mimi.ecommerceloginapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeTestRule

/**
 * Utility functions for UI testing
 */
object TestUtils {
    
    // Common test data
    const val VALID_EMAIL = "test@zennia.com"
    const val VALID_PASSWORD = "TestPassword123"
    const val VALID_NAME = "Test User"
    const val INVALID_EMAIL = "invalid-email"
    const val SHORT_PASSWORD = "123"
    
    // Wait times
    const val SHORT_WAIT = 1000L
    const val MEDIUM_WAIT = 2000L
    const val LONG_WAIT = 3000L
    const val LOADING_WAIT = 2000L
    
    /**
     * Wait for element to appear with timeout
     */
    fun ComposeTestRule.waitForElementToAppear(
        matcher: SemanticsMatcher,
        timeoutMs: Long = 5000L
    ) {
        waitUntil(timeoutMs) {
            onAllNodes(matcher).fetchSemanticsNodes().isNotEmpty()
        }
    }
    
    /**
     * Wait for element to disappear with timeout
     */
    fun ComposeTestRule.waitForElementToDisappear(
        matcher: SemanticsMatcher,
        timeoutMs: Long = 5000L
    ) {
        waitUntil(timeoutMs) {
            onAllNodes(matcher).fetchSemanticsNodes().isEmpty()
        }
    }
    
    /**
     * Wait for loading to complete
     */
    fun ComposeTestRule.waitForLoadingToComplete(timeoutMs: Long = 10000L) {
        // Wait for loading screen to disappear
        try {
            waitForElementToDisappear(hasText("Loading luxury collection..."), timeoutMs)
        } catch (e: Exception) {
            // Loading might have already completed
        }
    }
    
    /**
     * Perform login with given credentials
     */
    fun ComposeTestRule.performLogin(email: String, password: String) {
        // Wait for login screen to load
        waitForLoadingToComplete()
        
        // Fill email field
        onNodeWithText("Email").performTextInput(email)
        
        // Fill password field  
        onNodeWithText("Password").performTextInput(password)
        
        // Click login button
        onNodeWithText("Login").performClick()
    }
    
    /**
     * Perform signup with given data
     */
    fun ComposeTestRule.performSignup(name: String, email: String, password: String, confirmPassword: String) {
        // Navigate to signup
        onNodeWithText("Don't have an account? Sign up").performClick()
        
        // Fill form fields
        onNodeWithText("Full Name").performTextInput(name)
        onNodeWithText("Email").performTextInput(email)
        onNodeWithText("Password").performTextInput(password)
        onNodeWithText("Confirm Password").performTextInput(confirmPassword)
        
        // Click signup button
        onNodeWithText("Sign Up").performClick()
    }
    
    /**
     * Navigate to a specific tab in bottom navigation
     */
    fun ComposeTestRule.navigateToTab(tabName: String) {
        onNodeWithText(tabName).performClick()
        Thread.sleep(SHORT_WAIT)
    }
    
    /**
     * Scroll to find and interact with element
     */
    fun ComposeTestRule.scrollToAndClick(text: String) {
        onNodeWithText(text).performScrollTo().performClick()
    }
    
    /**
     * Check if error message is displayed
     */
    fun ComposeTestRule.assertErrorMessage(errorText: String) {
        onNodeWithText(errorText).assertIsDisplayed()
    }
    
    /**
     * Check if success message is displayed
     */
    fun ComposeTestRule.assertSuccessMessage(successText: String) {
        onNodeWithText(successText).assertIsDisplayed()
    }
    
    /**
     * Take a screenshot (requires additional setup)
     */
    fun ComposeTestRule.takeScreenshot(name: String) {
        // This would require additional screenshot library setup
        // For now, just a placeholder
        println("Screenshot taken: $name")
    }
}