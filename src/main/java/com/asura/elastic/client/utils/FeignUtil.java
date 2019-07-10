package com.asura.elastic.client.utils;


import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.codec.StringDecoder;

public class FeignUtil {

    public static <T> T getTarget(Class<T> clazz, String url) {
        T target = Feign.builder().decoder(new StringDecoder())
                                  //.encoder(new GsonEncoder())
                                  //.decoder(new GsonDecoder())
                                  .options(new Request.Options(1000, 5000))
                                  .logger(new feign.Logger.ErrorLogger())
                                  .retryer(new Retryer.Default(5000, 5000, 3))
                                  .target(clazz, url);
        return target;
    }


}
