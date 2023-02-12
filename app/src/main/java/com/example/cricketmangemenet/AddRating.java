package com.example.cricketmangemenet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AddRating extends AppCompatActivity implements JsonResponse {

    RatingBar ratingbar;
    String rat, status;
    TextView e1;
    SharedPreferences sh;
    float rats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_add_rating);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ratingbar = (RatingBar) findViewById(R.id.rating);
        e1 = (TextView) findViewById(R.id.ratingText);
        Button b1 = findViewById(R.id.button5);




        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) AddRating.this;
        String q = "/viewrating?lid=" + sh.getString("log_id","") + "&pid=" + sh.getString("receiver_id","");
        q = q.replace(" ", "%20");
        JR.execute(q);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) AddRating.this;
                String q = "/addrating?lid=" + sh.getString("log_id","") + "&pid=" + sh.getString("receiver_id","")+"&rating="+rats;
                q = q.replace(" ", "%20");
                JR.execute(q);

            }
        });

        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {

                e1.setText("Your Rating :\t" + rating);
                rats=rating;

            }
        });


    }

    @Override
    public void response(JSONObject jo) {
        try {
            String method = jo.getString("method");
            Log.d("pearl", method);

            if (method.equalsIgnoreCase("addrating")) {
                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "Tanks for the review", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), UserHome.class));
                }
            }
            else if (method.equalsIgnoreCase("viewrating")) {

                status = jo.getString("status");
                Log.d("pearl", status);

                if (status.equalsIgnoreCase("okey")) {

                    String revtable = jo.getString("data");


                    if (revtable.equalsIgnoreCase("1.0")) {
                        ratingbar.setRating(1);
                    } else if (revtable.equalsIgnoreCase("2.0")) {
                        ratingbar.setRating(2);
                    } else if (revtable.equalsIgnoreCase("3.0")) {
                        ratingbar.setRating(3);
                    } else if (revtable.equalsIgnoreCase("4.0")) {
                        ratingbar.setRating(4);
                    } else if (revtable.equalsIgnoreCase("5.0")) {
                        ratingbar.setRating(5);

                    }

                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}