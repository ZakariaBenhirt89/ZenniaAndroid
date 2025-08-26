# 🧪 Zennia E-commerce App - UI Testing Guide

This guide explains how to run and use the comprehensive UI automation test suite for the Zennia Android e-commerce app.

## 📋 Test Coverage

The test suite covers all major functionality:

### 🔐 Authentication Tests
- **LoginScreenTest**: Login screen UI, validation, success/error flows
- **SignupScreenTest**: Signup screen UI, form validation, account creation

### 🛍️ E-commerce Functionality Tests  
- **EcommerceScreenTest**: Product browsing, search, categories, cart operations
- **ProductDetailsScreenTest**: Product details view, quantity selection, add to cart
- **UserProfileScreenTest**: Profile management, order history, favorites, addresses

### 🧭 Navigation Tests
- **NavigationFlowTest**: Complete user flows, screen transitions, error recovery

## 🚀 Quick Start

### Prerequisites
- Android device or emulator running
- ADB installed and in PATH
- App can be built successfully

### Running Tests

#### 1. Easy Way - Use Test Runner Script
```bash
# Make script executable (first time only)
chmod +x run_tests.sh

# Run all tests
./run_tests.sh all

# Run specific test categories
./run_tests.sh login
./run_tests.sh ecommerce  
./run_tests.sh navigation
```

#### 2. Manual Way - Using Gradle
```bash
# Run all tests
./gradlew connectedAndroidTest

# Run specific test class
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.mimi.ecommerceloginapp.LoginScreenTest

# Run test suite
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.mimi.ecommerceloginapp.ZenniaTestSuite
```

## 📊 Test Categories

### 🔑 Login Screen Tests
**File**: `LoginScreenTest.kt`

- ✅ UI elements display correctly
- ✅ Valid login navigates to main screen  
- ✅ Empty fields show validation
- ✅ Invalid email shows error
- ✅ Password visibility toggle
- ✅ Navigation to signup screen
- ✅ Logo animations work
- ✅ Form validation updates

**Run**: `./run_tests.sh login`

### 📝 Signup Screen Tests  
**File**: `SignupScreenTest.kt`

- ✅ UI elements display correctly
- ✅ Valid signup creates account
- ✅ Form field validation
- ✅ Password mismatch detection
- ✅ Terms and conditions
- ✅ Navigation to login screen
- ✅ Scrollable form handling

**Run**: `./run_tests.sh signup`

### 🏪 Ecommerce Screen Tests
**File**: `EcommerceScreenTest.kt`

- ✅ Product grid displays
- ✅ Search functionality works
- ✅ Category filtering
- ✅ Add to cart operations
- ✅ Favorite products
- ✅ Bottom navigation
- ✅ Cart badge updates
- ✅ Product ratings display
- ✅ Scrolling functionality

**Run**: `./run_tests.sh ecommerce`

### 📱 Product Details Tests
**File**: `ProductDetailsScreenTest.kt`

- ✅ Product information display
- ✅ Image gallery navigation
- ✅ Quantity selector
- ✅ Size selection (rings)
- ✅ Materials display
- ✅ Expandable descriptions
- ✅ Add to cart with validation
- ✅ Favorite toggle
- ✅ Share functionality
- ✅ Back navigation

**Run**: `./run_tests.sh product`

### 👤 User Profile Tests
**File**: `UserProfileScreenTest.kt`

- ✅ Profile information display
- ✅ Statistics (orders, spent, points)
- ✅ Tab navigation (Overview, Orders, Favorites, etc.)
- ✅ Order history display
- ✅ Address management
- ✅ Payment methods
- ✅ Add new items functionality
- ✅ Logout button
- ✅ Profile editing

**Run**: `./run_tests.sh profile`

### 🧭 Navigation Flow Tests
**File**: `NavigationFlowTest.kt`

- ✅ Complete user journeys
- ✅ Login → Browse → Product → Profile flows  
- ✅ Bottom navigation between all tabs
- ✅ Deep navigation scenarios
- ✅ Cart flow with multiple items
- ✅ Favorite management flow
- ✅ Error recovery flows
- ✅ Login/signup switching

**Run**: `./run_tests.sh navigation`

## 🛠️ Test Configuration

### Dependencies Added
The following test dependencies were added to `app/build.gradle.kts`:

```kotlin
// Testing
androidTestImplementation("androidx.test.ext:junit:1.1.5")
androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
androidTestImplementation("androidx.test:runner:1.5.2")
androidTestImplementation("androidx.test:rules:1.5.0")
androidTestImplementation("androidx.test.uiautomator:uiautomator:2.3.0")
androidTestImplementation("androidx.compose.ui:ui-test-junit4")
```

### Test Runner Configuration
```kotlin
defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}
```

## 📈 Test Reports

After running tests, detailed reports are available at:
- **HTML Report**: `app/build/reports/androidTests/connected/index.html`
- **XML Results**: `app/build/outputs/androidTest-results/connected/`

## 🎯 Test Utilities

### TestUtils.kt
Common utilities for all tests:
- **Test Data**: Valid/invalid email, passwords, names
- **Wait Functions**: Element appearance/disappearance with timeouts
- **Helper Methods**: Login, signup, navigation shortcuts
- **Assertion Helpers**: Error messages, success states

### BaseUITest.kt  
Base class providing:
- Compose test rule setup
- Common setup/teardown methods
- Shared test configuration

## 🔧 Troubleshooting

### Common Issues

#### 1. No devices found
```bash
# Check connected devices
adb devices

# Start emulator if needed
emulator -avd YOUR_AVD_NAME
```

#### 2. App installation fails
```bash
# Clean and rebuild
./gradlew clean
./gradlew assembleDebug
```

#### 3. Tests fail due to timing
- Tests include appropriate wait times
- Loading screens are handled automatically
- Network delays are accounted for

#### 4. Element not found errors
- Tests use flexible element matching
- Fallback strategies for different UI states
- Graceful handling of missing elements

### Debug Mode
Run tests with debug output:
```bash
./gradlew connectedAndroidTest --debug
```

### Run Specific Test Methods
```bash
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.mimi.ecommerceloginapp.LoginScreenTest -Pandroid.testInstrumentationRunnerArguments.method=loginScreen_validLogin_navigatesToMainScreen
```

## 📱 Device Requirements

### Minimum Requirements
- **API Level**: 24 (Android 7.0)
- **Resolution**: Any (tests are responsive)
- **RAM**: 2GB+ recommended
- **Storage**: 100MB+ free space

### Recommended Setup
- **Emulator**: Pixel 4 API 30+
- **Physical Device**: Any modern Android device
- **Network**: Stable internet connection (for image loading)

## 🎉 Best Practices

### For Test Maintenance
1. **Update test data** when app content changes
2. **Add new tests** for new features
3. **Review failed tests** and update selectors if needed
4. **Run tests regularly** in CI/CD pipeline

### For Reliable Tests
1. **Use explicit waits** instead of hard sleeps when possible
2. **Handle loading states** appropriately
3. **Test both success and error scenarios**
4. **Verify visual elements and functionality**

## 📞 Support

If you encounter issues with the test suite:

1. **Check device/emulator status**
2. **Verify app builds successfully**
3. **Review test logs** in the reports directory
4. **Update test selectors** if UI has changed
5. **Add debug logging** to failing tests

---

## 🏃‍♂️ Quick Reference

```bash
# Run all tests
./run_tests.sh all

# Individual test categories  
./run_tests.sh login      # Login tests
./run_tests.sh signup     # Signup tests
./run_tests.sh ecommerce  # Main app tests
./run_tests.sh product    # Product details tests
./run_tests.sh profile    # Profile tests
./run_tests.sh navigation # Flow tests

# View results
open app/build/reports/androidTests/connected/index.html
```

**Happy Testing! 🧪✨**