package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class TextMatchUtilTest {

    @Test
    public void calculateTokenSetRatio_identicalStrings_returns100Percent() {
        String str1 = "hello";
        String str2 = "hello";

        int ratio = TextMatchUtil.calculateTokenSetRatio(str1, str2);
        
        assertEquals(100, ratio);
    }

    @Test
    public void calculateTokenSetRatio_completelyDifferentStrings_returnsLowRatio() {
        String str1 = "abc";
        String str2 = "xyz";

        int ratio = TextMatchUtil.calculateTokenSetRatio(str1, str2);
        
        assertEquals(0, ratio);
    }

    @Test
    public void calculateTokenSetRatio_partiallyMatchingStrings_returnsProportionalRatio() {
        String str1 = "abcde";
        String str2 = "cdefg";

        int ratio = TextMatchUtil.calculateTokenSetRatio(str1, str2);
        
        // They share 3 characters (c, d, e) out of 7 unique characters (a, b, c, d, e, f, g)
        assertEquals(42, ratio);
    }

    @Test
    public void calculateTokenSetRatio_differentOrderSameChars_returns100Percent() {
        String str1 = "abcde";
        String str2 = "edcba";

        int ratio = TextMatchUtil.calculateTokenSetRatio(str1, str2);
        
        assertEquals(100, ratio);
    }

    @Test
    public void calculateLevenshteinDistance_identicalStrings_returnsZero() {
        String str1 = "hello";
        String str2 = "hello";

        int distance = TextMatchUtil.calculateLevenshteinDistance(str1, str2);
        
        assertEquals(0, distance);
    }

    @Test
    public void calculateLevenshteinDistance_oneCharacterDifference_returnsOne() {
        String str1 = "hello";
        String str2 = "hallo";

        int distance = TextMatchUtil.calculateLevenshteinDistance(str1, str2);
        
        assertEquals(1, distance);
    }

    @Test
    public void calculateLevenshteinDistance_addOneCharacter_returnsOne() {
        String str1 = "hello";
        String str2 = "helloo";

        int distance = TextMatchUtil.calculateLevenshteinDistance(str1, str2);
        
        assertEquals(1, distance);
    }

    @Test
    public void calculateLevenshteinDistance_deleteOneCharacter_returnsOne() {
        String str1 = "hello";
        String str2 = "hell";

        int distance = TextMatchUtil.calculateLevenshteinDistance(str1, str2);
        
        assertEquals(1, distance);
    }

    @Test
    public void calculateLevenshteinDistance_completelyDifferentStrings_returnsMaxDistance() {
        String str1 = "hello";
        String str2 = "world";

        int distance = TextMatchUtil.calculateLevenshteinDistance(str1, str2);
        
        assertEquals(4, distance);
    }

    @Test
    public void findClosestMatch_exactMatch_returnsExactMatch() {
        String input = "help";
        Set<String> candidates = new HashSet<>();
        candidates.add("help");
        candidates.add("exit");
        candidates.add("list");

        String result = TextMatchUtil.findClosestMatch(input, candidates);
        
        assertEquals("help", result);
    }

    @Test
    public void findClosestMatch_closeLevenshteinMatch_returnsCloseMatch() {
        String input = "hlp";  // One character missing from "help"
        Set<String> candidates = new HashSet<>();
        candidates.add("help");
        candidates.add("exit");
        candidates.add("list");

        String result = TextMatchUtil.findClosestMatch(input, candidates);
        
        assertEquals("help", result);
    }

    @Test
    public void findClosestMatch_closeTokenSetMatch_returnsCloseMatch() {
        String input = "hlpe";  // Same characters as "help" but in different order
        Set<String> candidates = new HashSet<>();
        candidates.add("help");
        candidates.add("exit");
        candidates.add("list");

        String result = TextMatchUtil.findClosestMatch(input, candidates);
        
        assertEquals("help", result);
    }

    @Test
    public void findClosestMatch_noGoodMatch_returnsNull() {
        String input = "xyz";  // Completely different from any candidates
        Set<String> candidates = new HashSet<>();
        candidates.add("help");
        candidates.add("exit");
        candidates.add("list");

        String result = TextMatchUtil.findClosestMatch(input, candidates);
        
        assertNull(result);
    }

    @Test
    public void findClosestMatch_emptySet_returnsNull() {
        String input = "help";
        Set<String> candidates = new HashSet<>();

        String result = TextMatchUtil.findClosestMatch(input, candidates);
        
        assertNull(result);
    }
} 