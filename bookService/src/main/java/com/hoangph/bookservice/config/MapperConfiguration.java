package com.hoangph.bookservice.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    @Bean
    public ObjectMapper getMapper(){
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();

        xStream.allowTypesByWildcard(new String[] { "org.springframework.data.domain.**", "com.hoangph.bookservice.**" });
        return xStream;
    }
}
