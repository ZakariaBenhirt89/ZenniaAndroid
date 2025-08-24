package com.mimi.ecommerceloginapp.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.mimi.ecommerceloginapp.components.ZenniaColors
import com.mimi.ecommerceloginapp.components.AnimatedBackground
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onSignUpClick: (String, String, String) -> Unit,
    onLoginClick: () -> Unit
) {
    // State variables
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var showFields by remember { mutableStateOf(false) }
    var showButton by remember { mutableStateOf(false) }
    var showFooter by remember { mutableStateOf(false) }

    // Timed animation triggers
    LaunchedEffect(Unit) {
        delay(1000)
        showFields = true
        delay(500)
        showButton = true
        delay(500)
        showFooter = true
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedBackground()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated Logo
            AnimatedZenniaLogo()

            Spacer(modifier = Modifier.height(32.dp))

            // Input Fields
            if (showFields) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ZenniaColors.primary,
                        unfocusedBorderColor = ZenniaColors.secondary,
                        cursorColor = ZenniaColors.primary
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ZenniaColors.primary,
                        unfocusedBorderColor = ZenniaColors.secondary,
                        cursorColor = ZenniaColors.primary
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ZenniaColors.primary,
                        unfocusedBorderColor = ZenniaColors.secondary,
                        cursorColor = ZenniaColors.primary
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ZenniaColors.primary,
                        unfocusedBorderColor = ZenniaColors.secondary,
                        cursorColor = ZenniaColors.primary
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sign Up Button
            if (showButton) {
                Button(
                    onClick = { onSignUpClick(name, email, password) },
                    colors = ButtonDefaults.buttonColors(containerColor = ZenniaColors.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .height(50.dp)
                ) {
                    Text("Sign Up")
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Sign Up",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer (Already have an account?)
            if (showFooter) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Already have an account?")
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = onLoginClick) {
                        Text(
                            "Login",
                            color = ZenniaColors.primary
                        )
                    }
                }
            }
        }
    }
}
