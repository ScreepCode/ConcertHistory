import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.ComponentActivity

class AppContextHolder private constructor() {

    private lateinit var appContext: Context
    private lateinit var currentActivity: ComponentActivity

    fun getApplicationContext(): Context {
        return appContext
    }

    fun getActiveActivity(): ComponentActivity {
        return currentActivity
    }

    fun setApplicationContext(context: Context) {
        appContext = context
    }

    fun setActiveActivity(activity: ComponentActivity) {
        currentActivity = activity
    }

    companion object {
        private var instance: AppContextHolder? = null

        @SuppressLint("StaticFieldLeak")
        fun getInstance(): AppContextHolder = instance ?: synchronized(this) {
            instance ?: AppContextHolder().also { instance = it }
        }
    }
}

fun ComponentActivity.initializeAppContextHolder() {
    AppContextHolder.getInstance().apply {
        setApplicationContext(applicationContext)
        setActiveActivity(this@initializeAppContextHolder)
    }
}