### Criar um novo usuário

**Rota:** `POST /user`

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
    "message": "Erro interno",
    "error": "Detalhes do erro"
  }
```

---

### Obter usuário por ID

**Rota:** `GET /user?id={id}`

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
      "message": "Record not found with ID: e98a4fa8-1ad7-4e7c-87a9-21808c668451",
      "data": null,
      "status": 404
  }
  ```
- **500 Internal Server Error**: Erro interno do servidor.
  ```json
    {
      "message": "Erro interno",
      "error": "Detalhes do erro"
    }
  ```

---

### Deletar usuário por ID

**Rota:** `DELETE /user?id={id}`

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
      "message": "Record not found with ID: e98a4fa8-1ad7-4e7c-87a9-21808c668451",
      "data": null,
      "status": 404
  }
  ```
- **500 Internal Server Error**: Erro interno do servidor.
  ```json
    {
      "message": "Erro interno",
      "error": "Detalhes do erro"
    }
  ```
  
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
    "message": "Monitoramento salvo com sucesso"
  }
  ```
- **400 Bad Request**: Dados inválidos ou ausentes na requisição.
  ```json
  {
    "message": "Erro na solicitação"
  }
  ```
- **500 Internal Server Error**: Erro interno do servidor.
  ```json
  {
    "message": "Erro interno",
    "error": "Detalhes do erro"
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
    "userId": "550e8400-e29b-41d4-a716-446655440000",
    "coinId": "BTC",
    "price": 50000.0,
    "greatherThan": true
  }
  ```
- **404 Not Found**: Registro não encontrado.
  ```json
  {
    "message": "Monitoramento não encontrado"
  }
  ```
- **500 Internal Server Error**: Erro interno do servidor.
  ```json
  {
    "message": "Erro interno",
    "error": "Detalhes do erro"
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
    "message": "Monitoramento {id} deletado com sucesso!"
  }
  ```
- **500 Internal Server Error**: Erro interno do servidor.
  ```json
  {
    "message": "Erro interno",
    "error": "Detalhes do erro"
  }
  ```

### Obter todas as moedas

**Rota:** `GET /coins/all`

**Descrição:**
Retorna a lista de todas as moedas disponíveis no sistema, juntamente com a data e hora da última atualização.

**Respostas:**

- **200 OK**: Lista de moedas retornada com sucesso.
  ```json
   {
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
    "lastUpdated": "13/03/2025 00:13:41",
    "message": "ok"
  }
  ```
- **500 Internal Server Error**: Erro interno do servidor.
  ```json
  {
    "message": "Erro interno",
    "error": "Detalhes do erro"
  }
  ```

---

### Obter uma moeda pelo NAME

**Rota:** `GET /coins?name={name}`

**Descrição:**
Retorna os detalhes de uma moeda específica pelo seu identificador.

**Respostas:**

- **200 OK**: Moeda encontrada com sucesso.
  ```json
  {
    "id": "bitcoin",
    "symbol": "BTC",
    "name": "Bitcoin",
    "image": "https://example.com/bitcoin.png",
    "currentPrice": 50000.0,
    "marketCap": 1000000000,
    "marketCapRank": 1,
    "fullyDilutedValuation": 1200000000,
    "totalVolume": 500000000,
    "high24h": 51000.0,
    "low24h": 49000.0,
    "priceChange24h": 200.0,
    "priceChangePercentage24h": 0.4,
    "marketCapChange24h": 5000000,
    "marketCapChangePercentage24h": 0.5,
    "circulatingSupply": 19000000,
    "totalSupply": 21000000,
    "maxSupply": 21000000,
    "ath": 69000.0,
    "athChangePercentage": -27.5,
    "athDate": "2021-11-10T00:00:00",
    "atl": 67.81,
    "atlChangePercentage": 73700.0,
    "atlDate": "2013-07-05T00:00:00",
    "lastUpdated": "2025-03-06T12:00:00",
    "priceChanges": {}
  }
  ```
- **404 Not Found**: Moeda não encontrada.
  ```json
  {
    "message": "Moeda não encontrada"
  }
  ```
- **500 Internal Server Error**: Erro interno do servidor.
  ```json
  {
    "message": "Erro interno",
    "error": "Detalhes do erro"
  }
  ```

### Enviar um e-mail

**Rota:** `POST /mail`

**Descrição:**
Envia um e-mail para o destinatário informado.

**Requisição:**

```json
{
  "to": "usuario@example.com",
  "title": "Assunto do e-mail",
  "message": "Conteúdo da mensagem"
}
```

**Respostas:**

- **200 OK**: E-mail enviado com sucesso.
  ```json
  {
    "message": "E-mail enviado"
  }
  ```
- **500 Internal Server Error**: Erro interno do servidor.
  ```json
  {
    "message": "Erro interno",
    "error": "Detalhes do erro"
  }
  ```



