package med.voll.api.domain.paciente;

public record DadosDetalhamentoPaciente(long id,String nome,String email,String telefone, String cpf,boolean ativo) {
    public DadosDetalhamentoPaciente (Paciente paciente){
        this(paciente.getId(),paciente.getNome(),paciente.getEmail(),paciente.getTelefone(),paciente.getCpf(),paciente.isAtivo());
    }

}
