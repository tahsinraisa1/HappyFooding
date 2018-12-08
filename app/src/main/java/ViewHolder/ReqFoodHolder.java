package ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.happyfooding.R;

import Interface.ItemClickListener;

public class ReqFoodHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView foodname, foodquant;

    private ItemClickListener itemClickListener;
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ReqFoodHolder(View itemView) {
        super(itemView);
        foodname = itemView.findViewById(R.id.fname);
        foodquant = itemView.findViewById(R.id.fquan);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(),false);

    }
}
