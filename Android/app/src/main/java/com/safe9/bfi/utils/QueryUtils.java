package com.safe9.bfi.utils;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;


/**
 * Utils class for networking
 */
public final class QueryUtils {

    private static String TOKEN_BASE_URL = "https://hacknineapi.herokuapp.com/raspi/edit";
    private static String CHILD_BASE_URL = "https://paddychild.herokuapp.com/childs/new";
    private static String PATIENT_BASE_URL = "https://paddychild.herokuapp.com/patients/details";
    private static String VACCINATIONS_BASE_URL = "https://paddychild.herokuapp.com/vaccines/new";

    public static RequestQueue sendTokenRequest(RequestQueue queue, boolean isGetRequest,
                                                final String token) {
        int requestMethod;
        if (isGetRequest) {
            requestMethod = Request.Method.GET;
        } else {
            requestMethod = Request.Method.POST;

        }
        StringRequest request = new StringRequest(requestMethod, TOKEN_BASE_URL + "?securekey=" + token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(QueryUtils.class.getSimpleName(), response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(QueryUtils.class.getSimpleName(), error.toString());

            }
        }
        );

// Add the request to the RequestQueue.
        queue.add(request);
        return queue;
    }

    public static RequestQueue sendPatientDataRequest(RequestQueue queue, boolean isGetRequest, boolean isPatient,

                                                      final String urlParams) {
        String baseUrl;
        int requestMethod;
        if (isGetRequest) {
            requestMethod = Request.Method.GET;
        } else {
            requestMethod = Request.Method.POST;
        }
        if (isPatient) {
            baseUrl = PATIENT_BASE_URL;
        } else {
            baseUrl = CHILD_BASE_URL;
        }
        StringRequest request = new StringRequest(requestMethod, baseUrl + urlParams,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(QueryUtils.class.getSimpleName(), response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(QueryUtils.class.getSimpleName(), error.toString());

            }
        }
        );

        // Add the request to the RequestQueue.
        queue.add(request);
        return queue;
    }

    public static RequestQueue sendVaccineDataRequest(RequestQueue queue,
                                                      final String urlParams) {
        int requestMethod = Request.Method.POST;
        StringRequest request = new StringRequest(requestMethod, VACCINATIONS_BASE_URL + urlParams,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(QueryUtils.class.getSimpleName(), response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(QueryUtils.class.getSimpleName(), error.toString());
            }
        }
        );

        // Add the request to the RequestQueue.
        queue.add(request);
        return queue;
    }

    public interface QueryCallback {
        void returnLatLng(double lat, double lng);

        void returnResponse(String response);
    }

    public static void getLatLongFromAddress(RequestQueue queue, String address, final QueryCallback callback) {
        String url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&sensor=false";

        JsonObjectRequest stateReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject location;
                try {
                    // Get JSON Array called "results" and then get the 0th
                    // complete object as JSON
                    location = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                    // Get the value of the attribute whose name is
                    // "formatted_string"
                    double lat = location.getDouble("lat");
                    double lng = location.getDouble("lng");
                    callback.returnLatLng(lat, lng);
                    Timber.d("lat = " + String.valueOf(lat) + " long= " + String.valueOf(lng));
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Timber.tag("Error.Response").d(error.toString());
            }
        });
        // add it to the RequestQueue

        queue.add(stateReq);
    }

    public static void sendLocation(RequestQueue queue, double lat, double lng, final QueryCallback callback) {

        String url = "https://paddychild.herokuapp.com/patients/neighbours?latitude=" + lat + "&longitude=" + lng;
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(QueryUtils.class.getSimpleName(), response);
                        callback.returnResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(QueryUtils.class.getSimpleName(), error.toString());

            }
        }
        );

        // Add the request to the RequestQueue.
        queue.add(request);
    }
}
