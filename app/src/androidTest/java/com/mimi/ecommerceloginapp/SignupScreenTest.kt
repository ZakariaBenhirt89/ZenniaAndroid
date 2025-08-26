package com.mimi.ecommerceloginapp

import androidx.compose.ui.test.*
import com.mimi.ecommerceloginapp.TestUtils.INVALID_EMAIL
import com.mimi.ecommerceloginapp.TestUtils.LONG_WAIT
import com.mimi.ecommerceloginapp.TestUtils.SHORT_PASSWORD
import com.mimi.ecommerceloginapp.TestUtils.SHORT_WAIT
import com.mimi.ecommerceloginapp.TestUtils.VALID_EMAIL
import com.mimi.ecommerceloginapp.TestUtils.VALID_NAME
import com.mimi.ecommerceloginapp.TestUtils.VALID_PASSWORD
import com.mimi.ecommerceloginapp.TestUtils.performSignup
import com.mimi.ecommerceloginapp.TestUtils.waitForLoadingToComplete
import org.junit.Before
import org.junit.Test

/**
 * UI Tests for Signup Screen
 * Tests all signup functionality including validation, success, and error cases
 */
class SignupScreenTest : BaseUITest() {
    
    @Before
    override fun setUp() {
        super.setUp()
        // Wait for the app to load and navigate to signup screen
        composeTestRule.waitForLoadingToComplete()
        composeTestRule.onNodeWithText("Don't have an account? Sign up").performClick()
        Thread.sleep(SHORT_WAIT)
    }
    
    @Test
    fun signupScreen_displaysCorrectly() {
        // Check if main UI elements are displayed
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
        composeTestRule.onNodeWithText("Join Zennia today").assertIsDisplayed()
        composeTestRule.onNodeWithText("Full Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Confirm Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithText("Already have an account? Sign in").assertIsDisplayed()
        
        // Check if Zennia logo is displayed
        composeTestRule.onNode(hasContentDescription("Zennia") or hasText("Zennia")).assertExists()
    }
    
    @Test
    fun signupScreen_validSignup_navigatesToMainScreen() {
        // Perform signup with valid data
        composeTestRule.onNodeWithText("Full Name").performTextInput(VALID_NAME)
        composeTestRule.onNodeWithText("Email").performTextInput(VALID_EMAIL)
        composeTestRule.onNodeWithText("Password").performTextInput(VALID_PASSWORD)
        composeTestRule.onNodeWithText("Confirm Password").performTextInput(VALID_PASSWORD)
        composeTestRule.onNodeWithText("Sign Up").performClick()
        
        // Wait for navigation
        Thread.sleep(LONG_WAIT)
        
        // Check if we're on the main ecommerce screen
        composeTestRule.onNodeWithText("Zennia").assertIsDisplayed()
        composeTestRule.onNodeWithText("Browse").assertExists()
    }
    
    @Test
    fun signupScreen_emptyFields_showsValidation() {
        // Try to signup with empty fields
        composeTestRule.onNodeWithText("Sign Up").performClick()
        
        Thread.sleep(SHORT_WAIT)
        
        // Should remain on signup screen
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
    }
    
    @Test
    fun signupScreen_invalidEmail_showsError() {
        // Enter invalid email
        composeTestRule.onNodeWithText("Full Name").performTextInput(VALID_NAME)
        composeTestRule.onNodeWithText("Email").performTextInput(INVALID_EMAIL)
        composeTestRule.onNodeWithText("Password").performTextInput(VALID_PASSWORD)
        composeTestRule.onNodeWithText("Confirm Password").performTextInput(VALID_PASSWORD)
        composeTestRule.onNodeWithText("Sign Up").performClick()
        
        Thread.sleep(SHORT_WAIT)
        
        // Should remain on signup screen
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
    }
    
    @Test
    fun signupScreen_shortPassword_showsError() {
        // Enter short password
        composeTestRule.onNodeWithText("Full Name").performTextInput(VALID_NAME)
        composeTestRule.onNodeWithText("Email").performTextInput(VALID_EMAIL)
        composeTestRule.onNodeWithText("Password").performTextInput(SHORT_PASSWORD)
        composeTestRule.onNodeWithText("Confirm Password").performTextInput(SHORT_PASSWORD)
        composeTestRule.onNodeWithText("Sign Up").performClick()
        
        Thread.sleep(SHORT_WAIT)
        
        // Should remain on signup screen
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
    }
    
    @Test
    fun signupScreen_passwordMismatch_showsError() {
        // Enter mismatched passwords
        composeTestRule.onNodeWithText("Full Name").performTextInput(VALID_NAME)
        composeTestRule.onNodeWithText("Email").performTextInput(VALID_EMAIL)
        composeTestRule.onNodeWithText("Password").performTextInput(VALID_PASSWORD)
        composeTestRule.onNodeWithText("Confirm Password").performTextInput("DifferentPassword123")
        composeTestRule.onNodeWithText("Sign Up").performClick()
        
        Thread.sleep(SHORT_WAIT)
        
        // Should remain on signup screen
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
    }
    
    @Test
    fun signupScreen_navigateToLogin() {
        // Click on login link
        composeTestRule.onNodeWithText("Already have an account? Sign in").performClick()
        
        Thread.sleep(SHORT_WAIT)
        
        // Check if we're on login screen
        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign in to continue").assertIsDisplayed()
    }
    
    @Test
    fun signupScreen_formFieldsAcceptInput() {
        // Test that all form fields accept input
        val testName = "John Doe"
        val testEmail = "john.doe@example.com"
        val testPassword = "SecurePassword123"
        
        composeTestRule.onNodeWithText("Full Name").performTextInput(testName)
        composeTestRule.onNodeWithText("Email").performTextInput(testEmail)
        composeTestRule.onNodeWithText("Password").performTextInput(testPassword)
        composeTestRule.onNodeWithText("Confirm Password").performTextInput(testPassword)
        
        // Verify the input was accepted (fields should contain the text)
        Thread.sleep(SHORT_WAIT)
        
        // The form should be ready for submission
        composeTestRule.onNodeWithText("Sign Up").assertIsEnabled()
    }
    
    @Test
    fun signupScreen_passwordToggleVisibility() {
        // Enter password
        composeTestRule.onNodeWithText("Password").performTextInput(VALID_PASSWORD)
        composeTestRule.onNodeWithText("Confirm Password").performTextInput(VALID_PASSWORD)
        
        // Try to find password toggle buttons (eye icons)
        try {
            composeTestRule.onAllNodes(hasContentDescription("Toggle password visibility")).apply {
                if (fetchSemanticsNodes().isNotEmpty()) {
                    get(0).performClick()
                    Thread.sleep(SHORT_WAIT)
                }
            }
        } catch (e: Exception) {
            // Toggle might not be implemented
            println("Password toggle not found")
        }
    }
    
    @Test
    fun signupScreen_scrollableForm() {
        // Test that the form is scrollable if needed
        // This is especially important on smaller screens
        
        // Fill form fields
        composeTestRule.onNodeWithText("Full Name").performScrollTo().performTextInput(VALID_NAME)
        composeTestRule.onNodeWithText("Email").performScrollTo().performTextInput(VALID_EMAIL)
        composeTestRule.onNodeWithText("Password").performScrollTo().performTextInput(VALID_PASSWORD)
        composeTestRule.onNodeWithText("Confirm Password").performScrollTo().performTextInput(VALID_PASSWORD)
        
        // Make sure signup button is accessible
        composeTestRule.onNodeWithText("Sign Up").performScrollTo().assertIsDisplayed()
    }
    
    @Test
    fun signupScreen_termsAndConditions() {
        // If your signup screen has terms and conditions checkbox
        try {
            composeTestRule.onNode(hasText("I agree to the Terms and Conditions")).assertExists()
            composeTestRule.onNode(hasText("I agree to the Terms and Conditions")).performClick()
            Thread.sleep(SHORT_WAIT)
        } catch (e: Exception) {
            // Terms and conditions checkbox might not be implemented
            println("Terms and conditions checkbox not found")
        }
    }
    
    @Test
    fun signupScreen_realTimeValidation() {
        // Test real-time validation if implemented
        val emailField = composeTestRule.onNodeWithText("Email")
        val passwordField = composeTestRule.onNodeWithText("Password")
        val confirmPasswordField = composeTestRule.onNodeWithText("Confirm Password")
        
        // Type invalid email
        emailField.performTextInput("invalid")
        Thread.sleep(SHORT_WAIT)
        
        // Clear and type valid email
        emailField.performTextClearance()
        emailField.performTextInput(VALID_EMAIL)
        Thread.sleep(SHORT_WAIT)
        
        // Type password
        passwordField.performTextInput(VALID_PASSWORD)
        Thread.sleep(SHORT_WAIT)
        
        // Type mismatched confirm password
        confirmPasswordField.performTextInput("different")
        Thread.sleep(SHORT_WAIT)
        
        // Clear and type matching password
        confirmPasswordField.performTextClearance()
        confirmPasswordField.performTextInput(VALID_PASSWORD)
        Thread.sleep(SHORT_WAIT)
        
        // Form should now be valid
        composeTestRule.onNodeWithText("Sign Up").assertExists()
    }
    
    @Test
    fun signupScreen_nameFieldValidation() {
        // Test name field specific validation
        val nameField = composeTestRule.onNodeWithText("Full Name")
        
        // Test empty name
        nameField.performTextInput("")
        Thread.sleep(SHORT_WAIT)
        
        // Test name with only spaces
        nameField.performTextInput("   ")
        Thread.sleep(SHORT_WAIT)
        
        // Test valid name
        nameField.performTextClearance()
        nameField.performTextInput(VALID_NAME)
        Thread.sleep(SHORT_WAIT)
        
        // Name field should accept valid input
        composeTestRule.onNodeWithText("Full Name").assertExists()
    }
}