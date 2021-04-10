
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class ModuloAlumno {
    //Llamar las clases necesarias
    Contenedores datos=new Contenedores();
    Guardar_Cargar_Datos GCD=new Guardar_Cargar_Datos();
    Login login=new Login();
    CursoAl info=new CursoAl();
    //Frames para el funcionamiento de la clase
    JFrame frameprincipal=new JFrame("Modulo de Profesores"),frameactua=new JFrame("");
    //Arreglo de botones y labels de curso
    JButton[] botonCurso=new JButton[50];
    JLabel[] labelCurso=new JLabel[50];
    //variable para codigo curso al que se llama
    int codigoCurso=0;
    
    void ventana(){
        //Pantalla de Inicio
        frameprincipal=new JFrame("Modulo de Alumno");
        frameprincipal.setSize(1350, 700);
        frameprincipal.setLayout(null);
        frameprincipal.getContentPane().setBackground(Color.orange);
        frameprincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameprincipal.setVisible(true);
        
        //Boton para cerrar Sesión
        JButton Bcierre=new JButton("Cerrar Sesión");
        Bcierre.setBounds(new Rectangle(1200, 0, 130, 30));
        Bcierre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GCD.guardar();
                login.pantallaLogin.setVisible(true);
                frameprincipal.dispose();
            }
        });
        frameprincipal.add(Bcierre);
        
        //Boton para actualizar datos
        JButton Bactu=new JButton("Actualizar Datos");
        Bactu.setBounds(new Rectangle(1040, 0, 130, 30));
        Bactu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actualizar();
                frameprincipal.dispose();
            }
        });
        frameprincipal.add(Bactu);
        
        //Labels del Frame
        JLabel titulo=new JLabel("Cursos Asignados");
        titulo.setFont(new Font("Serif",Font.PLAIN,30));
        titulo.setBounds(10, 10, 400, 35);
        frameprincipal.add(titulo);
        
        //Posiciones X y Y
        int x=10,y=80,x1=0;
        //Ciclo para crear botones y su funcionamiento
        int a=0;
        while(a<datos.cant_In){
            //Se determina si el profesor tiene asignado ese curso
            if(datos.usuario==datos.codigoAC[a]){
                //Se busca la información del curso
                int b=0;
                while(b<datos.cantCur){
                    if(datos.codigoCC[a]==datos.codigoC[b]){break;}
                    b++;
                }
                //Se establecen las características del boton
                botonCurso[a]=new JButton(datos.nombreC[b]);
                botonCurso[a].setFont(new Font("Serif",Font.PLAIN,40));
                botonCurso[a].setBounds(x,y, 300, 50);
                //Se establecen las características del label
                //Se busca al profesor del curso
                int d=0;
                while(d<datos.cantPro){
                    if (datos.codigoPC[b]==datos.codigoP[d]) {
                       break; 
                    }
                    d++;
                }
                labelCurso[a]= new JLabel(datos.nombreP[d]+" Alumnos",SwingConstants.RIGHT);
                labelCurso[a].setBounds(x,y+50, 300, 20);
                labelCurso[a].setBackground(Color.lightGray);
                labelCurso[a].setOpaque(true);
                frameprincipal.add(botonCurso[a]);
                frameprincipal.add(labelCurso[a]);
                x+=320;
                //Se comprueba si la posición en x es demasiado grande
                if (x>=1290) {
                    x=10;
                    y+=100;
                }
                
                //La variable guarda el codigo del curso que le pertenece al boton
                int c=datos.codigoCC[a];
                //Se le da una acción al boton
                botonCurso[a].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        //Se manda el codigo determinado a la clase Administración de Cursos
                        info.cursoseleccionado=c;
                        info.codalumno=datos.usuario;
                        info.ventana();
                    }
                });
                
            }
            a++;   
        }
    }
    
    void actualizar(){
        //Se buscan los datos del profesor
        int a=0;
        while(a<datos.cantAlu){
            if(datos.usuario==datos.codigoA[a]){break;}
            a++;
        }
        
        frameactua=new JFrame("");
        frameactua.setSize(500, 450);
        frameactua.setLayout(null);
        frameactua.getContentPane().setBackground(Color.ORANGE);
        frameactua.setVisible(true);
        frameactua.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Labels del Frame
                JLabel titulo=new JLabel("Actualizar datos del alumno");
                titulo.setFont(new Font("Serif",Font.PLAIN,30));
                titulo.setBounds(10, 10, 400, 35);
                frameactua.add(titulo);
                
        //Crear y establecer Labels y su Text para el Frame
                JLabel Lcodigo=new JLabel("Codigo",SwingConstants.CENTER);
                Lcodigo.setFont(new Font("Serif",Font.PLAIN,20));
                Lcodigo.setBounds(10,60,100,25);
                frameactua.add(Lcodigo);
                JLabel Tcodigo=new  JLabel(datos.codigoA[a]+"");
                Tcodigo.setFont(new Font("Serif",Font.PLAIN,20));
                Tcodigo.setBounds(120,60,300,25);
                Tcodigo.setBackground(Color.white);
                Tcodigo.setOpaque(true);
                frameactua.add(Tcodigo);
                
                JLabel Lnombre=new JLabel("Nombre");
                Lnombre.setFont(new Font("Serif",Font.PLAIN,20));
                Lnombre.setBounds(10,100,100,25);
                frameactua.add(Lnombre);
                JTextField Tnombre=new  JTextField(datos.nombreA[a]);
                Tnombre.setFont(new Font("Serif",Font.PLAIN,20));
                Tnombre.setBounds(120,100,300,25);
                frameactua.add(Tnombre);
                
                JLabel Lapellido=new JLabel("Apellido");
                Lapellido.setFont(new Font("Serif",Font.PLAIN,20));
                Lapellido.setBounds(10,140,100,25);
                frameactua.add(Lapellido);
                JTextField Tapellido=new  JTextField(datos.apellidoA[a]);
                Tapellido.setFont(new Font("Serif",Font.PLAIN,20));
                Tapellido.setBounds(120,140,300,25);
                frameactua.add(Tapellido);
                
                JLabel Lcorreo=new JLabel("Correo");
                Lcorreo.setFont(new Font("Serif",Font.PLAIN,20));
                Lcorreo.setBounds(10,180,100,25);
                frameactua.add(Lcorreo);
                JTextField Tcorreo=new  JTextField(datos.correoA[a]);
                Tcorreo.setFont(new Font("Serif",Font.PLAIN,20));
                Tcorreo.setBounds(120,180,300,25);
                frameactua.add(Tcorreo);
                
                JLabel Lcontraseña=new JLabel("Contraseña");
                Lcontraseña.setFont(new Font("Serif",Font.PLAIN,20));
                Lcontraseña.setBounds(10,220,100,25);
                frameactua.add(Lcontraseña);
                JTextField Tcontraseña=new  JTextField(datos.contraseñaA[a]);
                Tcontraseña.setFont(new Font("Serif",Font.PLAIN,20));
                Tcontraseña.setBounds(120,220,300,25);
                frameactua.add(Tcontraseña);
                
                JLabel Lgenero=new JLabel("Genero");
                Lgenero.setFont(new Font("Serif",Font.PLAIN,20));
                Lgenero.setBounds(10,260,100,25);
                frameactua.add(Lgenero);
                String generos[]={datos.generoA[a],"m","f"};
                JComboBox Cgenero=new JComboBox(generos);
                Cgenero.setFont(new Font("Serif",Font.PLAIN,20));
                Cgenero.setBounds(120,260,100,25);
                frameactua.add(Cgenero);
                
                //Button Encargado para actualizar datos
                JButton Bactu=new JButton("Actualizar");
                Bactu.setBounds(100,310,300,25);
                Bactu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    //Buscar al profesor seleccionado
                    int a=0;
                    while(a<datos.cantAlu){
                        //Se establece si el código es el mismo
                        if(datos.usuario==datos.codigoA[a]){ break;}
                        a++;
                    }
                    //Dar los valores escritos y seleccionado por el usuario
                    datos.nombreA[a]=Tnombre.getText();
                    datos.apellidoA[a]=Tapellido.getText();
                    datos.correoA[a]=Tcorreo.getText();
                    datos.contraseñaA[a]=Tcontraseña.getText();
                    datos.generoA[a]=Cgenero.getSelectedItem().toString();
                    
                    //Guardar valores
                    GCD.guardar();
                    ventana();
                    frameactua.dispose();
                    JOptionPane.showMessageDialog(null,"Se actualizaron los datos");
                }
                });
                frameactua.add(Bactu);
    }
}
