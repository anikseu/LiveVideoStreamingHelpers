// Authentication System Implementation of Nimble-WMSPanel WMSAuth PayWall 
// This code uses java.util.Base64 library instead of very old Apache Common Codec Library

package testruns;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class WMSAuth {

    public static String getFinalURL(String url, String ipAddress, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("M/d/y h:m:s a").withZone(DateTimeZone.UTC).withLocale(Locale.US);
        DateTime currentServerTime = new DateTime(DateTimeZone.UTC); // lets get localtime in UTC timezone
        String today = timeFormatter.print(currentServerTime);
        String video_url = url;
        String ip = ipAddress;
        String key = password;
        String validminutes = "20";
        String to_hash = ip + key + today + validminutes;
        byte[] ascii_to_hash = to_hash.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        String base64hash = Base64.getEncoder().encodeToString(md.digest(ascii_to_hash));
        String urlsignature = "server_time=" + today + "&hash_value=" + base64hash + "&validminutes=" + validminutes;
        String base64urlsignature = Base64.getEncoder().encodeToString(urlsignature.getBytes("UTF-8"));
        String finalURL = video_url + "?wmsAuthSign=" + base64urlsignature;
        
        return finalURL; 
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
        String URL = getFinalURL("http://xxxx.xx/test/anik/playlist.m3u8", 
                                  "xxx.xxx.xxx.xxx", "password"); 
        
        System.out.println(URL); 
        
    }

}
