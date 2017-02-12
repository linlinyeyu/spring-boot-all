/**
 * 
 */
package com.ibenben.config.swagger;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author sszheng
 *
 * Created on 2016年7月12日 下午12:00:17
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements EnvironmentAware{
	private RelaxedPropertyResolver propertyResolver;
	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment, "swagger.");
	}
	 
    @Bean
    public Docket goodsApi() {
    	return getDocket("goods","/goods/.*","oms商品档案相关接口");
    }
    
    @Bean
    public Docket testApi() {
    	return getDocket("test","/test/.*","测试相关接口");
    }
    
    @Bean
    public Docket userApi() {
    	return getDocket("user","/user/.*","用户相关接口");
    }
    
    @Bean
    public Docket productApi() {
        return getDocket("product","/product/.*","product相关接口");
    }

    @Bean
    public Docket shippingApi() {
    	return getDocket("shipping","/shipping/.*","shipping接口");
    }
    
    @Bean
    public Docket merchantApi() {
    	return getDocket("merchant","/merchant/.*","merchant接口");
    }
    
    @Bean
    public Docket ProductSupplierApi() {
    	return getDocket("productSupplier","/product/supplier/.*","productSupplier接口");
    }
    
    @Bean
    public Docket TaxRateApi() {
    	return getDocket("taxRate","/tax/rate/.*","taxRate接口");
    }
    
    @Bean
    public Docket PurchasePlaceApi() {
    	return getDocket("purchasePlace","/purchase/place/.*","purchasePlace接口");
    }
    @Bean
    public Docket platformApi() {
    	return getDocket("platform","/platform/.*","platform接口");
    }
    @Bean
    public Docket commonApi() {
    	return getDocket("common","/common/.*","common接口");
    }
    
    @Bean
    public Docket aRegionApi() {
    	return getDocket("api_region","/api/region/.*","region对外接口");
    }
    
    @Bean
    public Docket aProductContainerApi() {
    	return getDocket("api_product_container","/api/productcontainer/.*","箱规对外接口");
    }
    
    @Bean
    public Docket facilityApi() {
    	return getDocket("facility","/facility/.*","facility接口");
    }
    
    @Bean
    public Docket facilityShippingApi() {
    	return getDocket("facilityShipping","/facility/shipping/.*","facilityShipping接口");
    }
    
    private Docket getDocket(String groupName, String regexValue, String apiInfo) {
		return new Docket(DocumentationType.SWAGGER_2)
        		.host(propertyResolver.getProperty("host"))
				.groupName(groupName)
		        .genericModelSubstitutes(DeferredResult.class)
		        .genericModelSubstitutes(ResponseEntity.class)
		        .useDefaultResponseMessages(false)
		        .forCodeGeneration(true)
		        .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
		        .select()
		        .paths(PathSelectors.regex(regexValue))//过滤的接口
		        .build()
		        .apiInfo(apiInfo(apiInfo));
	}
    
    private ApiInfo apiInfo(String title) {
    	Contact contact = new Contact(propertyResolver.getProperty("contact"), propertyResolver.getProperty("url"), propertyResolver.getProperty("email"));
    	return new ApiInfo(
    			title,//大标题
                propertyResolver.getProperty("description"),//小标题
                propertyResolver.getProperty("version"),//版本
                propertyResolver.getProperty("termsOfServiceUrl"),
                contact,//作者
                propertyResolver.getProperty("license"),//链接显示文字
                propertyResolver.getProperty("licenseUrl") //网站链接
        );
    }

}