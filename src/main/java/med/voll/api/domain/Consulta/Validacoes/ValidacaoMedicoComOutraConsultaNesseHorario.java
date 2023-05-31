package med.voll.api.domain.Consulta.Validacoes;

import med.voll.api.domain.Consulta.Consulta;
import med.voll.api.domain.Consulta.ConsultasRepository;
import med.voll.api.domain.Consulta.DadosAgendamentoConsultas;
import med.voll.api.domain.ValidacaoExecption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class ValidacaoMedicoComOutraConsultaNesseHorario implements ValidadorAgendamentoDeConsultas{

    @Autowired
    ConsultasRepository consultasRepository;

    public void validar(DadosAgendamentoConsultas dados){
        ArrayList<Consulta> listaDeConsultas = (ArrayList<Consulta>) consultasRepository.findAll();

        for (Consulta consulta : listaDeConsultas) {
            if(Objects.equals(consulta.getMedico().getId(), dados.idMedico()) && consulta.getData().equals(dados.data())){
                throw new ValidacaoExecption("Erro, medico ja tem uma consulta nesse horario.");
            }
        }
    }
}
