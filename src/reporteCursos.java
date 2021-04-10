/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
/**
 *
 * @author Diego
 */
public class reporteCursos {
    //Clases necesarias para agregar los datos
    Contenedores datos=new Contenedores();
    //Metodo encargado de generar el reporte
    void reporte(){
        //Variable para abrir el listado automaticamente
        Desktop pc=Desktop.getDesktop();
        //Se crea el documento
        Document doc=new Document();
        try {
            //Se crea el documento PDF
            FileOutputStream pdf=new FileOutputStream("ListadoCursos.pdf");
            //El documento almacena la infomación del pdf.
            PdfWriter.getInstance(doc, pdf);
            
            //Se abre el documento para que se pueda modificar
            doc.open();
            
            //Se crea el título del documento y se configura
            Paragraph titulo=new Paragraph("Listado de Cursos\n\n",
                      FontFactory.getFont("Serif",22,Font.PLAIN));
            //Se agrega el titulo al documento
            doc.add(titulo);
            
            //Se crea la tabla con la información
            PdfPTable tabla=new PdfPTable(5);
            //Se agregan los titulos a la tabla
            tabla.addCell("Codigo");
            tabla.addCell("Nombre");
            tabla.addCell("Creditos");
            tabla.addCell("Alumnos");
            tabla.addCell("Profesor");
            
            //Se agregan los datos del los profesores
            for (int i = 0; i < datos.cantCur; i++) {
                tabla.addCell(datos.codigoC[i]+"");
                tabla.addCell(datos.nombreC[i]);
                tabla.addCell(datos.creditosC[i]+"");
                tabla.addCell(datos.alumnosC[i]+"");
                //Se busca al profesor
                String profesor="";
                int a=0;
                while(a<datos.cantPro){
                    if (datos.codigoPC[i]==datos.codigoP[a]) {
                        profesor=datos.nombreP[a]+" "+datos.apellidoP[a];
                        break;
                    }
                   a++;
                }
                tabla.addCell(profesor);
            }
            
            //Se agrega la tabla
            doc.add(tabla);
            //Se cierra el documento
            doc.close();
            //El documento creado se pasa a una variable tipo FILE
            File f=new File("ListadoCursos.pdf");
            //Se abre el documento PDF
            pc.open(f);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al crear el pdf");
        }
    }
}
