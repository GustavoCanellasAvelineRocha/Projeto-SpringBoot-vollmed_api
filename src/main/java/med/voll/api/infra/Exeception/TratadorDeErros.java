package med.voll.api.infra.Exeception;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.ValidacaoExecption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException exception){
        List<FieldError> error = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(error.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity tratarErro409(SQLException exception){
        String duplicateField = buildDuplicateError(exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(duplicateField + " Esta duplicado no Banco de dados!");
    }

    private record DadosErroValidacao(String field, String msg){
        public DadosErroValidacao(FieldError error){
            this(error.getField(),error.getDefaultMessage());
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity tratarErro400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity tratarErroBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity tratarErroAuthentication() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity tratarErroAcessoNegado() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity tratarErro500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " +ex.getLocalizedMessage());
    }

    @ExceptionHandler(ValidacaoExecption.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoExecption ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    private String buildDuplicateError(String erro){
        var acha= erro.lastIndexOf(".");
        acha++;
        StringBuilder cria = new StringBuilder();
        char aux;
        while((aux = erro.charAt(acha)) != '\''){
            cria.append(aux);
            acha++;
        }

        return cria.toString();
    }
}
