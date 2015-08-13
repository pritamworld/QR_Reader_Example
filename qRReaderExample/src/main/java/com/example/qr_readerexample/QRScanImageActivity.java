package com.example.qr_readerexample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.HybridBinarizer;

import java.nio.ByteBuffer;


public class QRScanImageActivity extends Activity {

    TextView tvcontent ;
    ImageView imgQr;
    private int intArray[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan_image);

        tvcontent =(TextView)findViewById(R.id.tvqrimage);
        imgQr=(ImageView)findViewById(R.id.imgQr);

        BitmapDrawable drawable = (BitmapDrawable) imgQr.getDrawable();
        //Bitmap bMap = Bitmap.createBitmap(imgQr.getWidth(), imgQr.getHeight(), Bitmap.Config.RGB_565);
        Bitmap bMap = drawable.getBitmap();

        //Initialize the intArray with the same size as the number of pixels on the image
        intArray = new int[bMap.getWidth()*bMap.getHeight()];
        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());


        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(),bMap.getHeight(),intArray);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Reader reader = new MultiFormatReader();
        try
        {
            Result result = reader.decode(bitmap);
            String contents = result.getText();
            byte[] rawBytes = result.getRawBytes();
            BarcodeFormat format;
            format = result.getBarcodeFormat();
            ResultPoint[] points = result.getResultPoints();

            tvcontent.setText(contents);
        }
        catch (NotFoundException e)
        {
            e.printStackTrace();
            return;
        }
        catch (ChecksumException e)
        {
            e.printStackTrace();
            return;
        }
        catch (FormatException e)
        {
            e.printStackTrace();
            return;
        }
    }


}
