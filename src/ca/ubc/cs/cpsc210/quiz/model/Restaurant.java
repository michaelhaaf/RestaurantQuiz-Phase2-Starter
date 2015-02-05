package ca.ubc.cs.cpsc210.quiz.model;

import java.util.List;

/**
 * Created by Jonathan Stiansen on 2014-08-08.
 *
 * Represents a restaurant having a street address, geographical area, postal code, name, id categories
 * and list of reviews.
 */
public class Restaurant {

    private String address;
    private GeoArea geographicalArea;
    private String name;
    private List<Category> categories;
    private String id;
    private String postalCode;

    /**
     * Constructor
     *
     * @param name              the name of the restaurant
     * @param categories        list of categories associated with this restaurant
     * @param id                the Yelp id
     * @param address           the address
     * @param postalCode        the postal code
     * @param geographicalArea  the geographical area
     */
    public Restaurant(String name, List<Category> categories, String id, String address, String postalCode, GeoArea geographicalArea) {
        this.name = name;
        this.categories = categories;
        this.id = id;
        this.address = address;
        this.geographicalArea = geographicalArea;
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getId() {
        return id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public GeoArea getGeographicalArea() {
        return geographicalArea;
    }

    /**
     * Two restaurants are equal if they have the same street address, geographical area, postal code, id, name
     * and categories.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant that = (Restaurant) o;

        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (!categories.equals(that.categories)) return false;
        if (!geographicalArea.equals(that.geographicalArea)) return false;
        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null)
            return false;

        return true;
    }

    /**
     * Produces string representation of restaurant of form:
     * name + first string in geographical areas geostrings + postal code
     * (e.g., "Heirloom Vegetarian, Fairview, Vancouver, BC, V6J 2E1")
     *
     * Postal code omitted if it is null
     *
     * @return  string representation of restaurant
     */
    @Override
    public String toString() {
        return name + ", " + geographicalArea.getAllGeoStrings().get(0) + ", " +
                (this.getPostalCode() == null ? "" : this.getPostalCode());
    }
}