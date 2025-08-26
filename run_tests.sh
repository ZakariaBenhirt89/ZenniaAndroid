#!/bin/bash

# Zennia E-commerce App UI Test Runner
# This script runs comprehensive UI automation tests

echo "🎯 Zennia E-commerce App - UI Test Runner"
echo "========================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if device is connected
print_status "Checking for connected devices..."
DEVICES=$(adb devices | grep -v "List of devices" | grep "device$" | wc -l)

if [ $DEVICES -eq 0 ]; then
    print_error "No Android devices or emulators found!"
    print_status "Please connect a device or start an emulator"
    exit 1
else
    print_success "$DEVICES device(s) found"
fi

# Build the app first
print_status "Building the app..."
./gradlew assembleDebug
if [ $? -ne 0 ]; then
    print_error "App build failed!"
    exit 1
fi
print_success "App built successfully"

# Install the app
print_status "Installing app on device..."
./gradlew installDebug
if [ $? -ne 0 ]; then
    print_error "App installation failed!"
    exit 1
fi
print_success "App installed successfully"

# Function to run specific test
run_test() {
    local test_name=$1
    local test_class=$2
    
    print_status "Running $test_name tests..."
    ./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=$test_class
    
    if [ $? -eq 0 ]; then
        print_success "$test_name tests PASSED"
    else
        print_error "$test_name tests FAILED"
        return 1
    fi
}

# Check command line arguments
case "$1" in
    "all")
        print_status "Running ALL tests..."
        ./gradlew connectedAndroidTest
        ;;
    "login")
        run_test "Login" "com.mimi.ecommerceloginapp.LoginScreenTest"
        ;;
    "signup")
        run_test "Signup" "com.mimi.ecommerceloginapp.SignupScreenTest"
        ;;
    "ecommerce")
        run_test "Ecommerce" "com.mimi.ecommerceloginapp.EcommerceScreenTest"
        ;;
    "product")
        run_test "Product Details" "com.mimi.ecommerceloginapp.ProductDetailsScreenTest"
        ;;
    "profile")
        run_test "User Profile" "com.mimi.ecommerceloginapp.UserProfileScreenTest"
        ;;
    "navigation")
        run_test "Navigation Flow" "com.mimi.ecommerceloginapp.NavigationFlowTest"
        ;;
    "suite")
        run_test "Test Suite" "com.mimi.ecommerceloginapp.ZenniaTestSuite"
        ;;
    *)
        echo
        print_status "Zennia E-commerce App Test Runner"
        echo
        echo "Usage: $0 [test_type]"
        echo
        echo "Available test types:"
        echo "  all        - Run all tests"
        echo "  login      - Run login screen tests"
        echo "  signup     - Run signup screen tests"  
        echo "  ecommerce  - Run main ecommerce screen tests"
        echo "  product    - Run product details screen tests"
        echo "  profile    - Run user profile screen tests"
        echo "  navigation - Run navigation flow tests"
        echo "  suite      - Run organized test suite"
        echo
        echo "Examples:"
        echo "  $0 all         # Run all tests"
        echo "  $0 login       # Run only login tests"
        echo "  $0 navigation  # Run only navigation flow tests"
        echo
        exit 0
        ;;
esac

# Test results
if [ $? -eq 0 ]; then
    print_success "✅ All tests completed successfully!"
    echo
    print_status "Test reports can be found in:"
    print_status "app/build/reports/androidTests/connected/"
else
    print_error "❌ Some tests failed!"
    echo
    print_warning "Check test reports for details:"
    print_warning "app/build/reports/androidTests/connected/"
    exit 1
fi

echo
print_status "🎉 Testing complete!"