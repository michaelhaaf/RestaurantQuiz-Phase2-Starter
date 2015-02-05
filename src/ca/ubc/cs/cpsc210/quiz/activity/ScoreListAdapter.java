package ca.ubc.cs.cpsc210.quiz.activity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pcarter on 14-11-06.
 *
 * Score list adapter.
 */
public class ScoreListAdapter extends ArrayAdapter<Score> {
    private Context context;
    private ScoreTracker scoreTracker;

    /**
     * Constructor
     *
     * @param context       the current context
     * @param scoreTracker  a score tracker
     */
    public ScoreListAdapter(Context context, ScoreTracker scoreTracker) {
        super(context, R.layout.row_layout, scoreTracker.getScores());
        this.context = context;
        this.scoreTracker = scoreTracker;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        TextView scoreNumberView = (TextView) rowView.findViewById(R.id.score_number);
        TextView pointsView = (TextView) rowView.findViewById(R.id.points);
        TextView attemptsView = (TextView) rowView.findViewById(R.id.attempts);

        List<Score> scores = scoreTracker.getScores();

        scoreNumberView.setText(Integer.toString(position + 1));

        int points = scores.get(position).getPointsEarned();

        if (points == scoreTracker.getHighPointsEarned())
            pointsView.setBackgroundColor(Color.parseColor("#11AA22"));

        pointsView.setText(Integer.toString(points));

        int attempts = scores.get(position).getNumAttempts();

        if (attempts == scoreTracker.getMinAttemptsTaken())
            attemptsView.setBackgroundColor(Color.parseColor("#11AA22"));

        attemptsView.setText(Integer.toString(attempts));

        return rowView;
    }
}
