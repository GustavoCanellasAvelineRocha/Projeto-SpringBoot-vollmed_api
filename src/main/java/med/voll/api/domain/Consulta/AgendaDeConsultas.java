package med.voll.api.domain.Consulta;

import med.voll.api.domain.Consulta.Validacoes.ValidadorAgendamentoDeConsultas;
import med.voll.api.domain.ValidacaoExecption;
import med.voll.api.domain.medico.DadosListaMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultasRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsultas> validadores;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsultas dados){

        if (!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoExecption("Id do paciente informado nao existe!");
        }

        if (dados.idMedico() != null  &&!medicoRepository.existsById(dados.idPaciente())){
            throw new ValidacaoExecption("Id do paciente informado nao existe!");
        }

        validadores.forEach(v->v.validar(dados));

        Paciente paciente = pacienteRepository.findById(dados.idPaciente()).get();
        Medico medico = escolheMedico(dados);

        if (medico == null){
            throw new ValidacaoExecption("Nao existe medico disponivel nesta data!");
        }

        Consulta consulta = new Consulta(paciente,medico,dados.data());
        consultaRepository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolheMedico(DadosAgendamentoConsultas dados) {
        if(dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if(dados.especialidade() == null){
            throw new ValidacaoExecption("A especialidade deve ser preenchida quando o medico nao eh informado!");
        }

        return medicoRepository.escolherMedicoAletorioDisponivelNaData(dados.especialidade(),dados.data());
    }

    public ResponseEntity cancelar(DadosCancelamentoConsultas dados){
        if (!consultaRepository.existsById(dados.idConsulta())){
            throw new ValidacaoExecption("Id da consulta informada nao existe!");
        }

        LocalDateTime dataConsulta = consultaRepository.findById(dados.idConsulta()).get().getData();
        if(dataConsulta.compareTo(LocalDateTime.now().plusDays(1)) <= 0){
            throw new ValidacaoExecption("So eh permitido desmarcar a consulta com 24 horas de antecedencia!");
        }

        Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivoCancelamento());
        return ResponseEntity.ok("Consulta cancelada por motivo = " + dados.motivoCancelamento());
    }

    public Page<DadosDetalhamentoConsulta> listarConsultas(Pageable paginacao) {
        Page<DadosDetalhamentoConsulta> page = consultaRepository.findAllByMotivoCancelamentoNull(paginacao).map(DadosDetalhamentoConsulta::new);
        return page;
    }
}
