package np.com.lashman.learnit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Application class annotated with @HiltAndroidApp to trigger Hilt's code generation for dependency injection.
@HiltAndroidApp
class LearnITApp: Application() {
    // This class serves as the entry point for the application.
    // By annotating it with @HiltAndroidApp, Hilt initializes the necessary components for dependency injection at the application level.
}
