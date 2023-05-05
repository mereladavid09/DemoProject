package adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.demoproject.ProductViewActivity;
import com.example.demoproject.R;
import com.example.demoproject.UserViewActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import models.Product;
import models.user.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> userList;
    private List<User> filteredUserList;

    private SearchView searchView;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
        this.filteredUserList = new ArrayList<>();

    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString();
                if (query.isEmpty()) {
                    filteredUserList = userList;
                } else {
                    List<User> filteredList = new ArrayList<>();
                    for (User user : userList) {
                        if ((user.getFirstName() + " " + user.getLastName()).toLowerCase().contains(query.toLowerCase())) {
                            filteredList.add(user);
                        }
                    }
                    filteredUserList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredUserList = (List<User>) results.values;
                userList = filteredUserList;
                notifyDataSetChanged();
            }
        };
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView userName;


        ViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userImage = itemView.findViewById(R.id.userImage);
        }
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userName.setText(user.getFirstName() +" "+user.getLastName());
        Glide.with(holder.itemView.getContext())
                .load(user.getImage())
                .into(holder.userImage);

        holder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserViewActivity.class);
                intent.putExtra("USER_KEY",user);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

}
