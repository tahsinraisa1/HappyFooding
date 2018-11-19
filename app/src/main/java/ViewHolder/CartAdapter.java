package ViewHolder;

import android.content.ContentProvider;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.hp.happyfooding.Cart;
import com.example.hp.happyfooding.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Model.Order;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView cart_name, price, cart_id;
    public ImageView cart_img;
    private OnItemClickListener mlistener;
    public ImageView mDelete;
    public interface OnItemClickListener{
        void onDeleteClick(int position);
    }

    public void setCart_name(TextView cart_name) {
        this.cart_name = cart_name;
    }
    public void setOnItemClickListener(OnItemClickListener listener){ mlistener = listener;}

    public CartViewHolder(View itemView, final OnItemClickListener listener) {
        super(itemView);
        cart_name = itemView.findViewById(R.id.cart_item_name);
        price = itemView.findViewById(R.id.cart_item_price);
        cart_img = itemView.findViewById(R.id.cart_item_count);
        mDelete = itemView.findViewById(R.id.img_delete);

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onDeleteClick(position);
                    }

                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listData;
    private Context context;
    private OnItemClickListener mlistener;
    public interface OnItemClickListener extends CartViewHolder.OnItemClickListener {
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){ mlistener = listener;}

    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(itemView, mlistener);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(), Color.RED);
        holder.cart_img.setImageDrawable(drawable);
        int pri = (Integer.parseInt(listData.get(position).getPrice())*(Integer.parseInt(listData.get(position).getQuantity())));
        holder.price.setText(Integer.toString(pri));
        holder.cart_name.setText(listData.get(position).getProductName());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
