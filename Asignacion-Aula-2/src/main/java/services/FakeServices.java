package services;

import entity.Estudiante;
import util.BaseServiceDatabase;
import util.NoExisteEstudianteException;
import java.util.List;

public class FakeServices extends BaseServiceDatabase<Estudiante> {
    private static FakeServices instancia;

    private FakeServices(){
      super(Estudiante.class);
      this.dbCreate(new Estudiante(20011136, "Carlos Camacho", "ITT"));
    }

    public static FakeServices getInstancia(){
        if(instancia==null){
            instancia = new FakeServices();
        }
        return instancia;
    }

    public List<Estudiante> listarEstudiante(){
        return this.dbFindAll();
    }

    public Estudiante getEstudiantePorMatricula(int matricula){
        return this.dbFind(matricula);
    }

    public Estudiante crearEstudiante(Estudiante estudiante){
        if(getEstudiantePorMatricula(estudiante.getMatricula())!=null){
            throw new NoExisteEstudianteException("Ya Existe el estudiante: "+estudiante.getMatricula());
        }
        return this.dbCreate(estudiante);
    }

    public Estudiante actualizarEstudiante(Estudiante estudiante){
        Estudiante tmp = getEstudiantePorMatricula(estudiante.getMatricula());
        if(tmp == null){
            throw new NoExisteEstudianteException("No Existe el estudiante: "+estudiante.getMatricula());
        }
        return this.dbModify(estudiante);
    }

    public boolean eliminandoEstudiante(int matricula){
        return this.dbRemove(matricula);
    }

}
