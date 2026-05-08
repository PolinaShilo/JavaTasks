package com.phonebook.util;

public final class XssUtil {
    private XssUtil() {}

    public static String sanitize(String input) {
        if (input == null) return null;
        return input.replaceAll("<script.*?>.*?</script>", "")
                .replaceAll("javascript:", "")
                .replaceAll("onload=", "")
                .replaceAll("onerror=", "");
    }

    public static String escapeHtml(String input) {
        if (input == null) return null;
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}