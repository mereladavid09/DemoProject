package adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.demoproject.ProductViewActivity;
import com.example.demoproject.R;
import com.google.android.material.search.SearchBar;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable {

    private List<Product> productList; //

    //private List<Product> cart;

    private List<Product> productListFiltered;



    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
        //this.cart = new ArrayList<>();


    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString();
                if (query.isEmpty()) {
                    productListFiltered = productList;
                } else {
                    List<Product> filteredList = new ArrayList<>();
                    for (Product product : productList) {
                        if (product.getTitle().toLowerCase().contains(query.toLowerCase())) {
                            filteredList.add(product);
                        }
                    }
                    productListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                productListFiltered = (List<Product>) results.values;
                productList = productListFiltered;
                notifyDataSetChanged();
            }
        };
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productPrice;
        ImageView productThumbNail;


        ViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productName);
            productThumbNail = itemView.findViewById(R.id.productImage);
            productPrice = itemView.findViewById(R.id.productPrice);


        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productNameTextView.setText(product.getTitle());
        holder.productPrice.setText(Double.toString(product.getPrice()));
        Glide.with(holder.itemView.getContext())
                .load(product.getThumbnail())
                .into(holder.productThumbNail);

        holder.productThumbNail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cart.add(product);
                Intent intent = new Intent(view.getContext(), ProductViewActivity.class);
                intent.putExtra("PRODUCT_KEY",product);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

}

