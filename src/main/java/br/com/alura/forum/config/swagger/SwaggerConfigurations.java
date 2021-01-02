package br.com.alura.forum.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.alura.forum.modelo.Usuario;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

//http://localhost:8080/swagger-ui.html
@Configuration
public class SwaggerConfigurations {

	@Bean //exportamos o bean do tipo Docket para injecao
	public Docket forumApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.ignoredParameterTypes(Usuario.class)//ignora todas as urls que trabalham com a classe usuario, nao deve ser mostrado na documentacao
				.globalOperationParameters(//esse parametro vai ser exibido no swagger em todos os endpoints
                        Arrays.asList(//passo a lista de parametros
                                new ParameterBuilder()//construcao do parametro global
                                    .name("Authorization")
                                    .description("Header para Token JWT")
                                    .modelRef(new ModelRef("string"))//o tipo de parametro que e string
                                    .parameterType("header")//cabecalho
                                    .required(false)//nao e required em todos
                                    .build()));
	}
	
}
