package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.Endereco;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String crm, String telefone, Especialidade especialidade, Endereco endereco,boolean ativo) {
    public DadosDetalhamentoMedico (Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco(), medico.isAtivo());

    }

    public DadosDetalhamentoMedico(DadosCadastroMedico dadosCadastro) {
        this(null,dadosCadastro.nome(),dadosCadastro.email(),dadosCadastro.crm(), dadosCadastro.telefone(),dadosCadastro.especialidade(),new Endereco(dadosCadastro.endereco()),true);
    }
}
