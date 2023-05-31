package med.voll.api.domain.Consulta.Validacoes;

import med.voll.api.domain.Consulta.DadosAgendamentoConsultas;
import med.voll.api.domain.ValidacaoExecption;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsultas{
    public void validar(DadosAgendamentoConsultas dados){
        LocalDateTime dataConsulta = dados.data();
        LocalDateTime agora = LocalDateTime.now();
        Long diferencaEmMinutos = Duration.between(agora,dataConsulta).toMinutes();

        if(diferencaEmMinutos < 30){
            throw new ValidacaoExecption("Consulta deve ser agendada com antencedencia minima de 30 minutos");
        }
    }
}
