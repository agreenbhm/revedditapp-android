package com.agreenbhm.reveddit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class UndditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
        finish();
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            Uri uri = Uri.parse(sharedText);
            String path = uri.getPath();
            try {
                if (path.matches("/(?:r|user)/[^/?]+/comments/\\w.*")) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    String unddit = "https://www.unddit.com" + path;
                    i.setData(Uri.parse(unddit));
                    //Log.d("Reveddit", unddit);
                    startActivity(i);
                } else {
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Unsupported URL", Toast.LENGTH_SHORT);
                    toast.show();
                }
                //Log.d("Reveddit", path);
                //Log.d("Reveddit", sharedText);
            } catch (Exception e) {
                Log.d("Reveddit", e.getMessage());
            }
        }
    }
}