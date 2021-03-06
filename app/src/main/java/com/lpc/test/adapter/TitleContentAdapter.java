package com.lpc.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lpc.test.R;
import com.lpc.test.bean.TitleContentBean;

import java.util.List;

/**
 * @ Author     ：v_lipengcheng
 * @ Date       ：Created in 16:36 2019-07-29
 * @ Description：包括title和content两种样式
 */
public class TitleContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /*
     * 0 title; 1 content
     * */
    private static final int VIEW_TYPE_TITLE = 0;
    private static final int VIEW_TYPE_CONTENT = 1;

    private final LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<TitleContentBean> mList;

    public TitleContentAdapter(Context context, List<TitleContentBean> list) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
         * 当我们传进来的root不是null，并且第三个参数是false的时候，这个temp就被加入到了root中，
         * 但返回的是temp，当参三true的时候返回root;root为null的时候，第三个参数不起作用
         * */
        if (viewType == VIEW_TYPE_TITLE) {
            return new TitleViewHolder(mLayoutInflater.inflate(R.layout.item_main_title, parent, false));
        } else {
            return new ContentViewHolder(mLayoutInflater.inflate(R.layout.item_main_content, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        TitleContentBean bean = mList.get(position);
        if (bean.getViewType() == VIEW_TYPE_TITLE) {
            ((TitleViewHolder) viewHolder).tv_title.setText(bean.getName());
        } else {
            ((ContentViewHolder) viewHolder).tv_content.setText(bean.getName());
            ((ContentViewHolder) viewHolder).tv_content.setOnClickListener(bean.getOnClickListener());
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getViewType();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_content;

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_title;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
}
