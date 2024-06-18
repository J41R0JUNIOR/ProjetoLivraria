# AT3/N2 - Atividade Prática Coletiva - Bimestre N2

## Integrantes
- Jairo Júnior
- Ítalo Santana
- Gabriel de Oliveira Batista
- Guilherme Akio


## Descrição do Projeto

Este projeto foi desenvolvido para a disciplina de Programação, visando a implementação de um servidor e cliente utilizando sockets em Java 17. O servidor é responsável por gerenciar o registro/cadastro de livros de uma biblioteca, oferecendo funcionalidades como listagem, aluguel, devolução e cadastro de livros. As operações realizadas pelo usuário são refletidas em um arquivo JSON que atua como base de dados da biblioteca.

## Funcionalidades

1. **Listagem dos livros**: Exibe todos os livros cadastrados na biblioteca.
2. **Aluguel de livros**: Permite que um usuário alugue um livro.
3. **Devolução de livros**: Permite que um usuário devolva um livro.
4. **Cadastro de livros**: Permite a adição de novos livros na biblioteca.

## Armazenamento dos Dados

Os dados dos livros são armazenados em um arquivo JSON, que inicialmente contém 10 livros. Todas as alterações (cadastros, aluguéis e devoluções) são refletidas neste arquivo, mantendo sua estrutura correta.

## Tecnologias Utilizadas
- **Linguagem**: Java 17
- **Protocolos de Comunicação**: Sockets
- **Formato de Armazenamento**: JSON

## Manipulação de JSON com a Biblioteca Gson
Para manipular JSON no projeto, utilizamos a biblioteca Gson, que é uma poderosa ferramenta para converter objetos Java em seu equivalente JSON e vice-versa.

## Como Executar
- Faça o clone do repositório.
- Compile o código fonte usando uma IDE ou pela linha de comando.
- Inicie o servidor executando a classe "Server".
- Conecte-se ao servidor e realize operações como listagem, aluguel, devolução e cadastro de livros utilizando a classe "Client".

## Critérios de Avaliação

### Coletivos (3 pontos):

- **Criação da classe do livro** (0.1):
    - Implementação correta da classe `Livro`, com atributos e métodos.
- **Implementação das operações** (0.9):
    - Listagem, aluguel, devolução e cadastro de livros.
    - Atualização correta do arquivo JSON.
- **Criação do servidor** (0.7):
    - Implementação correta utilizando sockets.
- **Criação do cliente** (0.7):
    - Implementação correta utilizando sockets.
- **Estruturação e organização do código** (0.5):
    - Divisão do projeto em classes, modularização do código, clareza e organização.
- **Organização do GitHub** (0.1):
    - README, comentários, e tamanho dos commits.

### Individuais (1 ponto):

- **Participação no desenvolvimento** (0.5):
    - Quantidade e qualidade dos commits por cada membro.
- **Participação na apresentação final** (0.5).
