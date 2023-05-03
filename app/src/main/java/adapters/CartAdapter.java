package adapters;

import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoproject.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import models.Cart;
import models.Product;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> implements Filterable {

    private List<Cart> cartList;
    private List<Cart> filteredCartList;
    public CartAdapter(List<Cart> cartList) {
        this.cartList = cartList;
        this.filteredCartList = new ArrayList<>();
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cartCount;
        ImageView cartImage;

        ViewHolder(View itemView) {
            super(itemView);
            cartCount = itemView.findViewById(R.id.productCount);
            cartImage = itemView.findViewById(R.id.cartImage);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString();
                if (query.isEmpty()) {
                    filteredCartList = cartList;
                } else {
                    List<Cart> filteredList = new ArrayList<>();
                    for (Cart cart : cartList) {
                        if (cart.getTotal()> Integer.parseInt(query.toLowerCase())) {
                            filteredList.add(cart);
                        }
                    }
                    filteredCartList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredCartList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredCartList = (List<Cart>) results.values;
                cartList = filteredCartList;
                notifyDataSetChanged();
            }
        };
    }
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.cartCount.setText(cart.getTotal() + "$");

        holder.cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Cart " + cart.getId() +" selected from menu", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
