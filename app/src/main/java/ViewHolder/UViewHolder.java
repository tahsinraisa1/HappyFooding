package ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.happyfooding.R;

import Interface.ItemClickListener;

public class UViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView uphone, uname;
    private ItemClickListener itemClickListener;

    public UViewHolder(View itemView) {
        super(itemView);

        uphone = itemView.findViewById(R.id.item_userphone1);
        uname = itemView.findViewById(R.id.item_username1);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(),false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
