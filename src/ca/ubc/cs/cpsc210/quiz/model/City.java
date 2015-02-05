package ca.ubc.cs.cpsc210.quiz.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan Stiansen on 2014-09-05.
 *
 * Represents a name having a name, province/state code and country code
 */
public class City implements GeoArea {

    private String provinceOrStateCode;
    private String name;
    private String countryCode;

    /**
     * Constructor
     *
     * @param cityName     name of this city
     * @param province     province or state code
     * @param countryCode  country code
     * @throws IllegalArgumentException if length of province or length of country code is not exactly 2
     */
    public City(String cityName, String province, String countryCode) {
        if(countryCode.equalsIgnoreCase("canada")) countryCode = "CA";
        if(countryCode.equalsIgnoreCase("United States")) countryCode = "US";
        if ( countryCode.length() != 2 || province.length() != 2) // Used to check for 2 digit country, but geocoding returns full country : old code ''
            throw new IllegalArgumentException("Size of province and country code must be exactly 2, country was :" + countryCode + ", province was: "+province);
        this.provinceOrStateCode = province;
        this.name = cityName;
        this.countryCode = countryCode;
    }

    /**
     * Returns a string consisting of the form "city, province, country" (e.g. "Vancouver, BC, CA")
     *
     * @return  string representing the city
     */
    @Override
    public String getCityString() {
        return name + ", " + provinceOrStateCode + ", " + countryCode;
    }

    /**
     * Produces an array of strings that contains the string produced by getCityString
     *
     * @return array of geo strings
     */
    @Override
    public List<String> getAllGeoStrings() {
        List<String> geoStrings = new ArrayList<String>();

        geoStrings.add(getCityString());

        return geoStrings;
    }

    /**
     * Two cities are equal if they have the same name, province/state code and country code
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        // will have to revisit this if we add subtypes of GeoArea other than City and Neighborhood
        if (o == null || !(o instanceof GeoArea)) return false;

        if (o instanceof Neighbourhood) {
            Neighbourhood other = (Neighbourhood) o;
            return this.equals(other.getCity());
        }

        // must be a City
        City c = (City) o;

        if (!name.equals(c.name)) return false;
        if (!countryCode.equals(c.countryCode)) return false;
        if (!provinceOrStateCode.equals(c.provinceOrStateCode)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = provinceOrStateCode != null ? provinceOrStateCode.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        return result;
    }
}
