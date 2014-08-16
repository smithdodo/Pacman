package de.tu_darmstadt.gdi1.pacman.view;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class PButton {
	
	Shape button;
	String buttonName;
	float y;//y coordinate

	public PButton(float y, String name) {
		
		button=new Rectangle(260, y, 120, 40);
		this.buttonName=name;
		this.y=y;
		
	}
	
	public void render(Graphics g, GameContainer gc){
		
		if(button.contains(gc.getInput().getMouseX(),gc.getInput().getMouseY())){
			g.setColor(Color.yellow);
			g.fill(button);
		}else {
			g.setColor(Color.white);
			g.fill(button);
		}
		
		g.setColor(Color.black);
		g.drawString(buttonName, 270, y+10);
		
	}
	
	public void  update(StateBasedGame sbg, GameContainer gc, int StateID, boolean reinit) throws SlickException {
		
		// restart game
		if (button.contains(gc.getInput().getMouseX(), gc.getInput()
				.getMouseY())
				&& gc.getInput().isMousePressed(0)){
			
			if(reinit)
				sbg.getState(StateID).init(gc, sbg);
			sbg.enterState(StateID, new FadeOutTransition(), new FadeInTransition());
			
		}
		
	}

	public Shape getButton() {
		return button;
	}

}