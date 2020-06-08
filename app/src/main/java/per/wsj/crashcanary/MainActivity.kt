package per.wsj.crashcanary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import per.wsj.crashcanary.service.MyService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        startService(Intent(this, MyService::class.java))

        btn.setOnClickListener {
            var i = 0
            val j = 100 / i
        }
    }
}