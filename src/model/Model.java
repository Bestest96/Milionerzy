package model;

import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import view.*;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Predicate;

/**
 * The {@link model.Model} class represents the whole data and logic of an application.
 */
public final class Model {
    /**
     * An object of the {@link view.View} class to update displayed data on.
     */
    private final View view;
    /**
     * A clip to play looped tracks.
     */
    private Clip looped;
    /**
     * A clip to play non-looped tracks.
     */
    private Clip single;
    /**
     * An object representing the data for the main menu.
     */
    private final MainMenuModel mainMenuModel;
    /**
     * An object representing the data for the database.
     */
    private final DatabaseModel databaseModel;
    /**
     * An object representing the data for adding questions.
     */
    private final AddQuestionModel addQuestionModel;
    /**
     * An object representing the data for the game.
     */
    private final GameModel gameModel;
    /**
     * An object representing the data for the end game.
     */
    private final EndGameModel endGameModel;
    /**
     * An object representing the data for the audience lifeline.
     */
    private final AudienceModel audienceModel;
    /**
     * An object representing the data for the friend lifeline.
     */
    private final FriendModel friendModel;

    /**
     * {@link model.Model} class constructor
     * @param view an object of class {@link view.View}.
     */
    public Model(View view) {
        this.view = view;
        mainMenuModel = new MainMenuModel();
        databaseModel = new DatabaseModel();
        addQuestionModel = new AddQuestionModel();
        gameModel = new GameModel();
        endGameModel = new EndGameModel();
        audienceModel = new AudienceModel();
        friendModel = new FriendModel();
        setMainMenuView();
        setAddQuestionView();
        setGameView();
        setEndGameView();
        try {
            single = AudioSystem.getClip();
            looped = AudioSystem.getClip();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        playSongLooped("level2Loop.wav");
    }

    /**
     * Plays a track in a loop.
     * @param filename a path for a track to be played.
     */
    public void playSongLooped(String filename) {
        try {
            looped = prepareClip(filename);
            this.looped.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Plays a track one time.
     * @param filename a path for a track to be played.
     */
    public void playSongSingle(String filename) {
        try {
            single = prepareClip(filename);
            this.single.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A getter for a {@link model.DatabaseModel} object containing database-related data.
     * @return a {@link model.DatabaseModel} object.
     */
    public DatabaseModel getDatabaseModel() {
        return databaseModel;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    /**
     * A getter for looped clip.
     * @return a looped clip.
     */
    public Clip getLooped() {
        return looped;
    }
    /**
     * A getter for single clip
     * @return a single clip.
     */
    public Clip getSingle() {
        return single;
    }
    /**
     * A getter for a question to be added to a database.
     * @return a question to be added to a database.
     */
    public String getQuestionToAdd() {
        AddQuestionView aqv = view.getAddQuestionView();
        return aqv.getQuestion().getText();
    }

    /**
     * A getter for answers to be added to a database.
     * @return an array of answers to be added to a database.
     */
    public String[] getAnswersToAdd() {
        AddQuestionView aqv = view.getAddQuestionView();
        String[] answer = new String[4];
        for (int i = 0; i < 4; ++i)
            answer[i] = aqv.getAnswers()[i].getText();
        return answer;
    }
    /**
     * A getter for a difficulty of a question to be added.
     * @return a difficulty of a question to be added.
     */
    public String getDifficultyToAdd() {
        AddQuestionView aqv = view.getAddQuestionView();
        String diff = null;
        for (int i = 0; i < 3; ++i)
            if (aqv.getDiff()[i].isSelected())
                diff = this.addQuestionModel.getDiff()[i].substring(0, 1);
        return diff;
    }

    /**
     * Generates and uses the lifeline 50/50 in the game.
     */
    public void lifeline5050() {
        gameModel.getLifelinesUsed()[0] = true;
        Predicate<Button> predicate = btn -> !btn.getText().substring(3).equals(gameModel.getCorrectAnswer());
        ArrayList<Button> toDisableList = new ArrayList<>();
        Arrays.stream(view.getGameView().getAnswers()).filter(predicate).forEach(toDisableList::add);
        Button[] toDisable = toDisableList.toArray(new Button[3]);
        int disable[] = new Random().ints(0, 3).distinct().limit(2).toArray();
        toDisable[disable[0]].setText("");
        toDisable[disable[0]].setDisable(true);
        toDisable[disable[1]].setText("");
        toDisable[disable[1]].setDisable(true);
    }

    /**
     * Generates and uses the friend lifieline in the game.
     * @param used5050 describes if the 50/50 lifeline was used in the same question.
     */
    public void lifelineFriend(Boolean used5050) {
        gameModel.getLifelinesUsed()[1] = true;
        if (view.getFriendView().getFriendStage().isShowing())
            view.getFriendView().getFriendStage().close();
        if (view.getAudienceView().getChartStage().isShowing())
            view.getAudienceView().getChartStage().close();
        int correctIndex = -1;
        for (int i = 0; i < 4; ++i) {
            Boolean disabled = view.getGameView().getAnswers()[i].isDisabled();
            if (!disabled && view.getGameView().getAnswers()[i].getText().substring(3).equals(gameModel.getCorrectAnswer())){
                correctIndex = i;
                break;
            }
        }
        String help;
        if (!gameModel.getLifelinesUsed()[0] || !used5050)
            help = friendModel.createFriendText(correctIndex, gameModel.getQuestionCounter(), -1);
        else {
            int otherAns = -1;
            for (int i = 0; i < 4; ++i) {
                if (!view.getGameView().getAnswers()[i].isDisabled() && i != correctIndex) {
                    otherAns = i;
                    break;
                }
            }
            help = friendModel.createFriendText(correctIndex, gameModel.getQuestionCounter(), otherAns);
        }
        view.getFriendView().setFriendHelp(help);
        view.getFriendView().display();
    }

    /**
     * Generates and uses the audience lifeline.
     * @param used5050 describes if the 50/50 lifeline was used in the same question.
     */
    public void lifelineAudience(Boolean used5050) {
        gameModel.getLifelinesUsed()[2] = true;
        if (view.getAudienceView().getChartStage().isShowing())
            view.getAudienceView().getChartStage().close();
        if (view.getFriendView().getFriendStage().isShowing())
            view.getFriendView().getFriendStage().close();
        for (int i = 0; i < 4; ++i)
            audienceModel.getPercents()[i] = 0;
        AudienceView av = new AudienceView();
        view.setAudienceView(av);
        av = view.getAudienceView();
        int correctIndex = -1;
        for (int i = 0; i < 4; ++i) {
            Boolean disabled = view.getGameView().getAnswers()[i].isDisabled();
            if (!disabled && view.getGameView().getAnswers()[i].getText().substring(3).equals(gameModel.getCorrectAnswer())){
                correctIndex = i;
                break;
            }
        }
        if (!gameModel.getLifelinesUsed()[0] || !used5050)
            audienceModel.setPercents(correctIndex, gameModel.getQuestionCounter());
        else {
            int otherAns = -1;
            for (int i = 0; i < 4; ++i) {
                if (!view.getGameView().getAnswers()[i].isDisabled() && i != correctIndex) {
                    otherAns = i;
                    break;
                }
            }
            audienceModel.setPercents(correctIndex, gameModel.getQuestionCounter(), otherAns);
        }
        for (int i = 0; i < 4; ++i)
            av.getSeries().getData().add(new XYChart.Data<>(audienceModel.getAnswers()[i], audienceModel.getPercents()[i]));
        av.getPoll().getData().add(av.getSeries());
        av.display();
        av.getChartStage().getScene().getRoot().requestFocus();
    }

    /**
     * Creates a clip that is ready to play a track.
     * @param filename a file to be loaded to a clip.
     * @return  a clip ready for playing a track.
     * @throws LineUnavailableException when clip operations fail.
     * @throws IOException when a track cannot be loaded (i.e. bad path).
     * @throws UnsupportedAudioFileException when the audio file is unsupported.
     * @throws ClassNotFoundException when class Main is not found.
     */
    private Clip prepareClip(String filename) throws LineUnavailableException, IOException, UnsupportedAudioFileException, ClassNotFoundException {
        Clip clip;
        clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(Class.forName("Main").getResource(filename));
        clip.open(inputStream);
        return clip;
    }

    /**
     * Sets the main menu view (button labels).
     */
    private void setMainMenuView() {
        MainMenuView mmv = view.getMainMenuView();
        MainMenuModel mmm = this.mainMenuModel;
        mmv.getPlayButton().setText(mmm.getPlayText());
        mmv.getAddQuestionButton().setText(mmm.getAddQText());
        mmv.getExitButton().setText((mmm.getExitText()));
    }
    /**
     * Sets the add question view (button labels).
     */
    private void setAddQuestionView() {
        AddQuestionView aqv = view.getAddQuestionView();
        AddQuestionModel aqm = this.addQuestionModel;
        aqv.getAddToDBButton().setText(aqm.getAddToDBText());
        aqv.getReturnToMainButton().setText(aqm.getReturnToMenuText());
        for (int i = 0; i < 3; ++i)
            aqv.getDiff()[i].setText(aqm.getDiff()[i]);
    }

    /**
     * Sets the game view (money labels and resign button label).
     */
    private void setGameView() {
        GameView gv = view.getGameView();
        GameModel gm = this.gameModel;
        for (int i = 0; i < 12; ++i)
            gv.getMoney()[i].setText(gm.getMoney()[i] + " zł");
        gv.getResign().setText(gm.getResign());
    }

    /**
     * Sets the end game view (button labels).
     */
    private void setEndGameView() {
        EndGameView egv = view.getEndGameView();
        EndGameModel egm = this.endGameModel;
        egv.getReturnToMain().setText(egm.getReturnToMain());
        egv.getRestartGame().setText(egm.getRestartGame());
    }


}
