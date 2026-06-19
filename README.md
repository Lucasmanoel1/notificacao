# notificacao
Microsserviço de envio de e-mails do sistema Agendador de Tarefas. Recebe os dados de uma tarefa via HTTP e dispara um e-mail HTML formatado para o destinatário, usando SMTP com Thymeleaf como motor de template.

## Stack
- Java 17
- Spring Boot
- Spring Mail (JavaMailSender)
- Thymeleaf
- Gradle

## API
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | /email | Enviar e-mail de notificação para o destinatário de uma tarefa |

### Corpo esperado
```json
{
  "nome": "Reunião de sprint",
  "descricao": "Revisão do backlog com o time",
  "email": "usuario@email.com",
  "dataEvento": "2025-01-15T14:00:00"
}
```

## Regras de negocio
- O e-mail é renderizado a partir de um template Thymeleaf, exibindo nome, data/horário e descrição da tarefa.
- O envio é feito via SMTP; falhas de envio lançam uma exceção customizada (`EmailException`).
- Este serviço não decide quando notificar — quem aciona o disparo é o job agendado (CronService) do [bff-agendador](https://github.com/Lucasmanoel1/bff-agendador-tarefas).

## Executando localmente
Requer uma conta SMTP (ex: Gmail com App Password) configurada em `application.yaml`:
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
```

```bash
./gradlew bootRun
```
Serviço sobe na porta **8082**.

> ⚠️ Nunca commite credenciais reais. Use variáveis de ambiente em produção.

## Executando com Docker
Este serviço faz parte de um ambiente multi-container. Consulte o repositório principal da organização [agendador-tarefas](https://github.com/Lucasmanoel1) para o `docker-compose.yml` completo.
