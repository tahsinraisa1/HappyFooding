package ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.hp.happyfooding.R;

import java.net.URL;

import Interface.ItemClickListener;

public class AdOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

    public TextView order_id, order_phone, order_status, order_addr, order_name, getloc;
    private ItemClickListener itemClickListener;

    public AdOrderViewHolder(View itemView) {
        super(itemView);

        order_id = itemView.findViewById(R.id.order_id);
        order_phone = itemView.findViewById(R.id.order_phone);
        order_status = itemView.findViewById(R.id.order_status);
        order_addr = itemView.findViewById(R.id.order_addr);
        order_name = itemView.findViewById(R.id.order_name);
        getloc = itemView.findViewById(R.id.getloc);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(itemView, getAdapterPosition(), false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select action");
        menu.add(0,0, getAdapterPosition(), "Update");
        menu.add(0,1, getAdapterPosition(), "Delete");

    }
}
