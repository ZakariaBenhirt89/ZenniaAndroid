package com.mimi.ecommerceloginapp

import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Comprehensive Test Suite for Zennia E-commerce App
 * 
 * This test suite runs all UI automation tests for the app.
 * It includes tests for:
 * - Login functionality
 * - Signup functionality  
 * - Main ecommerce screen
 * - Product details screen
 * - User profile screen
 * - Navigation flows
 * 
 * To run all tests: ./gradlew connectedAndroidTest
 * To run specific test class: ./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.mimi.ecommerceloginapp.LoginScreenTest
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
    LoginScreenTest::class,
    SignupScreenTest::class,
    EcommerceScreenTest::class,
    ProductDetailsScreenTest::class,
    UserProfileScreenTest::class,
    NavigationFlowTest::class
)
class ZenniaTestSuite