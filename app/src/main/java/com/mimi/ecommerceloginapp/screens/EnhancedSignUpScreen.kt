                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Continue with Google",
                                fontSize = 16.sp
                            )
                        }
                    }

                    // Apple Button
                    OutlinedButton(
                        onClick = { 
                            Toast.makeText(context, "Apple sign-up coming soon!", Toast.LENGTH_SHORT).show()
                            // For demo purposes, redirect to success
                            onSignupSuccess()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp, 
                            Color.White.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Apple Icon (using phone as placeholder)
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Apple",
                                modifier = Modifier.size(20.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Continue with Apple",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            // Enhanced Login Link
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(800, delayMillis = 2700))
            ) {
                Row(
                    modifier = Modifier.padding(top = 24.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Already have an account? ",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    TextButton(
                        onClick = onSwitchToLogin
                    ) {
                        Text(
                            text = "Sign in to Zennia",
                            color = ZenniaColors.accent,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Enhanced Footer
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = tween(800, delayMillis = 2900)),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "By creating an account, you agree to our Terms of Service and Privacy Policy",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
