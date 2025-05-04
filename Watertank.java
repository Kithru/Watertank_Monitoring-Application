import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

interface WaterLevelObserver{
	void update(int waterLevel);
}

class SMSSenderFrame extends JFrame implements WaterLevelObserver{

	private JLabel smsLabel;

	SMSSenderFrame(){
		setSize(400, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("SMS");
		setLayout(new FlowLayout());

		smsLabel = new JLabel("SMS Sending : 50");
		smsLabel.setFont(new Font("", Font.BOLD, 30));
		add(smsLabel);

		setVisible(true);
	}

	public void update(int waterLevel){
		if(waterLevel <=100 && waterLevel >= 0){
			this.smsLabel.setText("SMS Sending : " + waterLevel );
		}
	}
}


class AlarmFrame extends JFrame implements WaterLevelObserver{

	private JLabel alarmLabel;

	AlarmFrame(){
		setSize(400, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Alarm");
		setLayout(new FlowLayout());

		alarmLabel = new JLabel("Off");
		alarmLabel.setFont(new Font("", Font.BOLD, 30));
		add(alarmLabel);

		setVisible(true);
	}

	public void update(int waterLevel){
		if(waterLevel <=100 && waterLevel >= 0){
			this.alarmLabel.setText(waterLevel > 50 ? "On" : "Off" );
		}
	}
}

class DisplayFrame extends JFrame implements WaterLevelObserver{

	private JLabel displayLabel;

	DisplayFrame(){
		setSize(400, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Display");
		setLayout(new FlowLayout());

		displayLabel = new JLabel("50");
		displayLabel.setFont(new Font("", Font.BOLD, 30));
		add(displayLabel);

		setVisible(true);
	}

	public void update(int waterLevel){
		if(waterLevel <=100 && waterLevel >= 0){
			this.displayLabel.setText(Integer.toString(waterLevel));
		}
	}
}

class SplitterFrame extends JFrame implements WaterLevelObserver{

	private JLabel splitterLabel;

	SplitterFrame(){
		setSize(400, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Splitter");
		setLayout(new FlowLayout());

		splitterLabel = new JLabel("Off");
		splitterLabel.setFont(new Font("", Font.BOLD, 30));
		add(splitterLabel);

		setVisible(true);
	}

	public void update(int waterLevel){
		if(waterLevel <=100 && waterLevel >= 0){
			this.splitterLabel.setText(waterLevel > 75 ? "On" : "Off" );
		}
	}
}

class WaterTankContoller{
	private int waterLevel;

	private WaterLevelObserver[] observers = new WaterLevelObserver[0];

	public void setWaterLevel(int waterLevel){
		if(this.waterLevel != waterLevel){
			this.waterLevel = waterLevel;
			notifyObjects();
		}
	}

	public void notifyObjects(){
		for (WaterLevelObserver waterLevelObserver : observers) {
			waterLevelObserver.update(waterLevel);
		}
	}

	public void addWaterLevelObserver(WaterLevelObserver observer){
		WaterLevelObserver[] temp = new WaterLevelObserver[observers.length + 1];
		for (int i = 0; i < observers.length; i++) {
			temp[i] = observers[i];
		}
		temp[temp.length-1] = observer;
		observers = temp;
	}
}

class WaterTankFrame extends JFrame{
	private JSlider slider;
	private WaterTankContoller waterTankContoller;
	WaterTankFrame(WaterTankContoller waterTankContoller){

		this.waterTankContoller = waterTankContoller;

		setSize(400, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Water Tank");
		setLayout(new FlowLayout());

		slider = new JSlider(JSlider.VERTICAL);
		slider.setMajorTickSpacing(10);
		slider.setPaintLabels(true);

		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int waterLevel = slider.getValue();
				waterTankContoller.setWaterLevel(waterLevel);	
			}
		});

		add(slider);

		setVisible(true);
	}
}

class Example {
	public static void main(String[] args) {
		WaterTankContoller waterTankContoller = new WaterTankContoller();
		waterTankContoller.addWaterLevelObserver(new AlarmFrame());
		waterTankContoller.addWaterLevelObserver(new DisplayFrame());
		waterTankContoller.addWaterLevelObserver(new SplitterFrame());
		waterTankContoller.addWaterLevelObserver(new SMSSenderFrame());
		new WaterTankFrame(waterTankContoller);
	}
}
