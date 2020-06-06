package wsj.crash.lib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_crash_viewer.*
import wsj.crash.lib.adapter.LogAdapter
import wsj.crash.lib.db.DbManager

class CrashViewerActivity : AppCompatActivity() {
    lateinit var adapter: LogAdapter
    private val mData = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash_viewer)

        initData()
        initView()
    }

    private fun initView() {
        adapter = LogAdapter(mData)
        rvLog.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvLog.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvLog.adapter = adapter
    }

    private fun initData() {
        val query = DbManager.getInstance(this).query(null, null)
        for (hashMap in query) {
            mData.add(hashMap["info"]!!.substring(0, 160))
        }
    }
}