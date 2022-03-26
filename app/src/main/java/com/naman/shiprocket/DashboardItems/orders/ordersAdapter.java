package com.naman.shiprocket.DashboardItems.orders;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
                //bundle.putString("paymentStatus",new String [ordersDAOS.get(i).getPaymentStatus());
                intent.putExtra("values", new String[]{ordersDAOS.get(i).getPaymentStatus(),

                        ordersDAOS.get(i).getPaymentMethod(),ordersDAOS.get(i).getCreatedAt(),
                        ordersDAOS.get(i).getProductId(),ordersDAOS.get(i).getUpdatedAt()
                });

                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(intent);

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

        public TextView bname, bemail, bphone, bproductName,bid ,seeMore,btotal;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            this.bname = (TextView) itemView.findViewById(R.id.bname);
            this.bemail = (TextView) itemView.findViewById(R.id.bemail);
            this.bphone = (TextView) itemView.findViewById(R.id.bphone);
            this.bproductName = (TextView) itemView.findViewById(R.id.bproductName);
            this.bid = (TextView) itemView.findViewById(R.id.bid);
            this.seeMore= (TextView) itemView.findViewById(R.id.seeMoreId);
            this.btotal= (TextView) itemView.findViewById(R.id.btotal);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}
