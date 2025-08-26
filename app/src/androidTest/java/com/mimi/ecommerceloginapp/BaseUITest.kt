package com.mimi.ecommerceloginapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith

/**
 * Base class for all UI tests
 * Provides common setup and utilities
 */
@RunWith(AndroidJUnit4::class)
abstract class BaseUITest {
    
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    
    /**
     * Setup method to be called before each test
     * Override in subclasses if needed
     */
    open fun setUp() {
        // Common setup can go here
    }
    
    /**
     * Cleanup method to be called after each test
     * Override in subclasses if needed
     */
    open fun tearDown() {
        // Common cleanup can go here
    }
}