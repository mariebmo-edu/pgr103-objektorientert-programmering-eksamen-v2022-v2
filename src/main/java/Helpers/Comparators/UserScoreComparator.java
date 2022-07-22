package Helpers.Comparators;

import Model.UserScore;

import java.util.Comparator;

public class UserScoreComparator implements Comparator<UserScore> {

    @Override
    public int compare(UserScore o1, UserScore o2) {
        return o2.getScore() - o1.getScore();
    }
}