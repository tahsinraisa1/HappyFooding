package ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hp.happyfooding.R;

import Interface.ItemClickListener;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView order_id, order_phone, order_status, order_addr, order_name, click;
    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        order_id = itemView.findViewById(R.id.order_id);
        order_phone = itemView.findViewById(R.id.order_phone);
        order_status = itemView.findViewById(R.id.order_status);
        order_addr = itemView.findViewById(R.id.order_addr);
        order_name = itemView.findViewById(R.id.order_name);
        click = itemView.findViewById(R.id.order_items1);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(itemView, getAdapterPosition(), false);
    }
}
