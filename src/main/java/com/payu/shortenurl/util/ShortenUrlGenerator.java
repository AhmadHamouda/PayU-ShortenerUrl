package com.payu.shortenurl.util;

import com.payu.shortenurl.dao.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@Component
public class ShortenUrlGenerator {
    private static Random random = new Random((new Date()).getTime());
    @Autowired
    UrlRepository urlRepository;
    @Value("${shorten.length}")
    private int shortenLength;

    public String generateShorten(String url) {


        char[] values = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
                '4', '5', '6', '7', '8', '9'};
        StringBuilder out = new StringBuilder("");
        while (isShortenUrlExist(out) || out.length()==0) {
            out = new StringBuilder("");
            for (int i = 0; i < shortenLength; i++) {
                int idx = random.nextInt(values.length);
                out.append(values[idx]);
            }
        }
        return out.toString();
    }

    private boolean isShortenUrlExist(StringBuilder out) {
        return urlRepository.getByShortenUrl(out.toString()) != null;
    }
}
