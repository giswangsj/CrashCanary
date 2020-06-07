package wsj.crash.lib.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import wsj.crash.lib.R;
import wsj.crash.lib.bean.LogBean;
import wsj.crash.lib.db.DbManager;
import wsj.crash.lib.ui.CrashInfoActivity;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {

    private List<LogBean> mData;
    private Context mContext;

    private OnDeleteListener mOnDeleteListener;

    public LogAdapter(Context context, List<LogBean> data) {
        this.mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public LogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_crash_log_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LogAdapter.ViewHolder holder, int position) {
        final LogBean item = mData.get(position);
        holder.tvTitle.setText(item.getInfo());
        holder.tvTime.setText(ts2Str(item.getTime()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CrashInfoActivity.class);
                intent.putExtra("id", item.getId());
                mContext.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnDeleteListener != null) {
                    mOnDeleteListener.onDelete(item.getId());
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 返回当前时间的格式为 yy-MM-dd日 HH:mm:ss
     *
     * @return
     */
    public static String ts2Str(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
        return sdf.format(time);
    }

    public void setOnDeleteListener(OnDeleteListener mOnDeleteListener) {
        this.mOnDeleteListener = mOnDeleteListener;
    }

    public interface OnDeleteListener {
        void onDelete(int id);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
