# iDox – Inteligência para Documentação, Organização e Extração de Informações

**iDox** é uma aplicação desenvolvida com **Spring Boot** e **Thymeleaf** com o objetivo de agilizar e simplificar o tratamento de **áudios institucionais**, gerando automaticamente **resumos objetivos** e **transcrições textuais**. A proposta é facilitar o registro, a organização e a recuperação de informações em ambientes públicos ou jurídicos, como na atuação da Procuradoria Geral do Estado do Maranhão (PGE-MA).

## Objetivo

Converter gravações de reuniões ou declarações orais em textos úteis e estruturados, permitindo:
- Acompanhamento assíncrono de decisões
- Registro fiel de conteúdos
- Compartilhamento e arquivamento eficiente

## Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Maven
- REST API
- Thymeleaf (interface web)
- HTML, CSS, JS puro
- DTOs para entrada e saída
- Whisper/LLM (IA local ou externa – em evolução)

## Estrutura do Projeto

- `br.gov.ma.idox.controller`: Camada de controle (REST + Web)
- `br.gov.ma.idox.service`: Regras de negócio (transcrição e resumo)
- `br.gov.ma.idox.dto`: Objetos de transporte de dados (Request e Response)
- `br.gov.ma.idox.model`: Modelos internos, se aplicável

## Fluxo Básico da Aplicação

1. O usuário envia um áudio via formulário (web) ou endpoint REST.
2. A aplicação valida e processa o arquivo.
3. Uma ferramenta de transcrição converte o áudio em texto.
4. Um resumo automático é gerado a partir da transcrição.
5. O sistema exibe o conteúdo e permite ações como copiar ou baixar.

## Interface Web Responsiva

A aplicação oferece uma **interface moderna, amigável e adaptável** para uso direto em navegadores, sem necessidade de ferramentas externas.

### Funcionalidades:

- Upload de arquivos `.mp3`, `.m4a`, `.wav` com validação de tipo
- Visualização imediata do resumo gerado
- Download da transcrição em `.txt`
- Botão para copiar o resumo para a área de transferência
- Animação de carregamento estilo Nubank
- Layout otimizado para **desktop e mobile**

## Escopo Inicial

- Áudios de até 5 minutos
- Geração de transcrição e resumo em português
- Integração com modelo de IA local ou remoto
- Projeto demonstrativo para uso na PGE-MA

---

⚠️ **Aviso:** O iDox ainda está em fase inicial de desenvolvimento. Funcionalidades como suporte a múltiplos falantes, organização por temas e integração com bancos de dados serão avaliadas em etapas futuras.
