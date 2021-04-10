
import javax.swing.JOptionPane;

public class Redireccionar {
    //LLamar clases necesarias
    Login login =new Login();
    Contenedores datos=new Contenedores();
    ModuloAdministracion admin=new ModuloAdministracion();
    ModuloProfesores profe=new ModuloProfesores();
    ModuloAlumno alum= new ModuloAlumno();
    
    public void abrir(String codigo,String contraseña){
        //Contador para iniciar el ciclo que se encarga de buscar al usuario
        int a=0,encontrado=0;
        while(a<1){
            //Se determina si el codigo y la contraseña es del administrador
            if(codigo.equals("admin" ) && contraseña.equals("admin")){
                //Se oculta la ventana de login
                login.pantallaLogin.setVisible(false);
                //Se abre el modulo de administracion
                admin.menu();
                //Se termina el ciclo
                break;
            }
            //Ciclo para determinar si el codigo y contraseña pertenece a un profesor
            int b=0;
            while (b<datos.cantPro) {
                int cod=Integer.parseInt(codigo);
                //Se confirma si el codigo y contraseña coinciden
                if(cod==datos.codigoP[b] && contraseña.equalsIgnoreCase(datos.contraseñaP[b])){
                    datos.usuario=datos.codigoP[b];
                    //Se establce que ya se encontro al usuario
                    encontrado++;
                    //Se oculta la ventana de login
                    login.pantallaLogin.setVisible(false);
                    profe.ventana();
                    break;
                }
                b++;
            }
            //Ciclo para determinar si el codigo y contraseña pertenece a un alumno
            b=0;
            while (b<datos.cantAlu) {
                int cod=Integer.parseInt(codigo);
                //Se confirma si el codigo y contraseña coinciden
                if(cod==datos.codigoA[b] && contraseña.equalsIgnoreCase(datos.contraseñaA[b])){
                    datos.usuario=datos.codigoA[b];
                    encontrado++;
                    //Se oculta la ventana de login
                    login.pantallaLogin.setVisible(false);
                    alum.ventana();
                    break;
                }
                b++;
            }
            //Se determina si ya se encontro al profesor o al alumno
            if (encontrado==1) {break;}
            //Se aumenta la variable si no se ha encontrado ninguna coincidencia
            a++;
        }
        if(a==1){
            JOptionPane.showMessageDialog(null,"Codigo o Contraseña incorrecta");
        }
    }
}
