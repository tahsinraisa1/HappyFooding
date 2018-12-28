package ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.happyfooding.R;

import Interface.ItemClickListener;

public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
    public TextView name;
    public TextView phone;
    public TextView message;
    public TextView date;

    private ItemClickListener itemClickListener;
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ChatViewHolder(View view) {
        super(view);
        date = view.findViewById(R.id.date);
        name = view.findViewById(R.id.item_username);
        phone = view.findViewById(R.id.item_userphone);
        message = view.findViewById(R.id.item_message);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(),false);

    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0,0, getAdapterPosition(), "Delete");
    }
}
