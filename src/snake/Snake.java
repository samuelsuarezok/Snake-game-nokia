/**
 *
 * @author Samuel
 *//**
 *
 * @author Samuel
 */
package snake;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import snake.Snake.ImagenSnake;

public class Snake extends JFrame {

    int WIDTH = 640;
    int HEIGHT = 480;

    Point snake;
    Point comida;

    boolean gameOver = false;

    int WIDTHPoint = 10;
    int HEIGHTPoint = 10;

    ArrayList<Point> lista = new ArrayList<Point>();

    ImagenSnake imagenSnake;
    int direccion = KeyEvent.VK_LEFT;
    long frecuencia = 60;

    public Snake() {
        setTitle("Snake");
        setSize(WIDTH, HEIGHT);
        setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        teclas teclas = new teclas();
        this.addKeyListener(teclas);
        startGame();

        imagenSnake = new ImagenSnake();

        this.getContentPane().add(imagenSnake);

        Momento momento = new Momento();

        Thread trid = new Thread(momento);

        trid.start();

    }

    public void startGame() {
        comida = new Point(200, 200);
        snake = new Point(WIDTH / 2, HEIGHT / 2);
        generarComida();
        lista = new ArrayList<Point>();
        lista.add(snake);
    }

    public void generarComida() {
        Random rdm = new Random();
        comida.x = rdm.nextInt(WIDTH);
        if ((comida.x % 5) > 0) {
            comida.x = comida.x - (comida.x % 5);
        }
        if (comida.x < 5) {
            comida.x = comida.x + 10;
        }
        comida.y = rdm.nextInt(HEIGHT);
        if ((comida.y % 5) > 0) {
            comida.y = comida.y - (comida.y % 5);
        }
        if (comida.y < 5) {
            comida.y = comida.y + 10;
        }
    }

    public static void main(String[] args) {
        Snake s = new Snake();

    }

    public void actualizar() {
        imagenSnake.repaint();
        lista.add(0, new Point(snake.x, snake.y));
        lista.remove((lista.size() - 1));

        for (int i = 1; i < lista.size(); i++) {
            Point punto = lista.get(i);
            if (snake.x == punto.x && snake.y == punto.y) {
                gameOver = true;
            }
        }

        if ((snake.x > (comida.x - 10)) && (snake.x < (comida.x + 10)) && (snake.y > (comida.y - 10)) && (snake.y > (comida.y - 10))) {
            lista.add(0, new Point(snake.x, snake.y));
            generarComida();
        }
    }

    public class ImagenSnake extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.blue);
            for (int i = 0; i < lista.size(); i++) {
                Point point = (Point) lista.get(i);
                g.fillRect(point.x, point.y, WIDTHPoint, HEIGHTPoint);
            }

            g.setColor(Color.red);
            g.fillRect(comida.x, comida.y, WIDTHPoint, HEIGHTPoint);
            if (gameOver) {
                g.drawString("GAME OVER", 500, 320);
            }
        }
    }

    public class teclas extends java.awt.event.KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (direccion != KeyEvent.VK_DOWN) {
                    direccion = KeyEvent.VK_UP;
                }

            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (direccion != KeyEvent.VK_UP) {
                    direccion = KeyEvent.VK_DOWN;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (direccion != KeyEvent.VK_RIGHT) {
                    direccion = KeyEvent.VK_LEFT;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (direccion != KeyEvent.VK_LEFT) {
                    direccion = KeyEvent.VK_RIGHT;
                }

            }

        }
    }

    public class Momento extends Thread {

        private long last = 0;

        public void run() {
            while (true) {
                if ((java.lang.System.currentTimeMillis() - last) > frecuencia) {
                    if (!gameOver) {
                        if (direccion == KeyEvent.VK_RIGHT) {
                            snake.x = snake.x + WIDTHPoint;
                            if (snake.x > WIDTH) {
                                snake.x = 0;
                            }
                        } else if (direccion == KeyEvent.VK_LEFT) {
                            snake.x = snake.x - WIDTHPoint;
                            if (snake.x < 0) {
                                snake.x = WIDTH - WIDTHPoint;
                            }
                        } else if (direccion == KeyEvent.VK_UP) {
                            snake.y = snake.y - HEIGHTPoint;
                            if (snake.y < 0) {
                                snake.y = HEIGHT;
                            }
                        } else if (direccion == KeyEvent.VK_DOWN) {
                            snake.y = snake.y + HEIGHTPoint;
                            if (snake.y > HEIGHT) {
                                snake.y = 0;
                            }
                        }
                    }
                    actualizar();
                    last = java.lang.System.currentTimeMillis();

                }
            }
        }
    }
}
// :)
