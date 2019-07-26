package com.maven;

import com.moandjiezana.toml.Toml;

import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String str = "[bucketname]\n" +
                "中=\"jmhtml-dev\"\n" +
                "jumeicdb=\"jumeicdb-devcd\"\n" +
                "jumeicdb_enc=\"jumeicdb-devcd-enc\"\n" +
                "jdhd=\"jiedianhd-prod\"\n" +
                "#test123=\"jmfiletest-enc\"";
        Map<String, Object> map = new Toml().read(str).toMap();
        System.out.println(map.get("中"));
    }
}
