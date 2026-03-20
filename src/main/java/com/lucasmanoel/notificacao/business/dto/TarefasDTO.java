package com.lucasmanoel.notificacao.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lucasmanoel.notificacao.business.enums.StatusNotificacaoEnum;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TarefasDTO {
    private String id;
    private String nome;
    private String descricao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataEvento;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataAlteracao;
    private StatusNotificacaoEnum statusNotificacaoEnum;
}
