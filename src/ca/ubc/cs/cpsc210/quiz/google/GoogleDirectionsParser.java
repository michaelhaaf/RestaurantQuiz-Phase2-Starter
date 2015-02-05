package ca.ubc.cs.cpsc210.quiz.google;

import ca.ubc.cs.cpsc210.quiz.model.Leg;
import ca.ubc.cs.cpsc210.quiz.model.Route;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by pcarter on 2014-10-17.
 * Based on code written by Jonathan Stiansen.
 *
 * Parser for response from Google Directions API.
 */
public class GoogleDirectionsParser {

    /**
     * Parse route from Google Directions API response
     *
     * @param response  response from Google Directions API
     * @return  route object corresponding to response provided by Google Directions API
     * @throws JSONException  when response from Google does not contain expected format (e.g. missing elements)
     */
    public static Route parseRoute(String response) throws JSONException {
        Route route = new Route();

        // Create a JSON object from the results
        JSONObject jsonObj = new JSONObject(response);
        JSONArray routesArray = jsonObj.getJSONArray("routes");
        JSONObject routeObj = routesArray.getJSONObject(0);
        JSONArray legsArray = routeObj.getJSONArray("legs");

        for (int legIndex = 0; legIndex < legsArray.length(); legIndex++) {
            JSONObject legObj = legsArray.getJSONObject(legIndex);
            JSONObject distObj = legObj.getJSONObject("distance");
            Integer legDistance = distObj.getInt("value");

            Leg leg = parseLeg(legObj);

            leg.setDistance(legDistance);
            route.addLeg(leg);
        }

        return route;
    }

    private static Leg parseLeg(JSONObject legObj) throws JSONException {
        Leg leg = new Leg();
        JSONArray stepsArray = legObj.getJSONArray("steps");

        for (int stepIndex = 0; stepIndex < stepsArray.length(); stepIndex++) {
            JSONObject step = stepsArray.getJSONObject(stepIndex);
            JSONObject polyline = step.getJSONObject("polyline");
            String encodedPoints = polyline.getString("points");
            List<LatLng> points = PolyUtil.decode(encodedPoints);
            leg.addAllPoints(points);
        }

        return leg;
    }
}
