package org.langke.design.pattern.observer2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class SwingObserverExample {
	JFrame frame;
	
	public static void main(String[] args){
		SwingObserverExample example = new SwingObserverExample();
		example.go();
	}
	
	public void go(){
		frame = new JFrame();
		JButton button  = new JButton("should i do it");
		button.addActionListener(new AngleListener());
		button.addActionListener(new DevilListener());
		frame.getContentPane().add(BorderLayout.CENTER,button);
		frame.show();
	}
	
	class AngleListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.out.println("Don't do it,you might regret it ! ");
		}
	}
	
	class DevilListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.out.println("ome on ,do it!");
		}
	}
}
