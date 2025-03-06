import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class S_SlimGUI extends JFrame {
    @SuppressWarnings("unused")
    private JButton bmiButton, bmrButton, bodyFatButton, waterButton, cancelButton, calculateButton;
    private Font customFont;

    public S_SlimGUI() {
        setTitle("S-Slim");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        try {
            // โหลดฟอน+เปลี่ยนที่จุดที่ดึงไฟล์ตามที่โหลดไว้
            customFont = Font
                    .createFont(Font.TRUETYPE_FONT,
                            new File("C:/Users/Lenovo  Notebook/Desktop/S_Slim-main/src/Mali/Mali-Regular.ttf"))
                    .deriveFont(Font.PLAIN, 14);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            UIManager.put("OptionPane.messageFont", customFont);
            UIManager.put("OptionPane.buttonFont", customFont);
        } catch (Exception e) {
            e.printStackTrace();
            customFont = new Font("SansSerif", Font.PLAIN, 14);
        }
        // เปลี่ยนที่จุดที่ดึงไฟล์ตามที่โหลดไว้
        bmiButton = createButton("C:/Users/Lenovo  Notebook/Desktop/S_Slim-main/src/images/bmi.jpg", 280, 90);
        bmrButton = createButton("C:/Users/Lenovo  Notebook/Desktop/S_Slim-main/src/images/bmr.jpg", 280, 90);
        bodyFatButton = createButton("C:/Users/Lenovo  Notebook/Desktop/S_Slim-main/src/images/bodyFat.jpg", 280, 90);
        waterButton = createButton("C:/Users/Lenovo  Notebook/Desktop/S_Slim-main/src/images/waterIntake.jpg", 280, 90);

        add(bmiButton);
        add(bmrButton);
        add(bodyFatButton);
        add(waterButton);

        bmiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInputDialog(new BMICalculator(0, 0, 0, ""), true, true, false, false, true);
            }
        });

        bmrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInputDialog(new BMRCalculator(0, 0, 0, ""), true, true, true, true, true);
            }
        });

        bodyFatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInputDialog(new BodyFatCalculator(0, 0, 0, ""), true, true, true, true, true);
            }
        });

        waterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInputDialog(new WaterIntakeCalculator(0, 0, 0, ""), true, false, false, false, true);
            }
        });
    }

    private JButton createButton(String imagePath, int width, int height) {
        File file = new File(imagePath);
        ImageIcon icon = file.exists() ? new ImageIcon(file.getAbsolutePath()) : new ImageIcon(imagePath);
        Image scaledImg = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaledImg));
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }

    private void setComponentFont(JComponent component) {
        if (customFont != null) {
            component.setFont(customFont);
        }
    }

    private JPanel createLabeledComponent(String label, JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel jLabel = new JLabel(label);
        if (customFont != null) {
            jLabel.setFont(customFont);
        }
        panel.add(jLabel);
        panel.add(component);
        return panel;
    }

    /**
     * @param calculator ฟังก์ชันการคำนวณ
     * @param needWeight ตรวจสอบว่าต้องการข้อมูลน้ำหนักหรือไม่
     * @param needHeight ตรวจสอบว่าต้องการข้อมูลส่วนสูงหรือไม่
     * @param needAge    ตรวจสอบว่าต้องการข้อมูลอายุหรือไม่
     * @param needGender ตรวจสอบว่าต้องการข้อมูลเพศหรือไม่
     * @param showResult แสดงผลลัพธ์
     */
    private void showInputDialog(HealthCalculator calculator, boolean needWeight, boolean needHeight, boolean needAge,
            boolean needGender, boolean showResult) {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        JTextField weightField = new JTextField();
        JTextField heightField = new JTextField();
        JTextField ageField = new JTextField();
        JComboBox<String> genderComboBox = new JComboBox<>(new String[] { "Male", "Female" });

        weightField.setPreferredSize(new Dimension(100, 25));
        heightField.setPreferredSize(new Dimension(100, 25));
        ageField.setPreferredSize(new Dimension(100, 25));

        setComponentFont(weightField);
        setComponentFont(heightField);
        setComponentFont(ageField);
        setComponentFont(genderComboBox);

        if (needWeight)
            panel.add(createLabeledComponent("Weight (kg):", weightField));
        if (needHeight)
            panel.add(createLabeledComponent("Height (cm):", heightField));
        if (needAge)
            panel.add(createLabeledComponent("Age:", ageField));
        if (needGender)
            panel.add(createLabeledComponent("Gender:", genderComboBox));

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Details for ", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                double weight = needWeight ? Double.parseDouble(weightField.getText()) : 0;
                double height = needHeight ? Double.parseDouble(heightField.getText()) : 0;
                int age = needAge ? Integer.parseInt(ageField.getText()) : 0;
                String gender = needGender ? (String) genderComboBox.getSelectedItem() : "Male";

                calculator.setWeight(weight);
                calculator.setHeight(height);
                calculator.setAge(age);
                calculator.setGender(gender);

                double resultValue = calculator.calculate();
                String resultDescription = calculator.getResultDescription();

                String resultMessage;
                switch (resultDescription) {
                    case "BMI":
                        String bmiCategory;
                        if (resultValue < 18.5) {
                            bmiCategory = "Underweight";
                        } else if (resultValue < 24.9) {
                            bmiCategory = "Normal weight";
                        } else if (resultValue < 29.9) {
                            bmiCategory = "Overweight";
                        } else {
                            bmiCategory = "Obese";
                        }
                        resultMessage = "Your BMI is " + String.format("%.2f", resultValue) + " (" + bmiCategory + ")";
                        break;
                    case "BMR":
                        resultMessage = "Your BMR is " + String.format("%.2f ", resultValue) + "calories/day";
                        break;
                    case "Body Fat":
                        resultMessage = "Your Body Fat percentage is " + String.format("%.2f ", resultValue) + "%";
                        break;
                    case "Water Intake":
                        resultMessage = "The amount of water you should drink is " + String.format("%.2f ", resultValue)
                                + "liters/day";
                        break;
                    default:
                        resultMessage = "Result: " + String.format("%.2f", resultValue);
                }

                JOptionPane.showMessageDialog(null, resultMessage, "Result", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter numbers.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new S_SlimGUI().setVisible(true));
    }
}
