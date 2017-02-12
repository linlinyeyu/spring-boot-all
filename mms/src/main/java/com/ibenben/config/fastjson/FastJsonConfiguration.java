package com.ibenben.config.fastjson;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;


@Configuration
@ConditionalOnClass({JSON.class})
public class FastJsonConfiguration {
	@Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){
		FastJsonHttpMessageConverterEx fastConverter = new FastJsonHttpMessageConverterEx();
        
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(supportedMediaTypes);
        
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
        		SerializerFeature.PrettyFormat,
        		SerializerFeature.WriteMapNullValue,
        		SerializerFeature.WriteNullStringAsEmpty,
        		SerializerFeature.WriteNullListAsEmpty,
        		SerializerFeature.WriteDateUseDateFormat
        );
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }
	


//	@Bean
//	public Filter characterEncodingFilter() {
//	CharacterEncodingFilter characterEncodingFilter =new CharacterEncodingFilter();
//	characterEncodingFilter.setEncoding("UTF-8");
//	characterEncodingFilter.setForceEncoding(true);
//	return characterEncodingFilter;
//	}

}
