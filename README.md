# Aplicação Java RMI de Gerenciador de Arquivos

Este é um exemplo simples de uma aplicação Java RMI que demonstra um sistema de Gerenciamento de Arquivos Remotos. A aplicação consiste em um servidor que permite o upload, download, exclusão e listagem de arquivos e um cliente que interage com o servidor.

## Pré-requisitos

Certifique-se de ter o seguinte instalado e configurado em seu ambiente de desenvolvimento:

- JDK (Java Development Kit)
- Java RMI
- IDE Java (opcional)

## Como usar

Siga estas etapas para executar a aplicação:

### 1. Iniciar o Servidor

1. Abra um terminal.
2. Navegue até o diretório onde os arquivos da aplicação estão localizados.
3. Compile o servidor executando o seguinte comando:

   ```
   javac FileServer.java
   ```

4. Inicie o servidor executando o seguinte comando:

   ```
   java FileServer
   ```

O servidor estará pronto para receber solicitações do cliente.

### 2. Iniciar o Cliente

1. Abra um novo terminal (ou outra janela).
2. Navegue até o mesmo diretório onde os arquivos da aplicação estão localizados.
3. Compile o cliente executando o seguinte comando:

   ```
   javac FileClient.java
   ```

4. Inicie o cliente executando o seguinte comando:

   ```
   java FileClient
   ```

O cliente será iniciado e você verá um menu com opções para listar arquivos, fazer upload, fazer download, excluir arquivos e sair.

### 3. Interagir com a Aplicação

- Use o cliente para listar os arquivos no servidor, fazer upload de arquivos, fazer download de arquivos e excluir arquivos conforme necessário.
- Siga as instruções fornecidas pelo cliente para realizar essas operações.

Lembre-se de que você pode executar o servidor e o cliente em máquinas diferentes na mesma rede ou em sua máquina local para fins de teste.

## Contato

- e-mail: ademar.castro.curriculo@gmail.com