package party.nullpointer.socketsotimeouttest

import android.app.Activity
import android.os.Bundle
import android.util.Log
import java.net.SocketTimeoutException
import javax.net.ssl.SSLSocketFactory

private const val LOG_TAG = "SocketSoTimeoutTest"

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread {
            var handshakeDone: Long = 0
            try {
                val socketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
                Log.d(LOG_TAG, "socketFactory.createSocket()")
                val socket = socketFactory.createSocket("echo.websocket.org", 443)
                socket.soTimeout = 3000
                Log.d(LOG_TAG, "socketFactory.getInputStream()")
                val stream = socket.getInputStream()
                socket.soTimeout = 30000
                handshakeDone = System.currentTimeMillis()
                Log.d(LOG_TAG, "stream.read()")

                while (stream.read() != -1) {

                }
                Log.d(LOG_TAG, "stream.read() == -1")
            } catch (e: SocketTimeoutException) {
                Log.d(LOG_TAG, "Timeout in ${System.currentTimeMillis() - handshakeDone}ms", e)
            } catch (e: Exception) {
                Log.e(LOG_TAG, "No senior", e)
            }
        }.start()
    }

}
