package com.mezzsy.copyzhihunews.util;

public class Util {

    //修改HTML格式
    public static String modifyHTML(String body) {
        String head = "<html><head>" +
                "<style " +
                "type=\"text/css\"> " +
                "a{" +
                "color:#526b8c;" +
                "font-size:18px;" +
                "word-break:break-all" +
                "}" +
                "body{" +
                "padding:0 10px" +
                "}" +
                ".meta {" +
                "overflow: hidden;" +
                "text-overflow: ellipsis;" +
                "white-space: nowrap;" +
                "}" +
                "</style>" +
                "</head><body>";
        body = body.replaceAll("<h2", "<h3");
        body = body.replaceAll("</h2>", "</h3>");
        body = body.replaceAll("<span class=\"bio\">"
                , "<span class=\"bio\"style=\"color: grey;\">");
        body = body.replaceAll("<div class=\"view-more\"><a",
                "<div class=\"view-more\" " +
                        "style=\"width:100% ; background: whitesmoke; text-align: center; padding-bottom: 5px;padding-top: 5px; \">" +
                        "<a style=\"text-decoration: none; color: grey;\"");
        body = body.replaceAll("<img class=\"content-image\"",
                "<img class=\"content-image\" style=\"width:100%\"");

        String closedTag = "</body></html>";
        return head + body + closedTag;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.equals("");
    }
}
