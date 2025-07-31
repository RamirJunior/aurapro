# AURAPRO – Assistente Unificado de Reuniões em Áudio da Procuradoria

**AURAPRO** é um sistema desenvolvido em **Spring Boot** com o objetivo de auxiliar a Procuradoria Geral do Estado do Maranhão (PGE-MA) na transcrição e resumo automático de áudios de reuniões institucionais.

## Objetivo

Transformar gravações de reuniões em **transcrições textuais** e **resumos objetivos**, facilitando o acompanhamento, registro e recuperação de informações discutidas internamente.

## Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Maven
- REST API
- DTOs para entrada e saída
- (IA local ou externa para transcrição e resumo – em desenvolvimento)

## Estrutura do Projeto

- `br.gov.ma.aurapro.controller`: Camada de entrada (REST)
- `br.gov.ma.aurapro.service`: Regras de negócio (transcrição e resumo)
- `br.gov.ma.aurapro.dto`: Objetos de transporte (Request e Response)
- `br.gov.ma.aurapro.model`: Modelos internos (se aplicável)

## Fluxo Básico

1. O usuário envia um áudio de até 5 minutos via API.
2. A aplicação processa o áudio com uma ferramenta de transcrição (em desenvolvimento).
3. Gera a transcrição e um resumo textual.
4. Retorna os dados em formato estruturado (DTO).

## Escopo Inicial

- Processamento de áudios curtos (≤ 5min).
- API simples de envio e resposta com JSON.
- Projeto demonstrativo para validação da ideia dentro da PGE-MA.

---

Aviso: Em desenvolvimento inicial. Suporte a áudios longos, integração com sistemas internos e melhorias de performance serão avaliados nas próximas etapas.
