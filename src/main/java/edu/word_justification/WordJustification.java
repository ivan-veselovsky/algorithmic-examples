package edu.word_justification;

import java.util.ArrayList;
import java.util.List;

public class WordJustification {
    private String[] words;
    private int maxWidth;

    public List<String> fullJustify(String[] words, final int maxWidth) {
        this.words = words;
        this.maxWidth = maxWidth;

        final List<String> result = new ArrayList<>(words.length / 2);

        int startWordIndex = 0;
        while (startWordIndex < words.length) {
            int count = getWordCountForOneLine(startWordIndex);
            assert count >= 1;
            boolean lastLine = (startWordIndex + count == words.length);
            String line = lastLine ? composeLastLine(startWordIndex, count) : composeOneLine(startWordIndex, count);
            assert line.length() == maxWidth;
            result.add(line);
            startWordIndex += count;
        }

        return result;
    }

    int getWordCountForOneLine(int startWordIndex) {
        int wordCount = 0;
        int lengthAccumulator = 0;
        while (startWordIndex < words.length) {
            lengthAccumulator += words[startWordIndex].length();
            if (wordCount > 0) {
                lengthAccumulator++; // at least one space is needed
            }
            if (lengthAccumulator > maxWidth) {
                break;
            }
            wordCount++;
            startWordIndex++;
        }
        return wordCount;
    }

    String composeOneLine(int wordIndex, int count) {
        assert count >= 1;
        int sumWidth = getSumWidth(wordIndex, count);
        int extraSpaces = maxWidth - sumWidth;
        assert extraSpaces >= 0;

        StringBuilder sb = new StringBuilder(maxWidth);
        if (count == 1) {
            sb.append(words[wordIndex]).repeat(' ', maxWidth - sumWidth);
        } else {
            int spaceAreas = count - 1;
            int div = extraSpaces / spaceAreas;
            int mod = extraSpaces % spaceAreas;
            for (int i=0; i<count; i++) {
                sb.append(words[wordIndex + i]);
                if (i < spaceAreas) {
                    int space = div;
                    if (i < mod) { // plus, mod times give one additional space:
                        space++;
                    }
                    sb.repeat(' ', space);
                }
            }
        }
        return sb.toString();
    }

    private int getSumWidth(int index, int count) {
        assert count >= 1;
        int sumWidth = 0;
        for (int i=0; i<count; i++) {
            sumWidth += words[index + i].length();
        }
        return sumWidth;
    }

    // left-justification only:
    String composeLastLine(int wordIndex, int count) {
        assert count >= 1;
        int sumWidth = getSumWidth(wordIndex, count);
        int extraSpaces = maxWidth - sumWidth;
        assert extraSpaces >= 0;
        StringBuilder sb = new StringBuilder(maxWidth);
        if (count == 1) {
            sb.append(words[wordIndex]).repeat(' ', extraSpaces);
        } else {
            for (int i=0; i<count; i++) {
                sb.append(words[wordIndex + i]);
                if (i < count - 1) {
                    sb.append(' ');
                }
            }
            int spacesRemaining = (extraSpaces - count + 1);
            if (spacesRemaining > 0) {
                sb.repeat(' ', spacesRemaining);
            }
        }
        return sb.toString();
    }
}
