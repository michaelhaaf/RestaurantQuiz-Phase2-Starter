package ca.ubc.cs.cpsc210.quiz.activity;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcarter on 14-11-06.
 *
 * Score serializer reads and writes scores to file
 */
public class ScoreSerializer {
    private static final String LOG_TAG = "Score Serializer";
    private static final String FILENAME = "scores.json";
    private Context context;

    public ScoreSerializer(Context c) {
        context = c;
    }

    /**
     * Write list scores to file
     *
     * @param scores  list of scores
     * @throws org.json.JSONException
     * @throws java.io.IOException
     */
    public void writeScores(List<Score> scores) throws JSONException, IOException {
        JSONArray scoresAsJSON = new JSONArray();

        for(Score next : scores) {
            scoresAsJSON.put(scoreToJSON(next));
        }

        Writer writer = null;
        try {
            OutputStream out = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(scoresAsJSON.toString());
            Log.i(LOG_TAG, "Scores written to file");
        } finally {
            if(writer != null)
                writer.close();
        }
    }

    /**
     * Read list of scores from file
     * @return list of favourite scores
     * @throws IOException
     * @throws JSONException
     */
    public List<Score> readScores() throws JSONException, IOException {
        List<Score> scores = new ArrayList<Score>();
        BufferedReader reader = null;

        try {
            InputStream in = context.openFileInput(FILENAME);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonStringBldr = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null) {
                jsonStringBldr.append(line);
            }

            JSONArray array = new JSONArray(jsonStringBldr.toString());

            for(int i = 0; i < array.length(); i++) {
                scores.add(scoreFromJSON(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            // ignore: will get thrown first time application is run
            Log.i(LOG_TAG, "Scores not read from file");
        }

        return scores;
    }

    /**
     * Convert score to JSONObject
     *
     * @param s   the score
     * @return    corresponding JSON object
     * @throws JSONException  when JSON object cannot be constructed from score
     */
    private JSONObject scoreToJSON(Score s) throws JSONException {
        JSONObject sObj = new JSONObject();
        sObj.put("points", s.getPointsEarned());
        sObj.put("attempts", s.getNumAttempts());
        return sObj;
    }

    /**
     * Parse JSONObject as a Score
     * @param o  the JSONObject
     * @return   corresonding score
     * @throws JSONException  when JSON object cannot be parsed as a score
     */
    private Score scoreFromJSON(JSONObject o) throws JSONException {
        int points = o.getInt("points");
        int attempts = o.getInt("attempts");
        return new Score(attempts, points);
    }
}
