
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import javax.swing.*;
//import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    
    
	private boolean avvio = false;
	private int punteggio = 0;
	private int mattoncini;
        private int vite=0;
	
	private Timer timer;
	private int delay=-1;
	
	private int giocatoreX = 310;
	
	private int pallapX = 120;
	private int pallapY = 350;
	private int direzionepX = 100;
	private int direzionepY = 200;
	
	private MapGen mappa;
	
	public Gameplay() throws InterruptedException
	{	
            // creazione "finestra di gioco", genera mappa tramite MapGen e la scelta difficoltà 
            // del menù e aspettando risposta mettendo in sleep il thread
            Menu1 menu=new Menu1();
            JFrame obj2 = new JFrame();
            obj2.setBounds(895, 290, 320, 509);
            obj2.setResizable(false);
            obj2.setVisible(true);
            obj2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            obj2.add(menu);
            obj2.setVisible(true);
            int temp=-1;
            do{
            System.out.println("\n"+"Fai la tua scelta di difficolta col menu");
            Thread.sleep(1500);
            }while(menu.getContatore()==0);
            temp=menu.getDifficolta();
            
            switch (menu.getDifficolta()){
                                    case 0: 
                                        break;
                                    case 1: 
                                        delay=-105;
                                        direzionepX = -170;
                                        direzionepY = -140;
                                        break;
                                    case 2:
                                        direzionepX = -60;
                                        direzionepY = -80;
                                        break;
                                }		

            mappa=new MapGen(temp);
                mattoncini=mappa.getMattoncini();
                addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
                timer=new Timer(delay,this);
            int pause = 0;
                timer.setInitialDelay(pause);
		timer.start();
                
	}
	
        
        public BufferedImage palla()
  {
      BufferedImage image = null;
    try
    {
        image = ImageIO.read(new File("palla.png"));
    } 
    catch (IOException e)
    {
    }
    return image;
  }


        
        
        @Override
	public void paint(Graphics g)
	{
		// sfondo
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		// mappa
		mappa.draw((Graphics2D) g);
		
		// bordi
		g.setColor(Color.pink);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		// punteggio	
		g.setColor(Color.WHITE);
                g.setFont(new Font("Arial",Font.BOLD, 16));
		g.drawString("Punteggio: ", 512,40);
                g.drawString("Tentativi: ", 280,40);
		g.setFont(new Font("Arial Black",Font.BOLD, 25));
		g.drawString(""+punteggio, 600,40);
                g.drawString(""+vite, 358,40);
                
		g.setFont(new Font("Arial",Font.BOLD, 14));
		g.drawString("MATTONCINI 1.1.2", 80,40);
  
		
		// barra
		g.setColor(Color.pink);
		g.fillRect(giocatoreX, 550, 100, 8);
		
		// palla
		g.setColor(Color.pink);
		//g.fillOval(pallapX, pallapY, 20, 20);
                BufferedImage image = this.palla();
                //super.paintComponent(g);
                g.drawImage(image, pallapX, pallapY, 0x14, 0x14, this);

               /* if(mattoncini == mattoncini){
                g.setFont(new Font("Arial Black",Font.BOLD, 20));
             int t=0;
                do{
                 g.drawString("Clicca invio per iniziare a giocare", 160,350);
                 t++;
             }while(t<5);
                g.setColor(Color.BLACK);
                g.fillRect(140, 310, 460, 50);
        } */
                
                
                
		// casistica se vinci
		if(mattoncini <= 0)
		{
			 avvio = false;
             direzionepX = 0;
     		 direzionepY = 0;
             g.setColor(Color.pink);
             g.setFont(new Font("Arial Black",Font.BOLD, 30));
             g.drawString("Hai vinto!", 270,300);
             
             g.setColor(Color.pink);
             g.setFont(new Font("Arial Black",Font.BOLD, 20));           
             g.drawString("Grazie per aver giocato", 220,350);  
             
             BufferedImage image2 = coppa();
             g.drawImage(image2, 310, 400, 100, 100, null);
		}
                
                
		
		// casistica se "perdi"
		if(pallapY > 570)
        {
			 avvio = false;
             direzionepX = 0;
     		 direzionepY = 0;
             g.setColor(Color.pink);
             g.setFont(new Font("Arial Black",Font.BOLD, 30));
             g.drawString("Game Over",260,300);
             g.drawString("Hai fatto: "+punteggio+" punti", 200,400);
             
             g.setColor(Color.pink);
             g.setFont(new Font("Arial Black",Font.BOLD, 20));
             g.drawString("Clicca invio per continuare a giocare", 170,350);  
             g.dispose();
        }
		
		
	}
        
        public BufferedImage coppa()
  {
      BufferedImage image = null;
    try
    {
        image = ImageIO.read(new File("coppa.png"));
    } 
    catch (IOException e)
    {
    }
    return image;
  }
                //prendere l'evento del tasto premuto
        @Override
	public void keyPressed(KeyEvent e){
		if (e.getKeyCode() == KeyEvent.VK_RIGHT){        
                    if(giocatoreX >= 600){
                        giocatoreX = 600;
                    }else{
                        moveRight();
                    }
        }
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT){          
			if(giocatoreX < 10){
                            giocatoreX = 10;
			}else{
				moveLeft();
			}
        }		
		if (e.getKeyCode() == KeyEvent.VK_ENTER){          
                    
			if(!avvio){
				avvio = true;
				pallapX = ThreadLocalRandom.current().nextInt(250, 550);
				pallapY = 350;
                                do {
                                    direzionepX = ThreadLocalRandom.current().nextInt(-4, 3);
                                }while (direzionepX==0);
                                do {
                                    direzionepY = ThreadLocalRandom.current().nextInt(-2, 1);
                                }while (direzionepY==0);
				giocatoreX = 310;
                                vite++;
				//punteggio = 0;
			
                                /*mappa = new MapGen(2);
                                mattoncini = 48;*/
                                        	
				repaint();
			}

        }		
	}
            //rilascio del tasto
        @Override
	public void keyReleased(KeyEvent e) {
        }
        @Override
	public void keyTyped(KeyEvent e) {
        }
        
	//movimento richiamato nella pressione del tasto
        
	public void moveRight()
	{
		avvio = true;
		giocatoreX+=30;	
	}
	
	public void moveLeft()
	{
		avvio = true;
		giocatoreX-=30;	 	
	}
	
        //eventi della palla quando gioco startato
        @Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
                //definisce il movimento della palla
		if(avvio){			
			if(new Rectangle(pallapX, pallapY, 20, 20).intersects(new Rectangle(giocatoreX, 550, 30, 8))){
				direzionepY = -direzionepY;
				direzionepX = -5;
			}else if(new Rectangle(pallapX, pallapY, 20, 20).intersects(new Rectangle(giocatoreX + 70, 550, 30, 8))){
				direzionepY = -direzionepY;
				direzionepX = direzionepX + 3;
			}else if(new Rectangle(pallapX, pallapY, 20, 20).intersects(new Rectangle(giocatoreX + 30, 550, 40, 8))){
				direzionepY = -direzionepY;
			}
			
		// collisioni della palla e relativa direzione al contatto		
                A: for(int i = 0; i<mappa.mappa.length; i++){
                    for(int j =0; j<mappa.mappa[0].length; j++){				
			if(mappa.mappa[i][j] > 0){
                            int brickX = j * mappa.brickWidth + 80;
                            int brickY = i * mappa.brickHeight + 50;
                            int brickWidth = mappa.brickWidth;
                            int brickHeight = mappa.brickHeight;
						
                            Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);					
                            Rectangle ballRect = new Rectangle(pallapX, pallapY, 20, 20);
                            Rectangle brickRect = rect;
						
                        if(ballRect.intersects(brickRect)){					
                            mappa.setBrickValue(0, i, j);
                            punteggio+=5;	
                            mattoncini--;
							
                            if(pallapX + 19 <= brickRect.x || pallapX + 1 >= brickRect.x + brickRect.width){
                                direzionepX = -direzionepX;
                            }else{
                                direzionepY = -direzionepY;				
                            }
			break A;
			}
			}
                    }
                }
			
            pallapX += direzionepX;
            pallapY += direzionepY;
			
            if(pallapX < 0){
                direzionepX = -direzionepX;
            }

            if(pallapY < 0){
                direzionepY = -direzionepY;
            }
			
            if(pallapX > 670){
		direzionepX = -direzionepX;
            }		
	
            repaint();		
		}
	}
}
