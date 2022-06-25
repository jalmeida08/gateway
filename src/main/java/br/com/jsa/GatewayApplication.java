package br.com.jsa;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {


	@Value("${app.protocolo}")
	private String protocolo;

	@Value("${app.oauth.url}")
	private String oauthUrl;
	@Value("${app.oauth.port}")
	private String oauthPort;
	
	@Value("${app.usuario.url}")
	private String usuarioUrl;
	@Value("${app.usuario.port}")
	private String usuarioPort;
	
	@Value("${app.cadastroBasico.url}")
	private String cadastroBasicoUrl;
	@Value("${app.cadastroBasico.port}")
	private String cadastroBasicoPort;
	
	@Value("${app.studio.url}")
	private String studioUrl;
	@Value("${app.studio.port}")
	private String studioPort;
	
	
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
	
	@Bean
	public RouteLocator rotas(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("autenticacao_route", r -> r
						.path("/autenticacao/**")
						.filters( f-> f.rewritePath("/autenticacao", ""))
						.uri(protocolo+"://"+oauthUrl+":"+oauthPort))
				.route("usuario_route", r -> r
						.path("/usuario/**")
						.filters( f-> f.rewritePath("/usuario", ""))
						.uri(protocolo+"://"+usuarioUrl+":"+usuarioPort))
				.route("cadastro_basico_route", r -> r
					.path("/cadastro-basico/**")
					.filters( f-> f.rewritePath("/cadastro-basico", "")
							.setRequestHeader("Accept", MediaType.APPLICATION_JSON)
							.setRequestHeader("Content-type", MediaType.APPLICATION_JSON))
					.uri(protocolo+"://"+cadastroBasicoUrl+":"+cadastroBasicoPort))
				.route("studio_route", r -> r
						.path("/studio/**")
						.filters( f-> f.rewritePath("/studio", "")
								.setRequestHeader("Accept", MediaType.APPLICATION_JSON)
								.setRequestHeader("Content-type", MediaType.APPLICATION_JSON))
						.uri(protocolo+"://"+studioUrl+":"+studioPort))
				.build();
	}

}
