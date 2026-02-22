package com.arslan.shizuwall.utils

import android.Manifest
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

object WhitelistFilter {
    fun getPackagesToBlock(context: Context, selectedPkgs: List<String>, showSystemApps: Boolean): List<String> {
        val pm = context.packageManager
        val packages = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS)
        val selfPkg = context.packageName
        val result = mutableListOf<String>()
        
        for (pInfo in packages) {
            val appInfo = pInfo.applicationInfo ?: continue
            val isSystem = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
            
            if (!appInfo.enabled) continue
            if (pInfo.packageName == selfPkg) continue
            if (ShizukuPackageResolver.isShizukuPackage(context, pInfo.packageName)) continue
            
            val hasInet = pInfo.requestedPermissions?.contains(Manifest.permission.INTERNET) == true
            if (!hasInet) continue
            if (!showSystemApps && isSystem) continue
            
            if (!selectedPkgs.contains(pInfo.packageName)) {
                result.add(pInfo.packageName)
            }
        }
        return result
    }
}
