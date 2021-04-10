import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login {
    //Llamar Clases Necesarias
    static Redireccionar redireccionar=new Redireccionar();
    static Guardar_Cargar_Datos GCD=new Guardar_Cargar_Datos();
    //Variables para iniciar sesion
    static String codigoI,contraseñaI;
    //Frame  de Inicio
    static JFrame pantallaLogin=new JFrame("Login");
    
    public static void main(String[] args){
        GCD.cargar();
        //Tamaño Pantalla de Inicio
        pantallaLogin.setSize(400, 400);
        pantallaLogin.setLayout(null);
        //Color de Fondo
        pantallaLogin.getContentPane().setBackground(new Color(246, 190, 15 ));
        //Se indica que sea visible
        pantallaLogin.setVisible(true);
        //FOrma predeterminada de salir del programa
        pantallaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Labels
        //Label del Titulo
        JLabel Titulo=new JLabel("DTT");
        //Tipo de letra y tamaño
        Titulo.setFont(new Font("Serif", Font.PLAIN, 30));
        //Ubicación y tamaño del recuadro
        Titulo.setBounds(150, 0, 100, 50);
        //Agregar al frame
        pantallaLogin.add(Titulo);
        
        JLabel lcodigo=new JLabel("Código");
        lcodigo.setFont(new Font("Serif", Font.PLAIN, 20));
        lcodigo.setBounds(50, 150, 80, 50);
        pantallaLogin.add(lcodigo);
        JLabel lcontaseña=new JLabel("Contraseña");
        lcontaseña.setFont(new Font("Serif", Font.PLAIN, 20));
        lcontaseña.setBounds(50, 200, 100, 50);
        pantallaLogin.add(lcontaseña);
        
        //Celdas de Texto
        JTextField Tcodigo=new JTextField();
        //Ubicación y tamaño del recuadro
        Tcodigo.setBounds(150, 165, 200, 20);
        //Agregar al frame
        pantallaLogin.add(Tcodigo);
        
        JPasswordField Tcontraseña=new JPasswordField();
        Tcontraseña.setBounds(150, 215, 200, 20);
        pantallaLogin.add(Tcontraseña);
        //Boton para ingresar valores
       JButton Bingreso=new JButton("Iniciar Sesión");
       //Ubicación y tamaño del recuadro
       Bingreso.setBounds(new Rectangle(150, 260, 200, 30));
       Bingreso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    //Guardar el codigo y contraseña ingresado por el usuario
                    codigoI=Tcodigo.getText();
                    contraseñaI=Tcontraseña.getText();
                    //Vaciar las celdas de texto
                    Tcodigo.setText("");
                    Tcontraseña.setText("");
                    //Mandar al metodo que se encarga de Abrir la ventana necesaria
                    redireccionar.abrir(codigoI, contraseñaI);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Asegurese de Ingresar valores validos");
                }
            }
        });
       //Agregar el boton al frame
        pantallaLogin.add(Bingreso);
    }
    
}
