package ca.ubc.cs.cpsc210.quiz.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcarter on 2014-10-17.
 *
 * Represents a leg that has an arbitrary number of points and a distance.  Part of a route.
 */
public class Leg {
    private List<LatLng> points;
    private int distance;

    /**
     * Constructor
     *
     * List of points is empty and distance is zero.
     */
    public Leg() {
        points = new ArrayList<LatLng>();
        distance = 0;
    }

    /**
     * Add a point to the leg
     *
     * @param pt the point to be added
     */
    public void addPoint(LatLng pt) {
        points.add(pt);
    }

    /**
     * Add a list of points to the leg
     *
     * @param pts  the list of points to be added
     */
    public void addAllPoints(List<LatLng> pts) {
        points.addAll(pts);
    }

    /**
     * Gets points on this leg
     *
     * @return  list of points on this leg
     */
    public List<LatLng> getPoints() {
        return points;
    }

    /**
     * Set the distance for this leg
     *
     * @param distance  the leg distance in metres
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Get leg distance
     *
     * @return  the leg distance in metres
     */
    public int getDistance() {
        return distance;
    }
}
