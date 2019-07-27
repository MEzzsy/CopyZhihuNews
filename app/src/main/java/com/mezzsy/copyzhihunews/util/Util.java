package com.mezzsy.copyzhihunews.util;

public class Util {
    private static final String TAG = "Utilzzsy";

//    //修改HTML格式
//    public static String modifyHTML(String body, String css) {
//        String linkCss = "<link rel=\"stylesheet\" " +
//                "href=" + css +
//                "type=\"text/css\">";
//        body = "<html><header>" +
//                linkCss +
//                "</header>" +
//                "<body>" +
//                body;
//        String html = body + "</body></html>";
////        i(TAG,html);
//        return html;
//    }
//
//    public static void i(String tag, String msg) {  //信息太长,分段打印
//        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
//        //  把4*1024的MAX字节打印长度改为2001字符数
//        int max_str_length = 2001 - tag.length();
//        //大于4000时
//        while (msg.length() > max_str_length) {
//            Log.i(tag, msg.substring(0, max_str_length));
//            msg = msg.substring(max_str_length);
//        }
//        //剩余部分
//        Log.i(tag, msg);
//    }

    public static boolean isEmpty(String s) {
        return s == null || s.equals("");
    }
}
