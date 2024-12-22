package Checkers;

import java.util.Set;
import java.util.HashSet;

public class CheckersPiece {

	private char colour;
	private Cell position;
	
	public CheckersPiece(char c) {
		this.colour = c;
	}
	
	public char getColour() {
		return this.colour;
	}
	
	public void setColour() {
		this.colour = Character.toUpperCase(this.colour);
	}
	public void setPosition(Cell p) {
		this.position = p;
	}
	
	public Cell getPosition() {
		return this.position;
	}
	
	public Set<Cell> getAvailableMoves(Cell[][] board) {
		//TODO: Get available moves for this piece depending on the board layout, and whether this piece is a king or not
		//How to record if the move is a capture or not? Maybe make a new class 'Move' that stores this information, along with the captured piece?
		Set<Cell> availableCells = new HashSet<>();

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				int fromRow = this.position.getY();
				int fromCol = this.position.getX();
				int toRow = i;
				int toCol = j;

				int deltaX = toRow - fromRow;
				int deltaY = toCol - fromCol;
				CheckersPiece startPiece = this;
				Cell end = board[toRow][toCol];
		
				if (end.getPiece()!=null) {
					continue;
				}
		
				// Movement checks (either move one square diagonally or jump over two)
				if (!((Math.abs(deltaX) == 1 && Math.abs(deltaY) == 1) || (Math.abs(deltaX) == 2 && Math.abs(deltaY) == 2))) {
					continue;
				}

				// Player one piece (b or bk)
				if (startPiece.getColour() == App.b|| startPiece.getColour() == App.bk) {
					if (startPiece.getColour() == App.b) {
						// b can only move forward (deltaX -1 for normal, -2 for capturing)
						if (!(deltaX == -1 || deltaX == -2))
							continue;
					}
					if (Math.abs(deltaX) == 2) {
						// Check for a piece to jump over
						int midRow = fromRow + deltaX / 2;
						int midCol = fromCol + deltaY / 2;
						CheckersPiece midPiece = board[midRow][midCol].getPiece();
						if (midPiece == null) {
							continue; // No piece to jump over
						}
					}
					availableCells.add(end); // Move is valid
				}

				// Player one piece (w or wk)
				if (startPiece.getColour() == App.w || startPiece.getColour() == App.wk) {
					if (startPiece.getColour() == App.w) {
						// b can only move forward (deltaX -1 for normal, -2 for capture)
						if (!(deltaX == 1 || deltaX == 2))
							continue;
					}
					if (Math.abs(deltaX) == 2) {
						// Check for a piece to jump over
						int midRow = fromRow + deltaX / 2;
						int midCol = fromCol + deltaY / 2;
						CheckersPiece midPiece = board[midRow][midCol].getPiece();
						if (midPiece == null) {
							continue; // No piece to jump over
						}
					}
					availableCells.add(end); // Move is valid
				}

			}
		}
		return availableCells;
	}
	
	
	public void capture() {
		//capture this piece
	}
	
	public void promote() {
		//promote this piece
	}
	
	//draw the piece
	public void draw(App app) {
		app.strokeWeight(5.0f);
		if (colour == 'w') {
			app.fill(255);
			app.stroke(0);
		} else if (colour == 'b') {
			app.fill(0);
			app.stroke(255);
		}
		else if (colour == 'W') {
			app.fill(255);
			app.stroke(0);
			// drawing cross for white king
			app.rect(position.getX() * App.CELLSIZE + App.CELLSIZE / 2 - App.CELLSIZE * 0.2f, position.getY() * App.CELLSIZE + App.CELLSIZE / 2 - App.CELLSIZE * 0.4f, App.CELLSIZE * 0.4f, App.CELLSIZE * 0.8f);
            app.rect(position.getX() * App.CELLSIZE + App.CELLSIZE / 2 - App.CELLSIZE * 0.4f, position.getY() * App.CELLSIZE + App.CELLSIZE / 2 - App.CELLSIZE * 0.2f, App.CELLSIZE * 0.8f, App.CELLSIZE * 0.4f);
		}
		else if (colour == 'B') {
			app.fill(0);
			app.stroke(255);
			// drawing cross for black king
			app.rect(position.getX() * App.CELLSIZE + App.CELLSIZE / 2 - App.CELLSIZE * 0.2f, position.getY() * App.CELLSIZE + App.CELLSIZE / 2 - App.CELLSIZE * 0.4f, App.CELLSIZE * 0.4f, App.CELLSIZE * 0.8f);
            app.rect(position.getX() * App.CELLSIZE + App.CELLSIZE / 2 - App.CELLSIZE * 0.4f, position.getY() * App.CELLSIZE + App.CELLSIZE / 2 - App.CELLSIZE * 0.2f, App.CELLSIZE * 0.8f, App.CELLSIZE * 0.4f);
		}
		app.ellipse(position.getX()*App.CELLSIZE + App.CELLSIZE/2, position.getY()*App.CELLSIZE + App.CELLSIZE/2, App.CELLSIZE*0.8f, App.CELLSIZE*0.8f);
		app.noStroke();
	}
}