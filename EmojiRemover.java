package com.uek.etl.Utilities;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class created for removing Emoji and non UTF-8 Symbols from String which are saved to Mysql database
 * There is issue with saving UTF-8 4 bytes characters, even after setting up correct encoding in database and table
 */
public class EmojiRemover {

    public static String clearString(
            String content) {
        String utf8tweet = "";
        try {
            byte[] utf8Bytes = content.getBytes(
                    "UTF-8");

            utf8tweet = new String(
                    utf8Bytes, "UTF-8");
        } catch (
                UnsupportedEncodingException e
                ) {
            e.printStackTrace();
        }
        Pattern unicodeOutliers =
                Pattern.compile(
                        "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                        Pattern.UNICODE_CASE |
                                Pattern.CANON_EQ |
                                Pattern.CASE_INSENSITIVE
                );
        Matcher unicodeOutlierMatcher =
                unicodeOutliers.matcher(
                        utf8tweet);

        utf8tweet =
                unicodeOutlierMatcher.replaceAll(
                        " ");
        return utf8tweet;
    }

}
