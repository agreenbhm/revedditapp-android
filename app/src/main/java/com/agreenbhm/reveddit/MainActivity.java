package com.agreenbhm.reveddit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
        } else {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "To use Reveddit share a Reddit URL with the app.", Toast.LENGTH_LONG);
            toast.show();
        }
        finish();
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            Uri uri = Uri.parse(sharedText);
            String path = uri.getPath();
            try {
                if (path.regionMatches(true, 0, "/r/", 0, 3)) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    String reveddit = "https://reveddit.com/v/" + path.substring(3);
                    i.setData(Uri.parse(reveddit));
                    //Log.d("Reveddit", reveddit);
                    startActivity(i);
                } else{
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Unsupported URL", Toast.LENGTH_SHORT);
                    toast.show();
                }
                //Log.d("Reveddit", path.substring(0, 3));
                //Log.d("Reveddit", sharedText);
            }catch (Exception e) {
                Log.d("Reveddit", e.getMessage());
            }
        }
    }
}