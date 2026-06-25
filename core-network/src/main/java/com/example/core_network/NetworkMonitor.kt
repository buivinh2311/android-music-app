package com.example.core_network

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isConnect: Flow<Boolean>
}