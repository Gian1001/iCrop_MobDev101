package com.example.icrop_project;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment {



    private RelativeLayout homeRL;
    private RecyclerView weatherRV, newsRV;
    private WeatherRVAdapter weatherRVAdapter;
    private ArrayList<WeatherRVModal> weatherRVModalArrayList;

    private NewsRVAdapter newsRVAdapter;
    private ArrayList<NewsRVModel> newsRVModelArrayList;
    private LocationManager locationManager;
    private int PERMISSION_CODE = 1;
    private String cityName;
    private TextView cityTV, temperatureTV, conditionTV;

    private ImageView iconIV;


    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeRL = view.findViewById(R.id.rl_home);
        cityTV = view.findViewById(R.id.tv_cityname);
        temperatureTV = view.findViewById(R.id.tv_temperature);
        conditionTV = view.findViewById(R.id.tv_condition);
        iconIV = view.findViewById(R.id.iv_icon);
        weatherRV = view.findViewById(R.id.rv_weather);
        newsRV = view.findViewById(R.id.rv_news);

        newsRVModelArrayList = new ArrayList<>();
        newsRVModelArrayList.add(new NewsRVModel(R.drawable.image_news1, "A bright future for farming: 20-year-old agriculture student leads a youth urban farm in Taguig", ""));
        newsRVModelArrayList.add(new NewsRVModel(R.drawable.image_news2, "QC eyes to transform 30-ha. unused land into farm areas", ""));
        newsRVModelArrayList.add(new NewsRVModel(R.drawable.image_news3, "‘Farm along da riles’: Tondo youth marry urban farming and bayanihan for food security", ""));
        newsRVModelArrayList.add(new NewsRVModel(R.drawable.image_news4, "QC urban farming program pushed as model", ""));
        newsRVModelArrayList.add(new NewsRVModel(R.drawable.image_news3, "‘Farm along da riles’: Tondo youth marry urban farming and bayanihan for food security", ""));
        newsRVAdapter = new NewsRVAdapter(requireContext(), newsRVModelArrayList);
        newsRV.setAdapter(newsRVAdapter);
        newsRV.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        weatherRVModalArrayList = new ArrayList<>();
        weatherRVAdapter = new WeatherRVAdapter(requireContext(), weatherRVModalArrayList);
        weatherRV.setAdapter(weatherRVAdapter);
        weatherRV.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));




        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            cityName = getCityName(location.getLongitude(), location.getLatitude());
            getWeatherInfo(cityName);
        }
        // Now, you can use these views as needed.


        // Return the inflated view.
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                cityName = getCityName(location.getLongitude(), location.getLatitude());
                getWeatherInfo(cityName);
            } else {
                Toast.makeText(requireContext(), "Permissions Not Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getCityName(double longitude, double latitude) {
        String cityName = "Not Found";
        Geocoder gcd = new Geocoder(requireContext(), Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 10);

            for (Address adr : addresses) {
                if (adr != null) {
                    String city = adr.getLocality();
                    String region = adr.getAdminArea();

                    if (region != null && !region.equals("")) {
                        cityName = region;
                    } else {
                        Log.d("TAG", "City not found for coordinates: " + latitude + ", " + longitude);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }

    private void getWeatherInfo(String cityName) {

        String url = "https://api.weatherapi.com/v1/forecast.json?key=6c210eed9b1f4077b0015628231309&q=" + cityName + "&days=1&aqi=yes&alerts=yes";

        cityTV.setText(cityName);
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                weatherRVModalArrayList.clear();

                try {
                    String temperature = response.getJSONObject("current").getString("temp_c");
                    temperatureTV.setText(temperature+"°c");
//                  int isDay = response.getJSONObject("current").getInt("is_day");
                    String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("https://" + conditionIcon).into(iconIV);


                    conditionTV.setText(condition);

                    JSONObject forecastObj = response.getJSONObject("forecast");
                    JSONObject forecastO = forecastObj.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray hourArray = forecastO.getJSONArray("hour");

                    for (int i=0; i<hourArray.length(); i++){
                        JSONObject hourObj = hourArray.getJSONObject(i);
                        String time = hourObj.getString("time");
                        String temp = hourObj.getString("temp_c");
                        String img = hourObj.getJSONObject("condition").getString("icon");
                        String wind = hourObj.getString("wind_kph");
                        weatherRVModalArrayList.add(new WeatherRVModal(time,temp, img, wind));

                    }

                    weatherRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.getMessage());
                Toast.makeText(requireContext(), "Enter Valid City Name", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


}
