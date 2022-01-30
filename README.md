# CursoUdemyJavaSpringBoot
Projeto Curso Java SpringBoot + Ionic


-Criar a classe de domínio
-Criar o recurso (ex: categoria), essa classa dever ser anotada com RestController, e RequestMapping(value="/qualquerCoisa").
-Os métodos do recurso devem ser anotados com RequestMapping(method = RequestMethod.GET/POST/PUT/etc)

-Criacao do H2
-Nas dependências
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
<groupId>com.h2database</groupId>
<artifactId>h2</artifactId>
<scope>runtime</scope>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-devtools</artifactId>
</dependency>

-No application.properties


spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

-Algumas anotações para fazer o mapeamento da classe.

@Entity
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
