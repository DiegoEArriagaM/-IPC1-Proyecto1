import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;

public class Guardar_Cargar_Datos {
    //LLamar clases necesarias
    DatosGuardado datosG=new DatosGuardado();
    Contenedores datosC=new Contenedores();
    
    //Metodo para guardar datos
    void guardar(){
        //Se guardan los datos en la clase DatosGuardados
        datosG.GuardarProfesores(datosC.codigoP, datosC.nombreP, datosC.apellidoP, datosC.correoP, datosC.generoP, 
                datosC.contraseñaP,datosC.cantPro);
        
        datosG.GuardarCursos(datosC.codigoC, datosC.creditosC, datosC.alumnosC, datosC.codigoPC, datosC.nombreC,
                             datosC.cantCur,datosC.acumuladoC);
        
        datosG.GuardarAlumnos(datosC.codigoA, datosC.nombreA, datosC.apellidoA, datosC.correoA, 
                            datosC.generoA, datosC.contraseñaA, datosC.cantAlu);
        
        datosG.GuardarAsignacion(datosC.cant_In,datosC.codigoCC, datosC.codigoAC);
        
        datosG.GuardarActividad(datosC.cant_Ac, datosC.codigoC_Ac, datosC.codigoAC_Ac, 
                datosC.ponderacion_Ac, datosC.nombre_Ac, datosC.descripcion_Ac,
                datosC.notaAC,datosC.cant_AcIn,datosC.promedio,datosC.codigoC_Ac2,
                datosC.ponderacion_Ac2,datosC.codigo_AC,datosC.codigo_AC2);
        
        try {
            //Crear documento tipo binario
            ObjectOutputStream oos;
            oos=new ObjectOutputStream(new FileOutputStream("datos.bin"));
            //Se elige la clase que se transformara en el documento binario
            oos.writeObject(datosG);
            oos.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    //Metodo para cargar datos
    void cargar(){
        ObjectInputStream ois;
        try {
            //Tomar documento tipo binario
            ois=new ObjectInputStream(new FileInputStream("datos.bin"));
            //Se carga el valor del documento binario a la clase necesario
            DatosGuardado valores=(DatosGuardado)ois.readObject();
            //Datos de los profesores
            datosC.codigoP=valores.getCodigoP();
            datosC.nombreP=valores.getNombreP();
            datosC.apellidoP=valores.getApeliidoP();
            datosC.correoP=valores.getCorreoP();
            datosC.generoP=valores.getGeneroP();
            datosC.contraseñaP=valores.getContraseñaP();
            datosC.cantPro=valores.getCantPro();
            
            //Datos de los cursos
            datosC.codigoC=valores.getCodigoC();
            datosC.creditosC=valores.getCreditosC();
            datosC.alumnosC=valores.getAlumnosC();
            datosC.codigoPC=valores.getCodigoPC();
            datosC.nombreC=valores.getNombreC();
            datosC.cantCur=valores.getCantCur();
            datosC.acumuladoC=valores.getAcumuladoC();
            
            //Datos de los alumnos
            datosC.codigoA=valores.getCodigoA();
            datosC.nombreA=valores.getNombreA();
            datosC.apellidoA=valores.getApellidoA();
            datosC.correoA=valores.getCorreoA();
            datosC.generoA=valores.getGeneroA();
            datosC.contraseñaA=valores.getContraseñaA();
            datosC.cantAlu=valores.getCantAlu();
            
            //Datos Asignaciones
            datosC.cant_In=valores.getCant_In();
            datosC.codigoCC=valores.getCodigoCC();
            datosC.codigoAC=valores.getCodigoAC();
            
            //Datos Actividades
            datosC.cant_Ac=valores.getCant_Ac(); 
            datosC.codigoC_Ac=valores.getCodigoC_Ac(); 
            datosC.codigoAC_Ac=valores.getCodigoAC_Ac(); 
            datosC.ponderacion_Ac=valores.getPonderacion_Ac(); 
            datosC.nombre_Ac=valores.getNombre_Ac(); 
            datosC.descripcion_Ac=valores.getDescripcion_Ac();
            datosC.notaAC=valores.getNotaAC();
            datosC.cant_AcIn=valores.getCant_AcIn();
            datosC.promedio=valores.getPromedio();
            datosC.codigoC_Ac2=valores.getCodigoC_Ac2();
            datosC.ponderacion_Ac2=valores.getPonderacion_Ac2();
            datosC.codigo_AC=valores.getCodigo_AC();
            datosC.codigo_AC2=valores.getCodigo_AC2();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se han guardado datos de usuarios anteriormente.");
        }
    }
}
