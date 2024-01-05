# balanceConsultTransfer

Proposal: customers can carry out Balance Inquiry and Transfer operations between accounts.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Configuration](#configuration)
- [Contributing](#contributing)

## Getting Started

To put into service the project, it is recommended to have a basic understanding of springboot, Java language and have an IDEA to be able to visualize the code and folder hierarchy.

### Prerequisites

List the software and tools that need to be installed before setting up the project.

- Java (JDK 17 or higher)
- Maven (3.8.1 or higher)
- MongoDB

### Installation

To reach all the endpoints of this application, you need to download MockCadastro and MockBacen and run them all locally, mainly for PATCH.

1. Clone the repository:

   git clone https://github.com/victsismtto/balanceConsultAndTranfer.git
   
   git clone https://github.com/victsismtto/APIMockCadastro
   
   git clone https://github.com/victsismtto/APIMockBacen.git


## Configuration

In MongoDB, create a Database called "ApiDatabase" with the collections: "Accounts", "Cadastro", "PersistenceAccounts" and after creating your own DataBase, exchange the "username" and "password" for the ones you have created.

In Postman, create the endpoints with the context path of each API, I suggest separating them by collection, so that it will be possible to make the calls locally.

## Contributing

Victor Soares Sismotto
