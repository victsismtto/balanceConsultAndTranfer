# balanceConsultTransfer

Poposta: clientes consigam realizar as operações de Consulta de Saldo e Transferência entre contas.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Configuration](#configuration)
- [Contributing](#contributing)

## Getting Started

Para usar o projeto, é recomendado entender um pouco de sprinboot, linguagem Java e ter uma IDEA para poder visualizar bem o código e a hierarquia de pastas.

### Prerequisites

List the software and tools that need to be installed before setting up the project.

- Java (JDK 17 or higher)
- Maven (3.8.1 or higher)
- MongoDB

### Installation

Para chamar todos os endpoints dessa aplicação, precisa baixar o MockCadastro e MockBacen e rodar todos eles localmente, principalmente para o PATCH.

1. Clone the repository:

   git clone https://github.com/victsismtto/balanceConsultAndTranfer.git
   
   git clone https://github.com/victsismtto/APIMockCadastro
   
  git clone https://github.com/victsismtto/APIMockBacen.git


## configuration

No mongoDB, criar uma DataBAse chamada "ApiDatabase" com as collections: "Accounts", "Cadastro", "PersistenceAccounts" e depois de criar uma DataBase sua, trocar o "usernam" e "password" pelas suas.

No postman, criar os endpoints com os contexts path de cada API, sugiro separar por collection, assim fica possivel fazer as chamadas localmente.

## Contributing

Victor Soares Sismotto
