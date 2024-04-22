    package com.devcrushers.stegmate;

    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.graphics.drawable.BitmapDrawable;
    import android.net.Uri;
    import android.os.Bundle;
    import android.os.Environment;
    import android.view.View;
    import android.widget.Button;
    import android.widget.TextView;

    import androidx.appcompat.app.AppCompatActivity;

    import com.google.android.material.imageview.ShapeableImageView;

    import java.io.File;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.OutputStream;
    import java.util.Random;

    public class Encoded extends AppCompatActivity {

        ShapeableImageView encoded_image;
        Intent intent;
        private ProgressDialog save;
        Random random = new Random();
        private TextView whether_encoded;
        private int num;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_encoded);

            encoded_image = findViewById(R.id.displayEncodedImage);
            whether_encoded = findViewById(R.id.whether_encoded);


            ShapeableImageView save_image_button = findViewById(R.id.saveBtn);

            intent = getIntent();

            Bitmap uri = (Bitmap) intent.getParcelableExtra("image");

            encoded_image.setImageBitmap(uri);

            save_image_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BitmapDrawable drawable = (BitmapDrawable) encoded_image.getDrawable();
                    final Bitmap imgToSave = drawable.getBitmap();
                    Thread PerformEncoding = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            saveToInternalStorage(imgToSave);
                        }
                    });
                    save = new ProgressDialog(Encoded.this);
                    save.setMessage("Saving, Please Wait...");
                    save.setTitle("Saving Image");
                    save.setIndeterminate(false);
                    save.setCancelable(false);
                    save.show();
                    PerformEncoding.start();
                }
            });
        }

        private void saveToInternalStorage(Bitmap bitmapImage) {
            num = (random.nextInt(999999 - 100000) + 100000);
            OutputStream fOut;
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/Encoded_" + num + ".jpeg"); // the File to save ,
            try {
                fOut = new FileOutputStream(file);
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fOut); // saving the Bitmap to a file
                fOut.close(); // do not forget to close the stream
                whether_encoded.post(new Runnable() {
                    @Override
                    public void run() {
                        save.dismiss();
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }