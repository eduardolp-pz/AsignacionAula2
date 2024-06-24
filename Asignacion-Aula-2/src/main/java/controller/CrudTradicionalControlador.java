package controller;

import entity.Estudiante;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import services.FakeServices;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrudTradicionalControlador {
    private final static FakeServices fakeServices = FakeServices.getInstancia();

    public static void listar(@NotNull Context ctx) throws Exception {
        List<Estudiante> lista = fakeServices.listarEstudiante();
        System.out.println(lista);
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("titulo", "Listado de Estudiante");
        modelo.put("lista", lista);
        ctx.render("/templates/crud-tradicional/listar.html", modelo);
    }

    public static void crearEstudianteForm(@NotNull Context ctx) throws Exception {
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("titulo", "Formulario Creación Estudiante");
        modelo.put("accion", "/crud-simple/crear");
        ctx.render("/templates/crud-tradicional/crearEditarVisualizar.html", modelo);
    }

    public static void procesarCreacionEstudiante(@NotNull Context ctx) throws Exception {
        int matricula = ctx.formParamAsClass("matricula", Integer.class).get();
        String nombre = ctx.formParam("nombre");
        String carrera = ctx.formParam("carrera");
        //
        Estudiante tmp = new Estudiante(matricula, nombre, carrera);
        fakeServices.crearEstudiante(tmp);
        ctx.redirect("/crud-simple/");
    }

    public static void visualizarEstudiante(@NotNull Context ctx) throws Exception {
        Estudiante estudiante = fakeServices.getEstudiantePorMatricula(ctx.pathParamAsClass("matricula", Integer.class).get());
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("titulo", "Formulario Visaulizar Estudiante "+estudiante.getMatricula());
        modelo.put("estudiante", estudiante);
        modelo.put("visualizar", true);
        modelo.put("accion", "/crud-simple/");
        ctx.render("/templates/crud-tradicional/crearEditarVisualizar.html", modelo);
    }

    public static void editarEstudianteForm(@NotNull Context ctx) throws Exception {
        Estudiante estudiante = fakeServices.getEstudiantePorMatricula(ctx.pathParamAsClass("matricula", Integer.class).get());
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("titulo", "Formulario Editar Estudiante "+estudiante.getMatricula());
        modelo.put("estudiante", estudiante);
        modelo.put("accion", "/crud-simple/editar");
        ctx.render("/templates/crud-tradicional/crearEditarVisualizar.html", modelo);
    }

    public static void procesarEditarEstudiante(@NotNull Context ctx) throws Exception {
        int matricula = ctx.formParamAsClass("matricula", Integer.class).get();
        String nombre = ctx.formParam("nombre");
        String carrera = ctx.formParam("carrera");
        Estudiante tmp = new Estudiante(matricula, nombre, carrera);
        fakeServices.actualizarEstudiante(tmp);
        ctx.redirect("/crud-simple/");
    }

    public static void eliminarEstudiante(@NotNull Context ctx) throws Exception {
        fakeServices.eliminandoEstudiante(ctx.pathParamAsClass("matricula", Integer.class).get());
        ctx.redirect("/crud-simple/");
    }

}
