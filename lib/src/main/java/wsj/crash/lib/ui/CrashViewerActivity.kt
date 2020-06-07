package wsj.crash.lib.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_crash_viewer.*
import wsj.crash.lib.R
import wsj.crash.lib.adapter.LogAdapter
import wsj.crash.lib.bean.LogBean
import wsj.crash.lib.db.DbManager

class CrashViewerActivity : AppCompatActivity() {
    lateinit var adapter: LogAdapter
    private val mData = ArrayList<LogBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash_viewer)

        initData()
        initView()
    }

    private fun initView() {
        adapter = LogAdapter(this, mData)
        rvLog.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvLog.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvLog.adapter = adapter
    }

    private fun initData() {
        val query = DbManager.getInstance(this).query(null, null)
        for (hashMap in query) {
            val item = LogBean()
            item.id = hashMap["id"]!!.toInt()
            item.info = hashMap["info"]!!.substring(0, 160)
            item.time = hashMap["time"]!!.toLong()
            mData.add(item)
        }
    }
}