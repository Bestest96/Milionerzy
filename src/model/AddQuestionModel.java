package model;

/**
 * The {@link model.AddQuestionModel} class represents data for a part of application
 * responsible for adding questions (mainly texts for buttons).
 */
final class AddQuestionModel {
    /**
     * A text for button responsible for adding questions to a database.
     */
    private final String addToDBText;
    /**
     * A text for button responsible for returning to main menu.
     */
    private final String returnToMenuText;
    /**
     * An array of 3 texts describing difficulty of a question to be added.
     */
    private final String diff[];

    /**
     * {@link model.AddQuestionModel} class constructor. Defines text for button labels.
     */
    AddQuestionModel() {
        addToDBText = "Dodaj do bazy danych";
        returnToMenuText = "Wróć do menu";
        diff = new String[3];
        diff[0] = "Łatwe";
        diff[1] = "Średnie";
        diff[2] = "Trudne";
    }

    /**
     * A getter for a label for a button to add question.
     * @return a label for a button to add question.
     */
    String getAddToDBText() {
        return addToDBText;
    }
    /**
     * A getter for a label for a button to return to main menu.
     * @return a label for a button to return to main menu.
     */
    String getReturnToMenuText() {
        return returnToMenuText;
    }
    /**
     * A getter for difficulty labels.
     * @return an array of difficulty labels.
     */
    String[] getDiff() {
        return diff;
    }

}
