package med.voll.api.domain.Consulta.Validacoes;

import med.voll.api.domain.Consulta.ConsultasRepository;
import med.voll.api.domain.Consulta.DadosAgendamentoConsultas;
import med.voll.api.domain.ValidacaoExecption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidacaoPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsultas{
    @Autowired
    private ConsultasRepository repository;

    public void validar(DadosAgendamentoConsultas dados) {
        LocalDateTime primeiroHorario = dados.data().withHour(7);
        LocalDateTime ultimoHorario = dados.data().withHour(18);
        boolean pacientePossuiOutraConsultaNoDia = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(),primeiroHorario,ultimoHorario);
        if(pacientePossuiOutraConsultaNoDia){
            throw new ValidacaoExecption("Paciente Ja possui uma consulta nesse dia");
        }
    }
}
