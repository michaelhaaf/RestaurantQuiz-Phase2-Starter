package ca.ubc.cs.cpsc210.quiz.yelp;


import ca.ubc.cs.cpsc210.quiz.model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan Stiansen on 2014-08-18.
 *
 * Parser for JSON data returned by Yelp.
 */
public class YelpDataParser {
    /**
     * Parses a JSONObject received in response to a Yelp search for restaurants, filtered by city name and
     * produces corresponding list of restaurants that are not closed.  If the yelpCityFilter is null, "Vancouver"
     * is used as the city filter.
     *
     * @param response  the response produced by a Yelp search
     * @param yelpCityFilter  filter responses for restaurants with this city name
     * @return a list of restaurants parsed from Yelp response that are not closed and whose city matches
     *         yelpCityFilter
     * @throws JSONException when the response does not have the expected format (e.g. missing elements)
     */
    public static List<Restaurant> parseRestaurantData(String response, String yelpCityFilter)
            throws JSONException {
        if(yelpCityFilter == null)
            yelpCityFilter = "Vancouver";

        JSONObject yelpSearchResponse = new JSONObject(response);
        JSONArray businesses = yelpSearchResponse.getJSONArray("businesses");

        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();

        for (int i = 0; i < businesses.length(); i++) {
            JSONObject business = businesses.getJSONObject(i);
            //Create the restaurant
            Boolean isClosed = business.getBoolean("is_closed");

            if (!isClosed) {
                JSONObject locationObj = business.getJSONObject("location");

                if (locationObj.get("city").equals(yelpCityFilter)) {
                    JSONArray categoriesJSON = business.getJSONArray("categories");
                    List<Category> categories = parseCategories(categoriesJSON);
                    String id = business.getString("id");
                    String name = business.getString("name");

                    // Postal Code
                    String postalCode;
                    if (locationObj.has("postal_code"))
                        postalCode = locationObj.getString("postal_code");
                    else
                        postalCode = null;

                    // Street Address
                    JSONArray addressArr = locationObj.getJSONArray("address");
                    String address = "";
                    if(addressArr.length() > 0)
                        address = addressArr.getString(0);

                    // Location
                    GeoArea geoArea = parseGeoArea(locationObj);

                    Restaurant restaurant = new Restaurant(name, categories, id, address, postalCode, geoArea);
                    restaurants.add(restaurant);
                }
            }
        }

        return restaurants;
    }

    /**
     * Takes JSON array and parses it into a list of categories
     *
     * @param categoryArray a JSON array representing categories
     * @return a list of categories
     * @throws JSONException when JSONArray representing categories does not have expected format
     */
    private static List<Category> parseCategories(JSONArray categoryArray) throws JSONException {
        ArrayList<Category> categories = new ArrayList<Category>();

        for (int i = 0; i < categoryArray.length(); i++) {
            JSONArray jsonCategory = categoryArray.getJSONArray(i);

            Category category = new Category(jsonCategory.getString(0), jsonCategory.getString(1));
            categories.add(category);
        }

        return categories;
    }

    /**
     * Parses a geo area from a JSON object.  If the JSON object has a "neighbourhoods" JSONArray of non-zero length,
     * returns a Neighbourhood whose name is the first element of the "neighbourhoods" array, otherwise returns a City.
     *
     * @param jsonObject the JSON object that represents a geo area
     * @return  the geo area
     * @throws JSONException when JSONObject does not have expected format
     */
    private static GeoArea parseGeoArea(JSONObject jsonObject) throws JSONException {
        String cityName = jsonObject.getString("city");
        String stateCode = jsonObject.getString("state_code");
        String countryCode = jsonObject.getString("country_code");
        City city = new City(cityName, stateCode, countryCode);

        if (jsonObject.has("neighborhoods")) {
            JSONArray neighborhoods = jsonObject.getJSONArray("neighborhoods");
            if (neighborhoods.length() == 0)
                return city;
            else
                return new Neighbourhood(neighborhoods.getString(0), city);
        }
        else {
            return city;
        }
    }
}
