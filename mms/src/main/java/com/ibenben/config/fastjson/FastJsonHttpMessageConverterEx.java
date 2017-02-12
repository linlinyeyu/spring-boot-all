/**
 * 
 */
package com.ibenben.config.fastjson;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ibenben.config.swagger.SwaggerJsonSerializer;

import springfox.documentation.spring.web.json.Json;

/**
 * @author sszheng
 *
 * Created on 2016年7月26日 下午12:02:51
 */
public class FastJsonHttpMessageConverterEx extends FastJsonHttpMessageConverter {

    public FastJsonHttpMessageConverterEx() {
        super();
        this.getFastJsonConfig().getSerializeConfig().put(Json.class, SwaggerJsonSerializer.instance);
    }

}