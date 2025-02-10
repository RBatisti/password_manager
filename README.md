# Password Manager

## 📌 Sobre o Projeto
O **Password Manager** é um gerenciador de senhas desenvolvido em **Java** com **JavaFX** para a interface gráfica. Ele permite armazenar senhas de forma criptografada no banco de dados **MySQL**, utilizando **AES** para criptografia de logins e **SCrypt** para gerar hash da senha e email do usuário.

## 🚀 Funcionalidades
- Criação de diversos usuários independentes.
- Armazenamento das senhas criptografadas.
- Editar e excluir senhas cadastradas.

## 🛠️ Tecnologias Utilizadas
- **Java**
- **JavaFX** (para a interface gráfica)
- **MySQL** (para armazenar os dados)
- **JDBC** (para conexão com o banco de dados)
- **Bouncy Castle** (para criptografia)
- **Maven** (para gerenciamento de dependências)

## ⚠️ Pré-requisitos

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

