# ServPro API

API desenvolvida com arquitetura hexagonal (Ports and Adapters) usando Spring Boot 3.4.1, Java 21 e Maven 4.

## 🏗️ Arquitetura Hexagonal

A aplicação está organizada seguindo os princípios da arquitetura hexagonal:

### 📦 Estrutura de Pacotes

```
com.servpro.api
├── domain                          # Camada de Domínio (Núcleo)
│   ├── model                       # Entidades de negócio
│   └── port
│       ├── in                      # Portas de entrada (casos de uso)
│       └── out                     # Portas de saída (interfaces)
├── application                     # Camada de Aplicação
│   └── service                     # Implementação dos casos de uso
└── infrastructure                  # Camada de Infraestrutura (Adaptadores)
    ├── adapter
    │   ├── in
    │   │   └── rest                # Adaptador REST (Controllers)
    │   │       ├── dto             # DTOs de requisição/resposta
    │   │       └── mapper          # Mapeadores REST
    │   └── out
    │       └── persistence         # Adaptador de persistência
    │           ├── entity          # Entidades JPA
    │           └── mapper          # Mapeadores de persistência
    └── config
        └── exception               # Configuração de exceções
```

### 🎯 Camadas

#### Domain (Domínio)
- **Entidades de negócio**: Contêm as regras de negócio puras
- **Portas**: Interfaces que definem contratos
  - `in`: Casos de uso (o que a aplicação faz)
  - `out`: Repositórios e serviços externos (como os dados são acessados)
- **Sem dependências** de frameworks externos

#### Application (Aplicação)
- Implementa os casos de uso definidos nas portas de entrada
- Orquestra o domínio usando as portas de saída
- Contém lógica de aplicação e transações

#### Infrastructure (Infraestrutura)
- **Adaptadores de entrada**: Controllers REST, mensageria, etc.
- **Adaptadores de saída**: Repositórios JPA, clientes HTTP, etc.
- **Configurações**: Exception handlers, beans, etc.
- Implementa as portas definidas no domínio

## 🚀 Tecnologias

- **Java 21** - Versão LTS mais recente
- **Spring Boot 3.4.1** - Framework web
- **Maven 4** - Gerenciamento de dependências
- **Spring Data JPA** - Persistência de dados
- **H2 Database** - Banco de dados em memória (desenvolvimento)
- **Lombok** - Redução de código boilerplate
- **Jakarta Validation** - Validação de dados

## 📋 Pré-requisitos

- JDK 21 ou superior
- Maven 4 ou superior

## 🔧 Configuração

### Banco de Dados H2

A aplicação está configurada para usar H2 em memória. Para acessar o console:

```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:servprodb
Username: sa
Password: (deixe em branco)
```

## ▶️ Executando a Aplicação

```bash
mvn clean install
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

## 📡 Endpoints da API

### Produtos

- **POST** `/api/v1/products` - Criar produto
- **GET** `/api/v1/products` - Listar todos os produtos
- **GET** `/api/v1/products/{id}` - Buscar produto por ID
- **PUT** `/api/v1/products/{id}` - Atualizar produto
- **DELETE** `/api/v1/products/{id}` - Deletar produto
- **PATCH** `/api/v1/products/{id}/stock?quantity={qty}` - Atualizar estoque

### Exemplo de Request (POST)

```json
{
  "name": "Notebook Dell",
  "description": "Notebook Dell Inspiron 15",
  "price": 3500.00,
  "stock": 10
}
```

### Exemplo de Response

```json
{
  "id": 1,
  "name": "Notebook Dell",
  "description": "Notebook Dell Inspiron 15",
  "price": 3500.00,
  "stock": 10,
  "available": true,
  "createdAt": "2026-01-07T10:30:00",
  "updatedAt": "2026-01-07T10:30:00"
}
```

## 🎯 Benefícios da Arquitetura Hexagonal

1. **Independência de frameworks**: O domínio não conhece Spring, JPA, etc.
2. **Testabilidade**: Fácil criar testes unitários do domínio
3. **Flexibilidade**: Trocar implementações sem afetar o domínio
4. **Manutenibilidade**: Código organizado e com responsabilidades claras
5. **Evolução**: Fácil adicionar novos adaptadores (GraphQL, gRPC, etc.)

## 📝 Próximos Passos

- Adicionar testes unitários e de integração
- Implementar autenticação e autorização
- Adicionar documentação com Swagger/OpenAPI
- Configurar banco de dados PostgreSQL/MySQL
- Implementar cache com Redis
- Adicionar observabilidade (logs, métricas, traces)

## 📄 Licença

Este projeto é um exemplo educacional de arquitetura hexagonal.
