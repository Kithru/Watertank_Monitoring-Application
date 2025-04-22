import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class AlarmFrame extends JFrame{

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

	public void setAlarmLabelValue(int waterLevel){
		if(waterLevel <=100 && waterLevel >= 0){
			this.alarmLabel.setText(waterLevel > 50 ? "On" : "Off" );
		}
	}
}

class DisplayFrame extends JFrame{

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

	public void setDisplayLabelValue(int waterLevel){
		if(waterLevel <=100 && waterLevel >= 0){
			this.displayLabel.setText(Integer.toString(waterLevel));
		}
	}
}

class SplitterFrame extends JFrame{

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

	public void setSplitterLabelValue(int waterLevel){
		if(waterLevel <=100 && waterLevel >= 0){
			this.splitterLabel.setText(waterLevel > 75 ? "On" : "Off" );
		}
	}
}

class WaterTankContoller{
	private int waterLevel;

	private AlarmFrame alarmFrame;
	private DisplayFrame displayFrame;
	private SplitterFrame splitterFrame;

	public void setWaterLevel(int waterLevel){
		if(this.waterLevel != waterLevel){
			this.waterLevel = waterLevel;
			notifyObjects();
		}
	}

	public void notifyObjects(){
		alarmFrame.setAlarmLabelValue(waterLevel);
		displayFrame.setDisplayLabelValue(waterLevel);
		splitterFrame.setSplitterLabelValue(waterLevel);
	}

	public void setAlarmFrame(AlarmFrame alarmFrame){
		this.alarmFrame = alarmFrame;
	}
	public void setDisplayFrame(DisplayFrame displayFrame){
		this.displayFrame = displayFrame;
	}
	public void setSplitterFrame(SplitterFrame splitterFrame){
		this.splitterFrame = splitterFrame;
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
		waterTankContoller.setAlarmFrame(new AlarmFrame());
		waterTankContoller.setDisplayFrame(new DisplayFrame());
		waterTankContoller.setSplitterFrame(new SplitterFrame());

		new WaterTankFrame(waterTankContoller);
	}
}
