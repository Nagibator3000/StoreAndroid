package com.kracubo.storeandroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
    private Product[] items = new Product[]{};
    private ProductsActivity activity;

    public ProductsAdapter(ProductsActivity activity) {
        this.activity = activity;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        return new ProductViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bind(items[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public void setItems(Product[] products) {
        items = products;
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        private final TextView productName;
        private final TextView productPrice;
        private Product product;

        public ProductViewHolder(View view, ProductsAdapter adapter) {
            super(view);
            view.setOnLongClickListener(v -> {
                adapter.onLongClick(product.id);
                return true;
            });
            productName = (TextView) view.findViewById(R.id.product_name);
            productPrice = (TextView) view.findViewById(R.id.product_price);
        }

        public void bind(Product product) {
            this.product = product;
            productName.setText(product.name);
            productPrice.setText(product.price + "");
        }
    }

    private void onLongClick(long id) {
        activity.onLongClickProduct(id);
    }
}
