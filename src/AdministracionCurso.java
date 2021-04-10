import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class AdministracionCurso {
    //Se mandan a llamar las clases necesarias
    Contenedores datos=new Contenedores();
    reporteAlumnos Ralu=new reporteAlumnos();
    Guardar_Cargar_Datos GCD=new Guardar_Cargar_Datos();
   //Contenedor del codigo curso seleccionado 
    int cursoseleccionado=0,alumnoseleccionado=0;
    //Frame para la administración
    JFrame frameAdmin=new JFrame(),infoAl=new JFrame();
    //Tablas del Frame
    JTable tAlumnos,tActividades;
    //Modelos de tablas
    DefaultTableModel mtAlumnos, mtActividades;
    //Archivo para notas
    File notas;
    //JTextField para creación de actividades
    JTextField tnombre=new JTextField("");
    JTextField tdescri=new JTextField("");
    JTextField tponde=new JTextField("");
    
    void ventana(){
        //Se determina el curso seleccionado por el profesor
        int a=0;
        while(a<datos.cantCur){
            if (cursoseleccionado==datos.codigoC[a]) {
                break;
            }
            a++;
        }
        //Características del frame
        frameAdmin=new JFrame(datos.nombreC[a]);
        frameAdmin.setSize(1200, 700);
        frameAdmin.setLayout(null);
        frameAdmin.getContentPane().setBackground(Color.orange);
        frameAdmin.setVisible(true);
        
        //Labels del Frame
        JLabel titulo=new JLabel(datos.nombreC[a]);
        titulo.setFont(new Font("Serif",Font.PLAIN,30));
        titulo.setBounds(10, 10, 400, 35);
        frameAdmin.add(titulo);
        
        JLabel lListadoAlum=new JLabel("Listado Alumnos");
        lListadoAlum.setFont(new Font("Serif",Font.PLAIN,20));
        lListadoAlum.setBounds(10, 55, 200, 25);
        frameAdmin.add(lListadoAlum);
        //Tabla Listado de Alumnos
        mtAlumnos= new DefaultTableModel();
        //Se agregan columnas al modelo
        mtAlumnos.addColumn("Codigo");
        mtAlumnos.addColumn("Nombre");
        mtAlumnos.addColumn("Apellido");
        mtAlumnos.addColumn("Acciones");
        //Agregar filas
        for (int i = 0; i < datos.cant_In; i++) {
            Object fil[]=new Object[4];
            if(datos.codigoCC[i]==cursoseleccionado){
                //Se busca al alumno
                int c=0;
                while(c<datos.cantAlu){
                    if (datos.codigoAC[i]==datos.codigoA[c]) {
                        fil[0]=datos.codigoA[c];
                        fil[1]=datos.nombreA[c];
                        fil[2]=datos.apellidoA[c];
                        fil[3]="Ver mas información";
                        mtAlumnos.addRow(fil);
                        break;
                    }
                    c++;
                }
            }
        }
        
        //Se le da el modelo a la tabla
        tAlumnos=new JTable(mtAlumnos);
        //Se le da una accion a la tabla
        tAlumnos.addMouseListener(new MouseListener() {
            //Evento para mostrar datos del alumno
            @Override
            public void mouseClicked(MouseEvent me) {
                //Se determina la columana selecciona por el usuario
                int columna=tAlumnos.getSelectedColumn();
                if(columna==3){
                    //Se guarda el codigo del alumno
                    alumnoseleccionado=Integer.parseInt(tAlumnos.getValueAt(tAlumnos.getSelectedRow(),0).toString());
                    informacionAlumno();
                    frameAdmin.dispose();
                }
            }

            @Override
            public void mousePressed(MouseEvent me) { //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent me) { //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent me) {//To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent me) { //To change body of generated methods, choose Tools | Templates.
            }
        });
        //Se agrega la tabla a un scroll
        JScrollPane sp1=new JScrollPane(tAlumnos);
        //dimesiones del scroll
        sp1.setBounds(10, 95, 560, 300);
        frameAdmin.add(sp1);
        
        //Boton para carga masiva de alumno
        JButton bCargaMa=new JButton("Carga Masiva");
        bCargaMa.setBounds(10, 410, 560, 30);
        bCargaMa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               cargamasiva();
               frameAdmin.dispose();
            }
        });
        frameAdmin.add(bCargaMa);
        
        JLabel lReportesAlum=new JLabel("Reportes");
        lReportesAlum.setFont(new Font("Serif",Font.PLAIN,20));
        lReportesAlum.setBounds(10, 460, 200, 25);
        frameAdmin.add(lReportesAlum);
        //Botones para generar reportes de alumnos
        JButton bTopM=new JButton("Top 5-Estudiantes con Mejor Rendimiento");
        bTopM.setBounds(10, 490, 560, 30);
        bTopM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              Ralu.topM(cursoseleccionado);
            }
        });
        frameAdmin.add(bTopM);
        JButton bTopP=new JButton("Top 5-Estudiantes con Peor Rendimiento");
        bTopP.setBounds(10, 550, 560, 30);
        bTopP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              Ralu.topP(cursoseleccionado);
            }
        });
        frameAdmin.add(bTopP);
        
        JLabel lListadoAc=new JLabel("Actividades");
        lListadoAc.setFont(new Font("Serif",Font.PLAIN,20));
        lListadoAc.setBounds(600, 55, 200, 25);
        frameAdmin.add(lListadoAc);
        //Tabla Listado de Actividades
        mtActividades= new DefaultTableModel();
        //Se agregan columnas al modelo
        mtActividades.addColumn("Nombre");
        mtActividades.addColumn("Descripcion");
        mtActividades.addColumn("Ponderacion");
        mtActividades.addColumn("Promedio");
        //Se agregan filas
        for (int i = 0; i < datos.cant_AcIn; i++) {
            Object fil[]=new Object[4];
            if (datos.codigoC_Ac2[i]==cursoseleccionado) {
                fil[0]=datos.nombre_Ac[i];
                fil[1]=datos.descripcion_Ac[i];
                fil[2]=datos.ponderacion_Ac2[i];
                fil[3]=datos.promedio[i];
                mtActividades.addRow(fil);
            }
        }
        //Se agrega el modelo a la tabla
        tActividades=new JTable(mtActividades);
        //Se agrega la tabla a un scroll
        JScrollPane sp2=new JScrollPane(tActividades);
        //dimesiones del scroll
        sp2.setBounds(600, 95, 530, 200);
        frameAdmin.add(sp2);
        //Label Acumulado
        JLabel lacumulado=new JLabel("Acumulado: "+datos.acumuladoC[a]+"/100");
        lacumulado.setFont(new Font("Serif",Font.PLAIN,20));
        lacumulado.setBounds(960, 310, 200, 25);
        frameAdmin.add(lacumulado);
        //Label Crear Actividad
        JLabel lcrearAc=new JLabel("Crear Actividad");
        lcrearAc.setFont(new Font("Serif",Font.PLAIN,20));
        lcrearAc.setBounds(600, 345, 200, 25);
        frameAdmin.add(lcrearAc);
        //Labels y Text para ingresar las actividades
        JLabel lnombre=new JLabel("Nombre");
        lnombre.setFont(new Font("Serif",Font.PLAIN,15));
        lnombre.setBounds(600, 385, 150, 20);
        frameAdmin.add(lnombre);
        tnombre=new JTextField();
        tnombre.setFont(new Font("Serif",Font.PLAIN,15));
        tnombre.setBounds(760, 385, 400, 20);
        frameAdmin.add(tnombre);
        
        JLabel ldescri=new JLabel("Descripcion");
        ldescri.setFont(new Font("Serif",Font.PLAIN,15));
        ldescri.setBounds(600, 425, 150, 20);
        frameAdmin.add(ldescri);
        tdescri=new JTextField();
        tdescri.setFont(new Font("Serif",Font.PLAIN,15));
        tdescri.setBounds(760, 425, 400, 20);
        frameAdmin.add(tdescri);
        
        JLabel lponde=new JLabel("Ponderacion");
        lponde.setFont(new Font("Serif",Font.PLAIN,15));
        lponde.setBounds(600, 465, 150, 20);
        frameAdmin.add(lponde);
        tponde=new JTextField();
        tponde.setFont(new Font("Serif",Font.PLAIN,15));
        tponde.setBounds(760, 465, 400, 20);
        frameAdmin.add(tponde);
        
        JLabel lnotas=new JLabel("Notas");
        lnotas.setFont(new Font("Serif",Font.PLAIN,15));
        lnotas.setBounds(600, 505, 150, 20);
        frameAdmin.add(lnotas);
        //Boton para seleccionar las notas que se ingresaran al programa
        JButton tnotas=new JButton("Seleccionar archivo CSV");
        tnotas.setFont(new Font("Serif",Font.PLAIN,15));
        tnotas.setBounds(760, 505, 400, 20);
        
        tnotas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc=new JFileChooser();
                fc.showOpenDialog(fc);
                notas=fc.getSelectedFile();
            }
        });
        frameAdmin.add(tnotas);
        //Se almacena el acumulado del curso
        double acumulado=datos.acumuladoC[a];
        //Boton para la crear nueva actividad
                JButton crearAc=new JButton("Crear Actividad");
                crearAc.setFont(new Font("Serif",Font.PLAIN,20));
                crearAc.setBounds(600, 545, 560, 25);
                crearAc.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        if(tponde.getText().equals("")){
                            JOptionPane.showMessageDialog(null, "Asegurese de ingresar una ponderacion");
                        }else{
                            double ponderacion=Double.parseDouble(tponde.getText());
                            //Se verifica que no se haya alcanzado el límite de notas
                            if ((acumulado+ponderacion)>100) {
                            JOptionPane.showMessageDialog(null,"No se pueden ingresar mas notas."
                                    + " Ya se alcanzo el límite de nota permitido.");
                            }else{
                                cargaactividad();
                            }
                        }
                    }
                });
                frameAdmin.add(crearAc);
        
        
    }
    //Metodo para carga masiva de alumnos
    void cargamasiva(){
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
                if (datos.cant_In==0) {
                    datos.cant_In+=1;
                    datos.codigoCC=new int[datos.cant_In];
                    datos.codigoAC=new int[datos.cant_In]; 
                }else{
                    datos.cant_In+=1;
                }
                //Objetos del array
                JsonObject gsonO=obj.getAsJsonObject();
                //Arreglos auxiliares
                int codCC[]=new int[datos.cant_In],codAC[]=new int[datos.cant_In];
                //Se llenan los arreglos auxiliares
                for (int i = 0; i < datos.cant_In-1; i++) {
                    codCC[i]=datos.codigoCC[i];
                    codAC[i]=datos.codigoAC[i];
                }
                //Se reinician los arreglos
                datos.codigoCC=new int[datos.cant_In];
                datos.codigoAC=new int[datos.cant_In];
                //Se ingresan los valores anteriores
                for (int i = 0; i < datos.cant_In; i++) {
                    datos.codigoCC[i]=codCC[i];
                    datos.codigoAC[i]=codAC[i];
                }
                                        
                //Nuevos valores
                datos.codigoCC[datos.cant_In-1]=cursoseleccionado;
                datos.codigoAC[datos.cant_In-1]=gsonO.get("codigo").getAsInt();
                //Se busca al contador de alumnos del curso seleccionado
                int c=0;
                while(c<datos.cantCur){
                    if (datos.codigoC[c]==datos.codigoCC[datos.cant_In-1]) {
                        //Se aumenta el contador
                        datos.alumnosC[c]++;
                        break;
                    }
                    c++;
                }
            }
                
            }
                    //Limpiar Tabla
                    int filas=mtAlumnos.getRowCount();
                    for (int i = 0; i < filas; i++) {
                        mtAlumnos.removeRow(0);
                    }
                    //Agregar Columnas
                    for (int i = 0; i < datos.cant_In; i++) {
                        Object fil[]=new Object[4];
                        if(datos.codigoCC[i]==cursoseleccionado){
                            //Se busca al alumno
                            int c=0;
                            while(c<datos.cantAlu){
                                if (datos.codigoAC[i]==datos.codigoA[c]) {
                                    fil[0]=datos.codigoA[c];
                                    fil[1]=datos.nombreA[c];
                                    fil[2]=datos.apellidoA[c];
                                    fil[3]="Ver mas informacion";
                                     mtAlumnos.addRow(fil);
                                    break;
                                }
                                c++;
                            }
                            
                            
                        }
                    }
                    //Guardar valores
                    GCD.guardar();
                    ventana();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Error al ingresar el documento"+e);
                }
    }
    //Metodo para ver y eliminar alumno del curso
    void informacionAlumno(){
        //Se determina al alumno seleccionado
        int a=0;
        while(a<datos.cantAlu){
            if(alumnoseleccionado==datos.codigoA[a]){
                break;
            }
            a++;
        }
        //Caracterizticas del frame de informacion
        infoAl=new JFrame();
        infoAl.setSize(500, 700);
        infoAl.setLayout(null);
        infoAl.getContentPane().setBackground(Color.orange);
        infoAl.setVisible(true);
        
        //Labels del Frame
        JLabel titulo=new JLabel("Ver mas información");
        titulo.setFont(new Font("Serif",Font.PLAIN,30));
        titulo.setBounds(10, 10, 400, 35);
        infoAl.add(titulo);
        
        //Crear y establecer Labels y su Text para el Frame
        JLabel Lcodigo=new JLabel("Codigo");
        Lcodigo.setFont(new Font("Serif",Font.PLAIN,20));
        Lcodigo.setBounds(10,60,100,25);
        infoAl.add(Lcodigo);
        JLabel Tcodigo=new  JLabel(datos.codigoA[a]+"");
        Tcodigo.setFont(new Font("Serif",Font.PLAIN,20));
        Tcodigo.setOpaque(true);
        Tcodigo.setBackground(Color.white);
        Tcodigo.setHorizontalAlignment(JLabel.CENTER);
        Tcodigo.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        Tcodigo.setBounds(120,60,300,25);
        infoAl.add(Tcodigo);
        
        JLabel Lnombre=new JLabel("Nombre");
        Lnombre.setFont(new Font("Serif",Font.PLAIN,20));
        Lnombre.setBounds(10,100,100,25);
        infoAl.add(Lnombre);
        JLabel Tnombre=new  JLabel(datos.nombreA[a]);
        Tnombre.setFont(new Font("Serif",Font.PLAIN,20));
        Tnombre.setOpaque(true);
        Tnombre.setBackground(Color.white);
        Tnombre.setHorizontalAlignment(JLabel.CENTER);
        Tnombre.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        Tnombre.setBounds(120,100,300,25);
        infoAl.add(Tnombre);
        
        JLabel Lapellido=new JLabel("Apellido");
        Lapellido.setFont(new Font("Serif",Font.PLAIN,20));
        Lapellido.setBounds(10,140,100,25);
        infoAl.add(Lapellido);
        JLabel Tapellido=new  JLabel(datos.apellidoA[a]);
        Tapellido.setFont(new Font("Serif",Font.PLAIN,20));
        Tapellido.setOpaque(true);
        Tapellido.setBackground(Color.white);
        Tapellido.setHorizontalAlignment(JLabel.CENTER);
        Tapellido.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        Tapellido.setBounds(120,140,300,25);
        infoAl.add(Tapellido);
        
        JLabel Lcorreo=new JLabel("Correo");
        Lcorreo.setFont(new Font("Serif",Font.PLAIN,20));
        Lcorreo.setBounds(10,180,100,25);
        infoAl.add(Lcorreo);
        JLabel Tcorreo=new  JLabel(datos.correoA[a]);
        Tcorreo.setFont(new Font("Serif",Font.PLAIN,20));
        Tcorreo.setOpaque(true);
        Tcorreo.setBackground(Color.white);
        Tcorreo.setHorizontalAlignment(JLabel.CENTER);
        Tcorreo.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        Tcorreo.setBounds(120,180,300,25);
        infoAl.add(Tcorreo);
        
        JLabel Lcontraseña=new JLabel("Contraseña");
        Lcontraseña.setFont(new Font("Serif",Font.PLAIN,20));
        Lcontraseña.setBounds(10,220,100,25);
        infoAl.add(Lcorreo);
        JLabel Tcontraseña=new  JLabel(datos.contraseñaA[a]);
        Tcontraseña.setFont(new Font("Serif",Font.PLAIN,20));
        Tcontraseña.setOpaque(true);
        Tcontraseña.setBackground(Color.white);
        Tcontraseña.setHorizontalAlignment(JLabel.CENTER);
        Tcontraseña.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        Tcontraseña.setBounds(120,220,300,25);
        infoAl.add(Tcorreo);
        
        JLabel Lgenero=new JLabel("Genero");
        Lgenero.setFont(new Font("Serif",Font.PLAIN,20));
        Lgenero.setBounds(10,220,100,25);
        infoAl.add(Lgenero);
        JLabel Tgenero=new  JLabel("");
        if (datos.generoA[a].equalsIgnoreCase("m")) {
            Tgenero.setText("Masculino");
        }else{
            Tgenero.setText("Femenino");
        }
        Tgenero.setFont(new Font("Serif",Font.PLAIN,20));
        Tgenero.setOpaque(true);
        Tgenero.setBackground(Color.white);
        Tgenero.setHorizontalAlignment(JLabel.CENTER);
        Tgenero.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        Tgenero.setBounds(120,220,300,25);
        infoAl.add(Tgenero);
        
        //Boton para eliminar alumno del curso
        Button bEliminar=new Button("Eliminar");
        bEliminar.setFont(new Font("Serif",Font.PLAIN,20));
        bEliminar.setBounds(10,260,200,25);
        bEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                //Se busca la asignación correcta
                int b1=0,b2=0;
                while(b1<datos.cant_In){
                    if (alumnoseleccionado==datos.codigoAC[b1]) {
                        break;
                    }
                    b1++;
                }
                //Se busca el curso
                while(b2<datos.cantCur){
                    if(datos.codigoCC[b1]==datos.codigoC[b2]){break;}
                    b2++;
                }
                //Arreglos auxiliares
                int codCC[]=new int[datos.cant_In-1],codAC[]=new int[datos.cant_In-1];
                //Se llenan los arreglos auxiliares
                int c=0,cont=0;
                while(c<datos.cant_In){
                    if (datos.codigoAC[c]!=alumnoseleccionado) {
                        codCC[cont]=datos.codigoCC[c];
                        codAC[cont]=datos.codigoAC[c];
                        cont++;
                    }
                    c++;
                }
                datos.cant_In--;
                datos.alumnosC[b2]--;
                //Se reinician los arreglos
                datos.codigoCC=codCC;
                datos.codigoAC=codAC;
                
                //Se reinicia la tabla
                
                mtAlumnos= new DefaultTableModel();
                //Se agregan columnas al modelo
                mtAlumnos.addColumn("Codigo");
                mtAlumnos.addColumn("Nombre");
                mtAlumnos.addColumn("Apellido");
                mtAlumnos.addColumn("Acciones");
                //Agregar Columnas
                int d=0;
                for (int i = 0; i < datos.cant_In; i++) {
                    Object fil[]=new Object[4];
                    if(datos.codigoCC[i]==cursoseleccionado){
                        //Se busca al alumno
                        int e=0;
                        while(e<datos.cantAlu){
                            if (datos.codigoAC[i]==datos.codigoA[e]) {
                                fil[0]=datos.codigoA[e];
                                fil[1]=datos.nombreA[e];
                                fil[2]=datos.apellidoA[e];
                                fil[3]="Ver mas información";
                        
                                d++;
                                break;
                            }
                            e++;
                        }
                        mtAlumnos.addRow(fil);
                    }
                }
                //Se le da el modelo a la tabla
                
                tAlumnos=new JTable(mtAlumnos);
                //Se le da una accion a la tabla
                tAlumnos.addMouseListener(new MouseListener() {
                    //Evento para mostrar datos del alumno
            @Override
            public void mouseClicked(MouseEvent me) {
                //Se determina la columana selecciona por el usuario
                int columna=tAlumnos.getSelectedColumn();
                if(columna==3){
                    //Se guarda el codigo del alumno
                    alumnoseleccionado=Integer.parseInt(tAlumnos.getValueAt(tAlumnos.getSelectedRow(),0).toString());
                    informacionAlumno();
                }
            }

            @Override
            public void mousePressed(MouseEvent me) { //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent me) { //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent me) {//To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent me) { //To change body of generated methods, choose Tools | Templates.
            }
        });
        //Se agrega la tabla a un scroll
        JScrollPane sp1=new JScrollPane(tAlumnos);
        //dimesiones del scroll
        sp1.setBounds(10, 95, 560, 300);
        frameAdmin.add(sp1);
                //Se guardan los datos    
                GCD.guardar();
                ventana();
                infoAl.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Error al intentar eliminar al alumno");
                }
            }
        });
        infoAl.add(bEliminar);
        
        //Boton para cerrar la ventana
        Button bcerrar=new Button("Regresar");
        bcerrar.setFont(new Font("Serif",Font.PLAIN,20));
        bcerrar.setBounds(235,260,200,25);
        bcerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                infoAl.dispose();
            }
        });
        infoAl.add(bcerrar);
        
    }
    //Metodo para ingresar notas
    void cargaactividad(){
        frameAdmin.dispose();
        //Variables para calcular el promedio de notas
        int cont=0;
        double acumu=0;
        try{
            //Se llenan los primeros aspectos de la actividad
            //Se verifica si es la primera actividad ingresada en el programa
            //Datos destinados para los profesores
            if (datos.cant_AcIn==0) {
                datos.cant_AcIn++;
                datos.codigo_AC2=new int[datos.cant_AcIn];
                datos.codigoC_Ac2=new int[datos.cant_AcIn];
                datos.nombre_Ac=new String[datos.cant_AcIn];
                datos.descripcion_Ac=new String[datos.cant_AcIn];
                datos.ponderacion_Ac2=new double[datos.cant_AcIn];
                datos.promedio=new double[datos.cant_AcIn];
                //Se llena con los nuevos datos
                datos.codigoC_Ac2[datos.cant_AcIn-1]=cursoseleccionado;
                datos.codigo_AC2[datos.cant_AcIn-1]=datos.cant_AcIn;
                datos.nombre_Ac[datos.cant_AcIn-1]=tnombre.getText();
                datos.descripcion_Ac[datos.cant_AcIn-1]=tdescri.getText();
                datos.ponderacion_Ac2[datos.cant_AcIn-1]=Double.parseDouble(tponde.getText());
            //En caso de que no sea la primera vez
            }
            else if(datos.cant_AcIn>0){ 
                String nombre_Ac_Au[]=datos.nombre_Ac,descripcion_Ac[]=datos.descripcion_Ac;
                int[] codigo_AC2_Au=datos.codigo_AC2,codigoC_Ac2_Au=datos.codigoC_Ac2;
                double[] ponderacion_Ac2_Au=datos.ponderacion_Ac2,promedio_Au=datos.promedio;
                datos.cant_AcIn++;
                datos.codigo_AC2=new int[datos.cant_AcIn];
                datos.codigoC_Ac2=new int[datos.cant_AcIn];
                datos.nombre_Ac=new String[datos.cant_AcIn];
                datos.descripcion_Ac=new String[datos.cant_AcIn];
                datos.ponderacion_Ac2=new double[datos.cant_AcIn];
                datos.promedio=new double[datos.cant_AcIn];
                //Se llenan los arreglos con los datos antiguos
                int a=0;
                while(a<(datos.cant_AcIn-1)){
                    datos.codigo_AC2[a]=codigo_AC2_Au[a];
                    datos.codigoC_Ac2[a]=codigoC_Ac2_Au[a];
                    datos.nombre_Ac[a]=nombre_Ac_Au[a];
                    datos.descripcion_Ac[a]=descripcion_Ac[a];
                    datos.ponderacion_Ac2[a]=ponderacion_Ac2_Au[a];
                    datos.promedio[a]=promedio_Au[a];
                    a++;
                }
                //Se llena con los nuevos datos
                datos.codigoC_Ac2[datos.cant_AcIn-1]=cursoseleccionado;
                datos.codigo_AC2[datos.cant_AcIn-1]=datos.cant_AcIn;
                datos.nombre_Ac[datos.cant_AcIn-1]=tnombre.getText();
                datos.descripcion_Ac[datos.cant_AcIn-1]=tdescri.getText();
                datos.ponderacion_Ac2[datos.cant_AcIn-1]=Double.parseDouble(tponde.getText());
            }
            //Se busca al curso
            int c=0;
            while(c<datos.cantCur){
                if (datos.codigoC[c]==cursoseleccionado) {
                    datos.acumuladoC[c]+=datos.ponderacion_Ac2[datos.cant_AcIn-1];
                    break;
                }
                c++;
            }
            
            //Datos destinados para los alumnos
            
                //Lectura archivo CSV
                try {
                    FileReader fr=new FileReader(notas);
                    BufferedReader br=new BufferedReader(fr);
                    //Se lee la primera linea para omitir los titulos
                    String codigo=br.readLine(),
                        //Arreglo para codigo y nota
                        divisor[]=new String[2];
                    /**Se comienza un ciclo que se repetira hasta que el documento se 
                    encuentre completamente vacío*/
                    int b=0;
                    while((codigo=br.readLine())!=null){
                        divisor=codigo.split("\\s*,\\s*");
                        //En caso de que sea la primera actividad
                        if (datos.cant_Ac==0) {
                            //Se inicializan los arreglos
                            datos.cant_Ac++;
                            datos.codigoC_Ac=new int[datos.cant_Ac];
                            datos.codigo_AC=new int[datos.cant_Ac];
                            datos.codigoAC_Ac=new int[datos.cant_Ac];
                            datos.ponderacion_Ac=new double[datos.cant_Ac];
                            datos.notaAC=new double[datos.cant_Ac];
                            //Se llenan con los nuevos datos
                            datos.codigoC_Ac[datos.cant_Ac-1]=cursoseleccionado;
                            datos.codigo_AC[datos.cant_Ac-1]=datos.cant_AcIn;
                            datos.codigoAC_Ac[datos.cant_Ac-1]=Integer.parseInt(divisor[0]);
                            datos.ponderacion_Ac[datos.cant_Ac-1]=Double.parseDouble(tponde.getText());
                            datos.notaAC[datos.cant_Ac-1]=Double.parseDouble(divisor[1]);
                            acumu=acumu+datos.notaAC[datos.cant_Ac-1];
                            cont++;
                        //En caso de que NO sea la primera actividad
                        }else if(datos.cant_Ac>0){
                            //Arreglos que almacenan los datos mas antiguos
                            int codigoC_Ac_Au[]=datos.codigoC_Ac,codigo_AC_Au[]=datos.codigo_AC,
                                codigoAC_Ac_Au[]=datos.codigoAC_Ac;
                            double ponderacion_Ac_Au[]=datos.ponderacion_Ac,notaAC_Au[]=datos.notaAC;
                            //Se inicializan los arreglos
                            datos.cant_Ac++; 
                            datos.codigo_AC=new int[datos.cant_Ac];
                            datos.codigoC_Ac=new int[datos.cant_Ac];
                            datos.codigoAC_Ac=new int[datos.cant_Ac];
                            datos.ponderacion_Ac=new double[datos.cant_Ac];
                            datos.notaAC=new double[datos.cant_Ac];
                            //Se llenan con los datos antiguos
                            int a=0;
                            while(a<(datos.cant_Ac-1)){
                                datos.codigo_AC[a]=codigo_AC_Au[a];
                                datos.codigoC_Ac[a]=codigoC_Ac_Au[a];
                                datos.codigoAC_Ac[a]=codigoAC_Ac_Au[a];
                                datos.ponderacion_Ac[a]=ponderacion_Ac_Au[a];
                                datos.notaAC[a]=notaAC_Au[a];
                                a++;
                            }
                            //Se llenan con los nuevos datos
                            datos.codigoC_Ac[datos.cant_Ac-1]=cursoseleccionado;
                            datos.codigo_AC[datos.cant_Ac-1]=datos.cant_AcIn;
                            datos.codigoAC_Ac[datos.cant_Ac-1]=Integer.parseInt(divisor[0]);
                            datos.ponderacion_Ac[datos.cant_Ac-1]=Double.parseDouble(tponde.getText());
                            datos.notaAC[datos.cant_Ac-1]=Double.parseDouble(divisor[1]);
                            acumu=acumu+datos.notaAC[datos.cant_Ac-1];
                            cont++;
                        }
                        b++;
                    }
                    /**Se calcula el promedio de las notas de los alumnos en la actividad
                     redondeando el valor a dos decimales*/
                    BigDecimal bd = new BigDecimal(acumu/cont).setScale(2, RoundingMode.HALF_UP);
                   datos.promedio[datos.cant_AcIn-1]= bd.doubleValue(); 
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"No se pudo leer el archivo. Asegurese de que este sea csv. "+
                            e.getStackTrace()[0].getLineNumber());
                }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Asegurese de haber ingresado datos para la actividad");
        }
        
        GCD.guardar();
        ventana();
    }
}
