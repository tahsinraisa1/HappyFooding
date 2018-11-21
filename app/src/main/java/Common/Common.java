package Common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import Model.Request;
import Model.User;
import remote.IGeoCoordinates;
import remote.RetroFitClient;

public class Common {
    public static User currentUser;
    public static Request currentRequest;

    public static final String baseURL = "https://maps.googleapis.com";

    public static String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return "Placed.";
        else if(status.equals("1"))
            return "On the way.";
        else
            return "Shipped.";

    }
    public static IGeoCoordinates getGeoCodeService(){
        return RetroFitClient.getClient(baseURL).create(IGeoCoordinates.class);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight){
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth/(float)bitmap.getWidth();
        float scaleY = newHeight/(float)bitmap.getHeight();
        float pivotX=0, pivotY=0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX,scaleY,pivotX,pivotY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap,0,0, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }
}
