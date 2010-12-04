/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * Editor.java
 *
 * Created on 2010-11-25, 22:06:30
 */
package Controller;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import Model.World;

/**
 * 
 * @author Dorota
 */
public class Editor extends javax.swing.JPanel
{
    private World _editedWorld;
    // Variables declaration - do not modify
    private java.awt.TextArea _editorTextArea;
    // End of variables declaration
    private String _textAreaContent;

    public Editor( World world )
    {
        _editedWorld = world;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public void initComponents( int width, int height )
    {
        _editorTextArea = new java.awt.TextArea();
        this.setSize( width, height );
        BorderLayout layout = new BorderLayout();
        this.setLayout( layout );
        this.add( _editorTextArea, BorderLayout.CENTER );
        createTitledBorder();
        _editorTextArea.addKeyListener( new java.awt.event.KeyAdapter()
        {
            @Override
            public void keyPressed( java.awt.event.KeyEvent evt )
            {
                _editorTextAreaKeyTyped( evt );
            }
        } );
    }

    private void _editorTextAreaKeyTyped( java.awt.event.KeyEvent evt )
    {
        _textAreaContent = _editorTextArea.getText();
        if( evt.getKeyCode() == KeyEvent.VK_ENTER )
        {
            String lastLine = EditorParser.getLastLine( _textAreaContent );
            EditorParser.parseLine( lastLine, _editedWorld );
        }
    }

    private void createTitledBorder()
    {
        TitledBorder title = BorderFactory.createTitledBorder( "Building editor" );
        this.setBorder( title );
    }
}
