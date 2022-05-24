package br.com.jsa;

import javax.ws.rs.core.MediaType;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
	
	@Bean
	public RouteLocator rotas(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("autenticacao_route", r -> r
						.path("/autenticacao/**")
						.filters( f-> f.rewritePath("/autenticacao", ""))
						.uri("http://localhost:8088"))
				.route("usuario_route", r -> r
						.path("/usuario/**")
						.filters( f-> f.rewritePath("/usuario", ""))
						.uri("http://localhost:8081"))
				.route("cadastro_basico_route", r -> r
					.path("/cadastro-basico/**")
					.filters( f-> f.rewritePath("/cadastro-basico", "")
							.setRequestHeader("Accept", MediaType.APPLICATION_JSON)
							.setRequestHeader("Content-type", MediaType.APPLICATION_JSON))
					.uri("http://localhost:8082"))
				.route("studio_route", r -> r
						.path("/studio/**")
						.filters( f-> f.rewritePath("/studio", "")
								.setRequestHeader("Accept", MediaType.APPLICATION_JSON)
								.setRequestHeader("Content-type", MediaType.APPLICATION_JSON))
						.uri("http://localhost:8083"))
				.build();
	}

}
