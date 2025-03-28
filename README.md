<p align="center">
  <img src="https://img.shields.io/badge/Java-000?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring-000?style=for-the-badge&logo=spring&logoColor=green"/>
  <img src="https://img.shields.io/badge/PostgreSQL-black?style=for-the-badge&logo=postgresql&logoColor=blue"/>
</p>

# 📈 Monitoramento de Criptomoedas

Esta aplicação é uma **API de monitoramento de criptomoedas** que permite que usuários cadastrem alertas personalizados para serem notificados por e-mail quando determinada moeda atingir um preço específico.

A aplicação consome dados da [API pública da CoinGecko](https://www.coingecko.com/), de forma assíncrona(API Reativa para uma melhor performance) e realiza verificações automáticas a cada 15 minutos para avaliar se as condições dos alertas cadastrados foram atendidas.

---

## ⚙️ Funcionalidades

- 🔔 **Cadastro de alertas de preço**  
  Os usuários podem configurar monitoramentos com:
  - `coinId`: moeda a ser monitorada
  - `price`: valor de alerta
  - `greatherThan`: define se o alerta será disparado quando o preço **subir acima** (`true`) ou **cair abaixo** (`false`) do valor definido

- ✏️ **Atualização e remoção lógica de monitoramentos**
  - Os monitoramentos podem ser atualizados a qualquer momento
  - A exclusão é lógica (soft delete), permitindo manter o histórico no sistema

- 📬 **Envio automático de e-mails**
  - A cada 15 minutos, a aplicação:
    1. Atualiza os preços das criptomoedas via CoinGecko
    2. Verifica os monitoramentos ativos
    3. Envia e-mails personalizados aos usuários que possuem alertas atingidos

- 🚀 **Cache para alto desempenho**

  - Evita chamadas desnecessárias à API externa e melhora a performance da aplicação
  - Houve melhoria de no mínimo 75% no tempo de resposta para obter todas moedas
---

## 🛠️ Tecnologias e Conceitos

- Java 17 + Spring Boot + WebFlux
- Agendamentos com `@Scheduled`
- Cache com `@Cacheable` (configurado com Caffeine)
- Integração com API REST externa (CoinGecko)
- Programação reativa com Project Reactor (`Mono`, `Flux`)
- Envio de e-mails automáticos
- Manipulação de erros customizada (exceções personalizadas)

---

## 🧪 Exemplo de uso

1. Um usuário cadastra um alerta para ser notificado quando o **Bitcoin** ultrapassar **$70.000**
2. A cada 15 minutos, o sistema consulta os dados atualizados da CoinGecko
3. Quando o preço do Bitcoin ultrapassa o valor definido, um e-mail é enviado para o usuário

---

> 💡 Ideal para sistemas de notificação de preços de ativos digitais, automações financeiras e serviços personalizados para entusiastas de criptomoedas.

# Endpoints
## User
### Criar um novo usuário

**Rota:** `POST /userModel`

**Descrição:**
Cria um novo usuário no sistema.

**Requisição:**

```json
{
  "name": "Jamil",
  "lastName": "Apicela",
  "mail": "jamil@apicela.com"
}
```

**Respostas:**

- **201 Created**: Usuário criado com sucesso.

```json
{
    "message": "User created successfully",
    "data": {
        "name": "Jamil",
        "lastName": "Apicela",
        "mail": "jamil@apicela.com"
    },
    "status": 201
}
```

- **400 Bad Request**: Erro de validação.

```json
{
    "message": "Validation Error",
    "errors": {
        "lastName": "Last name cannot be empty",
        "name": "Name cannot be empty"
    },
    "status": 400
}
```

- **409 Conflict**: Conflito ao salvar dados no servidor.

```json
{
    "message": "E-mail already in use",
    "data": null,
    "status": 409
}
```

- **500 Internal Server Error**: Erro interno do servidor.

 ```json
  {
      "message": "Mensagem do erro",
      "data": null,
      "status": 500
  }
```

---

### Obter usuário por ID

**Rota:** `GET /userModel/{id}`

**Descrição:**
Busca um usuário específico pelo seu identificador único (UUID).

**Respostas:**

- **200 OK**: Usuário encontrado com sucesso.
  ```json
  {
      "message": "ok",
      "data": {
          "name": "Jamil",
          "lastName": "Apicela",
          "mail": "jamil@apicela.com"
      },
      "status": 200
  }
  ```
- **404 Not Found**: Usuário não encontrado.
  ```json
  {
      "message": "User not found with ID: ac5e383d-f0d7-4798-9995-2d181a3070e0",
      "data": null,
      "status": 404
  }
  ```
- **500 Internal Server Error**: Erro interno do servidor.
  ```json
  {
      "message": "Mensagem do erro",
      "data": null,
      "status": 500
  }
  ```

---

### Deletar usuário por ID

**Rota:** `DELETE /userModel/{id}`

**Descrição:**
Deleta um usuário específico pelo seu identificador único (UUID).

**Respostas:**

- **200 OK**: Usuário deletado com sucesso.
  ```json
  {
      "message": "ok",
      "data": null,
      "status": 200
  }
  ```
- **404 Not Found**: Usuário não encontrado.
  ```json
  {
      "message": "User not found with ID: ac5e383d-f0d7-4798-9995-2d181a3070e0",
      "data": null,
      "status": 404
  }
  ```
- **500 Internal Server Error**: Erro interno do servidor.
  ```json
  {
      "message": "Mensagem do erro",
      "data": null,
      "status": 500
  }
  ```
## Monitoring
### Criar um novo monitoramento

**Rota:** `POST /monitoring`

**Descrição:**
Salva um novo registro de monitoramento.

**Requisição:**

```json
{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "coinId": "BTC",
  "price": 50000.0,
  "greatherThan": true
}
```

**Respostas:**

- **201 Created**: Registro de monitoramento criado com sucesso.
  ```json
  {
      "message": "Monitoring record created successfully",
      "data": {
          "coinId": "btc",
          "price": 50000.0,
          "greatherThan": true
      },
      "status": 201
  }
  ```
- **400 Bad Request**: Dados inválidos ou ausentes na requisição.
  ```json
  {
      "message": "Validation Error",
      "errors": {
          "userId": "User ID cannot be empty"
      },
      "status": 400
  }
  ```
  - **404 Not Found**: User ID associado ao Monitoramento não encontrado.
  ```json
  {
      "message": "User not found with ID: ac5e383d-f0d7-4798-9995-2d181a3070e0",
      "data": null,
      "status": 404
  }
  ```
- **500 Internal Server Error**: Erro interno do servidor.
  ```json
  {
      "message": "Mensagem do erro",
      "data": null,
      "status": 500
  }
  ```

---

### Obter um monitoramento pelo ID

**Rota:** `GET /monitoring/{id}`

**Descrição:**
Obtém um registro de monitoramento pelo ID informado.

**Respostas:**

- **200 OK**: Registro encontrado.
  ```json
  {
      "message": "ok",
      "data": {
          "coinId": "btc",
          "price": 50000.0,
          "greatherThan": true
      },
      "status": 200
  }
  ```
- **404 Not Found**: Registro não encontrado.
  ```json
  {
      "message": "Monitoring not found with ID: 1",
      "data": null,
      "status": 404
  }
  ```
- **500 Internal Server Error**: Erro interno do servidor.
  ```json
  {
      "message": "Mensagem do erro",
      "data": null,
      "status": 500
  }
  ```

---

### Deletar um monitoramento pelo ID

**Rota:** `DELETE /monitoring/{id}`

**Descrição:**
Remove um registro de monitoramento pelo ID informado.

**Respostas:**

- **200 OK**: Registro deletado com sucesso.
  ```json
  {
      "message": "ok",
      "data": null,
      "status": 200
  }
  ```
  - **404 Not Found**: Registro não encontrado.
  ```json
  {
      "message": "Monitoring not found with ID: 1",
      "data": null,
      "status": 404
  }
  ```
- **500 Internal Server Error**: Erro interno do servidor.
  ```json
  {
      "message": "Mensagem do erro",
      "data": null,
      "status": 500
  }
  ```
## Coin
### Obter todas as moedas

**Rota:** `GET /coins/all`

**Descrição:**
Retorna a lista de todas as moedas disponíveis no sistema, juntamente com a data e hora da última atualização.

**Respostas:**

- **200 OK**: Lista de moedas retornada com sucesso.
  ```json
   {
      "message": "ok",
      "data": {
        "coins": [
            {
                "id": "bitcoin",
                "symbol": "btc",
                "name": "Bitcoin",
                "image": "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400",
                "currentPrice": 486704.0,
                "marketCap": 9648524573031,
                "marketCapRank": 1,
                "fullyDilutedValuation": 9648524573031,
                "totalVolume": 235117073655,
                "high24h": 491347.0,
                "low24h": 470353.0,
                "priceChange24h": 7650.9,
                "priceChangePercentage24h": 1.59709,
                "marketCapChange24h": 131915550617,
                "marketCapChangePercentage24h": 1.38616,
                "circulatingSupply": 19836071,
                "totalSupply": 19836071,
                "maxSupply": 21000000,
                "ath": 672350.0,
                "athChangePercentage": -27.7757,
                "athDate": [
                    2024,
                    12,
                    26,
                    0,
                    20,
                    46,
                    738000000
                ],
                "atl": 149.66,
                "atlChangePercentage": 324371.85148,
                "atlDate": [
                    2013,
                    7,
                    5,
                    0,
                    0
                ],
                "lastUpdated": [
                    2025,
                    3,
                    13,
                    3,
                    13,
                    34,
                    260000000
                ],
                "priceChanges": {
                    "priceChangePercentage1h": -0.24174978,
                    "priceChangePercentage24h": 1.597088,
                    "priceChangePercentage7d": -7.6695323,
                    "priceChangePercentage14d": -0.51532936,
                    "priceChangePercentage30d": -14.035927,
                    "priceChangePercentage200d": 38.22119,
                    "priceChangePercentage1y": 36.04486
                }
            },
            ...
        ],
      "lastUpdated": "13/03/2025 00:13:41"
    },
    "status": 200
  }
  ```
- **500 Internal Server Error**: Erro interno do servidor.
  ```json
  {
      "message": "Mensagem do erro",
      "data": null,
      "status": 500
  }
  ```

---

### Obter uma moeda pelo NAME

**Rota:** `GET /coins/{name}`

**Descrição:**
Retorna os detalhes de uma moeda específica pelo seu identificador.

**Respostas:**

- **200 OK**: Moeda encontrada com sucesso.
  ```json
   {
      "message": "ok",
      "data": {
          "id": "bitcoin",
          "symbol": "btc",
          "name": "Bitcoin",
          "image": "https://coin-images.coingecko.com/coins/images/1/thumb/bitcoin.png?1696501400",
          "currentPrice": 485028.0,
          "marketCap": 9616206257234,
          "marketCapRank": 1,
          "fullyDilutedValuation": 9616206257234,
          "totalVolume": 214699592544,
          "high24h": 491347.0,
          "low24h": 470353.0,
          "priceChange24h": 1627.0,
          "priceChangePercentage24h": 1.98403,
          "marketCapChange24h": 29782163594,
          "marketCapChangePercentage24h": 1.82913,
          "circulatingSupply": 19836071,
          "totalSupply": 19836071,
          "maxSupply": 21000000,
          "ath": 672350.0,
          "athChangePercentage": -27.91197,
          "athDate": [
              2024,
              12,
              26,
              0,
              20,
              46,
              738000000
          ],
          "atl": 149.66,
          "atlChangePercentage": 323759.65868,
          "atlDate": [
              2013,
              7,
              5,
              0,
              0
          ],
          "lastUpdated": [
              2025,
              3,
              13,
              4,
              8,
              4,
              884000000
          ],
          "priceChanges": {
              "priceChangePercentage1h": -0.3174,
              "priceChangePercentage24h": 1.82366,
              "priceChangePercentage7d": -7.94838,
              "priceChangePercentage14d": -1.81611,
              "priceChangePercentage30d": -14.13711,
              "priceChangePercentage200d": 37.74507,
              "priceChangePercentage1y": 35.54606
          }
      },
      "status": 200
  }
  ```
  - **400 Bad Request**: Requisição inválida.
  ```json
  {
      "message": "400 BAD_REQUEST \"Required query parameter 'name' is not present.\"",
      "data": null,
      "status": 400
  }
  ```
- **404 Not Found**: Moeda não encontrada.
  ```json
  {
      "message": "A moeda btct não existe.",
      "data": null,
      "status": 404
  }
  ```
- **500 Internal Server Error**: Erro interno do servidor.
  ```json
  {
      "message": "Mensagem do erro",
      "data": null,
      "status": 500
  }
  ```

