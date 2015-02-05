package ca.ubc.cs.cpsc210.quiz.activity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.*;

import java.util.List;

/**
 * Created by Michael Haaf on 20/11/2014.
 */
public class HintManager {
    private GoogleMap map;
    private Marker hintMarker;
    private int hints;

    /**
     * Constructor initializes manager with map for which hintMarkers are to be managed.
     *
     * @param map the map for which hintOverlays are to be managed
     */
    public HintManager(GoogleMap map) {
        this.map = map;
        hints = 0;
    }

    /**
     * Get the number of hints given
     *
     * @return number of hints
     */
    public int getHintsGiven() {
        return hints;
    }

    /**
     * Add arrow.png (the hintMarker) at the location of the marker clicked, pointing in the direction of the target restaurant
     *
     * @param point   the point at which to add arrow.png
     * @param bearing direction (relative to 0deg north) of target restaurant
     */
    public void addHintMarker(LatLng point, float bearing) {

        hintMarker = map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow))
                .anchor(0.125f, 0.875f)
                .rotation(bearing - 45f)
                .flat(true)
                .position(point));

        hints += 1;

    }

    /**
     * Removes the hintMarker from the map
     */
    public void removeHintMarker() {
        if (hintMarker != null) hintMarker.remove();
    }

}
