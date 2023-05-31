package med.voll.api.domain.Consulta.Validacoes;

import med.voll.api.domain.Consulta.DadosAgendamentoConsultas;
import med.voll.api.domain.ValidacaoExecption;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoMedicoAtivo implements ValidadorAgendamentoDeConsultas{

    @Autowired
    MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsultas dados){
        if(dados.idMedico()==null){
            return;
        }

        if(!medicoRepository.getReferenceById(dados.idMedico()).isAtivo())throw new ValidacaoExecption("Erro, esse medico nao esta ativo");
    }
}
