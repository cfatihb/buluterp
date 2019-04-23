package com.mobil.bulut.Adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobil.bulut.LoginActivity;
import com.mobil.bulut.Models.Customers;
import com.mobil.bulut.R;

import java.util.List;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.MyViewHolder> {

    private Context context;
    private List<Customers> customers;

    LoginActivity loginActivity = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView firmaTxt;
        public TextView usernameTxt;
        public View lineView;
        public RelativeLayout deleteCustomerBtn;

        public MyViewHolder(View view) {
            super(view);

            firmaTxt = (TextView) view.findViewById(R.id.firmaTxt);
            usernameTxt = (TextView) view.findViewById(R.id.usernameTxt);

            deleteCustomerBtn = (RelativeLayout) view.findViewById(R.id.deleteCustomerBtn);

            lineView = (View) view.findViewById(R.id.lineView);

        }
    }


    public CustomersAdapter(Context context, List<Customers> customers, LoginActivity loginActivity) {
        this.context = context;
        this.customers = customers;
        this.loginActivity = loginActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customers_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (position == 0) {
            holder.lineView.setVisibility(View.INVISIBLE);
        } else {
            holder.lineView.setVisibility(View.VISIBLE);
        }

        Customers beacon = customers.get(position);

        String firma = beacon.getFirma();
        String uuid = beacon.getUuid();
        String username = beacon.getUsername();

        firma = firma.toUpperCase();
        username = username.toUpperCase();

        String sensorType = "TYPE";

        holder.firmaTxt.setText(firma);
        holder.usernameTxt.setText(username);

        holder.deleteCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginActivity.deleteCustomer(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return customers.size();
    }


}
