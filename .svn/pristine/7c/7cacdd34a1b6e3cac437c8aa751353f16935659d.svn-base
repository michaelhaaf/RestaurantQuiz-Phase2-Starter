package ca.ubc.cs.cpsc210.quiz.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pcarter on 2014-10-17.
 *
 * Represents a route having a list of legs and a distance.
 */
public class Route implements Iterable<Leg> {
    private List<Leg> legs;

    /**
     * Constructs a route with no legs and zero distance
     */
    public Route() {
        legs = new ArrayList<Leg>();
    }

    /**
     * Add a leg to this route
     *
     * @param leg  the leg to add to route
     */
    public void addLeg(Leg leg) {
        legs.add(leg);
    }

    /**
     * Get points on this route
     *
     * @return  list of points on this route
     */
    public List<Leg> getLegs() {
        return legs;
    }

    /**
     * Get total distance for this route (the sum of the distances for the legs).
     *
     * @return  total distance
     */
    public int getDistance() {
        int distance = 0;

        for (Leg l : legs) {
            distance += l.getDistance();
        }

        return distance;
    }


    @Override
    public Iterator<Leg> iterator() {
        return legs.iterator();
    }
}
