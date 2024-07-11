![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) 
![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
# Sistema de Matrículas 💻

# Descrição
O Sistema de Matrícula é uma aplicação full-stack desenvolvida com React no front-end e Java Spring Boot no back-end. O sistema permite que alunos se inscrevam em diversas matérias, onde um aluno pode estar inscrito em várias matérias e uma matéria pode ter vários alunos.

![Print da Apliacação](screenshots\Sistema_de_Matricula.png)

### Funcionamento da Inscrição
- **Preferência na Inscrição**: O sistema implementa um mecanismo de prioridade para inscrição de alunos em matérias quando a capacidade máxima é atingida. 
  - **Vagas Disponíveis**: Se houver vagas, o aluno é adicionado normalmente. 
  - **Prioridade e Desempenho**: Caso contrário, o sistema verifica se o novo aluno possui prioridade ou uma nota geral maior que o aluno com a menor nota inscrito.
  - **Critérios de Prioridade**: A prioridade é dada aos alunos próximos de finalizar o curso (3 ou mais anos de curso).
  - **Substituição**: Se o novo aluno atender a essas condições, ele substitui o aluno com a menor nota; caso contrário, a inscrição é recusada.
- Esse mecanismo garante que os alunos com maior necessidade ou melhor desempenho acadêmico tenham prioridade nas vagas disponíveis.

# Tecnologias Utilizadas
## Front-end
- HTML
- CSS
- React 
## Back-end
- Java Spring Boot
- Hibernate
- JPA
- PostgreSQL


# Uso
## Funcionalidades Principais
- **Inscrição de Alunos**: Os alunos podem se inscrever em várias matérias.
-  **Gerenciamento de Matérias**: Cada matéria pode ter vários alunos inscritos, respeitando a capacidade máxima definida.
-  **Visualizar Matérias**: Acesse a lista de matérias para ver a disponibilidade de vagas.

## Passo a Passo para Uso

1. Acessar a Aplicação: Abra o navegador e acesse http://localhost:3000.

2. Cadastro de Alunos: Preencha o formulário com os dados do aluno e inscreva-se nas matérias desejadas.

3. Visualizar Matérias: Acesse a lista de matérias para ver a disponibilidade de vagas.

## License

[MIT](https://choosealicense.com/licenses/mit/)


<!-- 
## Instalação

```bash

``` -->

<!-- ## Environment Variables

## Usage -->

<!-- 
```bash
```


<h2 id="routes">📍 API Endpoints</h2>

Here you can list the main routes of your API, and what are their expected request bodies.
​
| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>GET /authenticate</kbd>     | retrieves user info see [response details](#get-auth-detail)
| <kbd>POST /authenticate</kbd>     | authenticate user into the api see [request details](#post-auth-detail) -->

<!-- <h3>GET</h3>

**RESPONSE**
```json
{
  "name": "",
  "age": ,
  "email": ""
}
```

<h3 >POST</h3>

**REQUEST**
```json
{
  "username": "admin",
  "password": "4444444"
}
```

**RESPONSE**
```json
{
  "resp": "resp"
}
``` -->
