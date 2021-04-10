
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.HorizontalAlignment;


public class CursoAl {
    //Se mandan a llamar las clases necesarias
    Contenedores datos=new Contenedores();
    Guardar_Cargar_Datos GCD=new Guardar_Cargar_Datos();
    //Contenedor del codigo curso seleccionado 
    int cursoseleccionado=0,codalumno=0;
    //Frame para la administración
    JFrame frameAdmin=new JFrame();
    //Tablas del Frame
    JTable tActividades;
    //Modelos de tablas
    DefaultTableModel  mtActividades;
    void ventana(){
        //Se determina el curso seleccionado por el alumno
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
        
        JLabel lListadoAlum=new JLabel("Listado Actividades");
        lListadoAlum.setFont(new Font("Serif",Font.PLAIN,20));
        lListadoAlum.setBounds(10, 55, 200, 25);
        frameAdmin.add(lListadoAlum);
        //Tabla Listado de actividades
        mtActividades= new DefaultTableModel();
        //Se agregan columnas al modelo
        mtActividades.addColumn("Nombre");
        mtActividades.addColumn("Descripcion");
        mtActividades.addColumn("Ponderacion");
        mtActividades.addColumn("Nota obtenida");
        
        //Se crean arreglos para almacenar el codigo de la actividad y la nota total del curso
            int codAc[]=new int[datos.cant_Ac];
            double notaAc[]=new double[datos.cant_Ac],notaT=0,ponderacionT=0;
        //Contador de las actividades encontradas
            int cont=0;
        // Se llenan los arreglos    
        int b=0;
        while (b<datos.cant_Ac) {
            if (codalumno==datos.codigoAC_Ac[b] && cursoseleccionado==datos.codigoC_Ac[b]) {
                codAc[cont]=datos.codigo_AC[b];
                notaAc[cont]=datos.notaAC[b];
                notaT+=datos.notaAC[b]*(datos.ponderacion_Ac[b]/100);
                ponderacionT+=datos.ponderacion_Ac[b];
                cont++;
            }
            b++;
        }
        //Agregar filas
        Object fil[]=new Object[4];
        for (int i = 0; i < cont; i++) {
            fil=new Object[4];
            //Se busca la actividad correspondiente
            int c=0;
            while(c<datos.cant_AcIn){
                if (codAc[i]==datos.codigo_AC2[c]) {
                    fil[0]=datos.nombre_Ac[c];
                    fil[1]=datos.descripcion_Ac[c];
                    fil[2]=datos.ponderacion_Ac2[c];
                    fil[3]=notaAc[i];
                    mtActividades.addRow(fil);
                    break;
                }
                c++;
            }
        }
        
        //Se le da el modelo a la tabla
        tActividades=new JTable(mtActividades);
        //Se modifica el tamaño de columnas
        TableColumnModel colum=tActividades.getColumnModel();
            colum.getColumn(0).setPreferredWidth(150);
            colum.getColumn(1).setPreferredWidth(600);
            colum.getColumn(2).setPreferredWidth(250);
            colum.getColumn(3).setPreferredWidth(150);
        //Se agrega la tabla a un scroll
        JScrollPane sp1=new JScrollPane(tActividades);
        //dimesiones del scroll
        sp1.setBounds(10, 95, 1150, 200);
        frameAdmin.add(sp1);
        
        JLabel info=new JLabel("Ponderacion del curso es "+ponderacionT+". "+
                                "Nota del curso es "+notaT+"/"+ponderacionT, SwingConstants.CENTER);
        info.setFont(new Font("Serif",Font.PLAIN,20));
        info.setBounds(660, 295, 500, 25);
        info.setOpaque(true);
        info.setBackground(Color.white);
        info.setBorder(BorderFactory.createLineBorder(Color.black,1));
        frameAdmin.add(info);
        
        //Panel para la gráfica
        JPanel Pgrafica=new JPanel();
        Pgrafica.setBounds(300,350,600,300);
        //Se cera variable para almacenar valores
        DefaultCategoryDataset dataset=new DefaultCategoryDataset();
        for (int i = 0; i < cont; i++) {
            //Se busca la actividad correspondiente
            int c=0;
            while(c<datos.cant_AcIn){
                if (codAc[i]==datos.codigo_AC2[c]) {
                    dataset.setValue(datos.ponderacion_Ac2[c]*(notaAc[i]/100), "Cantidad de alumnos", datos.nombre_Ac[c]);
                    break;
                }
                c++;
            }
        }
        
        JFreeChart grafico=ChartFactory.createBarChart("Nota obtenida por actividad", "", "", 
                dataset,PlotOrientation.VERTICAL,false,true,false);
        //Se adapta al panel
        ChartPanel panel=new ChartPanel(grafico);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(600,300));
        //Se agrega al panel
        Pgrafica.add(panel);
        frameAdmin.add(Pgrafica);
        
    }
}
