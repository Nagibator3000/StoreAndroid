package com.kracubo.storeandroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.CustomerViewHolder> {
    private Customer[] items = new Customer[]{};
    private CustomersActivity activity;

    public CustomersAdapter(CustomersActivity activity) {
        this.activity = activity;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_item, parent, false);
        return new CustomersAdapter.CustomerViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        holder.bind(items[position]);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public void setItems(Customer[] customer) {
        items = customer;
        notifyDataSetChanged();
    }
    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        private final TextView customerName;
        private final TextView customerDate;
        private Customer customer;

        public CustomerViewHolder(View view, CustomersAdapter adapter) {
            super(view);
            view.setOnLongClickListener(v -> {
                adapter.onLongClick(customer.id);
                return true;
            });
            customerName = (TextView) view.findViewById(R.id.customer_name);
            customerDate = (TextView) view.findViewById(R.id.customer_date);
        }

        public void bind(Customer customer) {
            this.customer = customer;
            customerName.setText(customer.name);
            customerDate.setText(customer.dateBirthDay + "");

        }
    }
    private void onLongClick(long id) {
        activity.onLongClickCustomer(id);
    }

}
