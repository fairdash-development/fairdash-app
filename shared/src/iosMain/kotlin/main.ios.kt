import com.moriatsushi.insetsx.WindowInsetsUIViewController
import platform.UIKit.UIViewController

actual fun getPlatformName(): String = "iOS"

fun MainViewController(): UIViewController {
    return WindowInsetsUIViewController {
        App()
    }
}