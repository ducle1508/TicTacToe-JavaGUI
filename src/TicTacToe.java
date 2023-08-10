import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class TicTacToe implements MouseListener {
    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    static JLabel title_label = new JLabel("Player X turn");
    JPanel button_panel = new JPanel();
    static JButton[] buttons = new JButton[9];
    JOptionPane pane = new JOptionPane();
    String[] buttonOptions = {"Play again"};
    ImageIcon logo = new ImageIcon("./images/logo.png");
    ImageIcon xLogo = new ImageIcon("./images/X-removebg.png");
    ImageIcon oLogo = new ImageIcon("./images/O-removebg.png");
    ImageIcon tieLogo = new ImageIcon("./images/Tie.png");
    JPanel content_panel = new JPanel();
    JPanel left_margin = new JPanel();
    JPanel right_margin = new JPanel();
    JPanel bottom_margin = new JPanel();
    JPanel top_margin = new JPanel();
    Color bgColor = new Color(26, 42, 50);
    static Color buttonColor = new Color(46, 71, 86);
    static Color xColor = new Color(60, 196, 191);
    static Color oColor = new Color(242, 177, 71);
    static Color lightButtonColor = Color.decode("#D3D3D3");



    Integer[][] winningCombos = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9},
            {1, 4, 7}, {2, 5, 8}, {3, 6, 9},
            {1, 5, 9}, {3, 5, 7}};

    static String currentPlayer = "X";

    TicTacToe() {
        // initiate frame

        frame.setIconImage(logo.getImage());
        frame.setTitle("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());

        // border panels
        content_panel.setLayout(new BorderLayout());
        left_margin.setPreferredSize(new Dimension(100, 100));
        left_margin.setBackground(bgColor);
        right_margin.setPreferredSize(new Dimension(100, 100));
        right_margin.setBackground(bgColor);
        top_margin.setPreferredSize(new Dimension(100, 100));
        top_margin.setBackground(bgColor);
        bottom_margin.setPreferredSize(new Dimension(100, 100));
        bottom_margin.setBackground(bgColor);

        // text panel
        title_panel.setLayout(new BorderLayout());
        title_panel.setPreferredSize(new Dimension(600, 100));

        // text message
        title_label.setBackground(bgColor);
        title_label.setForeground(Color.white);
        title_label.setFont(new Font("Montserrat",Font.TRUETYPE_FONT,30));
        title_label.setHorizontalAlignment(JLabel.CENTER);
        title_label.setOpaque(true);


        title_panel.add(title_label);

        // buttons
        Border emptyBorder = BorderFactory.createEmptyBorder();
        button_panel.setLayout(new GridLayout(3,3, 10, 10));
        button_panel.setBackground(bgColor);

        for (int i = 1; i < 10; i++) {
            buttons[i-1] = new JButton(" ");
            buttons[i-1].setBorder(emptyBorder);
            button_panel.add(buttons[i-1]);
            buttons[i-1].setFont(new Font("Montserrat",Font.BOLD,50));
            buttons[i-1].setFocusable(false);
            buttons[i-1].setBackground(buttonColor);
            buttons[i-1].addMouseListener(this);
        }

        frame.add(top_margin,BorderLayout.NORTH);
        frame.add(left_margin, BorderLayout.WEST);
        frame.add(right_margin, BorderLayout.EAST);
        frame.add(bottom_margin, BorderLayout.SOUTH);
        frame.add(content_panel);
        content_panel.add(title_panel, BorderLayout.NORTH);
        content_panel.add(button_panel, BorderLayout.CENTER);


        frame.setVisible(true);
        // x color rgb(60, 196, 191)
        // o color rgb(242, 177, 71)
        // bg color rgb(26, 42, 50)
        // button color rgb(46, 71, 86)

    }


    @Override

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {
        for (int i = 1; i < 10; i++) {
            if (e.getSource() == buttons[i-1]) {
                if (!updateXO(currentPlayer, buttons[i - 1])) {
                    title_label.setText("Pick a different spot");
                } else {
                    String gameOutcome = gameOutcome(buttons, winningCombos);
                    ImageIcon outcomeIcon = new ImageIcon();
                    if (!gameOutcome.equals("No Outcome")) {
                        switch (gameOutcome) {
                            case "Player X wins":
                                outcomeIcon = xLogo;
                                break;
                            case "Player O wins":
                                outcomeIcon = oLogo;
                                break;
                            case "Tie":
                                outcomeIcon = tieLogo;
                                break;
                            default:
                        }
                        Image outcomeImage = outcomeIcon.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it smoothly
                        ImageIcon newOutcomeImage = new ImageIcon(outcomeImage);
                        createDialog(pane, newOutcomeImage, gameOutcome, buttonOptions);
                    }
                    currentPlayer = changeTurn(currentPlayer, title_label);
                }
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
        for (int i = 1; i < 10; i++) {
            if (e.getSource() == buttons[i - 1]) {
                buttons[i - 1].setBackground(lightButtonColor);
            }
        }
    }

    public void mouseExited(MouseEvent e) {
        for (int i = 1; i < 10; i++) {
            if (e.getSource() == buttons[i - 1]) {
                buttons[i - 1].setBackground(buttonColor);
            }
        }
    }



    private static boolean updateXO(String symbol, JButton button) {
        // if symbol already exists then no add
        if (!button.getText().equals(" ")) {
            return false;
        } else {
            button.setFont(new Font("Montserrat", Font.PLAIN, 100));
            button.setText(symbol);
            switch (symbol) {
                case "X":
                    button.setForeground(xColor);
                    break;
                case "O":
                    button.setForeground(oColor);
                    break;
                default:
                    button.setForeground(buttonColor);
            }
            return true;
        }

    }
    private static String changeTurn(String currentTurn, JLabel title_label) {
        String newTurn = currentTurn.equals("X") ? "O" : "X";
        title_label.setText("Player " + newTurn + " turn");
        return newTurn;

    }

    private static boolean checkWinner(JButton[] buttons, Integer[][] winningCombos, String symbol) {
        for (Integer[] combo : winningCombos) {
            if (buttons[combo[0]-1].getText().equals(symbol)
                    && buttons[combo[1]-1].getText().equals(symbol)
                    && buttons[combo[2]-1].getText().equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    private static String gameOutcome(JButton[] buttons, Integer[][] winningCombos) {
        String result;

        if (checkWinner(buttons, winningCombos, "X")) {
            result = "Player X wins";
        } else if (checkWinner(buttons, winningCombos, "O")) {
            result = "Player O wins";
        } else if (Arrays.stream(buttons)
                .noneMatch(button -> button.getText().equals(" "))) {
            result = "Tie";
        } else {
            result = "No Outcome";
        }
        return result;
    }

    public static void createDialog(JOptionPane pane, Icon outcomeIcon, String outcomeMessage, Object[] buttonOptions) {
        JDialog dialog = pane.createDialog(null, "Announcement");
        pane.setIcon(outcomeIcon);
        pane.setMessage(outcomeMessage);
        pane.setOptions(buttonOptions);
        dialog.setVisible(true);
//        java.awt.Component parentComponent,
//        Object message,
//        String title,
//        @MagicConstant(intValues = {JOptionPane.DEFAULT_OPTION,JOptionPane.YES_NO_OPTION,JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.OK_CANCEL_OPTION})  int optionType,
//        @MagicConstant(intValues = {JOptionPane.INFORMATION_MESSAGE,JOptionPane.WARNING_MESSAGE,JOptionPane.ERROR_MESSAGE,JOptionPane.QUESTION_MESSAGE,JOptionPane.PLAIN_MESSAGE})  int messageType,
//        javax.swing.Icon icon,
//        @Nullable  Object[] options,
//        Object initialValue


        Object selectedValue = pane.getValue();
        if (selectedValue.equals("Play again")) {
            // clear buttons
            for (int i = 1; i < 10; i++) {
                buttons[i - 1].setText(" ");
            }

            // reset playerTurn
            changeTurn("O", title_label);
            // - playerTurn var
            // - title_label
        }
//        if(selectedValue == null)
//            return CLOSED_OPTION;
//        //If there is not an array of option buttons:
//        if(options == null) {
//            if(selectedValue instanceof Integer)
//                return ((Integer)selectedValue).intValue();
//            return CLOSED_OPTION;
//        }
//        //If there is an array of option buttons:
//        for(int counter = 0, maxCounter = options.length;
//            counter < maxCounter; counter++) {
//            if(options[counter].equals(selectedValue))
//                return counter;
//        }
//        return CLOSED_OPTION;
    }


}




