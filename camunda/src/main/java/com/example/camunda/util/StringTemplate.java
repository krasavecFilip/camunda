package com.example.camunda.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringTemplate {

    public static String replace(String fileName, Properties replacements) throws IOException {
        String text = resourceFileAsString(fileName);
        return replaceInString(text, replacements);
    }

    public static String replaceInString(String text, Properties replacements) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(text);
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            Object replacement = replacements.get(matcher.group(1));
            builder.append(text.substring(i, matcher.start()));
            if (replacement == null) {
                builder.append("");
            } else {
                builder.append(replacement.toString());
            }
            i = matcher.end();
        }
        builder.append(text.substring(i, text.length()));
        return builder.toString();
    }

    public static String resourceFileAsString(String fileName) throws IOException {
        try (InputStream is = StringTemplate.class.getResourceAsStream(fileName)) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }
}