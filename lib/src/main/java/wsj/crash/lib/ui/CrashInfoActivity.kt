package wsj.crash.lib.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import wsj.crash.lib.R

class CrashInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash_info)

        val intExtra = intent.getIntExtra("id", 0)
        Toast.makeText(this, "" + intExtra, Toast.LENGTH_LONG).show()
    }
}