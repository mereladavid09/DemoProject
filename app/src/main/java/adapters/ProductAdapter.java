package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.demoproject.R;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList; //


    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
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
                Toast.makeText(view.getContext() , product.getTitle()+ " selected from menu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}

