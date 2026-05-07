# Calculador de Trastes — Projeto de Extensão

> Programação Funcional com a Linguagem Clojure  
> **Prof. Aparecido V. de Freitas** — Doutor em Engenharia da Computação pela EPUSP  
> aparecido.freitas@maua.br

---

## Componentes do Grupo

- Kauã Bueno Marcatto Ortega
- Mateus Yuji Ohira

---

## Descrição Geral do Projeto

Desenvolvimento de uma **API REST em Clojure** para calcular e retornar a posição dos trastes em instrumentos de cordas com afinação temperada (baseada em trastes), como violões, guitarras e baixos.

O projeto é caracterizado como **extensão universitária** pois:
- Promove acesso à informação para a comunidade
- Aproxima o ambiente acadêmico à sociedade
- Desenvolve solução tecnológica com impacto social
- Envolve alunos em problemas reais

### Objetivo Estratégico

Implementar o calculador de trastes no site de **José Valderrama**, visando:
- Atrair e engajar potenciais estudantes para o curso de formação em **Lutheria**
- Despertar o interesse de futuros clientes nos instrumentos artesanais
- Ampliar a visibilidade do curso e dos produtos artesanais

### Público-Alvo

- Estudantes de Música Erudita ou Popular
- Músicos profissionais
- Construtores de Instrumentos Musicais (Luthiers)

---

## O Problema Central

Em instrumentos de cordas trasteados, a correta posição dos trastes é essencial para garantir a precisão da afinação ao longo de todo o braço do instrumento.

A disposição dos trastes **não segue uma progressão linear**, mas sim uma **progressão exponencial**, determinada pela relação física entre comprimento vibrante da corda e frequência sonora.

---

## Arquitetura do Sistema

```
[ Front End Web / Android ]
          |
          v
    [ API REST Clojure ]
          |
          v
  [ Módulo de Cálculo ]
          |
          v
  [ Geração de Resultados ]
```

O sistema é uma **API WEB** que pode ser chamada por qualquer FrontEnd, com:
- Tratamento de dados de entrada
- Processamento para o cálculo de medidas dos trastes
- Geração dos resultados

---

## Fluxo Operacional

1. Usuário informa o **tamanho da escala em mm** (ex: `650`)
2. A API valida o dado de entrada
3. O sistema calcula a posição de cada traste usando a fórmula da afinação temperada
4. A API retorna os resultados formatados

---

## Funcionalidades Principais

- Receber o tamanho da escala como entrada
- Calcular a **distância da pestana (nut) até cada traste**
- Calcular a **distância de cada traste até a ponte (cavalete)**
- Calcular o **espaço entre trastes consecutivos**
- Validar os dados de entrada
- Retornar resultados de forma clara via API REST

### Fórmula de Cálculo

Para cada traste `n`, a distância da pestana até o traste é calculada por:

```
d(n) = Escala - (Escala / 2^(n/12))
```

A distância do traste até a ponte é:

```
ponte(n) = Escala / 2^(n/12)
```

O espaço entre trastes:
- **Traste 1:** `espaco(1) = d(1)`
- **Demais trastes:** `espaco(n) = d(n) - d(n-1)`

### Verificação

No **12º traste**, a distância da pestana deve ser **exatamente metade da escala**:

> Para escala de 650 mm → 12º traste ≈ **325 mm**

---

## Tecnologias Utilizadas

| Tecnologia | Uso |
|------------|-----|
| **Clojure** | Linguagem principal da API |
| **REST API** | Interface de comunicação |
| **HTML/CSS/JS** | Interface Web (Front End) |
| **Android** _(opcional)_ | Interface mobile |

### Conceitos Clojure Aplicados

- Criação de projetos Clojure
- Variáveis e tipos de dados
- Estruturas de controle e coleções de dados
- Funções
- Validação de entrada de dados
- Organização de código
- Desenvolvimento de APIs
- Interface com banco de dados
- Front End — interfaces gráficas Web e Android

---

## Infraestrutura de Execução

| Camada | Tecnologia |
|--------|-----------|
| Gerenciador de dependências | Clojure CLI + `deps.edn` |
| Servidor HTTP | http-kit 2.8.0 |
| Roteamento | Compojure 1.7.1 |
| Middleware | Ring Core 1.12.2 |
| Serialização JSON | Cheshire 5.13.0 |
| Ambiente de dev | VS Code + Calva (nREPL) |
| Ambiente de deploy | localhost / AWS ou Integrator |

---

## Configuração do Ambiente

### Requisitos

- [Clojure CLI](https://clojure.org/guides/install_clojure) instalado (`clj` disponível no terminal)
- Java 11+ instalado

### Instalação

```bash
# Clone o repositório
git clone https://github.com/kauamarcatto/jose-valderrama.git
cd jose-valderrama

# Baixe as dependências e inicie o servidor
clj -M:run
```

O servidor iniciará em `http://localhost:3000`.

### Testes

```bash
clj -M:test
```

---

## Endpoints — API

### `GET /trastes`

Retorna a posição de todos os trastes para a escala e número de trastes informados.

**Parâmetros (query string):**

| Parâmetro | Tipo | Padrão | Descrição |
|-----------|------|--------|-----------|
| `escala` | `double` | `650` | Comprimento da escala em mm |
| `num-trastes` | `int` | `24` | Número de trastes do instrumento |

**Exemplo de requisição:**
```
GET http://localhost:3000/trastes?escala=650&num-trastes=24
```

**Exemplo de resposta:**
```json
{
  "escala": 650.0,
  "num-trastes": 24,
  "trastes": [
    { "traste": 1,  "distancia-pestana": 36.48,  "distancia-ponte": 613.52 },
    { "traste": 2,  "distancia-pestana": 70.94,  "distancia-ponte": 579.06 },
    { "traste": 12, "distancia-pestana": 325.0,  "distancia-ponte": 325.0  },
    { "traste": 24, "distancia-pestana": 487.5,  "distancia-ponte": 162.5  }
  ]
}
```

**Erros:**

| Status | Situação |
|--------|----------|
| `400` | `escala` ou `num-trastes` com valor zero ou negativo |
| `404` | Rota não encontrada |

---

## Futuras Melhorias (Roadmap)

- [ ] Suporte a diferentes números de trastes (ex: 19, 21, 22, 24)
- [ ] Cálculo para diferentes afinações (ex: Drop D, Open G)
- [ ] Exportação em PDF com diagrama visual do braço
- [ ] Interface Android nativa
- [ ] Integração com banco de dados para salvar configurações

---

## Entregáveis

**Prazo: 03/06/2026**

- [ ] Vídeo apresentando a operação do sistema
- [ ] Documentação (este README) com:
  - [x] Componentes do grupo
  - [x] Descrição geral do projeto
  - [x] Arquitetura do sistema
  - [x] Fluxo operacional
  - [x] Funcionalidades principais
  - [x] Tecnologias utilizadas
  - [x] Infraestrutura de execução
  - [x] Configuração do ambiente
  - [x] Endpoints — API
  - [x] Futuras melhorias (Roadmap)
  - [ ] Referências

---

## Referências

- Empresa Parceira: **José Valderrama** — +55 11 97334-4545
- Prof. Aparecido V. de Freitas — aparecidovfreitas@gmail.com
