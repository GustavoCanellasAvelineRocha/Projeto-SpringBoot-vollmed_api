package med.voll.api.domain.Consulta.Validacoes;

import med.voll.api.domain.Consulta.DadosAgendamentoConsultas;
import med.voll.api.domain.ValidacaoExecption;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsultas{
    public void validar(DadosAgendamentoConsultas dados){
        LocalDateTime dataconsulta = dados.data();

        boolean domingo = dataconsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean antesDaAberturaDaClinica = dataconsulta.getHour()<7;
        boolean depoisDaAberturaDaClinica = dataconsulta.getHour() > 18;
        if(domingo || antesDaAberturaDaClinica || depoisDaAberturaDaClinica){
            throw new ValidacaoExecption("Consulta fora do horario da clinica");
        }
    }
}
