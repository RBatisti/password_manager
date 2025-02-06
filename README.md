# Password Manager

## Pré-requisitos

1. **Banco de Dados MySQL**: O projeto usa o MySQL para armazenar dados. Siga os passos abaixo para configurar o banco de dados:

## Configuração do Banco de Dados

### Passo 1: Criação do Banco de Dados
- Abra o MySQL ou outro cliente SQL de sua escolha.
- Execute o script SQL fornecido na pasta `scrypt/create_db.sql` para criar o banco de dados e as tabelas.

### Passo 2: Configuração do `db.properties`
- Certifique-se de configurar corretamente a conexão com o banco de dados no arquivo `db.properties` na pasta `src/main/java/config`.

Exemplo de configuração:

```properties
user=seu_usuario
password=sua_senha
dburl=jdbc:mysql://localhost:3306/password_manager
```
