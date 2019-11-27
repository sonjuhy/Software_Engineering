package com.example.software_engineering;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{

    private ArrayList<Group> list;

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView name;
        protected TextView num;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.group_name);
            this.num = itemView.findViewById(R.id.group_pNum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        // 리스너 객체의 메서드 호출.
                        if(listener!=null){
                            listener.onItemClick(view,pos);
                        }
                    }
                }
            });
        }
    }

    public CustomAdapter(ArrayList<Group> list) {
        this.list = list;
    }

    public interface OnItemClickListener{
        void onItemClick(View v,int pos);
    }

    //리스너 객체 참조를 저장하는 변수
    private OnItemClickListener listener = null;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.group_item,viewGroup,false);
       CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        customViewHolder.name.setTextSize(24);
        customViewHolder.num.setTextSize(24);

        customViewHolder.name.setGravity(Gravity.CENTER_VERTICAL);
        customViewHolder.num.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);

        customViewHolder.name.setText(list.get(i).GroupName_output());
        customViewHolder.num.setText(Integer.toString(list.get(i).PeopleNumber_output()));
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }
}
