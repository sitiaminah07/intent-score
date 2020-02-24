package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MatchActivity extends AppCompatActivity {
    public static final String HASIL_KEY = "hasilnya";
    private TextView homeTeam;
    private TextView awayTeam;
    private ImageView homeLogo;
    private ImageView awayLogo;

    int scoreH, scoreA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        //TODO
        homeTeam = findViewById(R.id.txt_home);
        awayTeam = findViewById(R.id.txt_away);
        homeLogo = findViewById(R.id.home_logo);
        awayLogo = findViewById(R.id.away_logo);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            // TODO: display value here
            Bundle extra = getIntent().getExtras();
            Bitmap bmp = extra.getParcelable("homeImage");
            Bitmap bmp2 = extra.getParcelable("awayImage");
            homeLogo.setImageBitmap(bmp);
            awayLogo.setImageBitmap(bmp2);

            homeTeam.setText(extras.getString("nameHome"));
            awayTeam.setText(extras.getString("nameAway"));
        }
    }

    public void addscoreH(int score){
        TextView scoreView = findViewById(R.id.score_home);
        scoreView.setText(String.valueOf(score));
    }

    public void addScoreHome(View view) {
        scoreH = scoreH + 1;
        addscoreH(scoreH);
    }

    public void addscoreA(int score){
        TextView scoreView = findViewById(R.id.score_away);
        scoreView.setText(String.valueOf(score));
    }

    public void addScoreAway(View view) {
        scoreA = scoreA + 1;
        addscoreA(scoreA);
    }

    public void handleCek(View view) {
        String hasil = null;

        if(scoreH == scoreA){
            hasil = "DRAW";
        }
        else if(scoreH > scoreA){
            hasil = homeTeam.getText().toString();
        }
        else if(scoreA > scoreH){
            hasil = awayTeam.getText().toString();
        }

        Intent intent = new Intent (this, ResultActivity.class);
        intent.putExtra(HASIL_KEY, hasil);
        startActivity(intent);
    }
}
