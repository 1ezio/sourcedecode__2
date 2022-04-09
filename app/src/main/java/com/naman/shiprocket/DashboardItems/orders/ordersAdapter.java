package com.naman.shiprocket.DashboardItems.orders;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naman.shiprocket.R;

import java.util.ArrayList;

public class ordersAdapter extends RecyclerView.Adapter<ordersAdapter.ViewHolder>{

    private ArrayList<ordersDAO> ordersDAOS  ;
    Context c;
    final boolean[] flag = {false};
    public ordersAdapter(Context c , ArrayList<ordersDAO> ordersDAOS) {
        this.ordersDAOS=ordersDAOS;
        this.c = c ;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.orderitemsrecyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ordersDAO myListData = ordersDAOS.get(position);
        //holder.textView.setText(listdata[position].getDescription());
        holder.bname.setText(ordersDAOS.get(position).getName());
        holder.bphone.setText(ordersDAOS.get(position).getPhone());
        holder.bemail.setText(ordersDAOS.get(position).getEmail());
        holder.bid.setText(ordersDAOS.get(position).getId());
        holder.btotal.setText(ordersDAOS.get(position).getTotal());
        holder.bproductName.setText(ordersDAOS.get(position).getProductName());
        int i = position;

        holder.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c,seeMoreActivity.class);
                Bundle bundle = new Bundle();
                if(!flag[0]){
                    holder.lr.setVisibility(View.VISIBLE);
                    flag[0] = true;
                        holder.seeMore.setText("See Less");
                    holder.lr.setVisibility(View.VISIBLE );

                    holder.bpaymentStatus.setText(ordersDAOS.get(position).getPaymentStatus());
                    holder.bpaymentmethod.setText(ordersDAOS.get(position).getPaymentMethod());
                    holder.bcreatedAt.setText(ordersDAOS.get(position).getCreatedAt());
                    holder.bUpdatedAt.setText(ordersDAOS.get(position).getUpdatedAt());
                    holder.bproductId.setText(ordersDAOS.get(position).getProductId());
                    holder.baddress.setText(ordersDAOS.get(position).getAddress());


                }else{
                    holder.lr.setVisibility(View.GONE);
                    flag[0] = false;
                    holder.seeMore.setText("See More");

                }

            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
            }
        });




    }

    @Override
    public int getItemCount() {
        return ordersDAOS.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView bname, bemail, bphone, bproductName,bid ,seeMore,btotal,bAddress;
        public TextView bpaymentStatus, bpaymentmethod, bcreatedAt, bUpdatedAt, bproductId , baddress;
        public LinearLayout lr ;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            this.bname = (TextView) itemView.findViewById(R.id.bname);
            this.bemail = (TextView) itemView.findViewById(R.id.bemail);
            this.bphone = (TextView) itemView.findViewById(R.id.bphone);
            this.bproductName = (TextView) itemView.findViewById(R.id.bproductName);
            this.bid = (TextView) itemView.findViewById(R.id.bid);
            this.lr = (LinearLayout) itemView.findViewById(R.id.lr2);
            this.seeMore= (TextView) itemView.findViewById(R.id.seeMoreId);
            this.btotal= (TextView) itemView.findViewById(R.id.btotal);

            this.bpaymentStatus= (TextView) itemView.findViewById(R.id.bPaymentStatus);
            this.bpaymentmethod= (TextView) itemView.findViewById(R.id.bPaymentMethod);
            this.bcreatedAt= (TextView) itemView.findViewById(R.id.bCreatedat);
            this.bUpdatedAt= (TextView) itemView.findViewById(R.id.bUpdatedat);
            this.bproductId= (TextView) itemView.findViewById(R.id.bProductId);
            this.baddress= (TextView) itemView.findViewById(R.id.bCustomerAddress);

            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}
