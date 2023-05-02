package adapters;

import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoproject.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import models.Cart;
import models.Product;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Cart> cartList;
    public CartAdapter(List<Cart> cartList) {
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
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.cartCount.setText(cart.getProducts().size() + " items in the cart");

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
