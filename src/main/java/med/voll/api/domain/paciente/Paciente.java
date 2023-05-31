package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;


@Table(name = "Pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    @Embedded
    private Endereco endereco;
    private boolean ativo;

    public Paciente(DadosCadastroPaciente dados){
        nome = dados.nome();
        email = dados.email();
        telefone = dados.telefone();
        cpf = dados.cpf();
        endereco = new Endereco(dados.endereco());
        ativo = true;
    }

    public void atualiza(DadosAtualizacaoPaciente dados) {
        if(dados.nome() !=null){
            this.nome = dados.nome();
        }
        if(dados.telefone() !=null){
            this.telefone = dados.telefone();
        }
        if(dados.endereco() !=null){
            endereco.atualizarInformacoes(dados.endereco());
        }
    }

    public void exclui() {
        ativo = false;
    }
}
