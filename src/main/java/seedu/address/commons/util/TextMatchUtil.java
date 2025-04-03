package seedu.address.commons.util;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility methods for finding string similarity and matching.
 */
public class TextMatchUtil {

    /**
     * Calculates the token set ratio between two strings.
     * This is useful for fuzzy matching when the order of characters doesn't matter as much.
     *
     * @param a First string
     * @param b Second string
     * @return A value between 0 and 100 representing the similarity percentage
     */
    public static int calculateTokenSetRatio(String a, String b) {
        Set<Character> tokensA = a.chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.toCollection(HashSet::new));
        Set<Character> tokensB = b.chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.toCollection(HashSet::new));

        Set<Character> intersection = tokensA.stream()
            .filter(tokensB::contains)
            .collect(Collectors.toSet());

        Set<Character> union = new HashSet<>(tokensA);
        union.addAll(tokensB);

        return (int) ((double) intersection.size() / union.size() * 100);
    }

    /**
     * Calculates the Levenshtein distance between two strings.
     * Lower values indicate higher similarity.
     *
     * @param a First string
     * @param b Second string
     * @return The edit distance between the two strings
     */
    public static int calculateLevenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(
                        dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1),
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1)
                    );
                }
            }
        }
        return dp[a.length()][b.length()];
    }

    /**
     * Determines if two strings are sufficiently similar using both Levenshtein distance and token set ratio.
     *
     * @param str1 First string to compare
     * @param str2 Second string to compare
     * @param maxDistance Maximum allowed Levenshtein distance (default 2)
     * @param minTokenSetRatio Minimum required token set ratio (default 80)
     * @return true if the strings are considered similar enough, false otherwise
     */
    public static boolean areSimilarStrings(String str1, String str2, int maxDistance, int minTokenSetRatio) {
        int distance = calculateLevenshteinDistance(str1, str2);
        if (distance <= maxDistance) {
            return true;
        }

        int tokenSetRatio = calculateTokenSetRatio(str1, str2);
        return tokenSetRatio >= minTokenSetRatio;
    }

    /**
     * Finds the closest matching string from a set of candidates.
     * Uses Levenshtein distance first, then falls back to token set ratio.
     *
     * @param input The input string to find a match for
     * @param candidates A set of candidate strings to match against
     * @return The closest matching string, or null if no good match is found
     */
    public static String findClosestMatch(String input, Set<String> candidates) {
        int minDistance = Integer.MAX_VALUE;
        String closestMatch = null;

        // Try Levenshtein distance first
        for (String candidate : candidates) {
            int distance = calculateLevenshteinDistance(input, candidate);

            if (distance < minDistance) {
                minDistance = distance;
                closestMatch = candidate;
            }
        }

        if (minDistance <= 2) {
            return closestMatch;
        }

        // Use TokenSetRatio if LeveischteinDistance fails
        int bestTokenSetRatio = 0;
        for (String candidate : candidates) {
            int tokenSetRatio = calculateTokenSetRatio(input, candidate);

            if (tokenSetRatio > bestTokenSetRatio) {
                bestTokenSetRatio = tokenSetRatio;
                closestMatch = candidate;
            }
        }

        if (bestTokenSetRatio >= 80) {
            return closestMatch;
        }

        return null;
    }
}
