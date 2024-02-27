package org.example.servicios;

import org.example.encapsulaciones.Estudiante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para encapsular los metodos de CRUD
 * Created by vacax on 30/04/16.
 */
public class FakeServices {

    private static FakeServices instancia;
    private List<Estudiante> listaEstudiante = new ArrayList<>();
    public Estudiante getEstudiantePorMatricula(int matricula){
        return listaEstudiante.stream().filter(e -> e.getMatricula() == matricula).findFirst().orElse(null);
    }

    public static FakeServices getInstancia(){
        if(instancia==null){
            instancia = new FakeServices();
        }
        return instancia;
    }

    public List<Estudiante> listarEstudiante(){
        return listaEstudiante;
    }

    public boolean eliminandoEstudiante(int matricula){
        Estudiante tmp = new Estudiante();
        tmp.setMatricula(matricula);
        return listaEstudiante.remove(tmp);
    }

    /**
     * Listado de los estudiantes.
     * @return
     */
    public List<Estudiante> listaEstudiantes() {
        List<Estudiante> lista = new ArrayList<>();
        Connection con = null; //objeto conexion.
        try {
            //
            String query = "select * from estudiante";
            con = DataBaseServices.getInstancia().getConexion(); //referencia a la conexion.
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                Estudiante est = new Estudiante();
                est.setMatricula(rs.getInt("matricula"));
                est.setNombre(rs.getString("nombre"));
                est.setCarrera(rs.getString("carrera"));

                lista.add(est);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FakeServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(FakeServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return lista;
    }

    /**
     * Retorna un estudiante dado su matricula
     * @param matricula
     * @return
     */
    public Estudiante getEstudiante(int matricula) {
        Estudiante est = null;
        Connection con = null;
        try {
            //utilizando los comodines (?)...
            String query = "select * from estudiante where matricula = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setInt(1, matricula);
            //Ejecuto...
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                est = new Estudiante();
                est.setMatricula(rs.getInt("matricula"));
                est.setNombre(rs.getString("nombre"));
                est.setCarrera(rs.getString("carrera"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(FakeServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(FakeServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return est;
    }

    /**
     * 
     * @param est
     * @return
     */
    public boolean crearEstudiante(Estudiante est) {
        boolean ok = false;

        Connection con = null;
        if(getEstudiantePorMatricula(est.getMatricula())!=null) {
            try {

                String query = "insert into estudiante(matricula, nombre, apellido, telefono, carrera) values(?,?,?,?,?)";
                con = DataBaseServices.getInstancia().getConexion();
                //
                PreparedStatement prepareStatement = con.prepareStatement(query);
                //Antes de ejecutar seteo los parametros.
                prepareStatement.setInt(1, est.getMatricula());
                prepareStatement.setString(2, est.getNombre());
                prepareStatement.setString(5, est.getCarrera());
                //
                int fila = prepareStatement.executeUpdate();
                ok = fila > 0;

            } catch (SQLException ex) {
                Logger.getLogger(FakeServices.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FakeServices.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ok;
    }

    public boolean actualizarEstudiante(Estudiante est){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "update estudiante set nombre=?, apellido=?, carrera=?, telefono=? where matricula = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, est.getNombre());
            prepareStatement.setString(3, est.getCarrera());
            //Indica el where...
            prepareStatement.setInt(5, est.getMatricula());
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

        } catch (SQLException ex) {
            Logger.getLogger(FakeServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(FakeServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ok;
    }

    public boolean borrarEstudiante(int matricula){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "delete from estudiante where matricula = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);

            //Indica el where...
            prepareStatement.setInt(1, matricula);
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

        } catch (SQLException ex) {
            Logger.getLogger(FakeServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(FakeServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ok;
    }

}