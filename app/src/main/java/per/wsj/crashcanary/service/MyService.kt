package per.wsj.crashcanary.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


}