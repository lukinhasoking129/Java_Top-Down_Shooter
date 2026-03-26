import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class game extends JFrame implements KeyListener {

    private double x = 100; // posição inicial X
    private double y = 100; // posição inicial Y
    double velx = 0;
    double vely = 0;
    //double velocidade_anterior = velocidade;
    double aceleracao = 0.5;
    double atrito = 0.9;
    boolean correndo = false;
    private boolean up, down, left, right; // estado das teclas
    private PontoPanel painel;

    public adws() {
        super("Janela teste");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        painel = new PontoPanel();
        add(painel);

        addKeyListener(this);

        // Timer para atualizar a posição continuamente
        Timer timer = new Timer(8, e -> mover());
        timer.start();

        setVisible(true);
    }

    // Painel que desenha o ponto
    private class PontoPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.RED);
            g.fillOval((int)x, (int)y, 10, 10);
        }
    }

    // Atualiza a posição com base nas teclas pressionadas
    private void mover() {

        double acc = aceleracao;

        if (correndo) {
        acc *= 2.5;
        }

        double inputX = 0;
        double inputY = 0;

        if (left) inputX -= 1;
        if (right) inputX += 1;
        if (up) inputY -= 1;
        if (down) inputY += 1;

        // NORMALIZAÇÃO
        double length = Math.sqrt(inputX * inputX + inputY * inputY);

        if (length != 0) {
            inputX /= length;
            inputY /= length;
        }

        // aplica aceleração
        velx += inputX * acc;
        vely += inputY * acc;

        velx *= atrito;
        vely *= atrito;

        if (Math.abs(velx) < 0.05) velx = 0;
        if (Math.abs(vely) < 0.05) vely = 0;

        double maxVel = correndo ? 10 : 5;

        velx = Math.max(-maxVel, Math.min(maxVel, velx));
        vely = Math.max(-maxVel, Math.min(maxVel, vely));

        x += velx;
        y += vely;

        if (correndo) {
        acc *= 1.5; // multiplicador de corrida
    }

        painel.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_S: 
                down = true;
                break;
            case KeyEvent.VK_A: 
                left = true;
                break;
            case KeyEvent.VK_D: 
                right = true;
                break;
            case KeyEvent.VK_SHIFT: 
                correndo = true;
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: 
                up = false;
                break;
            case KeyEvent.VK_S: 
                down = false;
                break;
            case KeyEvent.VK_A: 
                left = false;
                break;
            case KeyEvent.VK_D: 
                right = false;
                break;
            case KeyEvent.VK_SHIFT: 
                correndo = false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Não usado
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(adws::new);
    }
}
