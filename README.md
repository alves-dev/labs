# Labs
O projeto consiste em receber um arquivo `.txt` desnormalizado contendo registros de vendas.
Após isso deve ser possível filtrar os pedidos que devem ser retornados em formato `json` adequando de acordo com o requisito.


### Das tecnologias escolhidas
- [Spring Boot](https://spring.io/projects/spring-boot)
  O eco sistema Spring foi escolhido pela facilidade que ele oferece para lidar com as outras ferramentas a seguir,
  e por ser um ótimo framework.

- [Java](https://dev.java/) O java por sua vez uma das melhores linguagens de se trabalhar que se encaixa bem com o Spring.

- [Mongo](https://www.mongodb.com/) Para o armazenamento optei pelo Mongo, um banco orientado a documentos,
  isso faz com que o mesmo seja muito dinâmico e rápido para iniciar um projeto.

- [Redis](https://redis.io/) Para ajudar na evolução do produto, prevendo que o mesmo vai receber muitas requisições,
  foi aplicada uma camada de cache com o Redis, aproveitando o ótimo suporte fornecido pelo Spring,
  Redis é um banco chave-valor que pode armazenar seus dados em RAM o que o torna muito rápido e acaba sendo ideal para caches.


## Como executar o projeto
O projeto foi configurado para ser executado sobre o docker, mais especificamente o docker compose.
Sendo assim basta você executar o script `build_up.sh` que o build da aplicação sera feito e o ambiente sera criado.
```sh
sh build_up.sh
```

### Após executar o comando anterior
 - Acesse o [Swagger](http://localhost:8080/swagger-ui/index.html) 
 - **Observação**: Ao executar o `GET` items sem filtros o swagger demora a mostrar os dados pelo fato do tamanho do retorno,
   o que leva a uma falsa demora no end-point, recomendo fazer essa requisição fora do swagger.
 - Caso queira ver os registros no banco mongo, você pode acessar pelo [Mongo express](http://localhost:8888/)


## Testes e Coverage
 - [JUnit](https://junit.org/junit5/) utlizado para execução dos testes juntamente com [mockito](https://site.mockito.org/)
 - [JaCoCo](https://www.eclemma.org/jacoco/) gera um relatório de cobertura de testes
 - Após executar `./gradlew test` você pode acessar a [pagina](http://localhost:63342/labs/build/reports/jacoco/test/html/index.html)
que é gerada pelo JaCoCo.


## Possíveis melhorias e evoluções
 - O processamento do arquivo de entrada pode ser realizado em segundo plano, 
assim podemos validar o aquivo e retornar uma resposta liberando a requisição.
 - Uma outra possível estrategia seria realizar o processo de normalização e a soma dos pedidos na leitura do aquivo,
   o que reduziria o processo no `GET` do items.