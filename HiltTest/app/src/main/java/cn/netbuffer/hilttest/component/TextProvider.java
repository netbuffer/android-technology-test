package cn.netbuffer.hilttest.component;

import org.apache.commons.lang3.RandomStringUtils;

import javax.inject.Inject;

public class TextProvider {

    @Inject
    public TextProvider() {
    }

    public String create(int limit) {
        return RandomStringUtils.random(limit, true, false);
    }

}