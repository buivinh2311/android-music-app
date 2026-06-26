package com.example.core_network

import kotlinx.coroutines.flow.StateFlow

interface NetworkMonitor {
    val isConnect: StateFlow<Boolean>
}