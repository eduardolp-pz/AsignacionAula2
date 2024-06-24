package org.example;

//Del proyecto Javalin-demo (https://github.com/vacax/javalin-demo), vamos a implementar la funcionalidad de persistir la información en la  base de datos del proceso CRUD presentando en la clase controlador CrudTradicionalControlador. Para ellos realice lo siguiente:
//
//  Realizar un proyecto y copiar las clases y documentos necesarios para ejecutar el CrudTradicionalControlador (los archivos mínimos necesarios).
//Agregar H2 al proyecto.
//Implementar clase BootStrapServices (https://github.com/vacax/DemoJdbc/blob/master/src/main/java/com/avathartech/demojdbc/services/BootStrapServices.java) para inicio en modo servidor de H2 y creación de la tabla necesario para la clase utilizada en el proyecto.
//  Crear clase servicio que implemente el CRUD integrador en la vista.
//  Subir la el URL del repositorio, no es necesario realizar el reporte.

import controller.CrudTradicionalControlador;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.bundled.RouteOverviewPlugin;
import io.javalin.rendering.template.JavalinThymeleaf;
import services.BootstrapService;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class Main {
  public static void main(String[] args) {
    String mensaje = "Hola Mundo en Javalin :-D";
    System.out.println(mensaje);

    BootstrapService bootstrapService = new BootstrapService();
    bootstrapService.startDb();

    Javalin app = Javalin.create(config ->{
      config.staticFiles.add(staticFileConfig -> {
        staticFileConfig.hostedPath = "/";
        staticFileConfig.directory = "/publico";
        staticFileConfig.location = Location.CLASSPATH;
        staticFileConfig.precompress=false;
        staticFileConfig.aliasCheck=null;
      });

      config.fileRenderer(new JavalinThymeleaf());

      config.router.apiBuilder(() -> {
        path("/crud-simple/", () -> {
          get(ctx -> {
            ctx.redirect("/crud-simple/listar");
          });
          get("/listar", CrudTradicionalControlador::listar);
          get("/crear",CrudTradicionalControlador::crearEstudianteForm);
          post("/crear",CrudTradicionalControlador::procesarCreacionEstudiante);
          get("/visualizar/{matricula}",CrudTradicionalControlador::visualizarEstudiante);
          get("/editar/{matricula}",CrudTradicionalControlador::editarEstudianteForm);
          post("/editar",CrudTradicionalControlador::procesarEditarEstudiante);
          get("/eliminar/{matricula}",CrudTradicionalControlador::eliminarEstudiante);
        });
      });

      config.registerPlugin(new RouteOverviewPlugin());
  }).start(7070);
  }
}