package com.nullprogram.chess.gui;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import com.nullprogram.chess.Board;
import com.nullprogram.chess.Game;
import com.nullprogram.chess.Player;

import com.nullprogram.chess.boards.StandardBoard;

import com.nullprogram.chess.ai.Minimax;

/**
 * Presents the "New Game" dialog to let the user set up a game.
 */
public class NewGame extends JDialog implements ActionListener {

    /** Version for object serialization. */
    private static final long serialVersionUID = 1L;

    /** Parent to this dialog. */
    private ChessFrame parent;

    /** The selected white player. */
    private Player white;

    /** The selected black player. */
    private Player black;

    /** The selected board. */
    private Board board;

    /** White player selector. */
    private PlayerSelector whitePanel;

    /** Black player selector. */
    private PlayerSelector blackPanel;

    /**
     * Create a new dialog to ask the user for the game configuration.
     *
     * @param owner parent to this dialog
     */
    public NewGame(final ChessFrame owner) {
        super(owner, "New game", true);
        parent = owner;
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        whitePanel = new PlayerSelector("White:", true);
        blackPanel = new PlayerSelector("Black:", false);
        add(whitePanel);
        add(blackPanel);

        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        ok.addActionListener(this);
        cancel.addActionListener(this);
        add(ok);
        add(cancel);

        pack();
    }

    /** {@inheritDoc} */
    public final void actionPerformed(final ActionEvent e) {
        if ("OK".equals(e.getActionCommand())) {
            /* Lock in selection. */
            white = createPlayer(whitePanel.getPlayer());
            black = createPlayer(blackPanel.getPlayer());
            board = new StandardBoard();
        } else {
            white = null;
            black = null;
            board = null;
        }
        setVisible(false);
        dispose();
    }

    /**
     * Create a new Player instance based on the given string.
     *
     * @param name name of type of player
     * @return player of named type
     */
    private Player createPlayer(final String name) {
        if ("human".equals(name)) {
            return parent.getPlayer();
        } else if ("computer".equals(name)) {
            return new Minimax(parent.getProgress());
        } else {
            return null;
        }
    }

    /**
     * Get the game selected/created by the user.
     *
     * @return the new game
     */
    public final Game getGame() {
        if ((white == null) || (black == null)) {
            return null;
        }
        return new Game(parent, board, white, black);
    }
}