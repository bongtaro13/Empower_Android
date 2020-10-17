package com.example.empower.api;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * create visual routes on map with result from API
 */

public class DataParser {

    public List<List<HashMap<String, String>>> parse(JSONObject jObject) {


        List<List<HashMap<String, String>>> routes = new ArrayList<>();

        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {
            jRoutes = jObject.getJSONArray("routes");
            /** Traversing all routes */
            for (int i = 0; i < jRoutes.length(); i++) {
                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                List path = new ArrayList<>();
                /** Traversing all legs */
                for (int j = 0; j < jLegs.length(); j++) {
                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                    /** Traversing all steps */
                    for (int k = 0; k < jSteps.length(); k++) {
                        String polyline = "";
                        polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = decodePoly(polyline);

                        /** Traversing all points */
                        for (int l = 0; l < list.size(); l++) {
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("lat", Double.toString((list.get(l)).latitude));
                            hm.put("lng", Double.toString((list.get(l)).longitude));
                            path.add(hm);
                        }
                    }
                    routes.add(path);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }

        return routes;

    }



    /**
     * Method to decode polyline points
     * Courtesy : https://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public List<HashMap<String, String>> stepParse(JSONObject jsonObject) {
        List<HashMap<String, String>> result = new ArrayList<>();
        try {
            JSONArray routes = jsonObject.getJSONArray("routes");
            JSONObject routeObject = routes.getJSONObject(0);
            JSONArray legs = routeObject.getJSONArray("legs");
            JSONObject legObject = legs.getJSONObject(0);

            String startAddress = legObject.getString("start_address");
            String endAddress = legObject.getString("end_address");
            String totalDistance = legObject.getJSONObject("distance").getString("text");
            String duration = legObject.getJSONObject("duration").getString("text");

            HashMap<String, String> resultSummary = new HashMap<>();
            resultSummary.put("start_address", startAddress);
            resultSummary.put("end_address", endAddress);
            resultSummary.put("total_distance", totalDistance);
            resultSummary.put("duration", duration);
            result.add(resultSummary);


            JSONArray steps = legObject.getJSONArray("steps");
            for (int i = 0; i < steps.length(); i++) {
                JSONObject step = steps.getJSONObject(i);
                String distance = step.getJSONObject("distance").getString("text");
                String travel_mode = step.getString("travel_mode");

                HashMap<String, String> stepsInfo = new HashMap<>();
                stepsInfo.put("distance", distance);
                stepsInfo.put("travel_mode", travel_mode);


                if (travel_mode.equals("TRANSIT")) {
                    JSONObject transit_details = step.getJSONObject("transit_details");
                    JSONObject line = transit_details.getJSONObject("line");
                    String transitShortName = line.getString("short_name");
                    String vehicleName = line.getJSONObject("vehicle").getString("name");
                    stepsInfo.put("short_name", transitShortName);
                    stepsInfo.put("vehicleName", vehicleName);

                }
                result.add(stepsInfo);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }


}
