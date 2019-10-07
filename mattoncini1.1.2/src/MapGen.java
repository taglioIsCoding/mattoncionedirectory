
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MapGen
{
	public int mappa[][];
	public int brickWidth;
	public int brickHeight;
        public int mattoncini;
        public int scelta;

    public int getMattoncini() {
        return mattoncini;
    }

    public void setMattoncini(int mattoncini) {
        this.mattoncini = mattoncini;
    }
        
        
	
	public MapGen (int scelta){
            //determina righe e colonne della matrice di mattoncini in base alla scelta
            this.scelta=scelta;
            int row=0;
            int col=0;
            switch (scelta){
                                    case 0: 
                                        mappa = new int[5][7];
                                        setMattoncini(35);
                                         row=4;
                                         col=7;
                                        break;
                                    case 1: 
                                        mappa = new int[6][12];
                                        setMattoncini(72);
                                         row=5;
                                         col=12;
                                        break;
                                    case 2:
                                        mappa = new int[7][12];
                                        setMattoncini(84);
                                         row=6;
                                         col=12;
                                        break;
                                }			
		for(int i = 0; i<mappa.length; i++)
		{
			for(int j =0; j<mappa[0].length; j++)
			{
				mappa[i][j] = 1;
			}			
		}
		
		brickWidth = 540/col;
		brickHeight = 150/row;
	}
        
	//si va a disegnare nel vero e proprio il cubetto per ogni singolo mattoncino, riga per riga, colonna per colonna
	public void draw(Graphics2D g){
		for(int i = 0; i<mappa.length; i++){
			for(int j =0; j<mappa[0].length; j++){
				if(mappa[i][j] > 0){
					//g.setColor(Color.white);
                                        BufferedImage image = this.mattoncino();
                                        g.drawImage(image, j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight, null);
                                        g.setStroke(new BasicStroke(4));
					//g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
					
					g.setColor(Color.white);
					g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);		
                                         
				}
			}
		}
	}
	
	public void setBrickValue(int value, int row, int col)
	{
		mappa[row][col] = value;
	}
        
                public BufferedImage mattoncino()
  {
      BufferedImage image = null;
    try
    {
        image = ImageIO.read(new File("mattoncini.jpg"));
        if(scelta==1){
            image = ImageIO.read(new File("mattoncini1.jpg"));
        }
        if(scelta==2){
            image = ImageIO.read(new File("mattoncini2.png"));
        }
    } 
    catch (IOException e)
    {
    }
    return image;
  }
                
}
