package ca.ubc.cs.cpsc210.quiz.google;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pcarter on 2014-10-16.
 * Represents Google Directions API
 */
public class GoogleAPI {
    private static final String DIRECTIONS_API_BASE = "https://maps.googleapis.com/maps/api/directions";
    private static final String OUT_JSON = "/json";
    private static final String MODE = "?mode=walking";

    public static StringBuilder getRoute(LatLng origin, LatLng destn) throws Exception {
        HttpURLConnection directionsConn = null;
        StringBuilder jsonResults = new StringBuilder();

        try {
            directionsConn = getHttpURLConnection(jsonResults, origin, destn);
        } finally {
            if (directionsConn != null) {
                directionsConn.disconnect();
            }
        }

        return jsonResults;
    }

    private static HttpURLConnection getHttpURLConnection(StringBuilder jsonResults, LatLng origin, LatLng destn) throws IOException {
        String sb = DIRECTIONS_API_BASE + OUT_JSON + MODE;
        sb += "&origin=" + origin.latitude + "," + origin.longitude;
        sb += "&destination=" + destn.latitude  + "," + destn.longitude;

        URL url = new URL(sb);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStreamReader in = new InputStreamReader(conn.getInputStream());

        // Load the results into a StringBuilder
        int read;
        char[] buff = new char[1024];
        while ((read = in.read(buff)) != -1) {
            jsonResults.append(buff, 0, read);
        }

        return conn;
    }
}
