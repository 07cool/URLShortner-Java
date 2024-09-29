package projectfor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.*;
import java.util.HashMap;
import java.util.Random;

public class URLShortener extends JFrame implements ActionListener {
    private JTextField longUrlField;
    private JLabel shortUrlLabel;
    private HashMap<String, String> urlMap;
    private Random random;

    public URLShortener() {
        urlMap = new HashMap<>();
        random = new Random();

        // Frame setup
        setTitle("URL Shortener");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Components
        longUrlField = new JTextField(20);
        JButton shortenButton = new JButton("Shorten URL");
        shortenButton.addActionListener(this);
        JButton copyButton = new JButton("Copy Short URL");
        copyButton.addActionListener(this);
        shortUrlLabel = new JLabel("Short URL: ");

        // Add components to frame
        add(new JLabel("Enter Long URL:"));
        add(longUrlField);
        add(shortenButton);
        add(copyButton);
        add(shortUrlLabel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Shorten URL")) {
            String longUrl = longUrlField.getText();
            String shortUrl = shortenUrl(longUrl);
            shortUrlLabel.setText("Short URL: " + shortUrl);
        } else if (e.getActionCommand().equals("Copy Short URL")) {
            String shortUrl = shortUrlLabel.getText().replace("Short URL: ", "").trim();
            copyToClipboard(shortUrl);
        }
    }

    private String shortenUrl(String longUrl) {
        String shortCode = generateShortCode();
        urlMap.put(shortCode, longUrl);
        return "http://short.url/" + shortCode;
    }

    private String generateShortCode() {
        StringBuilder shortCode;
        do {
            shortCode = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                shortCode.append((char) ('a' + random.nextInt(26))); // Generates a random letter
            }
        } while (urlMap.containsKey(shortCode.toString())); // Ensure unique code

        return shortCode.toString();
    }

    private void copyToClipboard(String shortUrl) {
        StringSelection stringSelection = new StringSelection(shortUrl);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        JOptionPane.showMessageDialog(this, "Short URL copied to clipboard!", "Copied", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(URLShortener::new);
    }
}
