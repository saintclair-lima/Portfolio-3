package br.com.flordeliz.dao;
// *
// * @author saintclair

import static br.com.flordeliz.controlador.InterfaceControle.endereco;
import static br.com.flordeliz.controlador.InterfaceControle.senha;
import static br.com.flordeliz.controlador.InterfaceControle.usuario;
import br.com.flordeliz.modelo.InsercaoException;
import br.com.flordeliz.modelo.ItemEstoque;
import br.com.flordeliz.modelo.LoteProducao;
import java.text.ParseException;
import java.util.GregorianCalendar;


// */
public class NewClass {
    public static void main (String args[]) throws InsercaoException, ParseException{
        ItemEstoqueDAO itemEstoqueDAO = new ItemEstoqueDAO();
        ItemEstoque item = itemEstoqueDAO.buscar(usuario, senha, endereco, 12);
        LoteProducao lote = new LoteProducao(0,new GregorianCalendar(),0,item);
        
        System.out.println(lote.getDataStringFormatada());
    }
}
