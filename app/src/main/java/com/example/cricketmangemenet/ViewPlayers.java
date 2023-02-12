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

public class ViewPlayers extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView lv1;

    public static String path,team_id;
    String[] name,phone,email,role,place,pic,lid,value;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_view_players);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv1=(ListView)findViewById(R.id.ltvideo);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewPlayers.this;
        String q = "/viewplayers?tid="+ViewTeams.team_id;
        q=q.replace(" ","%20");
        JR.execute(q);

    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),ViewTeams.class);
        startActivity(b);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("viewplayers")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");


                    name=new String[ja1.length()];
                    email=new String[ja1.length()];
                    phone=new String[ja1.length()];
                    role=new String[ja1.length()];
                    place=new String[ja1.length()];
                    pic=new String[ja1.length()];
                    lid=new String[ja1.length()];
                    value=new String[ja1.length()];




                    for(int i = 0;i<ja1.length();i++)
                    {



                        email[i]=ja1.getJSONObject(i).getString("email");
                        name[i]=ja1.getJSONObject(i).getString("name");
                        phone[i]=ja1.getJSONObject(i).getString("phone");
                        role[i]=ja1.getJSONObject(i).getString("team_role");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        pic[i]=ja1.getJSONObject(i).getString("photo");
                        lid[i]=ja1.getJSONObject(i).getString("login_id");

//                        value[i]="Team: "+name[i]+"\nAbout: "+about[i]+"\nCity: "+city[i]+"\nEmail: "+email[i];




                    }
				Custimage clist=new Custimage(this,name,email,phone,role,place,pic);
				 lv1.setAdapter(clist);

//                    ArrayAdapter<String> ar= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,value);
//                    lv1.setAdapter(ar);


                }

                else {
                    Toast.makeText(getApplicationContext(), "No Players found!", Toast.LENGTH_LONG).show();

                }
            }

        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        SharedPreferences.Editor e=sh.edit();
        e.putString("receiver_id",lid[i]);
        e.putString("name",name[i]);
        e.commit();

//        d_id=docid[i];

        final CharSequence[] items = {"Chat","Rating","View Player Updates", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewPlayers.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Chat")) {

                    startActivity(new Intent(getApplicationContext(), ChatHere.class));

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }else if (items[item].equals("Rating")) {

                    startActivity(new Intent(getApplicationContext(), AddRating.class));

                }else if (items[item].equals("View Player Updates")) {

                    startActivity(new Intent(getApplicationContext(), ViewPLayerUpdates.class));

                }

            }

        });
        builder.show();


    }
}