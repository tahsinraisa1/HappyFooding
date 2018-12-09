package ViewHolder;

import android.content.ContentProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.happyfooding.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import Interface.ItemClickListener;

public class AdCatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtmenuname, id;


    private ItemClickListener itemClickListener;

    public AdCatViewHolder(View itemView) {
        super(itemView);

        txtmenuname = itemView.findViewById(R.id.txt222);
        id = itemView.findViewById(R.id.txt111);

        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(),false);

    }
}
