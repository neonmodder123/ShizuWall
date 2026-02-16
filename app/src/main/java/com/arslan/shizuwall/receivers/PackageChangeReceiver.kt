package com.arslan.shizuwall.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.arslan.shizuwall.utils.ShizukuPackageResolver

/**
 * BroadcastReceiver that listens for package added/removed events
 * and clears the ShizukuPackageResolver cache to detect new Shizuku forks.
 */
class PackageChangeReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "PackageChangeReceiver"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        val action = intent?.action
        Log.d(TAG, "onReceive action=$action")

        when (action) {
            Intent.ACTION_PACKAGE_ADDED,
            Intent.ACTION_PACKAGE_REMOVED,
            Intent.ACTION_PACKAGE_REPLACED -> {
                val packageName = intent?.data?.schemeSpecificPart
                Log.d(TAG, "Package change detected: $action, package=$packageName")
                
                // Clear the ShizukuPackageResolver cache so new Shizuku forks can be detected
                ShizukuPackageResolver.clearCache()
                Log.d(TAG, "Cleared ShizukuPackageResolver cache due to package change")
            }
        }
    }
}
