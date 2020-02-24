package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String HOME_KEY = "nameHome";
    public static final String AWAY_KEY = "nameAway";
    public static final String IMAGEHOME_KEY = "homeImage";
    public static final String IMAGEAWAY_KEY = "awayImage";

    private static final String TAG = MainActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1 & 2;

    private EditText homeTeam;
    private EditText awayTeam;
    private ImageView homeAvatar;
    private ImageView awayAvatar;
    private Bitmap bmp1;
    private Bitmap bmp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO
        homeTeam = findViewById(R.id.home_team);
        awayTeam = findViewById(R.id.away_team);
        homeAvatar = findViewById(R.id.home_logo);
        awayAvatar = findViewById(R.id.away_logo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0) {
            return;
        }

        if (requestCode == 1) {
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                     bmp1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    homeAvatar.setImageBitmap(bmp1);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        if (requestCode == 2) {
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                     bmp2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    awayAvatar.setImageBitmap(bmp2);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void handleAvatarH(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    public void handleAvatarA(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    public void handleNext(View view) {
        String nameHome = homeTeam.getText().toString();
        String nameAway = awayTeam.getText().toString();

        if(nameHome.equals("") || nameAway.equals("") || (homeAvatar).equals(null)  || awayAvatar.equals(null) || bmp1 == null || bmp2 == null){
            Toast.makeText(this, "Data harus diisi", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, MatchActivity.class);

            homeAvatar.buildDrawingCache();
            awayAvatar.buildDrawingCache();
            Bitmap homeImage = homeAvatar.getDrawingCache();
            Bitmap awayImage = awayAvatar.getDrawingCache();
            Bundle extras = new Bundle();
            extras.putParcelable(IMAGEHOME_KEY, homeImage);
            extras.putParcelable(IMAGEAWAY_KEY, awayImage);
            intent.putExtras(extras);

            intent.putExtra(HOME_KEY, nameHome);
            intent.putExtra(AWAY_KEY, nameAway);


            startActivity(intent);
        }
    }
}
