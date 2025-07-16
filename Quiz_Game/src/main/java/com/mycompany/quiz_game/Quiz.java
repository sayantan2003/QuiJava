/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.quiz_game;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.Timer;
import javax.swing.JLabel;


public class Quiz extends javax.swing.JFrame {
    
    private String name;
    private int maxQuestions ;
    private String questions[][];
    private String answer[][];
    private int score=0;
    private int timer = 15;
    private JLabel timerLabel;
    private Timer swingTimer;
    private int currentQuestion = 0;
    private boolean isQuizSubmitted = false;
    public Quiz(String name,int maxQuestions) {
        this.name = name ;
        this.maxQuestions = maxQuestions;
        questions = new String[maxQuestions][5];
        answer = new String[maxQuestions][2];
        System.out.println(maxQuestions);
        initComponents();
        submitButton.setEnabled(false);
        setUpQuestions();
        //System.out.println("ok");
        setUpTimer();
        addQuestions(currentQuestion);
        
    }
    private String getSelectedAnswer(){
        String selectedAnswer = "";
        if (option1.isSelected()) {
            selectedAnswer = option1.getText();
        } else if (option2.isSelected()) {
            selectedAnswer = option2.getText();
        } else if (option3.isSelected()) {
            selectedAnswer = option3.getText();
        } else if (option4.isSelected()) {
            selectedAnswer = option4.getText();
        }
        System.out.println("You selected: " + selectedAnswer);
        return selectedAnswer;
        
    }
    private void getScore(int count){
        if(getSelectedAnswer() != null && answer[count][1] != null && answer[count][1].equals(getSelectedAnswer())){
            score++;
        }
    }
    private void setUpTimer(){
        setLayout(null);
        timerLabel = new JLabel("Time Left :"+ timer +" Seconds");
        timerLabel.setFont(new Font("Tahoma" ,Font.BOLD,18));
        timerLabel.setForeground(Color.RED);
        timerLabel.setBounds(1000, 460, 400, 30);
        jPanel1.add(timerLabel);
        
        
        swingTimer = new Timer(1000, e -> updateTimer());
        swingTimer.start();
        
        jPanel1.revalidate(); // ✅ Ensures UI updates
        jPanel1.repaint();
  

    }
    private void updateTimer(){
        if (timer > 0) {
            timer -= 1;
            timerLabel.setText("Time Left: " + timer + " Seconds");
        } 
        else {
            swingTimer.stop(); // Stop timer when it reaches 0
            timerLabel.setText("Time's Up!");
            if(currentQuestion >= maxQuestions - 1 ){
                submitQuiz();
            }
            else{
                nextQuestion();
            }
        }

    }
    private void addQuestions(int count){
        if(count < maxQuestions){
           
            qNumber.setText(String.valueOf(count+1)+".");
            question.setText(questions[count][0]);
            option1.setText(questions[count][1]);
            option2.setText(questions[count][2]);
            option3.setText(questions[count][3]);
            option4.setText(questions[count][4]);
            System.out.println(questions[count][0]);
            timer = 15;
            if (swingTimer != null && swingTimer.isRunning()) {
                swingTimer.stop();
            }
            swingTimer = new Timer(1000, e -> updateTimer());
            swingTimer.start(); 
            buttonGroup1.clearSelection();            
        }
    }
    private void nextQuestion(){
        getScore(currentQuestion);
        currentQuestion++;
        if(currentQuestion < maxQuestions){
            addQuestions(currentQuestion);
            if(currentQuestion == maxQuestions - 1){
                nextButton.setEnabled(false);
                submitButton.setEnabled(true);    
            }
        }
        else {
            submitQuiz();
        }
    }
    private void submitQuiz() {
        if(isQuizSubmitted )
                return;
        else{
            isQuizSubmitted = true;
            getScore(currentQuestion);
            System.out.println("Score: " + score);
            Score scorePage = new Score(name,String.valueOf(maxQuestions),String.valueOf(score)); // if passing the score
            scorePage.setVisible(true);
            this.dispose(); 
        }
        
    }
    
    private void setUpQuestions(){
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_db","root","Sa.bera@2003");
            
            statement = connection.prepareStatement("SELECT *FROM question_answer ORDER BY RAND() LIMIT ?");
            statement.setInt(1,maxQuestions);
            ResultSet result = statement.executeQuery(); 
            
            int i=0;
            
            while(result.next() && i < maxQuestions){
                String ques = result.getString(2);
                String op1 = result.getString(3);
                String op2 = result.getString(4);
                String op3 = result.getString(5);
                String op4 = result.getString(6);
                
                String ans = result.getString(7);
                
                questions[i][0] = ques;
                questions[i][1] = op1;
                questions[i][2] = op2;
                questions[i][3] = op3;
                questions[i][4] = op4;
                
                answer[i][1] = ans;
                i++;
                
            }
        }catch(Exception e){
            System.out.println(e);
        }
        finally{
            try{
                if(connection != null){
                    connection.close();
                }
                if(statement != null)
                    statement.close();
            }catch(SQLException ex){
                System.out.println(ex);
            } 
        }   
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        question = new javax.swing.JLabel();
        nextButton = new javax.swing.JButton();
        submitButton = new javax.swing.JButton();
        qNumber = new javax.swing.JLabel();
        option1 = new javax.swing.JRadioButton();
        option2 = new javax.swing.JRadioButton();
        option3 = new javax.swing.JRadioButton();
        option4 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        setSize(new java.awt.Dimension(1300, 600));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/WhatsApp Image 2025-07-03 at 14.50.44_f9e652c1.jpg"))); // NOI18N

        question.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        question.setForeground(new java.awt.Color(0, 0, 0));
        question.setText("Question");

        nextButton.setBackground(new java.awt.Color(0, 51, 255));
        nextButton.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        submitButton.setBackground(new java.awt.Color(0, 51, 255));
        submitButton.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        qNumber.setBackground(new java.awt.Color(255, 255, 255));
        qNumber.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        qNumber.setForeground(new java.awt.Color(0, 0, 0));
        qNumber.setText("0");

        option1.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(option1);
        option1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        option1.setForeground(new java.awt.Color(0, 0, 0));
        option1.setText("Option1");

        option2.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(option2);
        option2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        option2.setForeground(new java.awt.Color(0, 0, 0));
        option2.setText("Option2");

        option3.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(option3);
        option3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        option3.setForeground(new java.awt.Color(0, 0, 0));
        option3.setText("Option3");

        option4.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(option4);
        option4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        option4.setForeground(new java.awt.Color(0, 0, 0));
        option4.setText("Option4");
        option4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                option4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(qNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(question, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(option4, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(option1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(option3, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(option2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(599, 599, 599)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nextButton)
                            .addComponent(submitButton))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(65, 65, 65)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(question, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qNumber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(option1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nextButton)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(option2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(option3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(submitButton)
                    .addComponent(option4))
                .addGap(0, 59, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        // TODO add your handling code here:
        if(currentQuestion  != (maxQuestions - 1)){
            nextQuestion();
        }
    }//GEN-LAST:event_nextButtonActionPerformed

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        submitQuiz();
    }//GEN-LAST:event_submitButtonActionPerformed

    private void option4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_option4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_option4ActionPerformed

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Quiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Quiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Quiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Quiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Quiz("User",3).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton nextButton;
    private javax.swing.JRadioButton option1;
    private javax.swing.JRadioButton option2;
    private javax.swing.JRadioButton option3;
    private javax.swing.JRadioButton option4;
    private javax.swing.JLabel qNumber;
    private javax.swing.JLabel question;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables
}
