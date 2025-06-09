/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public ProdutosDAO() {
        // Inicializando a conexão corretamente:
        conn = new conectaDAO().connectDB();
    }
    
    public void cadastrarProduto (ProdutosDTO produtos){
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, produtos.getNome());
            stmt.setInt(2, produtos.getValor());
            stmt.setString(3, produtos.getStatus());
            stmt.execute();
            
            JOptionPane.showMessageDialog(null, "Produto cadastrado!");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto");
            System.out.println("Error: "+ e.getMessage());
        }
        
        
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        String sql = "SELECT * FROM produtos";
        listagem.clear();
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
                                  
            while(rs.next()){
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                
                listagem.add(produto);
            }
        }catch(SQLException e){
            System.out.println("Erro ao listar produtos: "+e.getMessage());
        }
        
        return listagem;
    }
    
    public void venderProduto(int id){
        String sql = "UPDATE produtos SET status = 'vendido' WHERE id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            stmt.execute();
        }catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    
        
}

