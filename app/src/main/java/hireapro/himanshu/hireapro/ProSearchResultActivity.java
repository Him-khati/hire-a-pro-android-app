package hireapro.himanshu.hireapro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProSearchResultActivity extends AppCompatActivity {
    int DEFAULTDISTANCE = 5;
    ProgressDialog progressDialog;
    RecyclerView searchedProList;
    private String professionalType="plumber";
    private double userLatitude=28.350595,userLongitude=77.3543528;
    private int distance=DEFAULTDISTANCE;
    RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private List<Professional> professionalList = new ArrayList<>();
    private SearchProfessionalAdapter searchProfessionalAdapter;

    String searchUrl = "http://hireapro.netii.net/api/pro/list_professional.php?type=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_search_result);
        Toolbar toolbar =  (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeComponents();
        extractParameters();
        prepareUrl(professionalType,userLatitude,userLongitude,distance);
        new ConnectServer().execute();


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(ProSearchResultActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Toast.makeText(ProSearchResultActivity.this, ""+view.getId()+"Position"+position, Toast.LENGTH_SHORT).show();
                        //Professional professional;
                       /* Intent outletDetails = new Intent(OutletListActivity.this, OutletDetails.class);
                        if (regionSpinner.getSelectedItemPosition() == 0 && salesAreaSpinner.getSelectedItemPosition() == 0)
                            outlet = outletList.get(position);
                        else
                            outlet = filteredOutletList.get(position);
                        outletDetails.putExtra("OutletObject", outlet);
                        startActivity(outletDetails);*/
                    }
                })
        );
    }

    private void extractParameters() {
    }

    private void initializeComponents() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait ....");
        recyclerView = (RecyclerView) findViewById(R.id.pro_search_recycler);
        searchProfessionalAdapter = new SearchProfessionalAdapter(professionalList);
        mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(searchProfessionalAdapter);
    }

    private void prepareUrl(String professionalType , double userLatitude ,double userLongitude , int distance ) {
        searchUrl = searchUrl + professionalType + "&user_latitude=";
        searchUrl = searchUrl + userLatitude + "&user_longitude=";
        searchUrl = searchUrl + userLongitude + "&distance=";
        searchUrl = searchUrl + distance;
    }

    public class ConnectServer extends AsyncTask< User,String,String>
    {
        boolean loginFailure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(User... users) {
            HttpURLConnection httpURLConnection = null;
            //   httpURLConnection.setConnectTimeout(CONNECTIONOUT_TIME);
            BufferedReader bufferedReader = null;
            try {
                URL url = new URL(searchUrl);
                httpURLConnection  =(HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer =new StringBuffer();

                String line = "";

                while((line = bufferedReader.readLine())!=null)
                {
                    stringBuffer.append(line);
                }
                String response = stringBuffer.toString();
                 Log.d("reponse",response);
                JSONObject finalResponse = new JSONObject(response);
                JSONArray jsonArray = finalResponse.getJSONArray("professional_list");

                Professional professional;
                for (int i = 0; i < jsonArray.length(); i++) {
                    professional = new Professional();
                    JSONObject finaljsonobject = jsonArray.getJSONObject(i);
                    professional.setProID(finaljsonobject.getString("pro_id"));
                    professional.setName(finaljsonobject.getString("name"));
                    professional.setBaseRate(Integer.valueOf(finaljsonobject.getString("base_rate")));
                    professional.setPhoneNumber(Long.valueOf(finaljsonobject.getString("phone_no")));
                    professional.setJob(finaljsonobject.getString("job_name"));
                   // professional.setSecondryPhoneNumber(Long.valueOf(finaljsonobject.getString("phone_no_secondary")));
                    professional.setAddress(finaljsonobject.getString("address"));
                    professional.setLocationLatitude(Float.valueOf(finaljsonobject.getString("base_location_latitude")));
                    professional.setLocationLongitude(Float.valueOf(finaljsonobject.getString("base_location_longitude")));
                    professional.setDistanceFromUser(Float.valueOf(finaljsonobject.getString("distance")));

                    //    Log.d("Sample Data",finaljsonobject.getString("OutletName"));
                    professionalList.add(professional);

                    //Log.d("outletData", outlet.getOutletName());
                }

            }


            catch (Exception e)
            {
                e.printStackTrace();
                loginFailure = true;

            }
            finally {


                if(httpURLConnection!=null)
                    httpURLConnection.disconnect();
            }

            return  null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.hide();

            searchProfessionalAdapter.notifyDataSetChanged();

            //Hiding the progress bar



        }
    }
}