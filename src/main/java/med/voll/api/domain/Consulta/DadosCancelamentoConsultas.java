package med.voll.api.domain.Consulta;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsultas(
        @NotNull
        Long idConsulta,
        @NotNull
        MotivoCancelamento motivoCancelamento){
}
