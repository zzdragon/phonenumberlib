package com.sahooz.library;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class VH extends RecyclerView.ViewHolder {

    public TextView tvName;
    public TextView tvCode;
    public ImageView ivFlag;

    public VH(View itemView) {
        super(itemView);
        ivFlag = (ImageView) itemView.findViewById(R.id.iv_flag);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvCode = (TextView) itemView.findViewById(R.id.tv_code);
    }
}
