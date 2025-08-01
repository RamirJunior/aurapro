# AURAPRO – Assistente Unificado de Reuniões em Áudio da Procuradoria

**AURAPRO** é um sistema desenvolvido em **Spring Boot** com o objetivo de auxiliar a Procuradoria Geral do Estado do Maranhão (PGE-MA) na transcrição e resumo automático de áudios de reuniões institucionais.

## Objetivo

Transformar gravações de reuniões em **transcrições textuais** e **resumos objetivos**, facilitando o acompanhamento, registro e recuperação de informações discutidas internamente.

## Tecnologias Utilizadas

* Java 17+
* Spring Boot
* Maven
* REST API
* Thymeleaf (para interface web)
* DTOs para entrada e saída
* (IA local ou externa para transcrição e resumo – em desenvolvimento)

## Estrutura do Projeto

* `br.gov.ma.aurapro.controller`: Camada de entrada (REST + Web)
* `br.gov.ma.aurapro.service`: Regras de negócio (transcrição e resumo)
* `br.gov.ma.aurapro.dto`: Objetos de transporte (Request e Response)
* `br.gov.ma.aurapro.model`: Modelos internos (se aplicável)

## Fluxo Básico da API

1. O usuário envia um áudio de até 5 minutos via endpoint REST.
2. A aplicação processa o áudio com uma ferramenta de transcrição (em desenvolvimento).
3. Gera a transcrição completa e um resumo textual.
4. Retorna os dados em formato estruturado (JSON via DTO).

## Interface Web com Thymeleaf

Além da API, o AURAPRO disponibiliza uma **interface web responsiva** construída com **Thymeleaf**, HTML, CSS e JavaScript puro, permitindo que usuários interajam com a aplicação sem depender de ferramentas externas como Postman ou cURL.

### Recursos da interface:

* Upload de áudios `.mp3`, `.m4a` e `.wav` com validação.
* Visualização do resumo gerado diretamente na página.
* Download da transcrição em `.txt`.
* Copiar resumo para a área de transferência.
* Animações de carregamento e feedback visual do estado do processo.
* Suporte a dispositivos móveis com layout adaptado.

## Escopo Inicial

* Processamento de áudios curtos (≤ 5min).
* API simples com resposta em JSON.
* Interface web integrada com Thymeleaf.
* Projeto demonstrativo para validação da ideia dentro da PGE-MA.

---

**Aviso:** Projeto em desenvolvimento inicial. Suporte a áudios longos, integração com sistemas internos e melhorias de performance serão avaliados nas próximas etapas.
