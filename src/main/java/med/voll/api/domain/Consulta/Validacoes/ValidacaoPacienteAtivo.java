package med.voll.api.domain.Consulta.Validacoes;

import med.voll.api.domain.Consulta.DadosAgendamentoConsultas;
import med.voll.api.domain.ValidacaoExecption;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoPacienteAtivo implements ValidadorAgendamentoDeConsultas{
    @Autowired
    PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsultas dados){
        if(!pacienteRepository.getReferenceById(dados.idPaciente()).isAtivo())throw new ValidacaoExecption("Erro, esse paciente nao esta ativo");
    }
}
