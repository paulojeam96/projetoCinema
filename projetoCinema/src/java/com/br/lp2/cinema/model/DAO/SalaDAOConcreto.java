/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.lp2.cinema.model.DAO;

import com.br.lp2.cinema.model.ConnectionFactory.ConnectionFactory;
import com.br.lp2.cinema.model.javabeans.Sala;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paulo
 */
public class SalaDAOConcreto implements SalaDAO{
    
    private Connection connection;
    private PreparedStatement pst;
    private ResultSet rs;
    
    public SalaDAOConcreto(){
        ConnectionFactory cf = new ConnectionFactory();
        connection = cf.getConnection("derby");
    }

    @Override
    public boolean insertSala(Sala sala) {
        boolean resultado = false;
        try{
            String sql = "INSERT INTO cinema.sala(num,lotacao,poltEsp,estado) VALUES (?,?,?,?)";
            pst = connection.prepareStatement(sql);
            
            pst.setInt(1, sala.getNum());
            pst.setInt(2, sala.getLotacao());
            pst.setInt(3, sala.getPoltEsp());
            pst.setObject(4, sala.getEstadoSala());
            resultado = pst.execute();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return resultado;
    }

    @Override
    public ArrayList<Sala> readSalas() {
        ArrayList<Sala> lista = new ArrayList<>();
        try{
            String sql = "SELECT * FROM  cinema.sala";
            pst = connection.prepareStatement(sql);
            
            rs = pst.executeQuery();
            while(rs.next()){
                Sala a = new Sala(rs.getInt("pk"), rs.getInt("num"), rs.getInt("lotacao"), rs.getInt("poltEsp"), (Sala.Estados)rs.getObject("estadoSala"));
                lista.add(a);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return lista;
    }

    @Override
    public Sala readSalaById(int id) {
        Sala a = null;
        
        try {
            String sql = "SELECT * FROM cinema.sala WHERE id=?";
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            rs=pst.executeQuery();
            while (rs.next()) {
                a = new Sala(rs.getInt("pk"), rs.getInt("num"), rs.getInt("lotacao"), rs.getInt("poltEsp"), (Sala.Estados)rs.getObject("estadoSala"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    @Override
    public Sala readSalaByEstados(Sala.Estados estado) {
        Sala a = null;
        
        try{
            String sql = "SELECT * FROM cinema.sala WHERE estado=?";
            pst = connection.prepareStatement(sql);
            pst.setObject(1, estado);
            rs = pst.executeQuery();
            while(rs.next()){
                a = new Sala(rs.getInt("pk"), rs.getInt("num"), rs.getInt("lotacao"), rs.getInt("poltEsp"), (Sala.Estados)rs.getObject("estadoSala"));
           }
        } catch( SQLException ex){
        ex.printStackTrace();
        }
        return a;
    }

    @Override
    public boolean updateSala(int id, Sala sala) {
        boolean res =false;
        
        try{
            String sql = "UPDATE cinema.sala SET num=? WHERE id=?";
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            int r = pst.executeUpdate();
            if(r > 0) res = true;
            else res = false;
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean deleteSala(Sala sala) {
        boolean resultado=false;
        try {
            String sql = "DELETE FROM cinema.sala WHERE id=?";
            pst = connection.prepareStatement(sql);
            pst.setInt(1,sala.getPk());
            int r = pst.executeUpdate();
            if(r>0) resultado = true;
            else resultado = false;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    @Override
    public boolean deleteSala(int id) {
        boolean res = false;
        try{
            String sql = "DELETE FROM cinema.sala WHERE id=?";
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            int r = pst.executeUpdate();
            if (r > 0) res = true;
            else res = false;
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return res;
    
    }
    
}
