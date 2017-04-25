import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class ChronoGui extends JFrame
{
    Console c;
    JRadioButton radioChannel1, radioChannel3, radioChannel5, 
    radioChannel7, radioChannel2, radioChannel4, 
    radioChannel6, radioChannel8;
    JTextArea printDisplayTex;
    JLabel labelLegend, labelheader;
    JComboBox<Object> typeSelect1, typeSelect3, typeSelect5, 
    typeSelect7, typeSelect2, typeSelect4, typeSelect6, typeSelect8;
    JButton buttonPower, buttonFunction, buttonSwap, printPower, 
    button1, button2, button3, button4, 
    button5, button6, button7, button8, button9, button0, 
    buttonStar, buttonPound, buttonTrigger1, 
    buttonTrigger3, buttonTrigger5, buttonTrigger7, 
    buttonTrigger2, buttonTrigger6, buttonTrigger4, buttonTrigger8;

    public ChronoGui()
    {
        c = new Console();
        getContentPane().setLayout(null);
        setupGUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void sendCommand(String command){
        c.input(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) +"\t"+command);
    }

    void setupGUI()
    {
        buttonPower = new JButton();
        buttonPower.setLocation(14,24);
        buttonPower.setSize(100,50);
        buttonPower.setText("Power");
        getContentPane().add(buttonPower);
        buttonPower.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("POWER");
            }
        });

        buttonFunction = new JButton();
        buttonFunction.setLocation(18,179);
        buttonFunction.setSize(100,50);
        buttonFunction.setText("Function");
        getContentPane().add(buttonFunction);
        buttonFunction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //not documented in project
                //TODO
            }
        });
        buttonSwap = new JButton();
        buttonSwap.setLocation(23,362);
        buttonSwap.setSize(100,50);
        buttonSwap.setText("Swap");
        getContentPane().add(buttonSwap);
        buttonSwap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("swap");
            }
        });
        radioChannel1 = new JRadioButton();
        radioChannel1.setLocation(160,30);
        radioChannel1.setSize(40,40);
        radioChannel1.setText("1");
        radioChannel1.setSelected(false);
        getContentPane().add(radioChannel1);
        radioChannel1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("tog 1");
            }
        });
        radioChannel3 = new JRadioButton();
        radioChannel3.setLocation(160,70);
        radioChannel3.setSize(40,40);
        radioChannel3.setText("3");
        radioChannel3.setSelected(false);
        getContentPane().add(radioChannel3);
        radioChannel3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("tog 3");
            }
        });
        radioChannel5 = new JRadioButton();
        radioChannel5.setLocation(160,110);
        radioChannel5.setSize(40,40);
        radioChannel5.setText("5");
        radioChannel5.setSelected(false);
        getContentPane().add(radioChannel5);
        radioChannel5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("tog 5");
            }
        });
        radioChannel7 = new JRadioButton();
        radioChannel7.setLocation(160,150);
        radioChannel7.setSize(40,40);
        radioChannel7.setText("7");
        radioChannel7.setSelected(false);
        getContentPane().add(radioChannel7);
        radioChannel7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("tog 7");
            }
        });
        radioChannel2 = new JRadioButton();
        radioChannel2.setLocation(350,30);
        radioChannel2.setSize(40,40);
        radioChannel2.setText("2");
        radioChannel2.setSelected(false);
        getContentPane().add(radioChannel2);
        radioChannel2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("tog 2");
            }
        });
        radioChannel4 = new JRadioButton();
        radioChannel4.setLocation(350,70);
        radioChannel4.setSize(40,40);
        radioChannel4.setText("4");
        radioChannel4.setSelected(false);
        getContentPane().add(radioChannel4);
        radioChannel4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("tog 4");
            }
        });
        radioChannel6 = new JRadioButton();
        radioChannel6.setLocation(350,110);
        radioChannel6.setSize(40,40);
        radioChannel6.setText("6");
        radioChannel6.setSelected(false);
        getContentPane().add(radioChannel6);
        radioChannel6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("tog 6");
            }
        });
        radioChannel8 = new JRadioButton();
        radioChannel8.setLocation(350,150);
        radioChannel8.setSize(40,40);
        radioChannel8.setText("8");
        radioChannel8.setSelected(false);
        getContentPane().add(radioChannel8);
        radioChannel8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("tog 8");
            }
        });
        printDisplayTex = new JTextArea();
        printDisplayTex.setLocation(136,257);
        printDisplayTex.setSize(100,100);
        printDisplayTex.setText("");
        printDisplayTex.setRows(10);
        printDisplayTex.setColumns(10);
        getContentPane().add(printDisplayTex);

        //not printing
        //TODO
        
        
        labelLegend = new JLabel();
        labelLegend.setLocation(135,360);
        labelLegend.setSize(175,48);
        labelLegend.setText("Queue/Running/Final");
        getContentPane().add(labelLegend);

        String typeSelect1_tmp[]={"Sensor1","Sensor2"};
        typeSelect1 = new JComboBox<Object>(typeSelect1_tmp);
        typeSelect1.setLocation(200,30);
        typeSelect1.setSize(80,40);
        typeSelect1.setEditable(false );
        getContentPane().add(typeSelect1);

        String typeSelect3_tmp[]={"Sensor1","Sensor2"};
        typeSelect3 = new JComboBox<Object>(typeSelect3_tmp);
        typeSelect3.setLocation(200,70);
        typeSelect3.setSize(80,40);
        typeSelect3.setEditable(false );
        getContentPane().add(typeSelect3);

        String typeSelect5_tmp[]={"Sensor1","Sensor2"};
        typeSelect5 = new JComboBox<Object>(typeSelect5_tmp);
        typeSelect5.setLocation(200,110);
        typeSelect5.setSize(80,40);
        typeSelect5.setEditable(false );
        getContentPane().add(typeSelect5);

        String typeSelect7_tmp[]={"Sensor1","Sensor2"};
        typeSelect7 = new JComboBox<Object>(typeSelect7_tmp);
        typeSelect7.setLocation(200,150);
        typeSelect7.setSize(80,40);
        typeSelect7.setEditable(false );
        getContentPane().add(typeSelect7);
        
        buttonTrigger1 = new JButton();
        buttonTrigger1.setLocation(300,30);
        buttonTrigger1.setSize(40,40);
        buttonTrigger1.setText("1");
        getContentPane().add(buttonTrigger1);
        buttonTrigger1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("trig 1");
            }
        });
        buttonTrigger3 = new JButton();
        buttonTrigger3.setLocation(300,70);
        buttonTrigger3.setSize(40,40);
        buttonTrigger3.setText("3");
        getContentPane().add(buttonTrigger3);
        buttonTrigger3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("trig 3");
            }
        });
        buttonTrigger5 = new JButton();
        buttonTrigger5.setLocation(300,110);
        buttonTrigger5.setSize(40,40);
        buttonTrigger5.setText("1");
        getContentPane().add(buttonTrigger5);
        buttonTrigger5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("trig 5");
            }
        });
        buttonTrigger7 = new JButton();
        buttonTrigger7.setLocation(300,150);
        buttonTrigger7.setSize(40,40);
        buttonTrigger7.setText("1");
        getContentPane().add(buttonTrigger7);
        buttonTrigger7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("trig 7");
            }
        });

        String typeSelect2_tmp[]={"Sensor1","Sensor2"};
        typeSelect2 = new JComboBox<Object>(typeSelect2_tmp);
        typeSelect2.setLocation(390,30);
        typeSelect2.setSize(80,40);
        typeSelect2.setEditable(false );
        getContentPane().add(typeSelect2);

        String typeSelect4_tmp[]={"Sensor1","Sensor2"};
        typeSelect4 = new JComboBox<Object>(typeSelect4_tmp);
        typeSelect4.setLocation(390,70);
        typeSelect4.setSize(80,40);
        typeSelect4.setEditable(false );
        getContentPane().add(typeSelect4);

        String typeSelect6_tmp[]={"Sensor1","Sensor2"};
        typeSelect6 = new JComboBox<Object>(typeSelect6_tmp);
        typeSelect6.setLocation(390,110);
        typeSelect6.setSize(80,40);
        typeSelect6.setEditable(false );
        getContentPane().add(typeSelect6);

        String typeSelect8_tmp[]={"Sensor1","Sensor2"};
        typeSelect8 = new JComboBox<Object>(typeSelect8_tmp);
        typeSelect8.setLocation(390,150);
        typeSelect8.setSize(80,40);
        typeSelect8.setEditable(false );
        getContentPane().add(typeSelect8);
        
        buttonTrigger2 = new JButton();
        buttonTrigger2.setLocation(490,30);
        buttonTrigger2.setSize(40,40);
        buttonTrigger2.setText("1");
        getContentPane().add(buttonTrigger2);
        buttonTrigger2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("trig 2");
            }
        });
        buttonTrigger4 = new JButton();
        buttonTrigger4.setLocation(490,70);
        buttonTrigger4.setSize(40,40);
        buttonTrigger4.setText("1");
        getContentPane().add(buttonTrigger4);
        buttonTrigger4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("trig 4");
            }
        });
        buttonTrigger6 = new JButton();
        buttonTrigger6.setLocation(490,110);
        buttonTrigger6.setSize(40,40);
        buttonTrigger6.setText("1");
        getContentPane().add(buttonTrigger6);
        buttonTrigger6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("trig 6");
            }
        });
        buttonTrigger8 = new JButton();
        buttonTrigger8.setLocation(490,150);
        buttonTrigger8.setSize(40,40);
        buttonTrigger8.setText("1");
        getContentPane().add(buttonTrigger8);
        buttonTrigger8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("trig 8");
            }
        });

        labelheader = new JLabel();
        labelheader.setLocation(250,0);
        labelheader.setSize(150,25);
        labelheader.setText("Enable/disable sensor");
        getContentPane().add(labelheader);

        printPower = new JButton();
        printPower.setLocation(135,418);
        printPower.setSize(100,50);
        printPower.setText("Print Power");
        printPower.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //TODO
                
            }
        });
        getContentPane().add(printPower);

        button1 = new JButton();
        button1.setLocation(376,273);
        button1.setSize(50,50);
        button1.setText("1");
        getContentPane().add(button1);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand("num 1");
            }
        });
        button2 = new JButton();
        button2.setLocation(427,273);
        button2.setSize(50,50);
        button2.setText("2");
        getContentPane().add(button2);
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendCommand("num 2");
            }});
        button3 = new JButton();
        button3.setLocation(478,273);
        button3.setSize(50,50);
        button3.setText("3");
        getContentPane().add(button3);
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendCommand("num 3");
            }});
        button4 = new JButton();
        button4.setLocation(376,325);
        button4.setSize(50,50);
        button4.setText("4");
        getContentPane().add(button4);
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendCommand("num 4");
            }});
        button7 = new JButton();
        button7.setLocation(376,377);
        button7.setSize(50,50);
        button7.setText("7");
        getContentPane().add(button7);
        button7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendCommand("num 7");
            }});
        buttonStar = new JButton();
        buttonStar.setLocation(376,429);
        buttonStar.setSize(50,50);
        buttonStar.setText("*");
        getContentPane().add(buttonStar);
        buttonStar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendCommand("num *");
            }});
        button5 = new JButton();
        button5.setLocation(427,325);
        button5.setSize(50,50);
        button5.setText("5");
        getContentPane().add(button5);
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendCommand("num 5");
            }});
        button6 = new JButton();
        button6.setLocation(478,325);
        button6.setSize(50,50);
        button6.setText("6");
        getContentPane().add(button6);
        button6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendCommand("num 6");
            }});
        button8 = new JButton();
        button8.setLocation(427,377);
        button8.setSize(50,50);
        button8.setText("8");
        getContentPane().add(button8);
        button8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendCommand("num 8");
            }});
        button9 = new JButton();
        button9.setLocation(478,377);
        button9.setSize(50,50);
        button9.setText("9");
        getContentPane().add(button9);
        button9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendCommand("num 9");
            }});
        button0 = new JButton();
        button0.setLocation(427,429);
        button0.setSize(50,50);
        button0.setText("0");
        getContentPane().add(button0);
        button0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendCommand("num 0");
            }});
        buttonPound = new JButton();
        buttonPound.setLocation(480,429);
        buttonPound.setSize(50,50);
        buttonPound.setText("#");
        getContentPane().add(buttonPound);
        buttonPound.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendCommand("num #");
            }});

        setTitle("ChronoTimer ( JFrame )");
        setSize(566,512);
        setVisible(true);
        setResizable(true);


    }
    public static void main( String args[] )
    {
        new ChronoGui();
    }
}
