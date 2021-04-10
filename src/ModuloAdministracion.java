import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import org.jfree.data.general.DefaultPieDataset;
public class ModuloAdministracion {
    //Llamar a las clases necesarias
    Guardar_Cargar_Datos GCD=new Guardar_Cargar_Datos();
    Login login=new Login();
    Contenedores datos=new Contenedores();
    reporteProfesores reportePro=new reporteProfesores();
    reporteCursos reporteCur=new reporteCursos();
    reporteAlumnos reporteAlu=new reporteAlumnos();
    //Se crea modeloS de la tablaS
    DefaultTableModel modeloTP,modeloTC,modeloTA;
    //Se creada tabla del listado
    JTable tablaP,tablaC,tablaA;
    //Crear Frames y Paneles Necesarios
    JFrame pantalla=new JFrame("Módulo de Administración");
    JFrame FCrearPr=new JFrame(""),FActuPr=new JFrame(""),FCrearCu=new JFrame(""),FActuCu=new JFrame("");
    JTabbedPane tabbePane=new JTabbedPane();
    JPanel panelProfesores=new JPanel(),panelCursos=new JPanel(),panelAlumnoss=new JPanel();
    //Paneles para la gráficas
    
    
    void menu(){
        //Se mandan a llmar  los metodos que se encargan de modificar los paneles creados
        panelProfesores();
        panelCurso();
        panelAlumnos();
    //Pantalla de Inicio
        pantalla=new JFrame("Módulo de Administración");
        pantalla.setSize(1400, 700);
        pantalla.setLayout(null);
        pantalla.getContentPane().setBackground(Color.yellow);
        pantalla.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pantalla.setVisible(true);
        
        
    //Crear menú de paneles
        tabbePane=new JTabbedPane();
        tabbePane.setBounds(10, 20, 1340, 640);
        tabbePane.addTab("Profesores", panelProfesores);
        tabbePane.addTab("Cursos", panelCursos);
        tabbePane.addTab("Alumnos", panelAlumnoss);
        pantalla.add(tabbePane);
    //Boton para cerrar Sesión
        JButton Bcierre=new JButton("Cerrar Sesión");
        Bcierre.setBounds(new Rectangle(1200, 0, 130, 30));
        Bcierre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GCD.guardar();
                login.pantallaLogin.setVisible(true);
                pantalla.dispose();
            }
        });
        pantalla.add(Bcierre);
    }
    
    //Metodos para el panel de profesores
    void panelProfesores(){
        panelProfesores=new JPanel();
        panelProfesores.setBackground(Color.ORANGE);
        panelProfesores.setLayout(null);
        
        //Labels
        JLabel lListado=new JLabel("Listado Oficial");
        lListado.setFont(new Font("Serif", Font.PLAIN, 15));
        lListado.setBounds(5, 5, 120, 25);
        //Se agrega al panel
        panelProfesores.add(lListado);
        
        //Boton para carga masiva
        JButton bCargaMa=new JButton("Carga Masiva");
        bCargaMa.setBounds(600, 25, 200, 30);
        bCargaMa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                //swing para seleccionar archivos
                JFileChooser fc = new JFileChooser();
                fc.showOpenDialog(fc);
                //Se toma la ruta y se convierte en una variable tipo archivo
                File archivo=fc.getSelectedFile();
                FileReader archivoG = new FileReader(archivo);
                BufferedReader lectura=new BufferedReader(archivoG);
                String fila="",listado;
                int inicio=0,fin=0;
                //Ciclo para introducir los datos del documento .json
                    while ((listado=lectura.readLine())!=null) {
                        //Determinar el comienzo del arreglo
                        inicio=listado.indexOf("{");
                         //Determinar el final del arreglo
                        fin=listado.indexOf("}");
                        //Se forma el arreglo
                        fila="[{"+listado.substring(inicio+1, fin)+"}]";
                           //Leer archivo .json
                                   JsonParser Jp= new JsonParser();
                                   //Se obtiene el array
                                   JsonArray JsA=Jp.parse(fila).getAsJsonArray();
                                   //For-each para cada elemento de la matriz
                                    for (JsonElement obj:JsA) {
                                        //Objetos del array
                                        JsonObject gsonO=obj.getAsJsonObject();
                                        //Elementos primitivos
                                        datos.codigoP[datos.cantPro]=gsonO.get("codigo").getAsInt();
                                        datos.nombreP[datos.cantPro]=gsonO.get("nombre").getAsString();
                                        datos.apellidoP[datos.cantPro]=gsonO.get("apellido").getAsString();
                                        datos.correoP[datos.cantPro]=gsonO.get("correo").getAsString();
                                        datos.generoP[datos.cantPro]=gsonO.get("genero").getAsString();
                                        datos.contraseñaP[datos.cantPro]="1234";
                                    }
                                    datos.cantPro++;
                        
                    }
                    //Limpiar Tabla
                    int filas=modeloTP.getRowCount();
                    for (int i = 0; i < filas; i++) {
                        modeloTP.removeRow(0);
                    }
                    //Agregar Columnas
                    for (int i = 0; i < datos.cantPro; i++) {
                        Object fil[]=new Object[5];
                        fil[0]=datos.codigoP[i];
                        fil[1]=datos.nombreP[i];
                        fil[2]=datos.apellidoP[i];
                        fil[3]=datos.correoP[i];
                        fil[4]=datos.generoP[i];
                    modeloTP.addRow(fil);
                    }
                    //Metodo para generar la grafica
                    panelProfesores.remove(7);
                    panelProfesores.repaint();
                    graficaPro();
                    //Guardar valores
                    GCD.guardar();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Error al ingresar el documento"+e);
                }
            }
        });
        panelProfesores.add(bCargaMa);
        
        //Boton para crear un nuevo profesor
        JButton bCrearP=new JButton("Crear");
        bCrearP.setBounds(825,25,200,30);
        bCrearP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Se llama metodo que se encarga de crear otro profesor
                CrearProfesor();
                //Metodo para generar la grafica
                panelProfesores.remove(7);
                panelProfesores.repaint();
                graficaPro();
            }
        });
        panelProfesores.add(bCrearP);
        
        //Boton para actualizar algun profesor
        JButton bActualP=new JButton("Actualizar");
        bActualP.setBounds(600, 70, 200, 30);
        bActualP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actualizarPro();
                //Metodo para generar la grafica
                panelProfesores.remove(7);
                panelProfesores.repaint();
                graficaPro();
            }
        });
        panelProfesores.add(bActualP);
        
        //Boton para eliminar algun profesor
        JButton bEliminP=new JButton("Eliminar");
        bEliminP.setBounds(825, 70, 200, 30);
        bEliminP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                eliminarPro();
                //Metodo para generar la grafica
                panelProfesores.remove(7);
                panelProfesores.repaint();
                graficaPro();
            }
        });
        panelProfesores.add(bEliminP);
        
        //Boton para generar el reporte
        JButton bReporte= new JButton("Exportar Listado a PDF");
        bReporte.setBounds(600, 115, 425, 30);
        bReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                reportePro.reporte();
            }
        });
        panelProfesores.add(bReporte);
        
        modeloTP=new DefaultTableModel();
        tablaP=new JTable(modeloTP);
        //Agregar columnas a la tabla
        modeloTP.addColumn("Código");
        modeloTP.addColumn("Nombre");
        modeloTP.addColumn("Apellido");
        modeloTP.addColumn("Correo");
        modeloTP.addColumn("Genero");
        //Agregar Columnas
        for (int i = 0; i < datos.cantPro; i++) {
            Object fila[]=new Object[5];
            fila[0]=datos.codigoP[i];
            fila[1]=datos.nombreP[i];
            fila[2]=datos.apellidoP[i];
            fila[3]=datos.correoP[i];
            fila[4]=datos.generoP[i];
            modeloTP.addRow(fila);
        }
        
        JScrollPane sp=new JScrollPane(tablaP);
        sp.setBounds(5, 25, 500, 580);
        panelProfesores.add(sp);
        
        //Metodo para generar la grafica
        graficaPro();
    }
   //Metodo encargado de crear un nuevo profesor 
    void CrearProfesor(){
        //Configuración del JFrame
        FCrearPr=new JFrame("");
        FCrearPr.setSize(500, 700);
        FCrearPr.setLayout(null);
        FCrearPr.getContentPane().setBackground(Color.ORANGE);
        FCrearPr.setVisible(true);
        
        //Crear y establecer Labels para el Frame
        JLabel titulo=new JLabel("Agregar nuevo profesor");
        titulo.setFont(new Font("Serif",Font.PLAIN,30));
        titulo.setBounds(10, 10, 400, 35);
        FCrearPr.add(titulo);
        
        //Crear y establecer Labels y su Text para el Frame
        JLabel Lcodigo=new JLabel("Codigo");
        Lcodigo.setFont(new Font("Serif",Font.PLAIN,20));
        Lcodigo.setBounds(10,60,100,25);
        FCrearPr.add(Lcodigo);
        JTextField Tcodigo=new  JTextField();
        Tcodigo.setFont(new Font("Serif",Font.PLAIN,20));
        Tcodigo.setBounds(120,60,300,25);
        FCrearPr.add(Tcodigo);
        
        JLabel Lnombre=new JLabel("Nombre");
        Lnombre.setFont(new Font("Serif",Font.PLAIN,20));
        Lnombre.setBounds(10,100,100,25);
        FCrearPr.add(Lnombre);
        JTextField Tnombre=new  JTextField();
        Tnombre.setFont(new Font("Serif",Font.PLAIN,20));
        Tnombre.setBounds(120,100,300,25);
        FCrearPr.add(Tnombre);
        
        JLabel Lapellido=new JLabel("Apellido");
        Lapellido.setFont(new Font("Serif",Font.PLAIN,20));
        Lapellido.setBounds(10,140,100,25);
        FCrearPr.add(Lapellido);
        JTextField Tapellido=new  JTextField();
        Tapellido.setFont(new Font("Serif",Font.PLAIN,20));
        Tapellido.setBounds(120,140,300,25);
        FCrearPr.add(Tapellido);
        
        JLabel Lcorreo=new JLabel("Correo");
        Lcorreo.setFont(new Font("Serif",Font.PLAIN,20));
        Lcorreo.setBounds(10,180,100,25);
        FCrearPr.add(Lcorreo);
        JTextField Tcorreo=new  JTextField();
        Tcorreo.setFont(new Font("Serif",Font.PLAIN,20));
        Tcorreo.setBounds(120,180,300,25);
        FCrearPr.add(Tcorreo);
        
        JLabel Lcontraseña=new JLabel("Contraseña");
        Lcontraseña.setFont(new Font("Serif",Font.PLAIN,20));
        Lcontraseña.setBounds(10,220,100,25);
        FCrearPr.add(Lcontraseña);
        JTextField Tcontraseña=new  JTextField();
        Tcontraseña.setFont(new Font("Serif",Font.PLAIN,20));
        Tcontraseña.setBounds(120,220,300,25);
        FCrearPr.add(Tcontraseña);
        
        JLabel Lgenero=new JLabel("Genero");
        Lgenero.setFont(new Font("Serif",Font.PLAIN,20));
        Lgenero.setBounds(10,260,100,25);
        FCrearPr.add(Lgenero);
        String generos[]={null,"m","f"};
        JComboBox Cgenero=new JComboBox(generos);
        Cgenero.setFont(new Font("Serif",Font.PLAIN,20));
        Cgenero.setBounds(120,260,100,25);
        FCrearPr.add(Cgenero);
        //Boton que se encarga de agregar el profesor
        JButton Bagregar=new JButton("Agregar");
        Bagregar.setBounds(100,310,300,25);
        Bagregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Dar los valores escritos y seleccionado por el usuario
                datos.codigoP[datos.cantPro]=Integer.parseInt(Tcodigo.getText());
                datos.nombreP[datos.cantPro]=Tnombre.getText();
                datos.apellidoP[datos.cantPro]=Tapellido.getText();
                datos.correoP[datos.cantPro]=Tcorreo.getText();
                datos.contraseñaP[datos.cantPro]=Tcontraseña.getText();
                datos.generoP[datos.cantPro]=Cgenero.getSelectedItem().toString();
                datos.cantPro++;
                
                //Limpiar Tabla
                int filas=modeloTP.getRowCount();
                for (int i = 0; i < filas; i++) {
                    modeloTP.removeRow(0);
                }
                //Agregar Columnas
                    for (int i = 0; i < datos.cantPro; i++) {
                        Object fil[]=new Object[5];
                        fil[0]=datos.codigoP[i];
                        fil[1]=datos.nombreP[i];
                        fil[2]=datos.apellidoP[i];
                        fil[3]=datos.correoP[i];
                        fil[4]=datos.generoP[i];
                    modeloTP.addRow(fil);
                    }
                    //Guardar valores
                    GCD.guardar();
                    Tcodigo.setText("");Tnombre.setText("");Tapellido.setText("");
                    Tcorreo.setText("");Tcontraseña.setText("");
            }
        });
        FCrearPr.add(Bagregar);
    }
    //Metodo encargado de eliminar un profesor 
    void eliminarPro(){
        try {
        //Se toma el valor de la primera celda de la fila
        int cod=Integer.parseInt(tablaP.getValueAt(tablaP.getSelectedRow(),0).toString());
        
        try {
        //Buscar al profesor seleccionado
        int a=0;
        while(a<datos.cantPro){
            //Se establece si el código es el mismo
            if(cod==datos.codigoP[a]){ break;}
            a++;
        }
        //Se eliminan los valores de la celdas
        datos.codigoP[a]=0;datos.nombreP[a]=null;datos.apellidoP[a]=null;
        datos.correoP[a]=null;datos.generoP[a]=null;datos.contraseñaP[a]=null;
        
        //Se reacomodan los valores de las celdas, a partir de la celda eliminada
        while(a<datos.cantPro){
            datos.codigoP[a]=datos.codigoP[a+1];
            datos.nombreP[a]=datos.nombreP[a+1];
            datos.apellidoP[a]=datos.apellidoP[a+1];
            datos.correoP[a]=datos.correoP[a+1];
            datos.generoP[a]=datos.generoP[a+1];
            datos.contraseñaP[a]=datos.contraseñaP[a+1];
            a++;
        }
        datos.cantPro--;
        //Limpiar Tabla
        int filas=modeloTP.getRowCount();
        for (int i = 0; i < filas; i++) {
            modeloTP.removeRow(0);
        }
        //Agregar Columnas
        for (int i = 0; i < datos.cantPro; i++) {
            Object fil[]=new Object[5];
            fil[0]=datos.codigoP[i];
            fil[1]=datos.nombreP[i];
            fil[2]=datos.apellidoP[i];
            fil[3]=datos.correoP[i];
            fil[4]=datos.generoP[i];
            modeloTP.addRow(fil);
        }
        //Guardar valores
        GCD.guardar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se encontro al profesor seleccionado en la base de datos");
        }
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"No se selecciono ninguna fila");
        }
        
    }
    //Metodo encargado de actualizar detos un profesor 
    void actualizarPro(){
        try {
            //Se toma el valor de la primera celda de la fila
            int cod=Integer.parseInt(tablaP.getValueAt(tablaP.getSelectedRow(),0).toString());
            
            try {
                //Buscar al profesor seleccionado
                int a=0;
                while(a<datos.cantPro){
                    //Se establece si el código es el mismo
                    if(cod==datos.codigoP[a]){ break;}
                    a++;
                }
                
                
                //Configuración del JFrame
                FActuPr=new JFrame("");
                FActuPr.setSize(500, 700);
                FActuPr.setLayout(null);
                FActuPr.getContentPane().setBackground(Color.ORANGE);
                FActuPr.setVisible(true);
                
                //Labels del Frame
                JLabel titulo=new JLabel("Actualizar datos del profesor");
                titulo.setFont(new Font("Serif",Font.PLAIN,30));
                titulo.setBounds(10, 10, 400, 35);
                FActuPr.add(titulo);
        
                //Crear y establecer Labels y su Text para el Frame
                JLabel Lcodigo=new JLabel("Codigo");
                Lcodigo.setFont(new Font("Serif",Font.PLAIN,20));
                Lcodigo.setBounds(10,60,100,25);
                FActuPr.add(Lcodigo);
                JLabel Tcodigo=new  JLabel(datos.codigoP[a]+"");
                Tcodigo.setFont(new Font("Serif",Font.PLAIN,20));
                Tcodigo.setBounds(120,60,300,25);
                FActuPr.add(Tcodigo);
        
                JLabel Lnombre=new JLabel("Nombre");
                Lnombre.setFont(new Font("Serif",Font.PLAIN,20));
                Lnombre.setBounds(10,100,100,25);
                FActuPr.add(Lnombre);
                JTextField Tnombre=new  JTextField(datos.nombreP[a]);
                Tnombre.setFont(new Font("Serif",Font.PLAIN,20));
                Tnombre.setBounds(120,100,300,25);
                FActuPr.add(Tnombre);
        
                JLabel Lapellido=new JLabel("Apellido");
                Lapellido.setFont(new Font("Serif",Font.PLAIN,20));
                Lapellido.setBounds(10,140,100,25);
                FActuPr.add(Lapellido);
                JTextField Tapellido=new  JTextField(datos.apellidoP[a]);
                Tapellido.setFont(new Font("Serif",Font.PLAIN,20));
                Tapellido.setBounds(120,140,300,25);
                FActuPr.add(Tapellido);
        
                JLabel Lcorreo=new JLabel("Correo");
                Lcorreo.setFont(new Font("Serif",Font.PLAIN,20));
                Lcorreo.setBounds(10,180,100,25);
                FActuPr.add(Lcorreo);
                JTextField Tcorreo=new  JTextField(datos.correoP[a]);
                Tcorreo.setFont(new Font("Serif",Font.PLAIN,20));
                Tcorreo.setBounds(120,180,300,25);
                FActuPr.add(Tcorreo);
        
                JLabel Lcontraseña=new JLabel("Contraseña");
                Lcontraseña.setFont(new Font("Serif",Font.PLAIN,20));
                Lcontraseña.setBounds(10,220,100,25);
                FActuPr.add(Lcontraseña);
                JTextField Tcontraseña=new  JTextField(datos.contraseñaP[a]);
                Tcontraseña.setFont(new Font("Serif",Font.PLAIN,20));
                Tcontraseña.setBounds(120,220,300,25);
                FActuPr.add(Tcontraseña);
        
                JLabel Lgenero=new JLabel("Genero");
                Lgenero.setFont(new Font("Serif",Font.PLAIN,20));
                Lgenero.setBounds(10,260,100,25);
                FActuPr.add(Lgenero);
                String generos[]={datos.generoP[a],"m","f"};
                JComboBox Cgenero=new JComboBox(generos);
                Cgenero.setFont(new Font("Serif",Font.PLAIN,20));
                Cgenero.setBounds(120,260,100,25);
                FActuPr.add(Cgenero);
                
                //Button Encargado para actualizar datos
                JButton Bactu=new JButton("Actualizar");
                Bactu.setBounds(100,310,300,25);
                Bactu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    //Buscar al profesor seleccionado
                    int a=0;
                    while(a<datos.cantPro){
                        //Se establece si el código es el mismo
                        if(cod==datos.codigoP[a]){ break;}
                        a++;
                    }
                    //Dar los valores escritos y seleccionado por el usuario
                    datos.nombreP[a]=Tnombre.getText();
                    datos.apellidoP[a]=Tapellido.getText();
                    datos.correoP[a]=Tcorreo.getText();
                    datos.contraseñaP[a]=Tcontraseña.getText();
                    datos.generoP[a]=Cgenero.getSelectedItem().toString();
                
                    //Limpiar Tabla
                    int filas=modeloTP.getRowCount();
                    for (int i = 0; i < filas; i++) {
                        modeloTP.removeRow(0);
                    }
                    //Agregar Columnas
                    for (int i = 0; i < datos.cantPro; i++) {
                        Object fil[]=new Object[5];
                        fil[0]=datos.codigoP[i];
                        fil[1]=datos.nombreP[i];
                        fil[2]=datos.apellidoP[i];
                        fil[3]=datos.correoP[i];
                        fil[4]=datos.generoP[i];
                    modeloTP.addRow(fil);
                    }
                    //Guardar valores
                    GCD.guardar();
                    JOptionPane.showMessageDialog(null,"Se actualizaron los datos del Profesor");
                }
                });
                FActuPr.add(Bactu);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"No se encontro al profesor seleccionado en la base de datos");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se selecciono ninguna fila");
        }
    }
    //Metodo para grafica del panel de profesores
    void graficaPro(){
        //Panel para la gráfica
        JPanel Pgrafica=new JPanel();
        Pgrafica.setBounds(640,230,600,300);
        //Variable para almacenar los datos
        DefaultPieDataset generoP=new DefaultPieDataset();
        //Contador cantidad de hombres
        int m=0,f=0;
        //Ingresar todos los generos que hay en el arreglo correspondiente
        for (int i = 0; i < datos.cantPro; i++) {
            if (datos.generoP[i].equalsIgnoreCase("m")) {
                m++;
            }
            if (datos.generoP[i].equalsIgnoreCase("f")) {
                f++;
            }
        }
        generoP.setValue("Masculino",m);
        generoP.setValue("Femenino",f);
        //Se crea el gráfico
        JFreeChart grafico=ChartFactory.createPieChart("Genero de Profesores", generoP,true,true,false);
        //Se adapta al panel
        ChartPanel panel=new ChartPanel(grafico);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(600,300));
        //Se agrega al panel
        Pgrafica.add(panel);
        panelProfesores.add(Pgrafica);
        
    }
    
    //Metodos para el panel de Cursos
    void panelCurso(){
        panelCursos=new JPanel();
        panelCursos.setBackground(Color.ORANGE);
        panelCursos.setLayout(null);
        
        //Labels
        JLabel lListado=new JLabel("Listado Oficial");
        lListado.setFont(new Font("Serif", Font.PLAIN, 15));
        lListado.setBounds(5, 5, 120, 25);
        //Se agrega al panel
        panelCursos.add(lListado);
        
        //Boton para carga masiva
        JButton bCargaMa=new JButton("Carga Masiva");
        bCargaMa.setBounds(600, 25, 200, 30);
        bCargaMa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                //Variable para seleccionar archivos
                JFileChooser fc = new JFileChooser();
                fc.showOpenDialog(fc);
                //Se toma la ruta y se convierte en una variable tipo archivo
                File archivo=fc.getSelectedFile();
                FileReader archivoG = new FileReader(archivo);
                BufferedReader lectura=new BufferedReader(archivoG);
                String fila="",listado;
                int inicio=0,fin=0;
                //Ciclo para introducir los datos del documento .json
                    while ((listado=lectura.readLine())!=null) {
                        //Determinar el comienzo del arreglo
                        inicio=listado.indexOf("{");
                        //Determinar el final del arreglo
                        fin=listado.indexOf("}");
                        //Se forma el arreglo
                        fila="[{"+listado.substring(inicio+1, fin)+"}]";
                                   
                                //Leer archivo .json
                                   JsonParser Jp= new JsonParser();
                                   //Se obtiene el array
                                   JsonArray JsA=Jp.parse(fila).getAsJsonArray();
                                   //For-each para cada elemento de la matriz
                                    for (JsonElement obj:JsA) {
                                        //Objetos del array
                                        JsonObject gsonO=obj.getAsJsonObject();
                                        //Elementos primitivos
                                        datos.codigoC[datos.cantCur]=gsonO.get("codigo").getAsInt();
                                        datos.nombreC[datos.cantCur]=gsonO.get("nombre").getAsString();
                                        datos.creditosC[datos.cantCur]=gsonO.get("creditos").getAsInt();
                                        datos.codigoPC[datos.cantCur]=gsonO.get("profesor").getAsInt();
                                        datos.acumuladoC[datos.cantCur]=0.0;
                                    }
                                   datos.cantCur++;
                    }
                    //Limpiar Tabla
                    int filas=modeloTC.getRowCount();
                    for (int i = 0; i < filas; i++) {
                        modeloTC.removeRow(0);
                    }
                    //Agregar Columnas
                    for (int i = 0; i < datos.cantCur; i++) {
                        Object fil[]=new Object[5];
                        fil[0]=datos.codigoC[i];
                        fil[1]=datos.nombreC[i];
                        fil[2]=datos.creditosC[i];
                        fil[3]=datos.alumnosC[i];
                        //Se busca al profesor encargado
                        //Se busca al profesor encargado
                        String profesor="";
                        int b=0;
                        while(b<datos.cantPro){
                            if (datos.codigoPC[i]==datos.codigoP[b]) {
                                profesor=datos.nombreP[b]+" "+datos.apellidoP[b];
                                break;
                            }
                            b++;
                        }
                        fil[4]=profesor;
                        modeloTC.addRow(fil);
                    }
                    //Guardar valores
                    GCD.guardar();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Error al ingresar el documento"+e);
                }
            }
        });
        panelCursos.add(bCargaMa);
        
         //Boton para crear un nuevo curso
        JButton bCrearC=new JButton("Crear");
        bCrearC.setBounds(825,25,200,30);
        bCrearC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Se llama metodo que se encarga de crear otro curso
                crearCurso();
            }
        });
        panelCursos.add(bCrearC);
        
        //Boton para actualizar algun profesor
        JButton bActualP=new JButton("Actualizar");
        bActualP.setBounds(600, 70, 200, 30);
        bActualP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actualizarCur();
            }
        });
        panelCursos.add(bActualP);
        
         //Boton para eliminar algun profesor
        JButton bEliminP=new JButton("Eliminar");
        bEliminP.setBounds(825, 70, 200, 30);
        bEliminP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                eliminarCur();
            }
        });
        panelCursos.add(bEliminP);
        
         //Boton para generar el reporte
        JButton bReporte= new JButton("Exportar Listado a PDF");
        bReporte.setBounds(600, 115, 425, 30);
        bReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                reporteCur.reporte();
            }
        });
        panelCursos.add(bReporte);
        
        modeloTC=new DefaultTableModel();
        tablaC=new JTable(modeloTC);
        //Agregar columnas a la tabla
        modeloTC.addColumn("Código");
        modeloTC.addColumn("Nombre");
        modeloTC.addColumn("Creditos");
        modeloTC.addColumn("Alumnos");
        modeloTC.addColumn("Profesor");
        //Agregar Columnas
        for (int i = 0; i < datos.cantCur; i++) {
            Object fila[]=new Object[5];
            fila[0]=datos.codigoC[i];
            fila[1]=datos.nombreC[i];
            fila[2]=datos.creditosC[i];
            fila[3]=datos.alumnosC[i];
            //Se busca al profesor encargado
            String profesor="";
            int b=0;
            while(b<datos.cantPro){
                if (datos.codigoPC[i]==datos.codigoP[b]) {
                    profesor=datos.nombreP[b]+" "+datos.apellidoP[b];
                    break;
                }
                b++;
            }
            fila[4]=profesor;
            modeloTC.addRow(fila);
        }
        JScrollPane sp=new JScrollPane(tablaC);
        sp.setBounds(5, 25, 500, 580);
        panelCursos.add(sp);
        //Metodo para generar la grafica
        graficaCur();
    }
    //Metodo para crear un nuevo curso
    void crearCurso(){
        //Configuración del JFrame
        FCrearCu=new JFrame("");
        FCrearCu.setSize(500, 700);
        FCrearCu.setLayout(null);
        FCrearCu.getContentPane().setBackground(Color.ORANGE);
        FCrearCu.setVisible(true);
        
        //Crear y establecer Labels para el Frame
        JLabel titulo=new JLabel("Agregar nuevo Curso");
        titulo.setFont(new Font("Serif",Font.PLAIN,30));
        titulo.setBounds(10, 10, 400, 35);
        FCrearCu.add(titulo);
        
        //Crear y establecer Labels y su Text para el Frame
        JLabel Lcodigo=new JLabel("Codigo");
        Lcodigo.setFont(new Font("Serif",Font.PLAIN,20));
        Lcodigo.setBounds(10,60,100,25);
        FCrearCu.add(Lcodigo);
        JTextField Tcodigo=new  JTextField();
        Tcodigo.setFont(new Font("Serif",Font.PLAIN,20));
        Tcodigo.setBounds(120,60,300,25);
        FCrearCu.add(Tcodigo);
        
        JLabel Lnombre=new JLabel("Nombre");
        Lnombre.setFont(new Font("Serif",Font.PLAIN,20));
        Lnombre.setBounds(10,100,100,25);
        FCrearCu.add(Lnombre);
        JTextField Tnombre=new  JTextField();
        Tnombre.setFont(new Font("Serif",Font.PLAIN,20));
        Tnombre.setBounds(120,100,300,25);
        FCrearCu.add(Tnombre);
        
        JLabel Lcreditos=new JLabel("Creditos");
        Lcreditos.setFont(new Font("Serif",Font.PLAIN,20));
        Lcreditos.setBounds(10,140,100,25);
        FCrearCu.add(Lcreditos);
        JTextField Tcreditos=new  JTextField();
        Tcreditos.setFont(new Font("Serif",Font.PLAIN,20));
        Tcreditos.setBounds(120,140,300,25);
        FCrearCu.add(Tcreditos);
        
        JLabel Lprofesor=new JLabel("Profesor");
        Lprofesor.setFont(new Font("Serif",Font.PLAIN,20));
        Lprofesor.setBounds(10,180,100,25);
        FCrearCu.add(Lprofesor);
        //Se crea un arreglo con todos los profesores
        String profesores[]=new String[datos.cantPro+1];profesores[0]=null;
        //Se llena el arreglo
        for (int i = 0; i < datos.cantPro; i++) {
            profesores[i+1]=datos.nombreP[i]+" "+datos.apellidoP[i];
        }
        //Se agrega el listado con los profesores al JComboBox
        JComboBox Cprofesores=new JComboBox(profesores);
        Cprofesores.setFont(new Font("Serif",Font.PLAIN,20));
        Cprofesores.setBounds(120,180,300,25);
        FCrearCu.add(Cprofesores);
        
        //Boton que se encarga de agregar el profesor
        JButton Bagregar=new JButton("Agregar");
        Bagregar.setBounds(100,310,300,25);
        Bagregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Dar los valores escritos y seleccionado por el usuario
                datos.codigoC[datos.cantCur]=Integer.parseInt(Tcodigo.getText());
                datos.nombreC[datos.cantCur]=Tnombre.getText();
                datos.creditosC[datos.cantCur]=Integer.parseInt(Tcreditos.getText());
                //Se busca el codigo del profesor seleccionado
                int a=0;
                while(a<datos.cantPro){
                    if (Cprofesores.getSelectedItem().toString().equals(datos.nombreP[a]+" "+datos.apellidoP[a])) {
                        datos.codigoPC[datos.cantCur]=datos.codigoP[a];
                        break;
                    }
                    a++;
                }
                datos.cantCur++;
                
                //Limpiar Tabla
                    int filas=modeloTC.getRowCount();
                    for (int i = 0; i < filas; i++) {
                        modeloTC.removeRow(0);
                    }
                    //Agregar Columnas
                    for (int i = 0; i < datos.cantCur; i++) {
                        Object fil[]=new Object[5];
                        fil[0]=datos.codigoC[i];
                        fil[1]=datos.nombreC[i];
                        fil[2]=datos.creditosC[i];
                        fil[3]=datos.alumnosC[i];
                        //Se busca al profesor encargado
                        //Se busca al profesor encargado
                        String profesor="";
                        int b=0;
                        while(b<datos.cantPro){
                            if (datos.codigoPC[i]==datos.codigoP[b]) {
                                profesor=datos.nombreP[b]+" "+datos.apellidoP[b];
                                break;
                            }
                            b++;
                        }
                        fil[4]=profesor;
                        modeloTC.addRow(fil);
                    }
                    //Guardar valores
                    GCD.guardar();
                    Tcodigo.setText("");Tnombre.setText("");Tcreditos.setText("");
            }
        });
        FCrearCu.add(Bagregar);
    }
    //Metodo para actualizar Curso
    void actualizarCur(){
        try {
            //Se toma el valor de la primera celda de la fila
            int cod=Integer.parseInt(tablaC.getValueAt(tablaC.getSelectedRow(),0).toString());
            try {
                //Buscar al profesor seleccionado
                int a=0;
                while(a<datos.cantPro){
                    //Se establece si el código es el mismo
                    if(cod==datos.codigoP[a]){ break;}
                    a++;
                }
                //Configuración del JFrame
                FActuCu=new JFrame("");
                FActuCu.setSize(500, 700);
                FActuCu.setLayout(null);
                FActuCu.getContentPane().setBackground(Color.ORANGE);
                FActuCu.setVisible(true);
        
                //Crear y establecer Labels para el Frame
                JLabel titulo=new JLabel("Actualizar nuevo Curso");
                titulo.setFont(new Font("Serif",Font.PLAIN,30));
                titulo.setBounds(10, 10, 400, 35);
                FActuCu.add(titulo);
        
                //Crear y establecer Labels y su Text para el Frame
                JLabel Lcodigo=new JLabel("Codigo");
                Lcodigo.setFont(new Font("Serif",Font.PLAIN,20));
                Lcodigo.setBounds(10,60,100,25);
                FActuCu.add(Lcodigo);
                JTextField Tcodigo=new  JTextField(datos.codigoC[a]+"");
                Tcodigo.setFont(new Font("Serif",Font.PLAIN,20));
                Tcodigo.setBounds(120,60,300,25);
                FActuCu.add(Tcodigo);
        
                JLabel Lnombre=new JLabel("Nombre");
                Lnombre.setFont(new Font("Serif",Font.PLAIN,20));
                Lnombre.setBounds(10,100,100,25);
                FActuCu.add(Lnombre);
                JTextField Tnombre=new  JTextField(datos.nombreC[a]);
                Tnombre.setFont(new Font("Serif",Font.PLAIN,20));
                Tnombre.setBounds(120,100,300,25);
                FActuCu.add(Tnombre);
        
                JLabel Lcreditos=new JLabel("Creditos");
                Lcreditos.setFont(new Font("Serif",Font.PLAIN,20));
                Lcreditos.setBounds(10,140,100,25);
                FActuCu.add(Lcreditos);
                JTextField Tcreditos=new  JTextField(datos.creditosC[a]+"");
                Tcreditos.setFont(new Font("Serif",Font.PLAIN,20));
                Tcreditos.setBounds(120,140,300,25);
                FActuCu.add(Tcreditos);
        
                JLabel Lprofesor=new JLabel("Profesor");
                Lprofesor.setFont(new Font("Serif",Font.PLAIN,20));
                Lprofesor.setBounds(10,180,100,25);
                FActuCu.add(Lprofesor);
                //Se crea un arreglo con todos los profesores
                String profesores[]=new String[datos.cantPro+1];
                //Se llena la primera posicion
                for (int i = 0; i < datos.cantPro; i++) {
                    if (datos.codigoPC[a]==datos.codigoP[i]) {
                      profesores[0]=datos.nombreP[i]+" "+datos.apellidoP[i];
                      break;
                    }
                }
                //Se llena el arreglo
                for (int i = 0; i < datos.cantPro; i++) {
                    profesores[i+1]=datos.nombreP[i]+" "+datos.apellidoP[i];
                }
                //Se agrega el listado con los profesores al JComboBox
                JComboBox Cprofesores=new JComboBox(profesores);
                Cprofesores.setFont(new Font("Serif",Font.PLAIN,20));
                Cprofesores.setBounds(120,180,300,25);
                FActuCu.add(Cprofesores);
            
                //Button Encargado para actualizar datos
                JButton Bactu=new JButton("Actualizar");
                Bactu.setBounds(100,310,300,25);
                Bactu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    //Buscar al curso seleccionado
                    int a=0;
                    while(a<datos.cantCur){
                        //Se establece si el código es el mismo
                        if(cod==datos.codigoC[a]){ break;}
                        a++;
                    }
                    //Dar los valores escritos y seleccionado por el usuario
                    datos.codigoC[a]=Integer.parseInt(Tcodigo.getText());
                    datos.nombreC[a]=Tnombre.getText();
                    datos.creditosC[a]=Integer.parseInt(Tcreditos.getText());
                    
                    int b=0;
                    while(b<datos.cantPro){
                        if (Cprofesores.getSelectedItem().toString().equals(datos.nombreP[b]+" "+datos.apellidoP[b])) {
                            datos.codigoPC[a]=datos.codigoP[b];
                            break;
                        }
                        b++;
                    }
                    
                    //Limpiar Tabla
                    int filas=modeloTC.getRowCount();
                    for (int i = 0; i < filas; i++) {
                        modeloTC.removeRow(0);
                    }
                    //Agregar Columnas
                    for (int i = 0; i < datos.cantCur; i++) {
                        Object fil[]=new Object[5];
                        fil[0]=datos.codigoC[i];
                        fil[1]=datos.nombreC[i];
                        fil[2]=datos.creditosC[i];
                        fil[3]=datos.alumnosC[i];
                        //Se busca al profesor encargado
                        //Se busca al profesor encargado
                        String profesor="";
                        int c=0;
                        while(c<datos.cantPro){
                            if (datos.codigoPC[i]==datos.codigoP[c]) {
                                profesor=datos.nombreP[c]+" "+datos.apellidoP[c];
                                break;
                            }
                            c++;
                        }
                        fil[4]=profesor;
                        modeloTC.addRow(fil);
                    }
                    //Guardar valores
                    GCD.guardar();
                    Tcodigo.setText("");Tnombre.setText("");Tcreditos.setText("");
                    JOptionPane.showMessageDialog(null,"Se actualizaron los datos del Curso");
                }
                });
                FActuCu.add(Bactu);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"No se encontro al profesor seleccionado en la base de datos");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se selecciono ninguna fila");
        }    
    }
    //Metodo encargado de eliminar un profesor
    void eliminarCur(){
        try {
            //Se toma el valor de la primera celda de la fila
            int cod=Integer.parseInt(tablaC.getValueAt(tablaC.getSelectedRow(),0).toString());
            try {
                //Buscar al curso seleccionado
                int a=0;
                while(a<datos.cantCur){
                    //Se establece si el código es el mismo
                    if(cod==datos.codigoC[a]){ break;}
                    a++;
                }
                datos.codigoC[a]=0;datos.nombreC[a]=null;datos.creditosC[a]=0;
                datos.alumnosC[a]=0;datos.codigoPC[a]=0;
                
                //Se reacomodan los valores de las celdas, a partir de la celda eliminada
                while(a<datos.cantCur){
                    datos.codigoC[a]=datos.codigoC[a+1];
                    datos.nombreC[a]=datos.nombreC[a+1];
                    datos.creditosC[a]=datos.creditosC[a+1];
                    datos.alumnosC[a]=datos.alumnosC[a+1];
                    datos.codigoPC[a]=datos.codigoPC[a+1];
                    a++;
                }
                datos.cantCur--;
                
                //Limpiar Tabla
                int filas=modeloTC.getRowCount();
                for (int i = 0; i < filas; i++) {
                    modeloTC.removeRow(0);
                }
                //Agregar Columnas
                for (int i = 0; i < datos.cantCur; i++) {
                    Object fil[]=new Object[5];
                    fil[0]=datos.codigoC[i];
                    fil[1]=datos.nombreC[i];
                    fil[2]=datos.creditosC[i];
                    fil[3]=datos.alumnosC[i];
                    //Se busca al profesor encargado
                    //Se busca al profesor encargado
                    String profesor="";
                    int c=0;
                    while(c<datos.cantPro){
                        if (datos.codigoPC[i]==datos.codigoP[c]) {
                            profesor=datos.nombreP[c]+" "+datos.apellidoP[c];
                            break;
                        }
                        c++;
                    }
                    fil[4]=profesor;
                    modeloTC.addRow(fil);
                }
                //Guardar valores
                GCD.guardar();
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null,"No se encontro al profesor seleccionado en la base de datos");
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"No se selecciono ninguna fila");
        }
    }
    //Metodo para grafica del panel de alumnos
    void graficaCur(){
        if(datos.cantCur>=3){
        //Panel para la gráfica
        JPanel Pgrafica=new JPanel();
        Pgrafica.setBounds(640,230,600,300);
        //Se almacenaran el nombre y cantidad de cada curso
        int cont[]=new int[datos.cantCur],aux1;
        String nom[]=new String[datos.cantCur],aux2;
        
        int a=0;
        while(a<datos.cantCur){
            cont[a]=datos.alumnosC[a];
            nom[a]=datos.nombreC[a];
            a++;
        }
        //Se ordenan los datos mediante el metodo burbuja
        for (int i = 0; i < cont.length-1; i++) {
            for (int j = 0; j < cont.length-i-1; j++) {
                if (cont[j+1]<cont[j]) {
                   aux1= cont[j+1];
                   aux2=nom[j+1];
                   
                   cont[j+1]=cont[j];
                   nom[j+1]=nom[j];
                   
                   cont[j]=aux1;
                   nom[j]=aux2;
                }
            }
        }
        //Se cera variable para almacenar valores
        DefaultCategoryDataset dataset=new DefaultCategoryDataset();
        //Se almacenana los valores
        dataset.setValue(cont[datos.cantCur-2], "Cantidad de alumnos", nom[datos.cantCur-2]);
        dataset.setValue(cont[datos.cantCur-1], "Cantidad de alumnos", nom[datos.cantCur-1]);
        dataset.setValue(cont[datos.cantCur-3], "Cantidad de alumnos", nom[datos.cantCur-3]);
        //Se crea la grafica
        JFreeChart grafico=ChartFactory.createBarChart("Top 3-Cursos con más estudiantes", "", "", 
                dataset,PlotOrientation.VERTICAL,false,true,false);
        //Se adapta al panel
        ChartPanel panel=new ChartPanel(grafico);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(600,300));
        //Se agrega al panel
        Pgrafica.add(panel);
        panelCursos.add(Pgrafica);
        }
    }
    
    //Metodos para el pnael de alumnos
    void panelAlumnos(){
        panelAlumnoss=new JPanel();
        panelAlumnoss.setBackground(Color.ORANGE);
        panelAlumnoss.setLayout(null);
        
        //Labels
        JLabel lListado=new JLabel("Listado Oficial");
        lListado.setFont(new Font("Serif", Font.PLAIN, 15));
        lListado.setBounds(5, 5, 120, 25);
        //Se agrega al panel
        panelAlumnoss.add(lListado);
        
        //Boton para carga masiva
        JButton bCargaMa=new JButton("Carga Masiva");
        bCargaMa.setBounds(600, 25, 425, 30);
        bCargaMa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                //Variable para seleccionar archivos
                JFileChooser fc = new JFileChooser();
                fc.showOpenDialog(fc);
                //Se toma la ruta y se convierte en una variable tipo archivo
                File archivo=fc.getSelectedFile();
                FileReader archivoG = new FileReader(archivo);
                BufferedReader lectura=new BufferedReader(archivoG);
                String fila="",listado;
                int inicio=0,fin=0;
                //Ciclo para introducir los datos del documento .json
                    while ((listado=lectura.readLine())!=null) {
                        //Determinar el comienzo del arreglo
                        inicio=listado.indexOf("{");
                        //Determinar el final del arreglo
                        fin=listado.indexOf("}");
                        //Se forma el arreglo
                        fila="[{"+listado.substring(inicio+1, fin)+"}]";
                                //Leer archivo .json
                                   JsonParser Jp= new JsonParser();
                                   //Se obtiene el array
                                   JsonArray JsA=Jp.parse(fila).getAsJsonArray();
                                   //For-each para cada elemento de la matriz
                                    for (JsonElement obj:JsA) {
                                        //Objetos del array
                                        JsonObject gsonO=obj.getAsJsonObject();
                                        //Elementos primitivos
                                        datos.codigoA[datos.cantAlu]=gsonO.get("codigo").getAsInt();
                                        datos.nombreA[datos.cantAlu]=gsonO.get("nombre").getAsString();
                                        datos.apellidoA[datos.cantAlu]=gsonO.get("apellido").getAsString();
                                        datos.correoA[datos.cantAlu]=gsonO.get("correo").getAsString();
                                        datos.generoA[datos.cantAlu]=gsonO.get("genero").getAsString();
                                        datos.contraseñaA[datos.cantAlu]="1234";
                                    }
                                   datos.cantAlu++;
                    }
                    //Limpiar Tabla
                    int filas=modeloTA.getRowCount();
                    for (int i = 0; i < filas; i++) {
                        modeloTA.removeRow(0);
                    }
                    //Agregar Columnas
                    for (int i = 0; i < datos.cantAlu; i++) {
                        Object fil[]=new Object[5];
                        fil[0]=datos.codigoA[i];
                        fil[1]=datos.nombreA[i];
                        fil[2]=datos.apellidoA[i];
                        fil[3]=datos.correoA[i];
                        fil[4]=datos.generoA[i];
                    modeloTA.addRow(fil);
                    }
                    //Metodo para generar la grafica
                    panelAlumnoss.remove(4);
                    panelAlumnoss.repaint();
                    graficaAlu();
                    //Guardar valores
                    GCD.guardar();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Error al ingresar el documento"+e);
                }
            }
        });
        panelAlumnoss.add(bCargaMa);
        
        //Boton para generar el reporte
        JButton bReporte= new JButton("Exportar Listado a PDF");
        bReporte.setBounds(600, 70, 425, 30);
        bReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                reporteAlu.reporte();
            }
        });
        panelAlumnoss.add(bReporte);
        
        modeloTA=new DefaultTableModel();
        tablaA=new JTable(modeloTA);
        //Agregar columnas a la tabla
        modeloTA.addColumn("Código");
        modeloTA.addColumn("Nombre");
        modeloTA.addColumn("Apellido");
        modeloTA.addColumn("Correo");
        modeloTA.addColumn("Genero");
        //Agregar Columnas
        for (int i = 0; i < datos.cantAlu; i++) {
            Object fila[]=new Object[5];
            fila[0]=datos.codigoA[i];
            fila[1]=datos.nombreA[i];
            fila[2]=datos.apellidoA[i];
            fila[3]=datos.correoA[i];
            fila[4]=datos.generoA[i];
            modeloTA.addRow(fila);
        }
        JScrollPane sp=new JScrollPane(tablaA);
        sp.setBounds(5, 25, 500, 580);
        panelAlumnoss.add(sp);
        //Metodo para generar la grafica
        graficaAlu();
    }
    //Metodo para grafica del panel de alumnos
    void graficaAlu(){
        //Panel para la gráfica
        JPanel Pgrafica=new JPanel();
        Pgrafica.setBounds(640,230,600,300);
        //Variable para almacenar los datos
        DefaultPieDataset generoA=new DefaultPieDataset();
        int m=0,f=0;
        //Ingresar todos los generos que hay en el arreglo correspondiente
        for (int i = 0; i < datos.cantAlu; i++) {
            if (datos.generoA[i].equalsIgnoreCase("m")) {
                m++;
            }
            if (datos.generoA[i].equalsIgnoreCase("f")) {
                f++;
            }
        }
        generoA.setValue("Masculino",m);
        generoA.setValue("Femenino",f);
        //Se crea el gráfico
        JFreeChart grafico=ChartFactory.createPieChart("Genero de Alumnos",generoA,true,true,false);
        //Se adapta al panel
        ChartPanel panel=new ChartPanel(grafico);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(600,300));
        //Se agrega al panel
        Pgrafica.add(panel);
        panelAlumnoss.add(Pgrafica);
    }
}
