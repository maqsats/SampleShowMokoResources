package com.dna.payments.kmm

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import com.dna.payments.kmm.di.initKoin
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level


class AndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(if (isDebug()) Level.ERROR else Level.NONE)
            androidContext(this@AndroidApp)
        }
        // Init App Check
        FirebaseApp.initializeApp(applicationContext)
        Firebase.analytics.setAnalyticsCollectionEnabled(true)
        startNewActivity(this)
//        logEvent(
//            "app_open", mapOf(
//                "app_open" to true
//            )
//        )
    }
}

fun startNewActivity(context: Context) {
    val intent = context.packageManager.getLaunchIntentForPackage("com.optomany.odin.dev")
    intent?.putExtra("REGISTER_DEVICE", true)
    intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent?.let {
        context.startActivity(intent)
    }
}

fun Context.isDebug() = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()

            systemUiController.setStatusBarColor(
                color = Color.White
            )
            systemUiController.setNavigationBarColor(
                color = Color.White
            )
            App()
        }
    }
}