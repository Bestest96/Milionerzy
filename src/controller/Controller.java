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
public class Controller {
    /**
     * An object representing a {@link model.Model} object of an application to be controlled.
     */
    private Model model;
    /**
     * An object representing a {@link view.View} object of an application to be controlled.
     */
    private View view;

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

        mmv.getExitButton().setOnAction((event) -> {
            exitApp();
        });

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
        for (Button b : gv.getAnswers()) {
            b.setOnAction((event) -> {
                checkAnswer(gv, b);
                event.consume();
            });

            b.setOnKeyPressed((event) -> {
                KeyCode code = event.getCode();
                if (code.equals(KeyCode.ENTER))
                    checkAnswer(gv, b);
                event.consume();
            });

        }

        gv.getLifelines()[0].setOnAction((event) -> {
            gv.getLifelines()[0].setDisable(true);
            model.lifeline5050();
            model.playSongSingle("50_50.wav");
            event.consume();
        });

        gv.getLifelines()[1].setOnAction((event) -> {
            gv.getLifelines()[1].setDisable(true);
            model.lifelineFriend();
            event.consume();
        });

        gv.getLifelines()[2].setOnAction((event) -> {
            gv.getLifelines()[2].setDisable(true);
            model.lifelineAudience();
            event.consume();
        });
    }

    private void setEndGameActions() {
        EndGameView egv = view.getEndGameView();

        egv.getReturnToMain().setOnAction((event) -> {
            view.getMainMenuView().resetState();
            model.playSongLooped("level2Loop.wav");
            view.setActiveScene(view.getMainMenuView().getScene());
        });

        egv.getRestartGame().setOnAction((event) -> {
            startGame();
        });
    }

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
                            } else {
                                model.getLooped().stop();
                                model.playSongSingle("winMillion.wav");
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
                });
            }
            taskEvent.consume();
        });
    }

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

    private void selectQuestion(String diff) {
        String question = model.getDatabaseModel().selectQuestion(diff, model.getGameModel().getUsedID());
        view.getGameView().getQuestion().setText(question);
        model.getDatabaseModel().checkConnection();
        int qid = model.getDatabaseModel().getQuestionID(question);
        String[] answers = model.getDatabaseModel().selectAnswers(qid);
        model.getGameModel().setCorrectAnswer(model.getDatabaseModel().selectCorrectAnswer(qid));
        char ans = 'A';
        for (int i = 0; i < 4; ++i)
            view.getGameView().getAnswers()[i].setText(ans++ + ": " + answers[i]);
        model.getGameModel().getUsedID().add(qid);
    }
}
