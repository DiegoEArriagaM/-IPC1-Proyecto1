import java.io.Serializable;

public class Contenedores implements Serializable{
    //Variable que sirve para establcer el codigo del usuario que se esta tratando
    public static int usuario=0;
    //Objetos de la información de los profesores
    public static int[] codigoP=new int[50];
    public static String[] nombreP=new String[50],apellidoP=new String[50],correoP=new String[50],
            generoP=new String[50],contraseñaP=new String[50];
    public static int cantPro=0;

    //Objetos de la información de los cursos
    public static int[] codigoC=new int[50],creditosC=new int[50],alumnosC=new int[50],codigoPC=new int[50];
    public static double acumuladoC[]=new double[50];
    public static String[] nombreC=new String[50];
    public static int cantCur=0;
    
    //Objetos de la información de los alumnos
    public static int[] codigoA=new int[300];
    public static String[] nombreA=new String[300],apellidoA=new String[300],correoA=new String[300],
            generoA=new String[300],contraseñaA=new String[300];
    public static int cantAlu=0;
    
    //Objetos para la asignación de alumnos a cursos
    public static int cant_In=0;
    public static int[] codigoCC=new int[cant_In],codigoAC=new int[cant_In];
    
    //Objetos para las actividades de los cursos
    public static int cant_Ac=0,cant_AcIn=0;
    public static int[] codigoC_Ac=new int[cant_Ac],codigoC_Ac2=new int[cant_AcIn],codigoAC_Ac=new int[cant_Ac],
                        codigo_AC=new int[cant_Ac],codigo_AC2=new int[cant_AcIn];
    public static double[] notaAC=new double[cant_Ac],promedio=new double[cant_AcIn],
            ponderacion_Ac=new double[cant_Ac],ponderacion_Ac2=new double[cant_AcIn];
    public static String[] nombre_Ac=new String[cant_AcIn],descripcion_Ac=new String[cant_AcIn];
}
