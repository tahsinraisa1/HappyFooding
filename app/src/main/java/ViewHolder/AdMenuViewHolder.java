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

public class AdMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtmenuname;
    public ImageView edit, dlt;


    private ItemClickListener itemClickListener;

    public AdMenuViewHolder(View itemView) {
        super(itemView);

        txtmenuname = itemView.findViewById(R.id.txt);
        edit = itemView.findViewById(R.id.editicon);
        dlt = itemView.findViewById(R.id.dlticon);

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
