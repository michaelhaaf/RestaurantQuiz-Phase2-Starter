package ca.ubc.cs.cpsc210.quiz.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan Stiansen on 2014-09-06.
 *
 * Represents a neighbourhood having a name in a city
 */
public class Neighbourhood implements GeoArea {

    private String neighborhoodName;
    private City city;

    /**
     * Constructor
     *
     * @param neighborhoodName  the name of this neighbourhood
     * @param city  the city in which this neighbourhood is located
     */
    public Neighbourhood(String neighborhoodName, City city) {
        this.neighborhoodName = neighborhoodName;
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    @Override
    public String getCityString() {
        return city.getCityString();
    }

    /**
     * Returns array of strings that can be used to describe the neighbourhood,
     * starting with just the first word, then first plus second
     * E.g. Neighborhood name: Fancy Slopes Drive would produce:
     * first -> Fancy, City, Prov,
     * second -> Fancy Slopes, City, Prov,
     * third -> Fancy Slopes Drive, City, Prov
     *
     * @return  array of strings that describe the neighbourhood
     */
    @Override
    public List<String> getAllGeoStrings(){
        String[] neighborhoodNames = neighborhoodName.split(" ");
        List<String> geoStrings = new ArrayList<String>(neighborhoodNames.length);

        for (int index = 0; index < neighborhoodNames.length; index++) {
            String geoString = "";

            int i;
            for (i = 0; i < index; i++) {
                geoString += neighborhoodNames[i] + " ";
            }

            geoString += neighborhoodNames[i] + ", " + city.getCityString();
            geoStrings.add(geoString);
        }

        return  geoStrings;
    }


    /**
     * Two neighbourhoods are equal if they have the same city.
     * When o is a City, this neighbourhood is equal to it, if it has the same city.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        // will have to revisit this if we add subtypes of GeoArea other than City and Neighborhood
        if (o == null || !(o instanceof GeoArea)) return false;

        // when o is a City, this Neighborhood is equal to o if the cities are equal
        if (o instanceof City)
            return city.equals(o);

        // must be a Neighborhood
        Neighbourhood other = (Neighbourhood) o;

        if (!city.equals(other.city)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return city != null ? city.hashCode() : 0;
    }
}
