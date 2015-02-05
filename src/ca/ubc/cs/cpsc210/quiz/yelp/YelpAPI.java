package ca.ubc.cs.cpsc210.quiz.yelp;

import android.content.Context;
import android.util.Log;

import ca.ubc.cs.cpsc210.quiz.activity.R;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;


/**
 * Code sample for accessing the Yelp API V2.
 * <p/>
 * This program demonstrates the capability of the Yelp API version 2.0 by using the Search API to
 * query for businesses by a search term and location, and the Business API to query additional
 * information about the top result from the search query.
 * <p/>
 * <p/>
 * See <a href="http://www.yelp.com/developers/documentation">Yelp Documentation</a> for more info.
 */
public class YelpAPI {

    private static final String API_HOST = "api.yelp.com";
    private static final int SEARCH_LIMIT = 20;
    private static final String SEARCH_PATH = "/v2/search";
    private static final String BUSINESS_PATH = "/v2/business";
    private static final LatLng VANCOUVER_UPPER_RIGHT_BOUND = new LatLng(49.293288, -123.038464);
    private static final LatLng VANCOUVER_LOWER_LEFT_BOUND = new LatLng(49.213687, -123.22197);

    private OAuthService service;
    private Token accessToken;
    private static YelpAPI instance;

    /**
     * Setup the Yelp API OAuth credentials.
     *
     * @param consumerKey    Consumer key
     * @param consumerSecret Consumer secret
     * @param token          Token
     * @param tokenSecret    Token secret
     */
    private YelpAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {
        this.service =
                new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(consumerKey)
                        .apiSecret(consumerSecret).build();
        this.accessToken = new Token(token, tokenSecret);
        instance = null;
    }

    public static YelpAPI getInstance(Context c) {
        if (instance == null)
            instance = new YelpAPI(c.getString(R.string.consumer_key),
                               c.getString(R.string.consumer_secret),
                               c.getString(R.string.token),
                               c.getString(R.string.token_secret));

        return instance;
    }

    /**
     * Queries the Search API based on the command line arguments.
     *
     * @param term            Term to be searched
     * @param location        Location to be searched
     * @param categoryAliases <<tt>String</tt> Comma seperated category aliases
     */
    public String queryAPI(String term, String location, String categoryAliases, int numOfResults) {
        String searchResponseJSON =
                this.searchForBusinessesByLocationAndCategory(term, location, categoryAliases, numOfResults);

        Log.d("YelpApi", "initialSearchResponse is : " + searchResponseJSON);
        return searchResponseJSON;
    }

    /**
     * Creates and sends a request to the Search API by term and location.
     * <p/>
     * See <a href="http://www.yelp.com/developers/documentation/v2/search_api">Yelp Search API V2</a>
     * for more info.
     *
     * @param term            <tt>String</tt> of the search term to be queried
     * @param location        <tt>String</tt> of the location
     * @param numOfResults    <<tt>int</tt> of the number of results desired
     * @param categoryAliases <<tt>String</tt> Comma seperated category aliases
     * @return <tt>String</tt> JSON Response
     */
    protected String searchForBusinessesByLocationAndCategory(String term, String location, String categoryAliases, int numOfResults) {
        OAuthRequest request = createOAuthRequest(SEARCH_PATH);
        request.addQuerystringParameter("term", term);
        request.addQuerystringParameter("sort", "1");
        if(location.startsWith("Vancouver")){
            request.addQuerystringParameter("bounds",
                    Double.toString(VANCOUVER_LOWER_LEFT_BOUND.latitude) + "," +
                            Double.toString(VANCOUVER_LOWER_LEFT_BOUND.longitude) + "|" +
                            Double.toString(VANCOUVER_UPPER_RIGHT_BOUND.latitude) + "," +
                            Double.toString(VANCOUVER_UPPER_RIGHT_BOUND.longitude));
        } else request.addQuerystringParameter("location", location);

        request.addQuerystringParameter("category_filter", categoryAliases);
        if (numOfResults > SEARCH_LIMIT && numOfResults > 0)
            request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
        else request.addQuerystringParameter("limit", String.valueOf(numOfResults));
        return sendRequestAndGetResponse(request);
    }

    /**
     * Creates and returns an {@link org.scribe.model.OAuthRequest} based on the API endpoint specified.
     *
     * @param path API endpoint to be queried
     * @return <tt>OAuthRequest</tt>
     */
    private OAuthRequest createOAuthRequest(String path) {
        return new OAuthRequest(Verb.GET, "http://" + API_HOST + path);
    }

    /**
     * Sends an {@link org.scribe.model.OAuthRequest} and returns the {@link org.scribe.model.Response} body.
     *
     * @param request {@link org.scribe.model.OAuthRequest} corresponding to the API request
     * @return <tt>String</tt> body of API response
     */
    private String sendRequestAndGetResponse(OAuthRequest request) {
        Log.d("YelpApi", "Querying " + request.getCompleteUrl() + " ...");
        this.service.signRequest(this.accessToken, request);
        Response response = request.send();
        return response.getBody();
    }

}
