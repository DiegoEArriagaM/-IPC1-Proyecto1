import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JOptionPane;

public class reporteAlumnos {
    //Clases necesarias para agregar los datos
    Contenedores datos = new Contenedores();
    //Metodo encargado de generar el reporte
    void reporte(){
        //Variable para abrir el listado automaticamente
        Desktop pc=Desktop.getDesktop();
        //Se crea el documento
        Document doc=new Document();
        try {
            //Se crea el documento PDF
            FileOutputStream pdf=new FileOutputStream("ListadoAlumnos.pdf");
            //El documento almacena la infomación del pdf.
            PdfWriter.getInstance(doc, pdf);
            
            //Se abre el documento para que se pueda modificar
            doc.open();
            
            //Se crea el título del documento y se configura
            Paragraph titulo=new Paragraph("Listado de Alumnos\n\n",
                      FontFactory.getFont("Serif",22,Font.PLAIN));
            //Se agrega el titulo al documento
            doc.add(titulo);
            
            //Se crea la tabla con la información
            PdfPTable tabla=new PdfPTable(5);
            //Se agregan los titulos a la tabla
            tabla.addCell("Codigo");
            tabla.addCell("Nombre");
            tabla.addCell("Apellido");
            tabla.addCell("Correo");
            tabla.addCell("Genero");
            
            //Se agregan los datos del los profesores
            for (int i = 0; i < datos.cantAlu; i++) {
                tabla.addCell(datos.codigoA[i]+"");
                tabla.addCell(datos.nombreA[i]);
                tabla.addCell(datos.apellidoA[i]);
                tabla.addCell(datos.correoA[i]);
                tabla.addCell(datos.generoA[i]);
            }
            //Se agrega la tabla
            doc.add(tabla);
            //Se cierra el documento
            doc.close();
            //El documento creado se pasa a una variable tipo FILE
            File f=new File("ListadoAlumnos.pdf");
            //Se abre el documento PDF
            pc.open(f);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al crear el pdf");
        }
    }
    
    void topM(int Codcurso){
        //Se busca al curso en el listado
        int a=0;
        while(a<datos.cantCur){
            if (datos.codigoC[a]==Codcurso) {
                break;
            }
            a++;
        }
       //Variable para abrir el listado automaticamente
        Desktop pc=Desktop.getDesktop();
        //Se crea el documento
        Document doc=new Document(); 
        try {
            //Se crea el documento PDF
            FileOutputStream pdf=new FileOutputStream("Top 5 Mejores Alumnos del Curso de "+datos.nombreC[a]+".pdf");
            //El documento almacena la infomación del pdf.
            PdfWriter.getInstance(doc, pdf);
            //Se abre el documento para que se pueda modificar
            doc.open();
            
            //Se crea el título del documento y se configura
            Paragraph titulo=new Paragraph("Listado de los 5 Mejores Alumnos del Curso de "+datos.nombreC[a]+"\n\n",
                      FontFactory.getFont("Serif",22,Font.PLAIN));
            //Se agrega el titulo al documento
            doc.add(titulo);
            
            //Se crean arreglos para almacenar los alumnos y sus notas
            int codA[]=new int[datos.alumnosC[a]];
            double notaA[]=new double[datos.alumnosC[a]];
            
            //Se crea la tabla con la información
            PdfPTable tabla=new PdfPTable(3);
            //Se agregan los titulos a la tabla
            tabla.addCell("Codigo");
            tabla.addCell("Nombre Completo");
            tabla.addCell("Nota");
            
            int b=0,c=0;
            //Se busca en toda la base de alumnos
            while(b<datos.cant_In){
                if(datos.codigoCC[b]==Codcurso){
                    codA[c]=datos.codigoAC[b];
                    //Se calcula su nota
                    int d=0;
                    while(d<datos.cant_Ac){
                        if (datos.codigoC_Ac[d]==Codcurso && datos.codigoAC_Ac[d]==datos.codigoAC[b]) {
                            notaA[c]+=datos.ponderacion_Ac[d]*(datos.notaAC[d]/100);
                        }
                        d++;
                    }
                    c++;
                }
                
                b++;
            }
            //Se utiliza el algoritmo burbuja para organizar los datos
                int aux1=0;
                double aux2=0;
                for (int i = 0; i < notaA.length-1; i++) {
                    for (int j = 0; j < notaA.length-i-1; j++) {
                        if (notaA[j+1]<notaA[j]) {
                            aux1=codA[j+1];
                            aux2=notaA[j+1];
                            
                            codA[j+1]=codA[j];
                            notaA[j+1]=notaA[j];
                            
                            codA[j]=aux1;
                            notaA[j]=aux2;
                        }
                    }
                }
                
            //Se agregan las filas
            int e=0,g=0;
            while(e<codA.length || g<5){
                if (e>=codA.length) {
                    break;
                }
                if(g>=5){
                    break;
                }
                e++;
                tabla.addCell(codA[codA.length-e]+"");
                //Se busca al alumno en el listado de alumnos del programa
                int f=0;
                while(f<datos.cantAlu){
                    if (codA[codA.length-e]==datos.codigoA[f]) {
                        tabla.addCell(datos.nombreA[f]+" "+datos.apellidoA[f]);
                        break;
                    }
                    f++;
                }
                BigDecimal bd = new BigDecimal(notaA[codA.length-e]).setScale(2, RoundingMode.HALF_UP);
                tabla.addCell(bd.doubleValue()+"");
                g++;
            }
            //Se agrega la tabla
            doc.add(tabla);
            //Se cierra el documento
            doc.close();
            //El documento creado se pasa a una variable tipo FILE
            File f=new File("Top 5 Mejores Alumnos del Curso de "+datos.nombreC[a]+".pdf");
            //Se abre el documento PDF
            pc.open(f);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al crear el pdf");
        }
    }
    
    void topP(int Codcurso){
        //Se busca al curso en el listado
        int a=0;
        while(a<datos.cantCur){
            if (datos.codigoC[a]==Codcurso) {
                break;
            }
            a++;
        }
       //Variable para abrir el listado automaticamente
        Desktop pc=Desktop.getDesktop();
        //Se crea el documento
        Document doc=new Document(); 
        try {
            //Se crea el documento PDF
            FileOutputStream pdf=new FileOutputStream("Top 5 Peores Alumnos del Curso de "+datos.nombreC[a]+".pdf");
            //El documento almacena la infomación del pdf.
            PdfWriter.getInstance(doc, pdf);
            //Se abre el documento para que se pueda modificar
            doc.open();
            
            //Se crea el título del documento y se configura
            Paragraph titulo=new Paragraph("Listado de los 5 Peores Alumnos del Curso de "+datos.nombreC[a]+"\n\n",
                      FontFactory.getFont("Serif",22,Font.PLAIN));
            //Se agrega el titulo al documento
            doc.add(titulo);
            
            //Se crean arreglos para almacenar los alumnos y sus notas
            int codA[]=new int[datos.alumnosC[a]];
            double notaA[]=new double[datos.alumnosC[a]];
            
            //Se crea la tabla con la información
            PdfPTable tabla=new PdfPTable(3);
            //Se agregan los titulos a la tabla
            tabla.addCell("Codigo");
            tabla.addCell("Nombre Completo");
            tabla.addCell("Nota");
            
            int b=0,c=0;
            //Se busca en toda la base de alumnos
            while(b<datos.cant_In){
                if(datos.codigoCC[b]==Codcurso){
                    codA[c]=datos.codigoAC[b];
                    //Se calcula su nota
                    int d=0;
                    while(d<datos.cant_Ac){
                        if (datos.codigoC_Ac[d]==Codcurso && datos.codigoAC_Ac[d]==datos.codigoAC[b]) {
                            notaA[c]+=datos.ponderacion_Ac[d]*(datos.notaAC[d]/100);
                        }
                        d++;
                    }
                    c++;
                }
                
                b++;
            }
            //Se utiliza el algoritmo burbuja para organizar los datos
                int aux1=0;
                double aux2=0;
                for (int i = 0; i < notaA.length-1; i++) {
                    for (int j = 0; j < notaA.length-i-1; j++) {
                        if (notaA[j+1]<notaA[j]) {
                            aux1=codA[j+1];
                            aux2=notaA[j+1];
                            
                            codA[j+1]=codA[j];
                            notaA[j+1]=notaA[j];
                            
                            codA[j]=aux1;
                            notaA[j]=aux2;
                        }
                    }
                }
                
            //Se agregan las filas
            int e=0,g=0;
            while(e<codA.length || g<5){
                if (e>=codA.length) {
                    break;
                }
                if(g>=5){
                    break;
                }
                tabla.addCell(codA[e]+"");
                //Se busca al alumno en el listado de alumnos del programa
                int f=0;
                while(f<datos.cantAlu){
                    if (codA[e]==datos.codigoA[f]) {
                        tabla.addCell(datos.nombreA[f]+" "+datos.apellidoA[f]);
                        break;
                    }
                    f++;
                }
                BigDecimal bd = new BigDecimal(notaA[e]).setScale(2, RoundingMode.HALF_UP);
                tabla.addCell(bd.doubleValue()+"");
                e++;
                g++;
            }
            //Se agrega la tabla
            doc.add(tabla);
            //Se cierra el documento
            doc.close();
            //El documento creado se pasa a una variable tipo FILE
            File f=new File("Top 5 Peores Alumnos del Curso de "+datos.nombreC[a]+".pdf");
            //Se abre el documento PDF
            pc.open(f);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al crear el pdf");
        }
    }
}
