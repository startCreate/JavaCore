package Snake;


import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Room
{
    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;

    public Room(int width, int height, Snake snake)
    {
        this.width = width;
        this.height = height;
        this.snake = snake;
    }

    public Snake getSnake()
    {
        return snake;
    }

    public Mouse getMouse()
    {
        return mouse;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public void setSnake(Snake snake)
    {
        this.snake = snake;
    }

    public void setMouse(Mouse mouse)
    {
        this.mouse = mouse;
    }


    public void run()
    {

        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();


        while (snake.isAlive())
        {

            if (keyboardObserver.hasKeyEvents())
            {
                KeyEvent event = keyboardObserver.getEventFromTop();

                if (event.getKeyChar() == 'q') return;


                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.setDirection(SnakeDirection.LEFT);

                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.setDirection(SnakeDirection.RIGHT);

                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    snake.setDirection(SnakeDirection.UP);

                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.setDirection(SnakeDirection.DOWN);
            }

            snake.move();
            print();
            sleep();
        }


        System.out.println("Game Over!");
    }


    public void print()
    {

        int[][] matrix = new int[height][width];


        ArrayList<SnakeSection> sections = new ArrayList<SnakeSection>(snake.getSections());
        for (SnakeSection snakeSection : sections)
        {
            matrix[snakeSection.getY()][snakeSection.getX()] = 1;
        }


        matrix[snake.getY()][snake.getX()] = snake.isAlive() ? 2 : 4;


        matrix[mouse.getY()][mouse.getX()] = 3;


        String[] symbols = {" . ", " x ", " X ", "^_^", "RIP"};
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                System.out.print(symbols[matrix[y][x]]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }


    public void eatMouse()
    {
        createMouse();
    }


    public void createMouse()
    {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);

        mouse = new Mouse(x, y);
    }


    public static Room game;

    public static void main(String[] args)
    {
        game = new Room(20, 20, new Snake(10, 10));
        game.snake.setDirection(SnakeDirection.DOWN);
        game.createMouse();
        game.run();
    }


    private static int[] levelDelay = {1000, 600, 550, 500, 480, 460, 440, 420, 400, 380, 360, 340, 320, 300, 285, 270};


    public void sleep()
    {
        try
        {
            int level = snake.getSections().size();
            int delay = level < 15 ? levelDelay[level] : 250;
            Thread.sleep(delay);
        }
        catch (InterruptedException e)
        {
        }
    }
}
