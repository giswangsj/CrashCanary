package wsj.crash.lib.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_crash_info.*
import wsj.crash.lib.R
import wsj.crash.lib.db.DbManager

class CrashInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash_info)

        val intExtra = intent.getIntExtra("id", 0)
        val queryById = DbManager.getInstance(this).queryById(intExtra)
        tvDetail.text = queryById[0]["detail"]
    }
}