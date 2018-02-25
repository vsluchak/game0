//Vladimir Sluchak sliding tiles game exercise
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


class PointButton extends Button implements ActionListener{
  /**
	 *
	 */
	private static final long serialVersionUID = 1L;
public int x;
  public int y;
  public PointButton(int x,int y, String label){
    super(label);
    this.x=x; this.y=y;
    //addActionListener();
  }
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub

}
}

class PointLabel extends JLabel {
  /**
	 *
	 */
	private static final long serialVersionUID = 1L;
public int x;
  public int y;
  public PointLabel(int x, int y){
    super();
    this.x = x ; this.y = y ;
  }
}


class Puzzle extends JPanel implements ActionListener {

  /**
	 *
	 */
	private static final long serialVersionUID = 622496661415367308L;
private int n;
  int fontsize;

  private int Npos ;  //holds the current button linear position
  private Point bP; // buffer for the coordinates
  private int GapPos; //holds the gap linear position
  private int i;
  private int k;

  public PointLabel gap;
  public PointButton curr_butt;
  public PointButton adj_butt;

  public void setPower(int p){
	  n=p;
  }

  public void setFontsize(int f){
	  fontsize = f;
  }

  public  Puzzle  (int n , int fontsize) {
    bP = new Point(0,0);
    this.n = n;
    this.fontsize = fontsize;
    setLayout(new GridLayout(n,n));
    setFont(new Font ("Helvetica", Font.BOLD, fontsize));

    for (  i = 0 ; i < n; i++) {
      for (  k=0; k < n ; k++) {
	Npos = i * n +k ;
	if (Npos>0)
	  {
	    bP = PositionCoord(n,Npos);
	    add(new PointButton(bP.x , bP.y , "" + Npos));

	  }
      }
    }

    bP = PositionCoord(n,Npos+1);
    gap = new PointLabel(bP.x, bP.y);
    add(gap);
    GapPos = Npos+1;
    //addActionListener();
  }


  public int CoordPosition(int X , int Y, int N) {
    if (Y == 0) return (X +1);
    return (N*Y + X+1 );
  }


  public Point PositionCoord(int N,  int Pos ) {
    int x;
    int y;

    y = Pos/N  ;

    if (Pos > y*N) { x = Pos - y*N -1;}
    else { x = N -1 ; y = y-1; }
    return (new Point(x,y));
  }

  public boolean gapIsNear ( PointLabel G , PointButton B) {

    if ((B.x != G.x) && (B.y != G.y ))
      return false;
// the next condition  would be enough:
    if (((B.x - G.x)*(B.x-G.x) + (B.y - G.y)*(B.y-G.y)) > 1)
      return false;
	return true;
  }
  //
  public boolean gapIsYaligned ( PointLabel G , PointButton B) {
	    if ((B.x == G.x) && (B.y != G.y )) return true;
		return false;
	  }
  public boolean gapIsXaligned ( PointLabel G , PointButton B) {
	    if ((B.x != G.x) && (B.y == G.y )) return true;
		return false;
	  }
  public boolean gameIsOver () {
    String label="0";
    int j;
    int CompNum = this.getComponentCount();
    for (j = 0 ; j < CompNum; j++){
      if(this.getComponent(j) instanceof PointButton){
	label=((Button)getComponent(j)).getLabel();
//	System.out.print(j);
	if (j != Integer.parseInt(label) - 1) return false;
      }
    }
    if (j < CompNum) return false;
      return true;
  }

  public void reshuffle () {
    int atGap[] = new int[4];
    int randomChoice;
    int choice;
    int onTable=0;
    //onTable will be from 2 to 4:
    if (gap.x+1 < n){
	atGap[onTable]=CoordPosition(gap.x+1 , gap.y,n);
	onTable++;
      }
    if (gap.x-1 >= 0){
      atGap[onTable]=CoordPosition(gap.x-1, gap.y,n);
      onTable++;
    }
    if (gap.y+1 < n){
      atGap[onTable]=CoordPosition(gap.x, gap.y+1,n);
      onTable++;
    }
    if (gap.y-1 >= 0){
      atGap[onTable]=CoordPosition(gap.x, gap.y-1,n);
      onTable++;
    }
   //randomChoice will be from 0 to 3;
    randomChoice = (int)(onTable*Math.random());

// System.out.print(randomChoice);

//the linear position of the adjacent button to be moved:
    choice = atGap[randomChoice];
    curr_butt = (PointButton)getComponent(choice-1);
    bP.x =  curr_butt.x; bP.y = curr_butt.y;
      Npos = CoordPosition(bP.x, bP.y,n);
    curr_butt.x = gap.x ; curr_butt.y = gap.y;
    remove(curr_butt);
    add(curr_butt, GapPos - 1);
    gap.x = bP.x; gap.y = bP.y;
    remove(gap);
    add(gap,Npos-1);
    GapPos = Npos;
  }

  //ActionListener butPressed = new ActionListener(){
  @Override
	 public void actionPerformed(ActionEvent e)
	 {
		    if(e.getSource() instanceof PointButton) {

		      curr_butt = (PointButton)e.getSource();

		      if (gapIsNear (gap , curr_butt))
			{
			  bP.x = curr_butt.x; bP.y = curr_butt.y;

				  Npos = CoordPosition(bP.x, bP.y, n ) ;

			  curr_butt.x = gap.x ; curr_butt.y = gap.y;

			  remove(curr_butt);
			  add(curr_butt, GapPos-1);   //because it counts positions from 0

			  gap.x =  bP.x; gap.y = bP.y;
			  remove(gap);
			  add(gap,Npos-1);

			  GapPos = Npos;
			}
		      // in the works - moving a row
		      if (gapIsXaligned(gap,curr_butt) && !gapIsNear(gap,curr_butt))
		      {
		    	//remove(curr_butt);

		    	  int unit;
		    	  int Xdif = curr_butt.x - gap.x;
		    	if (Xdif == 0 ) unit = 0;
		    	else
		    	unit = Xdif/Math.abs(Xdif);  //-1 or 1
		    	//move the row
		    	for (int i=1;i<=Math.abs(Xdif);i++)
		    		{
		    		//store adjacent button coordinates
		    		bP.x = gap.x + unit; bP.y = gap.y;
		    		//define linear position of the adjacent button
		    		Npos = CoordPosition(bP.x,bP.y,n);
		    		//get the button object
		    		//PointButton adj_butt = new PointButton(0,0,"JOPA");
		    		adj_butt = (PointButton)getComponent(Npos-1);

		    		remove(adj_butt);
		    		adj_butt.x = gap.x; adj_butt.y = gap.y;
		    		add(adj_butt,GapPos-1);



		    		remove(gap);
		    		gap.x = bP.x; gap.y = bP.y;
		    		add(gap,Npos-1);

		    		GapPos = Npos;

		    		}


		      }
		      if (gapIsYaligned(gap,curr_butt) && !gapIsNear(gap,curr_butt))
		      {
		    	//remove(curr_butt);

		    	  int unit;
		    	  int Ydif = curr_butt.y - gap.y;
		    	if (Ydif == 0 ) unit = 0;
		    	else
		    	unit = Ydif/Math.abs(Ydif);  //-1 or 1
		    	//move the row
		    	for (int i=1;i<=Math.abs(Ydif);i++)
		    		{
		    		//store adjacent button coordinates
		    		bP.y = gap.y + unit; bP.x = gap.x;
		    		//define linear position of the adjacent button
		    		Npos = CoordPosition(bP.x,bP.y,n);
		    		//get the button object
		    		//PointButton adj_butt = new PointButton(0,0,"JOPA");
		    		adj_butt = (PointButton)getComponent(Npos-1);

		    		remove(adj_butt);
		    		adj_butt.x = gap.x; adj_butt.y = gap.y;
		    		add(adj_butt,GapPos-1);



		    		remove(gap);
		    		gap.x = bP.x; gap.y = bP.y;
		    		add(gap,Npos-1);

		    		GapPos = Npos;

		    		}


		      }
		    }
		  };
		//};


//public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub

//}




  public boolean action (Event e , Object o)
  {
    if(e.target instanceof PointButton) {

      curr_butt = (PointButton)e.target;

      if (gapIsNear (gap , curr_butt))
	{
	  bP.x = curr_butt.x; bP.y = curr_butt.y;

		  Npos = CoordPosition(bP.x, bP.y, n ) ;

	  curr_butt.x = gap.x ; curr_butt.y = gap.y;

	  remove(curr_butt);
	  add(curr_butt, GapPos-1);   //because it counts positions from 0

	  gap.x =  bP.x; gap.y = bP.y;
	  remove(gap);
	  add(gap,Npos-1);

	  GapPos = Npos;
	}
      // in the works - moving a row
      if (gapIsXaligned(gap,curr_butt) && !gapIsNear(gap,curr_butt))
      {
    	//remove(curr_butt);

    	  int unit;
    	  int Xdif = curr_butt.x - gap.x;
    	if (Xdif == 0 ) unit = 0;
    	else
    	unit = Xdif/Math.abs(Xdif);  //-1 or 1
    	//move the row
    	for (int i=1;i<=Math.abs(Xdif);i++)
    		{
    		//store adjacent button coordinates
    		bP.x = gap.x + unit; bP.y = gap.y;
    		//define linear position of the adjacent button
    		Npos = CoordPosition(bP.x,bP.y,n);
    		//get the button object
    		//PointButton adj_butt = new PointButton(0,0,"JOPA");
    		adj_butt = (PointButton)getComponent(Npos-1);

    		remove(adj_butt);
    		adj_butt.x = gap.x; adj_butt.y = gap.y;
    		add(adj_butt,GapPos-1);



    		remove(gap);
    		gap.x = bP.x; gap.y = bP.y;
    		add(gap,Npos-1);

    		GapPos = Npos;

    		}


      }
      if (gapIsYaligned(gap,curr_butt) && !gapIsNear(gap,curr_butt))
      {
    	//remove(curr_butt);

    	  int unit;
    	  int Ydif = curr_butt.y - gap.y;
    	if (Ydif == 0 ) unit = 0;
    	else
    	unit = Ydif/Math.abs(Ydif);  //-1 or 1
    	//move the row
    	for (int i=1;i<=Math.abs(Ydif);i++)
    		{
    		//store adjacent button coordinates
    		bP.y = gap.y + unit; bP.x = gap.x;
    		//define linear position of the adjacent button
    		Npos = CoordPosition(bP.x,bP.y,n);
    		//get the button object
    		//PointButton adj_butt = new PointButton(0,0,"JOPA");
    		adj_butt = (PointButton)getComponent(Npos-1);

    		remove(adj_butt);
    		adj_butt.x = gap.x; adj_butt.y = gap.y;
    		add(adj_butt,GapPos-1);



    		remove(gap);
    		gap.x = bP.x; gap.y = bP.y;
    		add(gap,Npos-1);

    		GapPos = Npos;

    		}


      }
    }

    return false;
  }
}
 class YesNoDialog extends JDialog {
  /**
	 *
	 */
	private static final long serialVersionUID = -8572439701420617276L;
	public static final int NO = 0;
	public static final int YES = 1;

	JButton yes=null,no=null;
	JLabel label;
	JPanel p= new JPanel();


  public YesNoDialog(ActionListener actionListener, String title, String message,
		     String yes_label,String no_label)
    {
      super((JFrame) actionListener,title,true);
      this.setLayout(new BorderLayout(15,15));


      label = new JLabel(message,JLabel.CENTER);
      yes = new JButton(yes_label);
      no = new JButton(no_label);
      this.add("Center",label);
      p.setLayout(new FlowLayout(FlowLayout.CENTER,15,15));
      if (yes_label != null) p.add(yes);
      if (no_label != null) p.add(no);
      this.add("South",p);

      this.pack();

    }

  protected void finalize(){
	  this.setVisible(false);
  };

  public boolean action(Event e, Object arg)
    {
      if(e.target instanceof JButton){
	this.setVisible(false);
	this.dispose();
	if(e.target == yes) answer(YES);
	if(e.target == no) answer(NO);
	return true;
      }
      else return false;
    }

  protected void answer(int answer) {
    switch(answer){
    case YES: yes();break;
	    case NO: no();break;
	    }
  }
  protected void yes(){};
  protected void no(){};

}
 class ReplayQuitDialog extends YesNoDialog{
  /**
	 *
	 */
	private static final long serialVersionUID = -6335894307441348561L;
TextComponent status;
  public ReplayQuitDialog(ActionListener actionListener) {
    super(actionListener, "Quit or replay ?","Congratulations!","Quit","Replay");
    this.setLayout(new BorderLayout(140,140));
    ActionListener quit = new ActionListener(){
    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		System.exit(YES);

    	}
    };
    ActionListener replay = new ActionListener(){
    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		try {
    			setVisible(false);
				finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    };
    no.addActionListener(replay);
    yes.addActionListener(quit);

  }
  //public void yes() { System.exit(0);}
  //public void no() {};
 }




public class Game extends JFrame implements ActionListener   {
  /**
	 *
	 */
	private static final long serialVersionUID = 1L;
private int geom[] = new int[4];
  static Puzzle ThePuzzle;
  PopupMenu menu = new PopupMenu(" Options");
  MenuBar menuBar = new MenuBar() ;
 MenuItem item1 = new MenuItem("Exit");
 MenuItem item2 = new MenuItem("Power+");
 MenuItem item3 = new MenuItem("Power-");
 MenuItem item4 = new MenuItem("Reshuffle");

 ActionListener pButton=new ActionListener(){
	 public void actionPerformed(ActionEvent e){
		 if (e.getSource() instanceof PointButton) {
			 setVisible(true);
			 if (ThePuzzle.gameIsOver() ) {
					//ThePuzzle.disable();
					System.out.println("Game is over");
					ReplayQuitDialog d = new ReplayQuitDialog(this);
					d.setVisible(true);
					for (int l=0; l < 500; l++) ThePuzzle.reshuffle();
				 }
				 setVisible(true);

		 }


	 }

 };

 public boolean action (Event e , Object o) {
	        if ( e.target instanceof PointButton) {
 setVisible(true);
 if (ThePuzzle.gameIsOver() ) {
	//ThePuzzle.disable();
	System.out.println("Game is over");
	ReplayQuitDialog d = new ReplayQuitDialog(this);
	d.setVisible(true);
	for (int l=0; l < 500; l++) ThePuzzle.reshuffle();
 }
 setVisible(true);
  }
   return false;
}

 ActionListener exit=new ActionListener(){
	  public void actionPerformed(ActionEvent e)
	  {
		  System.exit(0);
	  }
 };

 ActionListener reshuffle=new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			for (int l=0; l < 500; l++) ThePuzzle.reshuffle();
			setVisible(true);
		}
		};

    public Game (String title)
    {
      super(title);
      //this.pop.add("bla");
      this.geom[0] =  4; this.geom[3] = 24; this.geom[1] =  400;
      this.geom[2] =  400;
      try {
      menu.add(item1);
      menu.add(item2);
      menu.add(item3);
      menu.add(item4);
      menuBar.add(menu);
      setMenuBar(menuBar);
      	item1.addActionListener(exit);
      	item4.addActionListener(reshuffle);
  		}
     finally {}
      }

  public static void main(String argv[])
    {
  String NumberName;
 int	 GamePower;
  if (argv.length !=0 ) {
    GamePower = Integer.parseInt(argv[0]);
    GamePower = GamePower*GamePower - 1;
    NumberName = String.valueOf(GamePower);} else NumberName = "15";
 final Game TheGame = new Game("The famous " + NumberName + " Game.");

  for(int i = 0 ; (i < argv.length ) && (i < 3) ;i++)
    TheGame.geom[i] = Integer.parseInt(argv[i]);

  TheGame.geom[3]=TheGame.geom[1]/(3*TheGame.geom[0]); // the proportional font size

  ThePuzzle = new Puzzle(TheGame.geom[0],TheGame.geom[3]);
  //ThePuzzle.enable();
  TheGame.add("Center",ThePuzzle);
 //TheGame.addActionListener(pButton);

TheGame.setSize(TheGame.geom[1],TheGame.geom[2]);
  for (int l=0; l < 500; l++) {
    ThePuzzle.reshuffle();
  }
  TheGame.setVisible(true);
 // TheGame.addComponentListener(ThePuzzle.butPressed);
  ActionListener Plus1=new ActionListener(){
	  public void actionPerformed(ActionEvent e)
	  {
		 TheGame.geom[0] = TheGame.geom[0] + 1;
		 int fsize=TheGame.geom[1]/(3*TheGame.geom[0]); // the proportional font size
		 TheGame.geom[3] = fsize;
		 TheGame.remove(ThePuzzle);
		 String NName = String.valueOf(TheGame.geom[0]*TheGame.geom[0] -1);
		 ///String ttt=TheGame.getTitle();
		 //System.out.println(TheGame.getTitle());
		 TheGame.setTitle("The famous " + NName + " game.");
		 ThePuzzle = new Puzzle(TheGame.geom[0],TheGame.geom[3]);
		 //ThePuzzle.setPower(pow0);
		 ThePuzzle.setFontsize(fsize);
		 TheGame.add("Center",ThePuzzle);
		 TheGame.repaint(0,0,TheGame.geom[1],TheGame.geom[2]);
		 TheGame.setVisible(true);
		 for (int l=0; l < 500; l++) {
		    ThePuzzle.reshuffle();
		  }
		  //ThePuzzle.isVisible();
		  //TheGame.isVisible();
		  TheGame.setVisible(true);
		  //TheGame.isDisplayable();

	  }
};

ActionListener Minus1=new ActionListener(){
	  public void actionPerformed(ActionEvent e)
	  {
		 TheGame.geom[0] = TheGame.geom[0] - 1;
		 if (TheGame.geom[0] < 2 ) TheGame.geom[0] = 2;
		 int fsize=TheGame.geom[1]/(3*TheGame.geom[0]); // the proportional font size
		 TheGame.geom[3] = fsize;
		 String NName = String.valueOf(TheGame.geom[0]*TheGame.geom[0] -1);
		 TheGame.setTitle("The famous " + NName + " game.");
		 TheGame.remove(ThePuzzle);
		 ThePuzzle = new Puzzle(TheGame.geom[0],TheGame.geom[3]);
		 TheGame.add("Center",ThePuzzle);
		 TheGame.repaint(0,0,TheGame.geom[1],TheGame.geom[2]);
		 TheGame.setVisible(true);
		 for (int l=0; l < 500; l++) {
		    ThePuzzle.reshuffle();
		  }
		  TheGame.setVisible(true);
	  }
};

TheGame.item2.addActionListener(Plus1);
TheGame.item3.addActionListener(Minus1);
    }

@Override
public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub

}

}
