package com.rxandroiddemo.api.cookie;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.internal.Internal;

import static java.util.logging.Level.WARNING;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/5 14:32
 * @Description
 */

public class JavaCookieJar implements CookieJar {
    private final CookieHandler cookieHandler;

    public JavaCookieJar(CookieHandler cookieHandler) {
        this.cookieHandler = cookieHandler;
    }

    @Override public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookieHandler != null) {
            List<String> cookieStrings = new ArrayList<>();
            for (Cookie cookie : cookies) {
                cookieStrings.add(cookie.toString());
            }
            Map<String, List<String>> multimap = Collections.singletonMap("Set-Cookie", cookieStrings);
            try {
                cookieHandler.put(url.uri(), multimap);
            } catch (IOException e) {
                Internal.logger.log(WARNING, "Saving cookies failed for " + url.resolve("/..."), e);
            }
        }
    }

    @Override public List<Cookie> loadForRequest(HttpUrl url) {
        // The RI passes all headers. We don't have 'em, so we don't pass 'em!
        Map<String, List<String>> headers = Collections.emptyMap();
        Map<String, List<String>> cookieHeaders;
        try {
            cookieHeaders = cookieHandler.get(url.uri(), headers);
        } catch (IOException e) {
            Internal.logger.log(WARNING, "Loading cookies failed for " + url.resolve("/..."), e);
            return Collections.emptyList();
        }

        List<Cookie> cookies = null;
        for (Map.Entry<String, List<String>> entry : cookieHeaders.entrySet()) {
            String key = entry.getKey();
            if (("Cookie".equalsIgnoreCase(key) || "Cookie2".equalsIgnoreCase(key))
                    && !entry.getValue().isEmpty()) {
                for (String header : entry.getValue()) {
                    if (cookies == null) cookies = new ArrayList<>();
                    cookies.addAll(decodeHeaderAsJavaNetCookies(url, header));
                }
            }
        }

        return cookies != null
                ? Collections.unmodifiableList(cookies)
                : Collections.<Cookie>emptyList();
    }

    /**
     * Convert a request header to OkHttp's cookies via {@link HttpCookie}. That extra step handles
     * multiple cookies in a single request header, which {@link Cookie#parse} doesn't support.
     */
    private List<Cookie> decodeHeaderAsJavaNetCookies(HttpUrl url, String header) {
        List<HttpCookie> javaNetCookies;
        try {
            javaNetCookies = HttpCookie.parse(header);
        } catch (IllegalArgumentException e) {
            // Unfortunately sometimes java.net gives a Cookie like "$Version=1" which it can't parse!
            Internal.logger.log(WARNING, "Parsing request cookie failed for " + url.resolve("/..."), e);
            return Collections.emptyList();
        }
        List<Cookie> result = new ArrayList<>();
        for (HttpCookie javaNetCookie : javaNetCookies) {
            result.add(new Cookie.Builder()
                    .name(javaNetCookie.getName())
                    .value(javaNetCookie.getValue())
                    .domain(url.host())
                    .build());
        }
        return result;
    }
}
