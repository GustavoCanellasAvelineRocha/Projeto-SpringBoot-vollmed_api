package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.Consulta.AgendaDeConsultas;
import med.voll.api.domain.Consulta.DadosAgendamentoConsultas;
import med.voll.api.domain.Consulta.DadosCancelamentoConsultas;
import med.voll.api.domain.Consulta.DadosDetalhamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class  ConsultasController {

    @Autowired
    private AgendaDeConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsultas dados){
        DadosDetalhamentoConsulta dadosDetalhamentoConsulta = agenda.agendar(dados);
        return ResponseEntity.ok(dadosDetalhamentoConsulta);
    }

    @GetMapping
    public ResponseEntity listar(@PageableDefault(sort = {"id"}) Pageable paginacao){
        Page<DadosDetalhamentoConsulta> page = agenda.listarConsultas(paginacao);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsultas dados){
        return agenda.cancelar(dados);
    }


}