package med.voll.api.domain.Consulta;

import med.voll.api.domain.paciente.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConsultasRepository extends JpaRepository<Consulta,Long> {
    Page<Consulta> findAllByMotivoCancelamentoNull(Pageable paginacao);

    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
}
