package controller;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import model.DatabaseModel;
import model.Model;
import view.*;

import java.sql.SQLException;

/**
 * The {@link controller.Controller} class defines reactions to user actions like button mouse click or keyboard input.
 */
public final class Controller {
    /**
     * An object representing a {@link model.Model} object of an application to be controlled.
     */
    private final Model model;
    /**
     * An object representing a {@link view.View} object of an application to be controlled.
     */
    private final View view;

    /**
     * Controller class constructor.
     * @param model a {@link model.Model} object, which will be controlled by Controller object.
     * @param view a {@link view.View} object, which will be controlled by View object.
     */
    public Controller (Model model, View view) {
        this.view = view;
        this.model = model;
        setMainMenuActions();
        setAddQuestionActions();
        setGameActions();
        setEndGameActions();
    }

    /**
     * Defines reactions to user actions in main menu.
     */
    private void setMainMenuActions() {
        MainMenuView mmv = view.getMainMenuView();

        mmv.getPlayButton().setOnAction((event) -> {
            startGame();
            event.consume();
        });

        mmv.getPlayButton().setOnKeyPressed((event) -> {
            KeyCode code = event.getCode();
            if (code.equals(KeyCode.ENTER))
                startGame();
            else if (code.equals(KeyCode.DOWN))
                mmv.getAddQuestionButton().requestFocus();
            else if (code.equals(KeyCode.UP))
                mmv.getExitButton().requestFocus();
            event.consume();
        });

        mmv.getAddQuestionButton().setOnAction((event) -> {
            view.getAddQuestionView().resetState();
            view.setActiveScene(view.getAddQuestionView().getScene());
            event.consume();
        });

        mmv.getAddQuestionButton().setOnKeyPressed((event) -> {
            KeyCode code = event.getCode();
            if (code.equals(KeyCode.ENTER)) {
                view.getAddQuestionView().resetState();
                view.setActiveScene(view.getAddQuestionView().getScene());
            }
            else if (code.equals(KeyCode.DOWN))
                mmv.getExitButton().requestFocus();
            else if (code.equals(KeyCode.UP))
                mmv.getPlayButton().requestFocus();
            event.consume();
        });

        mmv.getExitButton().setOnAction((event) -> exitApp());

        mmv.getExitButton().setOnKeyPressed((event) -> {
            KeyCode code = event.getCode();
            if (code.equals(KeyCode.ENTER))
                exitApp();
            else if (code.equals(KeyCode.DOWN))
                mmv.getPlayButton().requestFocus();
            else if (code.equals(KeyCode.UP))
                mmv.getAddQuestionButton().requestFocus();
            event.consume();
        });

    }
    /**
     * Defines reactions to user actions in add question menu.
     */
    private void setAddQuestionActions() {
        AddQuestionView aqv = view.getAddQuestionView();

        aqv.getAddToDBButton().setOnAction((event) -> {
            DatabaseModel dm = model.getDatabaseModel();
            dm.checkConnection();
            if (dm.insertQuestion(model.getQuestionToAdd(), model.getDifficultyToAdd(), model.getAnswersToAdd()))
                new Alert(Alert.AlertType.INFORMATION, "Dodano pytanie do bazy danych!").showAndWait();
            else
                new Alert(Alert.AlertType.WARNING, "Nie udało się dodać pytania do bazy danych!").showAndWait();
            aqv.resetState();
        });

        aqv.getAddToDBButton().setOnKeyPressed((event) -> {
            KeyCode code = event.getCode();
            if (code.equals(KeyCode.ENTER)) {
                DatabaseModel dm = model.getDatabaseModel();
                dm.checkConnection();
                if (dm.insertQuestion(model.getQuestionToAdd(), model.getDifficultyToAdd(), model.getAnswersToAdd()))
                    new Alert(Alert.AlertType.INFORMATION, "Dodano pytanie do bazy danych!").showAndWait();
                else
                    new Alert(Alert.AlertType.WARNING, "Nie udało się dodać pytania do bazy danych!").showAndWait();
                aqv.resetState();
            }
            else if (code.equals(KeyCode.LEFT) || code.equals(KeyCode.RIGHT))
                aqv.getReturnToMainButton().requestFocus();
            event.consume();
        });

        aqv.getReturnToMainButton().setOnAction((event) -> {
            view.getMainMenuView().resetState();
            view.setActiveScene(view.getMainMenuView().getScene());
        });

        aqv.getReturnToMainButton().setOnKeyPressed((event) -> {
            KeyCode code = event.getCode();
            if (code.equals(KeyCode.ENTER)) {
                view.getMainMenuView().resetState();
                view.setActiveScene(view.getMainMenuView().getScene());
            }
            else if (code.equals(KeyCode.LEFT) || code.equals(KeyCode.RIGHT))
                aqv.getAddToDBButton().requestFocus();
            event.consume();
        });

        for (int i = 0; i < 3; ++i) {
            int index = i;
            aqv.getDiff()[i].setOnKeyPressed((event) -> {
                KeyCode code = event.getCode();
                if (code.equals(KeyCode.ENTER))
                    aqv.getDiff()[index].setSelected(true);
                else if (code.equals(KeyCode.UP))
                    aqv.getDiff()[(index + 2) % 3].setSelected(true);
                else if (code.equals(KeyCode.DOWN))
                    aqv.getDiff()[(index + 1) % 3].setSelected(true);
            });
        }
    }

    /**
     * Defines reactions to user actions in the game.
     */
    private void setGameActions() {
        GameView gv = this.view.getGameView();
        for (int i = 0; i < 4; ++i) {
            Button b = gv.getAnswers()[i];
            final int x = i;
            b.setOnAction((event) -> {
                checkAnswer(gv, b);
                event.consume();
            });

            b.setOnKeyPressed((event) -> {
                KeyCode code = event.getCode();
                if (code.equals(KeyCode.ENTER))
                    checkAnswer(gv, b);
                else if (code.equals(KeyCode.UP)) {
                    if (x - 2 >= 0)
                        gv.getAnswers()[x - 2].requestFocus();
                }
                else if (code.equals(KeyCode.DOWN)) {
                    if (x + 2 < 4)
                        gv.getAnswers()[x + 2].requestFocus();
                }
                else if (code.equals(KeyCode.LEFT)) {
                    if (x == 1 || x == 3)
                        gv.getAnswers()[x - 1].requestFocus();
                }
                else if (code.equals(KeyCode.RIGHT)) {
                    if (x == 1) {
                        if (!model.getGameModel().getLifelinesUsed()[0])
                            gv.getLifelines()[0].requestFocus();
                        else if (!model.getGameModel().getLifelinesUsed()[1])
                            gv.getLifelines()[1].requestFocus();
                        else if (!model.getGameModel().getLifelinesUsed()[2])
                            gv.getLifelines()[2].requestFocus();
                        else
                            gv.getResign().requestFocus();
                    }
                    else if (x == 3)
                        gv.getResign().requestFocus();
                    else {
                        if (!gv.getAnswers()[x + 1].isDisabled())
                            gv.getAnswers()[x + 1].requestFocus();
                        else if (x == 0) {
                            if (!gv.getLifelines()[0].isDisabled())
                                gv.getLifelines()[0].requestFocus();
                            else if (!gv.getLifelines()[1].isDisabled())
                                gv.getLifelines()[1].requestFocus();
                            else if (!gv.getLifelines()[2].isDisabled())
                                gv.getLifelines()[2].requestFocus();
                            else
                                gv.getResign().requestFocus();
                        }
                        else
                            gv.getResign().requestFocus();
                    }
                }
                event.consume();
            });

        }

        gv.getLifelines()[0].setOnAction((event) -> {
            gv.getLifelines()[0].setDisable(true);
            model.lifeline5050();
            model.playSongSingle("50_50.wav");
            event.consume();
        });

        gv.getLifelines()[0].setOnKeyPressed((event) -> {
            KeyCode code = event.getCode();
            if (code.equals(KeyCode.ENTER)) {
                gv.getLifelines()[0].setDisable(true);
                model.lifeline5050();
                model.playSongSingle("50_50.wav");
            }
            else if (code.equals(KeyCode.LEFT)) {
                focusFromLifelinesToAnswers(gv);
            }
            else if (code.equals(KeyCode.RIGHT)) {
                if (!gv.getLifelines()[1].isDisabled())
                    gv.getLifelines()[1].requestFocus();
                else if (!gv.getLifelines()[2].isDisabled())
                    gv.getLifelines()[2].requestFocus();
            }
            else if (code.equals(KeyCode.DOWN))
                gv.getResign().requestFocus();
            event.consume();
        });

        gv.getLifelines()[1].setOnAction((event) -> {
            gv.getLifelines()[1].setDisable(true);
            Boolean used5050 = false;
            for (Button b : gv.getAnswers())
                if (b.isDisabled()) {
                    used5050 = true;
                    break;
                }
            model.lifelineFriend(used5050);
            event.consume();
        });

        gv.getLifelines()[1].setOnKeyPressed((event) -> {
            KeyCode code = event.getCode();
            if (code.equals(KeyCode.ENTER)) {
                gv.getLifelines()[1].setDisable(true);
                Boolean used5050 = false;
                for (Button b : gv.getAnswers())
                    if (b.isDisabled()) {
                        used5050 = true;
                        break;
                    }
                model.lifelineFriend(used5050);
            }
            else if (code.equals(KeyCode.LEFT)) {
                if (!gv.getLifelines()[0].isDisabled())
                    gv.getLifelines()[0].requestFocus();
                else
                    focusFromLifelinesToAnswers(gv);
            }
            else if (code.equals(KeyCode.RIGHT))
                gv.getLifelines()[2].requestFocus();
            else if (code.equals(KeyCode.DOWN))
                gv.getResign().requestFocus();
            event.consume();
        });

        gv.getLifelines()[2].setOnAction((event) -> {
            gv.getLifelines()[2].setDisable(true);
            Boolean used5050 = false;
            for (Button b : gv.getAnswers())
                if (b.isDisabled()) {
                    used5050 = true;
                    break;
                }
            model.lifelineAudience(used5050);
            event.consume();
        });

        gv.getLifelines()[2].setOnKeyPressed((event) -> {
            KeyCode code = event.getCode();
            if (code.equals(KeyCode.ENTER)) {
                gv.getLifelines()[2].setDisable(true);
                Boolean used5050 = false;
                for (Button b : gv.getAnswers())
                    if (b.isDisabled()) {
                        used5050 = true;
                        break;
                    }
                model.lifelineAudience(used5050);
            }
            else if (code.equals(KeyCode.LEFT)) {
                if (!gv.getLifelines()[1].isDisabled())
                    gv.getLifelines()[1].requestFocus();
                else if (!gv.getLifelines()[0].isDisabled())
                    gv.getLifelines()[0].requestFocus();
                else
                    focusFromLifelinesToAnswers(gv);
            }
            else if (code.equals(KeyCode.DOWN))
                gv.getResign().requestFocus();
            event.consume();
        });

        gv.getResign().setOnAction((event) -> {
            resign(gv);
            event.consume();
        });

        gv.getResign().setOnKeyPressed((event) -> {
            KeyCode code = event.getCode();
            if (code.equals(KeyCode.ENTER))
                resign(gv);
            else if (code.equals(KeyCode.LEFT)) {
                if (!gv.getAnswers()[3].isDisabled())
                    gv.getAnswers()[3].requestFocus();
                else if (!gv.getAnswers()[2].isDisabled())
                    gv.getAnswers()[2].requestFocus();
                else
                    gv.getAnswers()[1].requestFocus();
            }
            else if (code.equals(KeyCode.UP)) {
                if (!model.getGameModel().getLifelinesUsed()[1])
                    gv.getLifelines()[1].requestFocus();
                else if (!model.getGameModel().getLifelinesUsed()[2])
                    gv.getLifelines()[2].requestFocus();
                else if (!model.getGameModel().getLifelinesUsed()[0])
                    gv.getLifelines()[0].requestFocus();
            }
            event.consume();
        });
    }

    /**
     * Defines reactions to user action in the end game view.
     */
    private void setEndGameActions() {
        EndGameView egv = view.getEndGameView();

        egv.getReturnToMain().setOnAction((event) -> returnToMainMenu());

        egv.getReturnToMain().setOnKeyPressed((event) -> {
            KeyCode code = event.getCode();
            if (code.equals(KeyCode.ENTER))
                returnToMainMenu();
            else if (code.equals(KeyCode.UP) || code.equals(KeyCode.DOWN))
                egv.getRestartGame().requestFocus();
            event.consume();
        });

        egv.getRestartGame().setOnAction((event) -> startGame());

        egv.getRestartGame().setOnKeyPressed((event) -> {
            KeyCode code = event.getCode();
            if (code.equals(KeyCode.ENTER))
                startGame();
            else if (code.equals(KeyCode.UP) || code.equals(KeyCode.DOWN))
                egv.getReturnToMain().requestFocus();
            event.consume();
        });
    }

    /**
     * A method responsible for returning to main menu.
     */
    private void returnToMainMenu() {
        view.getMainMenuView().resetState();
        model.getSingle().stop();
        model.getLooped().stop();
        model.playSongLooped("level2Loop.wav");
        view.setActiveScene(view.getMainMenuView().getScene());
    }

    /**
     * A method describing what happens when the user resigns from further play.
     * @param gv a {@link view.GameView} object at which the correct answer will be shown.
     */
    private void resign(GameView gv) {
        Task task = new Task() {
            @Override
            protected Void call() {
                try {
                    model.getLooped().stop();
                    model.playSongSingle("endGameResignation.wav");
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Thread td = new Thread(task);
        td.setDaemon(true);
        td.start();
        gv.setDisabledButtons(true, model.getGameModel().getLifelinesUsed());
        for (Button ans : view.getGameView().getAnswers())
            if (!ans.getText().equals("") && ans.getText().substring(3).equals(model.getGameModel().getCorrectAnswer())) {
                ans.setId("correctAnswer");
                break;
            }
        task.setOnSucceeded((endGameEvent) -> {
            byte qc = model.getGameModel().getQuestionCounter();
            if (qc != 0)
                view.getEndGameView().getLabel().setText("Udało Ci się wygrać " + model.getGameModel().getMoney()[qc - 1] + " zł!");
            else
                view.getEndGameView().getLabel().setText("Zrezygnowałeś na pierwszym pytaniu! No nic - spróbuj jeszcze raz!");
            view.setActiveScene(view.getEndGameView().getScene());
            endGameEvent.consume();
        });
    }

    /**
     * A method checking if the selected answer is correct.
     * @param gv a {@link view.GameView} object to set checked and/or correct answer and disable/enable buttons.
     * @param b a button that was chosen and hat to have it's look changed.
     */
    private void checkAnswer(GameView gv, Button b) {
        byte qc = model.getGameModel().getQuestionCounter();
        b.setId("selectedAnswer");
        gv.setDisabledButtons(true, model.getGameModel().getLifelinesUsed());
        Task task = new Task() {
            @Override
            protected Integer call() {
                try {
                    if (qc > 1) {
                        model.getLooped().stop();
                        model.playSongSingle("checkAnswer.wav");
                        model.playSongLooped("checkAnswerLoop.wav");
                    }
                    Thread.sleep(500 * qc + 1000);
                    if (b.getText().substring(3).equals(model.getGameModel().getCorrectAnswer()))
                        return 0;
                    else
                        return 1;
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    return 1;
                }
            }
        };
        Thread td = new Thread(task);
        td.setDaemon(true);
        td.start();

        task.setOnSucceeded((taskEvent) -> {
            if (task.getValue().equals(0)) {
                b.setId("correctAnswer");
                Task goodAnswer = new Task() {
                    @Override
                    protected Void call() {
                        try {
                            if (qc < 2) {
                                model.playSongSingle("level1Good.wav");
                                while (model.getSingle().getFramePosition() < model.getSingle().getFrameLength())
                                    Thread.sleep(100);
                                if (qc == 1) {
                                    model.getLooped().stop();
                                    model.playSongSingle("nextQuestionLevel2.wav");
                                    while (model.getSingle().getFramePosition() < model.getSingle().getFrameLength())
                                        Thread.sleep(100);
                                    model.playSongLooped("level2Loop.wav");
                                }
                            } else if (qc < 7) {
                                model.getLooped().stop();
                                model.playSongSingle("level2Good.wav");
                                while (model.getSingle().getFramePosition() < model.getSingle().getFrameLength())
                                    Thread.sleep(100);
                                if (qc != 6) {
                                    model.playSongSingle("nextQuestionLevel2.wav");
                                    while (model.getSingle().getFramePosition() < model.getSingle().getFrameLength())
                                        Thread.sleep(100);
                                    model.playSongLooped("level2Loop.wav");
                                } else {
                                    model.playSongSingle("nextQuestionLevel3.wav");
                                    while (model.getSingle().getFramePosition() < model.getSingle().getFrameLength())
                                        Thread.sleep(100);
                                    model.playSongLooped("level3Loop.wav");
                                }
                            } else if (qc < 11) {
                                model.getLooped().stop();
                                model.playSongSingle("level3Good.wav");
                                while (model.getSingle().getFramePosition() < model.getSingle().getFrameLength())
                                    Thread.sleep(100);
                                model.playSongSingle("nextQuestionLevel3.wav");
                                while (model.getSingle().getFramePosition() < model.getSingle().getFrameLength())
                                    Thread.sleep(100);
                                if (qc != 10)
                                    model.playSongLooped("level3Loop.wav");
                                else
                                    model.playSongLooped("level3MillionQuestion.wav");
                            }
                            else {
                                model.getLooped().stop();
                                model.playSongSingle("winMillion.wav");
                                Thread.sleep(2000);
                            }
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                Thread thread = new Thread(goodAnswer);
                thread.setDaemon(true);
                thread.start();

                goodAnswer.setOnSucceeded((changeQuestion) -> {
                    if (qc == 11) {
                        view.getEndGameView().getLabel().setText("Gratulacje! Udało Ci się wygrać 1000000 zł! Od dzisiaj jesteś Milionerem!");
                        view.setActiveScene(view.getEndGameView().getScene());
                        changeQuestion.consume();
                        return;
                    }
                    view.getGameView().getMoney()[qc].setId((qc != 1 && qc != 6) ? "moneyLabel" : "guaranteedMoneyLabel");
                    view.getGameView().getMoney()[qc + 1].setId("currentQuestionLabel");
                    b.setId("gameButton");
                    model.getGameModel().setQuestionCounter((byte)(qc + 1));
                    selectQuestion(qc + 1 < 2 ? "Ł" : (qc + 1 < 7 ? "Ś" : "T"));
                    gv.setDisabledButtons(false, model.getGameModel().getLifelinesUsed());
                    changeQuestion.consume();
                });
            }
            else if (task.getValue().equals(1)) {
                Task badAnswer = new Task() {
                    @Override
                    protected Void call()  {
                        try {
                            model.getLooped().stop();
                            if (qc < 2)
                                model.playSongSingle("failLevel1.wav");
                            else
                                model.playSongSingle("fail.wav");
                            while (model.getSingle().getFramePosition() < model.getSingle().getFrameLength())
                                Thread.sleep(100);
                            model.playSongSingle("endGame.wav");
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };

                Thread thread = new Thread(badAnswer);
                thread.setDaemon(true);
                thread.start();

                for (Button ans : view.getGameView().getAnswers())
                    if (!ans.getText().equals("") && ans.getText().substring(3).equals(model.getGameModel().getCorrectAnswer())) {
                        ans.setId("correctAnswer");
                        break;
                    }

                badAnswer.setOnSucceeded((endGame) -> {
                    if (qc < 2)
                        view.getEndGameView().getLabel().setText("Niestety, nie udało Ci się nic wygrać. Spróbuj ponownie!");
                    else if (qc < 7)
                        view.getEndGameView().getLabel().setText("Udało Ci się wygrać 1000 zł!");
                    else
                        view.getEndGameView().getLabel().setText("Udało Ci się wygrać 40000 zł!");
                    view.setActiveScene(view.getEndGameView().getScene());
                    endGame.consume();
                });
            }
            taskEvent.consume();
        });
    }

    /**
     * A method that starts a new game.
     */
    private void startGame() {
            Task task = new Task() {
                @Override
                protected Void call() {
                    try {
                        model.getLooped().stop();
                        model.getSingle().stop();
                        model.playSongSingle("nextQuestionLevel3.wav");
                        while (model.getSingle().getFramePosition() < model.getSingle().getFrameLength())
                            Thread.sleep(100);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            Thread td = new Thread(task);
            td.setDaemon(true);
            td.start();
            view.getMainMenuView().getAddQuestionButton().setDisable(true);
            view.getMainMenuView().getExitButton().setDisable(true);
            view.getMainMenuView().getPlayButton().setDisable(true);
            view.getEndGameView().getRestartGame().setDisable(true);
            view.getEndGameView().getReturnToMain().setDisable(true);
            task.setOnSucceeded((event) -> {
                model.playSongLooped("level1Loop.wav");
                model.getGameModel().resetState();
                view.getGameView().resetState();
                view.setActiveScene(view.getGameView().getScene());
                view.getMainMenuView().getAddQuestionButton().setDisable(false);
                view.getMainMenuView().getExitButton().setDisable(false);
                view.getMainMenuView().getPlayButton().setDisable(false);
                view.getEndGameView().getRestartGame().setDisable(false);
                view.getEndGameView().getReturnToMain().setDisable(false);
                selectQuestion("Ł");
            });
    }

    /**
     * A method that exits the app after closing the connection to the database.
     */
    private void exitApp() {
        try {
            model.getDatabaseModel().getConnection().close();
            System.exit(0);
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(-2);
        }
    }

    /**
     * A method that selects the next question for the user.
     * @param diff a difficulty of the question.
     */
    private void selectQuestion(String diff) {
        String question = model.getDatabaseModel().selectQuestion(diff, model.getGameModel().getUsedID());
        view.getGameView().getQuestion().setText(question);
        model.getDatabaseModel().checkConnection();
        int qid = model.getDatabaseModel().getQuestionID(question);
        String[] answers = model.getDatabaseModel().selectAnswers(qid);
        model.getGameModel().setCorrectAnswer(model.getDatabaseModel().selectCorrectAnswer(qid));
        char ans = 'A';
        for (int i = 0; i < 4; ++i) {
            assert answers != null;
            view.getGameView().getAnswers()[i].setText(ans++ + ": " + answers[i]);
        }
        model.getGameModel().getUsedID().add(qid);
    }

    /**
     * A method that switches focus from lifelines to answers.
     * @param gv a {@link view.GameView} object for which focus change is made.
     */
    private void focusFromLifelinesToAnswers(GameView gv) {
        if (!gv.getAnswers()[1].isDisabled())
            gv.getAnswers()[1].requestFocus();
        else if (!gv.getAnswers()[0].isDisabled())
            gv.getAnswers()[0].requestFocus();
        else
            gv.getAnswers()[3].requestFocus();
    }
}
