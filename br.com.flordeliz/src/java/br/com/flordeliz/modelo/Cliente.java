package br.com.flordeliz.modelo;
import br.com.flordeliz.utils.CNP;
import br.com.flordeliz.utils.Utils;
/**
 *
 * @author saintclair
 */
public class Cliente {
    private int codigo;
    private String nome;
    private String loja;
    private String fone;
    private String endereco;
    private String cpf;
    private String cnpj;
    
    public Cliente(int codigo, String nome, String loja, String fone, String endereco, String cpf, String cnpj) throws InsercaoException{
        this.setCodigo(codigo);
        this.setNome(nome);
        this.setLoja(loja);
        this.setFone(fone);
        this.setEndereco(endereco);
        this.setCpf(cpf);
        this.setCnpj(cnpj);
    }
    
    public int getCodigo() {
        return codigo;
    }
    
    public void setCodigo(int codigo) throws InsercaoException {
        if (codigo < 0){
            throw new InsercaoException("Codigo invalido: valor menor que zero");
        } else {
            this.codigo = codigo;
        }
    }

    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) throws InsercaoException {
        if (nome.length() > 35){
            throw new InsercaoException("Nome inválido: número de caracteres maior que 35");
        } else {
            this.nome = nome;
        }
    }
    
    public String getLoja() {
        return loja;
    }
    
    public void setLoja(String loja) throws InsercaoException {
        loja = Utils.checaNull(loja);
        if (loja.length() > 35){
            throw new InsercaoException("Nome da loja inválido: número de caracteres maior que 35");
        } else {
            this.loja = loja;
        }
    }
    
    public String getFone() {
        return fone;
    }
    
    public void setFone(String fone) throws InsercaoException {
        fone = Utils.checaNull(fone);
        
        if (fone.length() > 11){
            throw new InsercaoException("Numero de telefone inválido: mais que 15 caracteres");
        } else {
            this.fone = fone;
        }
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) throws InsercaoException {
        if (endereco.length() > 100){
            throw new InsercaoException("Endereco inválido: mais que 100 caracteres");
        } else {
            this.endereco = endereco;
        }
    }
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf)  throws InsercaoException {
        if (! CNP.isValidCPF(cpf)){
            throw new InsercaoException("CPF invalido");
        } else {
            this.cpf = cpf;
        }
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj)  throws InsercaoException {
        cnpj = Utils.checaNull(cnpj);
        
        if (cnpj.equals("")){
            this.cnpj = cnpj;
        }else if (! CNP.isValidCNPJ(cnpj)){
            throw new InsercaoException("CNPJ invalido");
        } else {
            this.cnpj = cnpj;
        }
    }
}
