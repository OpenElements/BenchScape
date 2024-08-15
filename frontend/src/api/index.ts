package com.nevmem.moneysaver.app.data

import android.util.Log
import com.nevmem.moneysaver.app.room.AppDatabase
import com.nevmem.moneysaver.app.room.entity.Feature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class SettingsManagerImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : SettingsManager {
    private val enabledFeatures = HashSet<Feature>()
    private val tag = "SETTINGS_MANAGER_IMPL"

    private val listeners = ArrayList<WeakReference<SettingsManagerListener>>()
    private var initialization: Job? = null

    init {
        Log.d(tag, "Initialization")
    }

    override fun initialize(): Job {
        if (initialization == null) {
            initialization = CoroutineScope(Dispatchers.IO).launch {
                appDatabase.featuresDao().loadAll().forEach {
                    Log.d(tag, "Feature: ${it.featureName}")
                    enabledFeatures.add(it)
                }
            }
        }
        return initialization as Job
    }

    override fun isFeatureEnabled(featureName: String): Boolean {
        return enabledFeatures.contains(Feature(featureName))
    }

    override fun enableFeature(featureName: String) {
        if (!isFeatureEnabled(featureName)) {
            val feature = Feature(featureName)
            enabledFeatures.add(feature)
            insertFeature(feature)
            notifyFeaturesChanged()
        }
    }

    override fun disableFeature(featureName: String) {
        if (isFeatureEnabled(featureName)) {
            val featureToDelete = enabledFeatures.find {
                it.featureName == featureName
            }
            enabledFeatures.remove(Feature(featureName))
            if (featureToDelete != null) {
                deleteFeature(featureToDelete)
            }
            notifyFeaturesChanged()
        }
    }

    override fun toggleFeature(featureName: String) {
        if (isFeatureEnabled(featureName)) {
            disableFeature(featureName)
        } else {
            enableFeature(featureName)
        }
    }

    override fun subscribe(listener: WeakReference<SettingsManagerListener>) {
        listeners.add(listener)
    }

    override fun unsubscribe(listener: WeakReference<SettingsManagerListener>) {
        listeners.remove(listener)
    }

    private fun notifyFeaturesChanged() {
        val iterator = listeners.iterator()
        while (iterator.hasNext()) {
            val ref = iterator.next().get()
            if (ref != null) {
                ref.onFeaturesUpdated()
            } else {
                iterator.remove()
            }
        }
    }

    private fun insertFeature(feature: Feature) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.featuresDao().insert(feature)
        }
    }

    private fun deleteFeature(feature: Feature) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.featuresDao().delete(feature)
        }
    }
}
