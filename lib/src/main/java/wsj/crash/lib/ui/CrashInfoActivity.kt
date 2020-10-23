package wsj.crash.lib.ui

import android.content.Intent
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
        if (queryById.size > 0) {
            tvDetail.text = queryById[0]["detail"]
        } else {
//            Toast.makeText(this, "index:$intExtra", Toast.LENGTH_LONG).show()
            startActivity(Intent(this,CrashViewerActivity::class.java))
            finish()
        }
    }
}