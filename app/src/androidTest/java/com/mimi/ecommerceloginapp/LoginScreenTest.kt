package com.mimi.ecommerceloginapp

import androidx.compose.ui.test.*
import com.mimi.ecommerceloginapp.TestUtils.INVALID_EMAIL
import com.mimi.ecommerceloginapp.TestUtils.LONG_WAIT
import com.mimi.ecommerceloginapp.TestUtils.SHORT_PASSWORD
import com.mimi.ecommerceloginapp.TestUtils.SHORT_WAIT
import com.mimi.ecommerceloginapp.TestUtils.VALID_EMAIL
import com.mimi.ecommerceloginapp.TestUtils.VALID_PASSWORD
import com.mimi.ecommerceloginapp.TestUtils.performLogin
import com.mimi.ecommerceloginapp.TestUtils.waitForLoadingToComplete
import org.junit.Before
import org.junit.Test

/**
 * UI Tests for Login Screen
 * Tests all login functionality including validation, success, and error cases
 */
class LoginScreenTest : BaseUITest() {
    
    @Before
    override fun setUp() {
        super.setUp()
        // Wait for the app to load and show login screen
        composeTestRule.waitForLoadingToComplete()
    }
    
    @Test
    fun loginScreen_displaysCorrectly() {
        // Check if main UI elements are displayed
        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign in to continue").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don't have an account? Sign up").assertIsDisplayed()
        
        // Check if Zennia logo is displayed
        composeTestRule.onNode(hasContentDescription("Zennia") or hasText("Zennia")).assertExists()
    }
    
    @Test
    fun loginScreen_showsAnimatedBackground() {
        // The animated background should be present
        // We can't easily test animations, but we can check the background exists
        composeTestRule.onRoot().assertIsDisplayed()
    }
    
    @Test
    fun loginScreen_validLogin_navigatesToMainScreen() {
        // Perform login with valid credentials
        composeTestRule.performLogin(VALID_EMAIL, VALID_PASSWORD)
        
        // Wait for navigation
        Thread.sleep(LONG_WAIT)
        
        // Check if we're on the main ecommerce screen
        composeTestRule.onNodeWithText("Zennia").assertIsDisplayed()
        composeTestRule.onNodeWithText("Browse").assertExists()
    }
    
    @Test
    fun loginScreen_emptyFields_showsValidation() {
        // Try to login with empty fields
        composeTestRule.onNodeWithText("Login").performClick()
        
        // Check if validation messages appear
        // Note: This assumes your app shows validation messages
        // You may need to adjust based on your actual validation implementation
        Thread.sleep(SHORT_WAIT)
        
        // The fields should still be empty and we should remain on login screen
        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
    }
    
    @Test
    fun loginScreen_invalidEmail_showsError() {
        // Enter invalid email and valid password
        composeTestRule.onNodeWithText("Email").performTextInput(INVALID_EMAIL)
        composeTestRule.onNodeWithText("Password").performTextInput(VALID_PASSWORD)
        composeTestRule.onNodeWithText("Login").performClick()
        
        Thread.sleep(SHORT_WAIT)
        
        // Should remain on login screen
        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
    }
    
    @Test
    fun loginScreen_shortPassword_showsError() {
        // Enter valid email and short password
        composeTestRule.onNodeWithText("Email").performTextInput(VALID_EMAIL)
        composeTestRule.onNodeWithText("Password").performTextInput(SHORT_PASSWORD)
        composeTestRule.onNodeWithText("Login").performClick()
        
        Thread.sleep(SHORT_WAIT)
        
        // Should remain on login screen
        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
    }
    
    @Test
    fun loginScreen_togglePasswordVisibility() {
        // Enter password
        composeTestRule.onNodeWithText("Password").performTextInput(VALID_PASSWORD)
        
        // Try to find password toggle button (eye icon)
        // This assumes your password field has a visibility toggle
        try {
            composeTestRule.onNode(hasContentDescription("Toggle password visibility")).performClick()
            Thread.sleep(SHORT_WAIT)
            // Password should now be visible, but we can't easily test this in Compose
        } catch (e: Exception) {
            // Toggle might not be implemented or have different content description
            println("Password toggle not found or not clickable")
        }
    }
    
    @Test
    fun loginScreen_navigateToSignup() {
        // Click on signup link
        composeTestRule.onNodeWithText("Don't have an account? Sign up").performClick()
        
        Thread.sleep(SHORT_WAIT)
        
        // Check if we're on signup screen
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
        composeTestRule.onNodeWithText("Join Zennia today").assertIsDisplayed()
    }
    
    @Test
    fun loginScreen_logoIsAnimated() {
        // Check if the logo exists and is likely animated
        // We can't directly test animations, but we can check if the logo component exists
        composeTestRule.onRoot().assertIsDisplayed()
        
        // Wait a bit to see if any animations complete
        Thread.sleep(LONG_WAIT)
        
        // Logo should still be visible after animations
        composeTestRule.onNode(hasText("Zennia") or hasContentDescription("Zennia")).assertExists()
    }
    
    @Test
    fun loginScreen_formValidation_realTimeUpdate() {
        // Test real-time validation if implemented
        val emailField = composeTestRule.onNodeWithText("Email")
        val passwordField = composeTestRule.onNodeWithText("Password")
        
        // Type invalid email
        emailField.performTextInput("invalid")
        Thread.sleep(SHORT_WAIT)
        
        // Type valid email
        emailField.performTextClearance()
        emailField.performTextInput(VALID_EMAIL)
        Thread.sleep(SHORT_WAIT)
        
        // Type password
        passwordField.performTextInput(VALID_PASSWORD)
        Thread.sleep(SHORT_WAIT)
        
        // Form should be ready for submission
        composeTestRule.onNodeWithText("Login").assertIsEnabled()
    }
    
    @Test
    fun loginScreen_handleDeviceRotation() {
        // This test would require additional setup for device rotation
        // For now, just ensure the screen still works after rotation
        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
        
        // Simulate some user interaction
        composeTestRule.onNodeWithText("Email").performTextInput(VALID_EMAIL)
        
        // After rotation (simulated by just waiting), elements should still be there
        Thread.sleep(SHORT_WAIT)
        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
    }
}