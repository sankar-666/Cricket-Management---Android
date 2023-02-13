package com.example.cricketmangemenet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewMatch extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView lv1;
    String [] photo;
    public static String path,match_id,matchstat;
    String[] t1name,t2name,mid,stat,date,tid,value;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_view_match);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv1=(ListView)findViewById(R.id.ltvideo);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewMatch.this;
        String q = "/viewmatches?tid="+ViewTournaments.tour_id;
        q=q.replace(" ","%20");
        JR.execute(q);

    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("viewmatches")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");



                    t1name=new String[ja1.length()];
                    t2name=new String[ja1.length()];
                    mid=new String[ja1.length()];
                    stat=new String[ja1.length()];

                    value=new String[ja1.length()];




                    for(int i = 0;i<ja1.length();i++)
                    {



                        t1name[i]=ja1.getJSONObject(i).getString("t1name");
                        t2name[i]=ja1.getJSONObject(i).getString("t2name");
                        mid[i]=ja1.getJSONObject(i).getString("matches_id");
                        stat[i]=ja1.getJSONObject(i).getString("match_status");

                        value[i]=t1name[i]+" Vs "+t2name[i];




                    }
//				Custimage clist=new Custimage(this,photo);
//				 lv1.setAdapter(clist);

                    ArrayAdapter<String> ar= new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list_view,value);
                    lv1.setAdapter(ar);


                }

                else {
                    Toast.makeText(getApplicationContext(), "No Matches", Toast.LENGTH_SHORT).show();

                }
            }

        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        match_id=mid[i];
        matchstat=stat[i];

        if(matchstat.equalsIgnoreCase("pending")) {


            final CharSequence[] items = {"Latest Batting Score", "Latest Bowling Score", "Extras", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(ViewMatch.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Latest Batting Score")) {

                        startActivity(new Intent(getApplicationContext(), ViewBattingScore.class));

                    } else if (items[item].equals("Latest Bowling Score")) {

                        startActivity(new Intent(getApplicationContext(), ViewBowlingScore.class));

                    }else if (items[item].equals("Extras")) {

                        startActivity(new Intent(getApplicationContext(), ViewExtrasScore.class));

                    }else if (items[item].equals("Cancel")) {

                        dialog.dismiss();
                    }

                }

            });
            builder.show();

        }else {

            final CharSequence[] items = {"Result", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(ViewMatch.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Result")) {

                        startActivity(new Intent(getApplicationContext(), ViewScores.class));

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }

                }

            });
            builder.show();

        }
    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),ViewTournaments.class);
        startActivity(b);
    }

}