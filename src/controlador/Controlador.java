package controlador;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import modelo.Modelo;
import vista.Vista;

public class Controlador implements ActionListener, MouseListener {
    Vista vista;
    
    Modelo modelo = new Modelo();
    
    public enum AccionMVC{
        ver_productos,
        agregar_producto,
        eliminar_producto
    }
    public Controlador(Vista vista){
        this.vista=vista;
    }
    
    public void iniciar() {
        //skin tipo WINDOWS
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vista);
            vista.setVisible(true);
        }catch(UnsupportedLookAndFeelException ex) {}
        catch (ClassNotFoundException ex){}
        catch (InstantiationException ex){}
        catch (IllegalAccessException ex){}
        
        this.vista.btnVerProducto.setActionCommand("ver_productos");
        this.vista.btnVerProducto.addActionListener(this);
        
        this.vista.btnAgregarProducto.setActionCommand("agregar_producto");
        this.vista.btnAgregarProducto.addActionListener(this);
        
        this.vista.btnEliminarProducto.setActionCommand("eliminar_producto");
        this.vista.btnEliminarProducto.addActionListener(this);
        
        this.vista.tblProductos.addMouseListener(this);
        this.vista.tblProductos.setModel(new DefaultTableModel());
    }
    public void mouseClicked(MouseEvent e){
        if(e.getButton() == 1) {
            int fila = this.vista.tblProductos.rowAtPoint(e.getPoint());
            if (fila > -1) {
                
            this.vista.txtProducto.setText( String.valueOf(this.vista.tblProductos.getValueAt(fila,0)));
            this.vista.txtNombre.setText( String.valueOf(this.vista.tblProductos.getValueAt(fila,1)));
            this.vista.txtPrecio.setText( String.valueOf( this.vista.tblProductos.getValueAt(fila, 2)));
            this.vista.txtCantidad.setText( String.valueOf(this.vista.tblProductos.getValueAt(fila,3)));
            }
        }
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    
    public void actionPerformed(ActionEvent e){
    switch (AccionMVC.valueOf(e.getActionCommand())) {
        case ver_productos:
        
        this.vista.tblProductos.setModel(this.modelo.getTablaProducto());
        break;
        case agregar_producto:
        if (this.modelo.NuevoProducto(
        this.vista.txtProducto.getText(),
        this.vista.txtNombre.getText(),
        this.vista.txtPrecio.getText(),
        this.vista.txtCantidad.getText())){
        this.vista.tblProductos.setModel(this.modelo.getTablaProducto());
        JOptionPane.showMessageDialog(vista,"Exito: Nuevo registro agregado.");
        this.vista.txtProducto.setText("");
        this.vista.txtNombre.setText("");
        this.vista.txtPrecio.setText("0");
        this.vista.txtCantidad.setText("0");
    }
        else
            JOptionPane.showMessageDialog(vista, "Error: Los datos son incorrectos.");
        
        break; 
        case eliminar_producto:
    if(this.modelo.EliminarProducto(this.vista.txtProducto.getText())){
        this.vista.tblProductos.setModel(this.modelo.getTablaProducto());
        JOptionPane.showMessageDialog(vista, "Exito: registro eliminado");
        this.vista.txtProducto.setText("");
        this.vista.txtNombre.setText("");
        this.vista.txtPrecio.setText("0");
        this.vista.txtCantidad.setText("0");
    }
    break;
        
        
        
        }}}


    
