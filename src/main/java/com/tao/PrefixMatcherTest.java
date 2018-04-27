package com.tao;

import java.util.Iterator;
import java.util.ServiceLoader;


/**
 * @Author TAO
 * @Date 2018/4/27 11:07
 */
public class PrefixMatcherTest {
    public static void main(String[] args) {
        ServiceLoader<PrefixMatcher> matcher = ServiceLoader.load(PrefixMatcher.class);
        Iterator<PrefixMatcher> matcherIter = matcher.iterator();
        while (matcherIter.hasNext()) {
            PrefixMatcher wordMatcher = matcherIter.next();
            System.out.println(wordMatcher.getClass().getName());
            String[] prefixes = new String[] {"a"};
            for (String prefix: prefixes) {
                wordMatcher.hello(prefix);
            }
        }
    }
}
