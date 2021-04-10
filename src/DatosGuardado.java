import java.io.Serializable;

public class DatosGuardado implements Serializable{
    //Objetos de la información de los profesores
    private int codigoP[]=new int[50];
    private String nombreP[]=new String[50],apeliidoP[]=new String[50],correoP[]=new String[50],
            generoP[]=new String[50],contraseñaP[]=new String[50];
    private int cantPro=0;
    
    //Objetos de la información de los cursos
    private  int[] codigoC=new int[50],creditosC=new int[50],alumnosC=new int[50],codigoPC=new int[50];
    private  String[] nombreC=new String[50];
    private int cantCur=0;
    private double acumuladoC[]=new double[50];
    
    //Objetos de la información de los alumnos
    private int[] codigoA=new int[300];
    private String[] nombreA=new String[300],apellidoA=new String[300],correoA=new String[300],
            generoA=new String[300],contraseñaA=new String[300];
    private int cantAlu=0;
    
    //Objetos para la asignación de alumnos a cursos
    private int cant_In=0;
    private int[] codigoCC=new int[cant_In],codigoAC=new int[cant_In];
    
    //Objetos para las actividades de los cursos
    private int cant_Ac=0,cant_AcIn=0;
    private int[] codigoC_Ac=new int[cant_Ac],codigoC_Ac2=new int[cant_AcIn],
            codigoAC_Ac=new int[cant_Ac],codigo_AC=new int[cant_Ac],codigo_AC2=new int[cant_AcIn];
    private double[] notaAC=new double[cant_Ac],promedio=new double[cant_AcIn]
            ,ponderacion_Ac=new double[cant_AcIn],ponderacion_Ac2=new double[cant_AcIn];
    private String[] nombre_Ac=new String[cant_AcIn],descripcion_Ac=new String[cant_AcIn];
    
    public static final long serialVersionUID=1L;
    
    //Metodo para guardar arreglos de profesores
    void GuardarProfesores(int codigoP[],String nombreP[],String apeliidoP[],String correoP[], 
            String generoP[],String contraseñaP[], int cantPro){
        //Se ingresan los valores de los profesores a su arreglo correspondiente
        this.codigoP=codigoP;
        this.nombreP=nombreP;
        this.apeliidoP=apeliidoP;
        this.correoP=correoP;
        this.generoP=generoP;
        this.contraseñaP=contraseñaP;
        this.cantPro=cantPro;
    }
    
    ////Metodo para guardar arreglos de cursos
    void GuardarCursos(int[]codigoC,int[]creditosC,int[]alumnosC,int[]codigoPC,String[]nombreC,
                       int cantCur, double[] acumuladoC){
        this.codigoC=codigoC;
        this.creditosC=creditosC;
        this.alumnosC=alumnosC;
        this.codigoPC=codigoPC;
        this.nombreC=nombreC;
        this.cantCur=cantCur;
        this.acumuladoC=acumuladoC;
    }
    
    //Metodo para guardar arreglos de alumnos
    void GuardarAlumnos(int codigoA[],String nombreA[],String apeliidoA[],String correoA[], 
            String generoA[],String contraseñaA[], int cantAlum){
        //Se ingresan los valores de los profesores a su arreglo correspondiente
        this.codigoA=codigoA;
        this.nombreA=nombreA;
        this.apellidoA=apeliidoA;
        this.correoA=correoA;
        this.generoA=generoA;
        this.contraseñaA=contraseñaA;
        this.cantAlu=cantAlum;
    }
    
    //Metodo para guardar arreglos de asignacion de curso
    void GuardarAsignacion(int cant_In, int[] codigoCC, int[] codigoAC){
        //Se ingresan los valores de las asignaciones
        this.cant_In=cant_In;
        this.codigoCC=codigoCC;
        this.codigoAC=codigoAC;
    }
    
    //Metodo para guardar arreglos de asignacion de actividades
    void GuardarActividad(int cant_Ac, int[]codigoC_Ac, int[]codigoAC_Ac, double[]ponderacion_Ac,
            String[] nombre_Ac,String[]descripcion_Ac,double notaAC[],int cant_AcIn, double[] promedio,
            int[] codigoC_Ac2,double[] ponderacion_Ac2,int[] codigo_AC,int[] codigo_AC2){
        this.cant_Ac=cant_Ac;
        this.codigoC_Ac=codigoC_Ac;
        this.codigoAC_Ac=codigoAC_Ac;
        this.ponderacion_Ac=ponderacion_Ac;
        this.nombre_Ac=nombre_Ac;
        this.descripcion_Ac=descripcion_Ac;
        this.notaAC=notaAC;
        this.cant_AcIn=cant_AcIn;
        this.promedio=promedio;
        this.codigoC_Ac2=codigoC_Ac2;
        this.ponderacion_Ac2=ponderacion_Ac2;
        this.codigo_AC=codigo_AC;
        this.codigo_AC2=codigo_AC2;
        
    }
    
//Getters
    
    public int[] getCodigo_AC2() {
        return codigo_AC2;
    }

    public int[] getCodigo_AC() {
        return codigo_AC;
    }

    public double[] getPonderacion_Ac2() {
        return ponderacion_Ac2;
    }

    public int[] getCodigoC_Ac2() {
        return codigoC_Ac2;
    }

    public double[] getPromedio() {
        return promedio;
    }

    public int getCant_AcIn() {
        return cant_AcIn;
    }

    public double[] getNotaAC() {
        return notaAC;
    }

    public int[] getCodigoP() {
        return codigoP;
    }

    public String[] getNombreP() {
        return nombreP;
    }

    public String[] getApeliidoP() {
        return apeliidoP;
    }

    public String[] getCorreoP() {
        return correoP;
    }

    public int[] getCodigoCC() {
        return codigoCC;
    }

    public int[] getCodigoAC() {
        return codigoAC;
    }

    public String[] getGeneroP() {
        return generoP;
    }

    public double[] getAcumuladoC() {
        return acumuladoC;
    }

    public int getCantPro() {
        return cantPro;
    }

    public int[] getCodigoA() {
        return codigoA;
    }

    public String[] getNombreA() {
        return nombreA;
    }

    public String[] getApellidoA() {
        return apellidoA;
    }

    public String[] getCorreoA() {
        return correoA;
    }

    public String[] getGeneroA() {
        return generoA;
    }

    public String[] getContraseñaA() {
        return contraseñaA;
    }

    public int getCantAlu() {
        return cantAlu;
    }

    public String[] getContraseñaP() {
        return contraseñaP;
    }

    public int[] getCodigoC() {
        return codigoC;
    }

    public int[] getAlumnosC() {
        return alumnosC;
    }

    public String[] getNombreC() {
        return nombreC;
    }

    public int[] getCreditosC() {
        return creditosC;
    }

    public int[] getCodigoPC() {
        return codigoPC;
    }

    public int getCantCur() {
        return cantCur;
    }

    public int getCant_Ac() {
        return cant_Ac;
    }

    public int[] getCodigoC_Ac() {
        return codigoC_Ac;
    }

    public int[] getCodigoAC_Ac() {
        return codigoAC_Ac;
    }

    public double[] getPonderacion_Ac() {
        return ponderacion_Ac;
    }

    public String[] getNombre_Ac() {
        return nombre_Ac;
    }

    public String[] getDescripcion_Ac() {
        return descripcion_Ac;
    }

    public int getCant_In() {
        return cant_In;
    }
    
    
   
}
