warning: LF will be replaced by CRLF in pom.xml.
The file will have its original line endings in your working directory
warning: LF will be replaced by CRLF in src/main/java/br/com/alura/forum/ForumApplication.java.
The file will have its original line endings in your working directory
warning: LF will be replaced by CRLF in src/main/resources/application.properties.
The file will have its original line endings in your working directory
[1mdiff --git a/pom.xml b/pom.xml[m
[1mindex 686597d..e0eda77 100644[m
[1m--- a/pom.xml[m
[1m+++ b/pom.xml[m
[36m@@ -49,6 +49,12 @@[m
     		<groupId>org.springframework.boot</groupId>[m
     		<artifactId>spring-boot-starter-validation</artifactId>[m
 		</dependency>[m
[32m+[m[41m		[m
[32m+[m		[32m<dependency>[m
[32m+[m[41m    [m		[32m<groupId>org.springframework.boot</groupId>[m
[32m+[m[41m    [m		[32m<artifactId>spring-boot-starter-cache</artifactId>[m
[32m+[m		[32m</dependency>[m
[32m+[m[41m		[m
 	</dependencies>[m
 [m
 	<build>[m
[1mdiff --git a/src/main/java/br/com/alura/forum/ForumApplication.java b/src/main/java/br/com/alura/forum/ForumApplication.java[m
[1mindex de13403..e9cf91c 100644[m
[1m--- a/src/main/java/br/com/alura/forum/ForumApplication.java[m
[1m+++ b/src/main/java/br/com/alura/forum/ForumApplication.java[m
[36m@@ -3,11 +3,13 @@[m [mpackage br.com.alura.forum;[m
 import org.springframework.boot.SpringApplication;[m
 import org.springframework.boot.autoconfigure.SpringBootApplication;[m
 import org.springframework.boot.autoconfigure.domain.EntityScan;[m
[32m+[m[32mimport org.springframework.cache.annotation.EnableCaching;[m
 import org.springframework.data.web.config.EnableSpringDataWebSupport;[m
 [m
 @SpringBootApplication[m
 @EntityScan("br.com.alura.forum.modelo")[m
 @EnableSpringDataWebSupport //habilita receber os parametros de paginacao na url[m
[32m+[m[32m@EnableCaching[m
 public class ForumApplication {[m
 [m
 	public static void main(String[] args) {[m
[1mdiff --git a/src/main/java/br/com/alura/forum/controller/TopicosController.java b/src/main/java/br/com/alura/forum/controller/TopicosController.java[m
[1mindex bf450dd..7f597ff 100644[m
[1m--- a/src/main/java/br/com/alura/forum/controller/TopicosController.java[m
[1m+++ b/src/main/java/br/com/alura/forum/controller/TopicosController.java[m
[36m@@ -8,6 +8,8 @@[m [mimport javax.transaction.Transactional;[m
 import javax.validation.Valid;[m
 [m
 import org.springframework.beans.factory.annotation.Autowired;[m
[32m+[m[32mimport org.springframework.cache.annotation.CacheEvict;[m
[32m+[m[32mimport org.springframework.cache.annotation.Cacheable;[m
 import org.springframework.data.domain.Page;[m
 import org.springframework.data.domain.PageRequest;[m
 import org.springframework.data.domain.Pageable;[m
[36m@@ -45,6 +47,7 @@[m [mpublic class TopicosController {[m
 	private CursoRepository cursoRepository;[m
 	[m
 	@GetMapping[m
[32m+[m	[32m@Cacheable(value = "listaDeTopicos")[m
 	//@ResponseBody com a anotacao do RestController o Spring identifica que todos os metodos vão ter o responseBody[m
 	//DTO - Usamos o padrao para quando os dados saem para o cliente[m
 	public Page<TopicoDto> lista([m
[36m@@ -92,6 +95,7 @@[m [mpublic class TopicosController {[m
 	@PostMapping	[m
 	@Transactional //necessario para atualizar no banco - devemos colocar em todo metodo que tiver uma acao de escrita[m
 	//uriBuilder e injetado pelo Spring para nos[m
[32m+[m	[32m@CacheEvict(value = "listaDeTopicos", allEntries = true) //ele limpa o cache quando este metodo for chamado[m
 	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {[m
 		Topico topico = form.converter(cursoRepository);[m
 		topicoRepository.save(topico);[m
[36m@@ -115,6 +119,7 @@[m [mpublic class TopicosController {[m
 	[m
 	@PutMapping("/{id}")[m
 	@Transactional //necessario para atualizar no banco - devemos colocar em todo metodo que tiver uma acao de escrita[m
[32m+[m	[32m@CacheEvict(value = "listaDeTopicos", allEntries = true) //ele limpa o cache quando este metodo for chamado[m
 	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form){[m
 		Optional<Topico> optional = topicoRepository.findById(id); [m
 		[m
[36m@@ -129,6 +134,7 @@[m [mpublic class TopicosController {[m
 	[m
 	@DeleteMapping("/{id}")[m
 	@Transactional //necessario para atualizar no banco - devemos colocar em todo metodo que tiver uma acao de escrita[m
[32m+[m	[32m@CacheEvict(value = "listaDeTopicos", allEntries = true) //ele limpa o cache quando este metodo for chamado[m
 	public ResponseEntity<?> remover(@PathVariable Long id){[m
 		Optional<Topico> topico = topicoRepository.findById(id); [m
 		[m
[1mdiff --git a/src/main/resources/application.properties b/src/main/resources/application.properties[m
[1mindex 48fb5f8..b7ecb76 100644[m
[1m--- a/src/main/resources/application.properties[m
[1m+++ b/src/main/resources/application.properties[m
[36m@@ -8,6 +8,9 @@[m [mspring.datasource.password=[m
 # jpa[m
 spring.jpa.database-platform=org.hibernate.dialect.H2Dialect[m
 spring.jpa.hibernate.ddl-auto=update[m
[32m+[m[32mspring.jpa.properties.hibernate.show_sql=true[m
[32m+[m[32mspring.jpa.properties.hibernate.format_sql=true[m
[32m+[m
 [m
 # h2 - Interface grafica que acessamos pelo brownser[m
 spring.h2.console.enabled=true[m
