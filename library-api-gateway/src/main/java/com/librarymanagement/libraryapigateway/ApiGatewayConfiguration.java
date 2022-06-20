package com.librarymanagement.libraryapigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	/*
	@Bean
	public RouteLocator getwayRouter(RouteLocatorBuilder builder) {
		Function<PredicateSpec, Buildable<Route>> routeFunction
			= p -> p.path("/get")
					.filters(
							f -> 
								f.addRequestHeader("MyHeader", "MyURI")
								 .addRequestParameter("Param", "MyValue")
							)
					.uri("http://httpbin.org:80");
						
		return builder.routes().route(routeFunction).build();
	} */
	
	@Bean
	public RouteLocator getwayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/get")
						.filters(f -> f.addRequestHeader("MyHeader", "MYURL"))
		 				.uri("http://httpbin.org:80")
					)
				.route(p -> p.path("/lbm/users/**")
						.uri("lb://library-users/")
					)
				.route(p -> p.path("/lbm/libraryuseraccounts/**")
						.uri("lb://library-users-accounts")
					)
				.route(p -> p.path("/lbm/librarybooks/**")
						.uri("lb://library-books")
					)
				.route(p -> p.path("/lbm/libraryreservations/**")
						.uri("lb://library-reservations")
					)
				.build();
	}
	
}
