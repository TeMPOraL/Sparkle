package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;

import com.sun.j3d.utils.universe.SimpleUniverse;

class MainWindow
{
    private JFrame _frame;
    private Canvas3D _sceneCanvas;
    private MenuPanel _menuPanel;
    private Scene3D _scene;

    public MainWindow()
    {
        _frame = new JFrame();
        _sceneCanvas = new Canvas3D( SimpleUniverse.getPreferredConfiguration() );
        _menuPanel = new MenuPanel();
        _scene = new Scene3D( _sceneCanvas );
    }

    public void initWindow()
    {
        _frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        int minWindowWidth = 1000;
        int minWindowHeight = 300;
        _frame.setMinimumSize( new Dimension( minWindowWidth, minWindowHeight ) );
        _frame.setExtendedState( JFrame.MAXIMIZED_BOTH );
        final int menuPanelWidth = 200;
        _frame.addComponentListener( new ComponentListener()
        {
            public void componentResized( ComponentEvent e )
            {
                int frameWidth = _frame.getWidth();
                int frameHeight = _frame.getHeight();
                // _sceneCanvas.setSize( new Dimension( frameWidth -
                // menuPanelWidth, frameHeight ) );
                // _menuPanel.setSize( new Dimension( menuPanelWidth,
                // frameHeight ) );
                System.out.println( "how it should be" + _frame.getWidth() + " "
                        + ( frameWidth - menuPanelWidth ) + " " + menuPanelWidth );
                System.out.println( "how it is" + _frame.getWidth() + " " + _sceneCanvas.getWidth()
                        + " " + _menuPanel.getWidth() );
            }

            public void componentMoved( ComponentEvent e )
            {
            }

            public void componentHidden( ComponentEvent e )
            {
            }

            public void componentShown( ComponentEvent e )
            {
            }
        } );
        _sceneCanvas.setBackground( Color.black );
        int frameWidth = _frame.getWidth();
        int frameHeight = _frame.getHeight();
        BorderLayout layout = new BorderLayout();
        _frame.setLayout( layout );
        _frame.setResizable( true );
        // _menuPanel.setSize( new Dimension( menuPanelWidth, frameHeight ) );
        _frame.add( _menuPanel, BorderLayout.EAST );
        // _sceneCanvas.setSize( new Dimension( frameWidth - menuPanelWidth,
        // frameHeight ) );
        _frame.add( _sceneCanvas, BorderLayout.CENTER );
        System.out.println( _menuPanel.getWidth() + " " + _menuPanel.getHeight() );
        _frame.pack();
        _frame.setVisible( true );
    }

    public static void main( String[] args )
    {
        MainWindow mainWindow = new MainWindow();
        mainWindow.initWindow();
    }
}