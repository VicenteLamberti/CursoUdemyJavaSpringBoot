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
	
	
-Nas classes de repository/services, quando for buscar por Id ao invés de utilizar o findOne(id). Usar o seguinte método.

ATUALIZAÇÃO
projeto usando Spring Boot versão 2.x.x:

Em CategoriaService, onde na aula é mostrado:
public Categoria find(Integer id) {
Categoria obj = repo.findOne(id);
return obj;
}
Troque pelo seguinte código (import java.util.Optional):
public Categoria find(Integer id) {
Optional<Categoria> obj = repo.findById(id);
return obj.orElse(null);
}
	
-Classes de repository, devem ser separados em um pacote separado, exemplo: repositories.
-Essas classes devem ser anotadas de Repository
-Porém os repositories não são classes, e sim interfaces, que devem extender JpaRepository<ClasseDomain,TipoPrimitivoDaChaveId>
	
-Para criar classes que são do tipo Services (que utilizarão os repositories), devem ser anotadas de Service.
-É necessário criar um atributo/dependência private do repository (CategoriaRepository) e deve ser anotado com Autowired, para que seja instanciado por injeção de depêndencia.
	
-No resource, é necessário fazer alterações, para que agora seja possível buscar um id. Primeiramente o método deve receber um Integer id
-O método deve possuir na anotacão RequestMapping, um novo parâmetro (value="/{apelidoGeralmenteid}",method=RequestMethod.GET)
-E no parâmetro do método, para associar o id da requisição, deve ser colocado uma anotação find(PathVariable Integer id)
-Para ficar mais técnico, ao invés do tipo do retorno ser o próprio objeto, deve-se retornar um ResponseEntity<?>
-Para se utilizar o Service que irá buscar os dados dos repositories, é necessário criar um atributo private CategoriaService service, com a anotação Autowired

-O retorno pode ser assim:
	Categoria obj = service.buscar(id);
	return ResponseEntity.ok().body(obj);

-Dessa forma, já ira retornar o status 200 e o corpo do objeto.


-ATUALIZAÇÃO

projeto usando Spring Boot versão 2.x.x:


categoriaRepository.save(Arrays.asList(cat1, cat2));
Troque pelo seguinte código:
categoriaRepository.saveAll(Arrays.asList(cat1, cat2));

-Para rodar o comando, assim que executar a aplicação foi necessário alterar a classe aplicattion. Deve implementar CommandLineRunner, e sobreescrever o método run.
-Cria-se os objetos e saveAll através de uma instancia de um repository.

-Dica: PARA SE CRIAR UMA LISTA - repo.saveAll(Arrays.asList(cat1,cat2));


-Criação da entidade Produto
-Anotar com Entity
-Criar o atributo de Categorias 
	@Entity
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private List<Produto>produtos = new ArrayList<>();
	
	
	public Categoria() {
		
	}
	public Categoria(Integer id, String nome) {

		this.id = id;
		this.nome = nome;
	}

As listas não devem estar no construtor e devem ser instanciadas no próprio atributo da classe;


-@JsonManagedReference é preciso ter essa anotação dentro da entidade Categoria, porque senão categoria chama produto, produto chama categoria, e vira um ciclo
-@JsonBackReference é preciso ter essa anotação quando o relacionamento é bidirecional, uma classe vai ter o @JsonManagedReference e outra @JsonBackReference

-Uma classe com a anotaçãoo @ControllerAdvice - para capturar excessoes na controller, sem ficar usando try catch

-Annotations de OneToMany (coleções) , devem ter também o mappedBy="nomeDoAtributoNaOutraClasse"


-Ao criar enuns, colocar a ordem na frente do atributo PESSOAFISICA(1,"PF"). Então criar construtor e get 

-Ao criar os atributos, em vez de ser um ENUM, vai ser um int, mas no construtor vai continuar recebendo um ENUM , ai é só usar o getCod();

-@ElementCollection
	@CollectionTable(name = "TELEFONE")
	private Set<String> telefones = new HashSet<>();

Essas anotações são para o caso de ter um relacionamento, porém não foi criado classe.





-Em relacionamentos OneToOne é interessante os id serem os mesmo. Para isso não anotar com GenerationType.Identity.

Anotar com @MapsId

public class Pagamento implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private EstadoPagamento estado;
	
	@OneToOne
	@JoinColumn(name="pedido_id")
	@MapsId
	private Pedido pedido;


Para colocar herança nas entidades anotar com @Inheritance



Para colocar uma chave primaria composta preciso criar a classe, e colocar os atributos referentes as chaves, colocar get e sets e hashcode equals.

Anotar a classe com @Embeddable , e o atributo usar @EmbeddedId

no construtor ao invés de setar o itemPedidoPK, eu seto as duas classes dentro do id
