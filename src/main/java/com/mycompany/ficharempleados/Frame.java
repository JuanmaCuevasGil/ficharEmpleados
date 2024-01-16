/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.ficharempleados;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JOptionPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Juanma
 */
public class Frame extends javax.swing.JFrame {

    private String ruta = "src\\main\\java\\com\\mycompany\\ficharEmpleados\\datos.xml";

    /**
     * Creates new form Frame
     */
    public Frame() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtId = new javax.swing.JTextField();
        btnEstado = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnEstado.setText("Estado");
        btnEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadoActionPerformed(evt);
            }
        });

        jLabel1.setText("Introduzca Id");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtId)))
                .addContainerGap(147, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(btnEstado)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoActionPerformed
        if (txtId.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Usted no ha introducido ningún id, pruebe de nuevo");
        } else {
            String id = txtId.getText();
            Boolean existe = compruebaId(id);
            
            if (existe) {
                String mensaje = "Hola " + consultaNombre(id) + ". Está usted en período de "
                        + (consultaEntrada(id) ? "trabajo" : "descanso")
                        + ". ¿Desea cambiar su estado?";

                Object[] opciones = {"Sí", "No"};

                int respuesta = JOptionPane.showOptionDialog(null, mensaje, "Confirmación",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

                if(JOptionPane.YES_OPTION==respuesta){
                        if(consultaEntrada(id)){
                            agregaSalida(id);
                        }else{
                            agregaEntrada(id);
                        }
                }

            } else {
                JOptionPane.showMessageDialog(rootPane, "Lo sentimos, el id proporcionado no existe en nuestra base de datos.");
            }
        }
    }//GEN-LAST:event_btnEstadoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtId;
    // End of variables declaration//GEN-END:variables

    private Boolean compruebaId(String idBuscado) {
        try {
            File archivoXml = new File(ruta);

            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(archivoXml);

            Element rootElement = document.getRootElement();

            List<Element> empleados = rootElement.getChildren("empleado");

            for (Element empleado : empleados) {
                List<Element> ids = empleado.getChildren("id");

                for (Element idElement : ids) {
                    String id = idElement.getTextTrim();

                    if (id.equals(idBuscado)) {
                        return true;
                    }
                }
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String consultaNombre(String id) {
        try {
            File archivoXml = new File(ruta);

            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(archivoXml);

            Element rootElement = document.getRootElement();

            List<Element> empleados = rootElement.getChildren("empleado");

            for (Element empleado : empleados) {
                Element idElement = empleado.getChild("id");
                Element nombreElement = empleado.getChild("nombre");

                if (idElement != null && nombreElement != null && id.equals(idElement.getTextTrim())) {
                    return nombreElement.getTextTrim();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

private Boolean consultaEntrada(String id) {
    try {
        File archivoXml = new File(ruta);

        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(archivoXml);

        Element rootElement = document.getRootElement();


        List<Element> fichajes = rootElement.getChildren("fichaje");
        for (Element fichaje : fichajes) {
            Element idElement = fichaje.getChild("id");
            Element horaSalidaElement = fichaje.getChild("horaSalida");

            if (idElement != null && horaSalidaElement == null && id.equals(idElement.getTextTrim())) {
                // Si hay una entrada para el ID sin horaSalida, devuelve true
                return true;
            }
        }

        // Si no se encontró una entrada sin horaSalida para el ID, devuelve false
        return false;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

    private void agregaEntrada(String id) {
        try {
            String horaEntrada = LocalDateTime.now().toString();
            File archivoXml = new File(ruta); 

            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(archivoXml);

            Element rootElement = document.getRootElement();

            // Crear nueva entrada de fichaje
            Element nuevaEntrada = new Element("fichaje");
            Element idElement = new Element("id");
            Element horaEntradaElement = new Element("horaEntrada");

            // Asignar valores a los elementos
            idElement.setText(id);
            horaEntradaElement.setText(horaEntrada);

            // Agregar elementos a la nueva entrada
            nuevaEntrada.addContent(idElement);
            nuevaEntrada.addContent(horaEntradaElement);

            // Agregar la nueva entrada al elemento root
            rootElement.addContent(nuevaEntrada);

            // Guardar los cambios en el archivo XML
            XMLOutputter xmlOutputter;
            xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            FileWriter writer = new FileWriter(archivoXml);
            xmlOutputter.output(document, writer);
            writer.close();

            System.out.println("Nueva entrada creada con éxito.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

private void agregaSalida(String id) {
    try {
        String horaSalida = LocalDateTime.now().toString();
        File archivoXml = new File(ruta);

        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(archivoXml);

        Element rootElement = document.getRootElement();

        // Buscar la entrada correspondiente al ID sin horaSalida
        List<Element> fichajes = rootElement.getChildren("fichaje");
        for (Element fichaje : fichajes) {
            Element idElement = fichaje.getChild("id");
            Element horaSalidaElement = fichaje.getChild("horaSalida");

            if (idElement != null && horaSalidaElement == null && id.equals(idElement.getTextTrim())) {
                // Agregar la horaSalida actual
                horaSalidaElement = new Element("horaSalida");
                horaSalidaElement.setText(horaSalida);
                fichaje.addContent(horaSalidaElement);

                // Guardar los cambios en el archivo XML
                XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
                FileWriter writer = new FileWriter(archivoXml);
                xmlOutputter.output(document, writer);
                writer.close();

                System.out.println("Hora de salida registrada con éxito.");

                return;  // Salir después de agregar la horaSalida
            }
        }

        System.out.println("No se encontró una entrada sin horaSalida para el ID: " + id);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
