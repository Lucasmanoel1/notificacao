# 📧 MS-Notificacao

Microserviço responsável pelo envio de e-mails de notificação de tarefas. Recebe os dados de uma tarefa via requisição HTTP e dispara um e-mail HTML formatado para o destinatário utilizando SMTP com Thymeleaf como motor de templates.

---

## 🏗️ Arquitetura do sistema

```
Frontend
    └── BFF (bff-agendador) :8083
            ├── MS-Usuario      :8080  → PostgreSQL
            ├── MS-Agendador    :8081  → MongoDB
            └── MS-Notificacao  :8082  → SMTP  ← você está aqui
```

> O BFF possui um **job agendado (CronService)** que consulta tarefas com vencimento na próxima hora e aciona este serviço automaticamente para disparar os e-mails.

---

## 🚀 Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 17 | Linguagem |
| Spring Boot 4.0.4 | Framework |
| Spring Mail (JavaMailSender) | Envio de e-mails SMTP |
| Thymeleaf | Template HTML do e-mail |
| Lombok | Redução de boilerplate |
| Docker | Containerização |
| GitHub Actions | CI/CD |

---

## 📋 Endpoints

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/email` | Envia e-mail de notificação para o destinatário da tarefa |

### Body esperado

```json
{
  "nome": "Reunião de sprint",
  "descricao": "Revisão do backlog com o time",
  "email": "usuario@email.com",
  "dataEvento": "2025-01-15T14:00:00"
}
```

---

## 📨 Template do e-mail

O e-mail enviado é renderizado com **Thymeleaf** e estilizado em HTML/CSS, exibindo:

- Nome da tarefa em destaque
- Data e horário do evento
- Descrição da tarefa
- Rodapé institucional

---

## ⚙️ Como executar

### Pré-requisitos
- Docker instalado
- Conta Google com **App Password** (senha de aplicativo) habilitada

### Configuração do `application.yaml`

```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: 'seu@email.com'
    password: 'sua-senha-de-aplicativo'
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true

envio:
  email:
    remetente: 'seu@email.com'
    nomeRemetente: 'Agendador de Tarefas'

server:
  port: 8082
```

> ⚠️ Nunca commite credenciais reais. Use variáveis de ambiente em produção.

### Com Docker

```bash
docker build -t ms-notificacao .
docker run -p 8082:8082 \
  -e SPRING_MAIL_USERNAME=seu@email.com \
  -e SPRING_MAIL_PASSWORD=suasenha \
  ms-notificacao
```

---

## 🗂️ Estrutura do projeto

```
src/main/java/com/lucasmanoel/notificacao/
├── business/
│   ├── EmailService.java              # Lógica de envio via JavaMailSender
│   ├── dto/
│   │   └── TarefasDTO.java            # Dados da tarefa recebidos pelo endpoint
│   └── enums/
│       └── StatusNotificacaoEnum.java # PENDENTE | NOTIFICADO | CANCELADO
├── controller/
│   └── EmailController.java           # Endpoint POST /email
└── infrastructure/
    └── exceptions/
        └── EmailException.java        # Exceção customizada para falhas de envio

src/main/resources/
├── application.yaml                   # Configuração SMTP e servidor
└── templates/
    └── notificacao.html               # Template Thymeleaf do e-mail
```

---

## 🔗 Repositórios relacionados

- [bff-agendador](https://github.com/Lucasmanoel1/bff-agendador-tarefas) — Gateway de entrada (aciona este serviço via Cron)
- [usuario](https://github.com/Lucasmanoel1/usuario) — Gerenciamento de usuários
- [agendador-tarefas](https://github.com/Lucasmanoel1/agendador-tarefas) — Gerenciamento de tarefas
