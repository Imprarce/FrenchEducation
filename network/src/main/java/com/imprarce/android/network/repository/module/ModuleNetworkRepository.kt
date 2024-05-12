package com.imprarce.android.network.repository.module

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.model.module.Module

interface ModuleNetworkRepository {
    suspend fun getModules(): ResponseNetwork<List<Module>>
}